package org.quickbundle.third.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.statistic.RmStatisticHandler;
import org.quickbundle.util.RmSequenceSet;

public class StatisticExport extends RmStatisticHandler {
    
    public StatisticExport(List lBeans, String rowKeyField, String columnKeyField, String rowColumnKeyFieldDisplay) {
		super(lBeans, rowKeyField, columnKeyField, rowColumnKeyFieldDisplay);
	}

	public void toExportExcel (OutputStream os) throws RowsExceededException, WriteException, IOException {

        jxl.write.WritableWorkbook wbook = Workbook.createWorkbook(os);  //建立excel文件
        jxl.write.WritableSheet wsheet = wbook.createSheet("第一页", 0); //sheet名称
        WritableCellFormat cellFormatNumber = new WritableCellFormat();
        cellFormatNumber.setAlignment(Alignment.RIGHT);
        
        if(myStatisticData != null) {
            this.statisticData = myStatisticData;
        }
        if(myFormatDouble != null) {
            this.formatDouble = myFormatDouble;
        }
        double dataSum = getAllDataSum();
        //针对行处理
        Set sRowKeyTemp = getRowKeySet();
        Set sRowKey = new RmSequenceSet();
        for(int i=0; rowKeyFieldAll != null && i<rowKeyFieldAll.length; i++) {
            sRowKey.add(rowKeyFieldAll[i]);
        }
        for(Iterator itSRowKeyTemp = sRowKeyTemp.iterator(); itSRowKeyTemp.hasNext(); ) {
            String tempStr = itSRowKeyTemp.next().toString();
            if(!sRowKey.contains(tempStr)) {
                sRowKey.add(tempStr);
            }
        }
        //针对列处理
        Set sColumnKeyTemp = getColumnKeySet();
        Set sColumnKey = new RmSequenceSet();
        for(int i=0; columnKeyFieldAll != null && i<columnKeyFieldAll.length; i++) {
            sColumnKey.add(columnKeyFieldAll[i]);
        }
        for(Iterator itSColumnKeyTemp = sColumnKeyTemp.iterator(); itSColumnKeyTemp.hasNext(); ) {
            String tempStr = itSColumnKeyTemp.next().toString();
            if(!sColumnKey.contains(tempStr)) {
                sColumnKey.add(tempStr);
            }
        }
        //开始处理输出html代码
        int rowIndex = 0;
        int columnIndex = 0;
        wsheet.addCell(new Label(columnIndex ++ , rowIndex, rowColumnKeyFieldDisplay));
        for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
            String tempColumnValue = String.valueOf(itSColumnKey.next());
            wsheet.addCell(new Label(columnIndex ++, rowIndex, tempColumnValue));
        }

        wsheet.addCell(new Label(columnIndex ++, rowIndex, "合计"));
        wsheet.addCell(new Label(columnIndex ++, rowIndex, "所占份额"));
        //开始行循环
        for(Iterator itSRowKey = sRowKey.iterator(); itSRowKey.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
            String tempRowValue = String.valueOf(itSRowKey.next());
            wsheet.addCell(new Label(columnIndex ++, rowIndex, tempRowValue));
            for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
                String tempColumnValue = String.valueOf(itSColumnKey.next());
                double thisSum = getDataSum(tempRowValue, tempColumnValue);
                wsheet.addCell(new Label(columnIndex ++, rowIndex, formatDouble.formatDouble(thisSum), cellFormatNumber));
            }
            wsheet.addCell(new Label(columnIndex ++, rowIndex, formatDouble.formatDouble(getRowDataSum(tempRowValue)), cellFormatNumber));
            wsheet.addCell(new Label(columnIndex ++, rowIndex, RmStringHelper.getPercentage(getRowDataSum(tempRowValue), dataSum), cellFormatNumber));
        }
        //合计行
        rowIndex ++;
        columnIndex = 0;
        wsheet.addCell(new Label(columnIndex ++, rowIndex, "合计"));
        for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
            String tempColumnValue = String.valueOf(itSColumnKey.next());
            wsheet.addCell(new Label(columnIndex ++, rowIndex, formatDouble.formatDouble(getColumnDataSum(tempColumnValue)), cellFormatNumber));
        }
        wsheet.addCell(new Label(columnIndex ++, rowIndex, formatDouble.formatDouble(dataSum), cellFormatNumber));
        wsheet.addCell(new Label(columnIndex ++, rowIndex, ""));

        //所占份额行
        rowIndex ++;
        columnIndex = 0;
        wsheet.addCell(new Label(columnIndex ++, rowIndex, "所占份额"));
        for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
            String tempColumnValue = String.valueOf(itSColumnKey.next());
            wsheet.addCell(new Label(columnIndex ++, rowIndex, RmStringHelper.getPercentage(getColumnDataSum(tempColumnValue), dataSum), cellFormatNumber));
        }
        wsheet.addCell(new Label(columnIndex ++, rowIndex, ""));
        wsheet.addCell(new Label(columnIndex ++, rowIndex, "100%", cellFormatNumber));
        wbook.write();
        if(wbook != null) {
            wbook.close();
        }
    }
    
    public void toExportExcel(OutputStream os, String[] rowKeyFieldAll, String[] columnKeyFieldAll, IStatisticData myStatisticData, IFormatDouble myFormatDouble) throws IOException, RowsExceededException, WriteException {
        setDisplayParameters(rowKeyFieldAll, columnKeyFieldAll, myStatisticData, myFormatDouble);
        toExportExcel(os);
    }
}

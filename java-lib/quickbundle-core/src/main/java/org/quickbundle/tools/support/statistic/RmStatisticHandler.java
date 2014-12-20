/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.statistic --> RmXmlHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-11-19 19:16:49 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.tools.support.statistic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.quickbundle.util.RmSequenceMap;
import org.quickbundle.util.RmSequenceSet;


/**
 * excel统计的处理类
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmStatisticHandler {

	protected String[] rowKeyFieldAll = new String[0];
	protected String[] columnKeyFieldAll = new String[0];
    protected IStatisticData myStatisticData = null;
    protected IFormatDouble myFormatDouble = null;
    
    protected String rowColumnKeyFieldDisplay = null;

    public void setDisplayParameters(String[] rowKeyFieldAll, String[] columnKeyFieldAll, IStatisticData myStatisticData, IFormatDouble myFormatDouble) {
        this.rowKeyFieldAll = rowKeyFieldAll;
        this.columnKeyFieldAll = columnKeyFieldAll;
        this.myStatisticData = myStatisticData;
        this.myFormatDouble = myFormatDouble;
    }
    
    /**
     * mStatisticRow 表示: 存放统计数据的Map
     */
    private Map mStatisticRow;
    
    /**
     * 构造函数: 初始化mStatisticRow
     * 
     */
    private RmStatisticHandler() {
        mStatisticRow = new RmSequenceMap();
    }
    
    public IStatisticData statisticData = new IStatisticData() {
	    public double getData(Object vo) {
	        return 1;
	    }
    };
    
    public IFormatDouble formatDouble = new IFormatDouble() {
        public String formatDouble(double value) {
            if(value == 0) {
                return "";
            } else {
                return RmStringHelper.defaultFormatDouble(value, 0);
            }
        }
    };
    
    /**
     * 构造函数: 传入List和rowKeyField，columnKeyField，自动处理
     * @param lBeans
     * @param rowKeyField
     * @param columnKeyField
     */
    public RmStatisticHandler(List lBeans, String rowKeyField, String columnKeyField, String rowColumnKeyFieldDisplay) {
        this();
        this.rowColumnKeyFieldDisplay = rowColumnKeyFieldDisplay;
        for(Iterator itLBeans = lBeans.iterator(); itLBeans.hasNext(); ) {
            Object vo = itLBeans.next();
            String rowKeyFieldValue = String.valueOf(RmVoHelper.getVoFieldValue(vo, rowKeyField));
            String columnKeyFieldValue = String.valueOf(RmVoHelper.getVoFieldValue(vo, columnKeyField));
            addData(rowKeyFieldValue, columnKeyFieldValue, vo);
        }
    }

    /**
     * 功能: 添加数据
     *
     * @param rowKey
     * @param columnKey
     * @param obj
     */
    public void addData(String rowKey, String columnKey, Object obj) {
        Map mTempColumn = null;
        if(mStatisticRow.get(rowKey) == null) {
            mTempColumn  = new RmSequenceMap();
        } else {
            mTempColumn = (Map) mStatisticRow.get(rowKey);
        }
        List lObj = null;
        if(mTempColumn.get(columnKey) == null) {
            lObj = new ArrayList();
        } else {
            lObj = (List)mTempColumn.get(columnKey);
        }
        lObj.add(obj);
        mTempColumn.put(columnKey, lObj);
        mStatisticRow.put(rowKey, mTempColumn);
    }
    
    /**
     * 功能: 得到数据
     *
     * @param rowKey
     * @param columnKey
     * @return
     */
    public List getDataList(String rowKey, String columnKey) {
        if(mStatisticRow.get(rowKey) == null) {
            return new ArrayList();
        } else {
            Map mTempColumn = (Map) mStatisticRow.get(rowKey);
            if(mTempColumn.get(columnKey) == null) {
                return new ArrayList();
            } else {
                return (List)mTempColumn.get(columnKey);
            }
        }
    }
    
    /**
     * 功能: 得到行Map
     *
     * @param rowKey
     * @return
     */
    public Map getRowMap(String rowKey) {
        if(mStatisticRow.get(rowKey) == null) {
            return new RmSequenceMap();
        } else {
            return (Map)mStatisticRow.get(rowKey);
        }
    }
    
    /**
     * 功能: 得到rowKey的集合
     *
     * @return
     */
    public Set getRowKeySet() {
        return mStatisticRow.keySet();
    }
    
    /**
     * 功能: 得到columnKey的集合
     *
     * @return
     */
    public Set getColumnKeySet() {
        Set sColumnKey = new RmSequenceSet();
        for(Iterator itMStatisticRow = mStatisticRow.keySet().iterator(); itMStatisticRow.hasNext(); ) {
            String tempKey = (String) itMStatisticRow.next();
            sColumnKey.addAll(((Map)mStatisticRow.get(tempKey)).keySet());
        }
        return sColumnKey;
    }
    
    /**
     * 功能: 得到某行某列的数据个数
     *
     * @param rowKey
     * @param columnKey
     * @return
     */
    public double getDataSum(String rowKey, String columnKey) {
        double rtValue = 0;
        List lResult = getDataList(rowKey, columnKey);
        for(Iterator itLResult = lResult.iterator(); itLResult.hasNext(); ) {
            rtValue += statisticData.getData(itLResult.next());
        }
        return rtValue;
    }
    
    /**
     * 功能: 得到某行的数据总和
     *
     * @param rowKey
     * @return
     */
    public double getRowDataSum(String rowKey) {
        double rtValue = 0;
        Map mTempColumn = getRowMap(rowKey);
        for(Iterator itMTempcolumn = mTempColumn.keySet().iterator(); itMTempcolumn.hasNext(); ) {
            String colomnKey = String.valueOf(itMTempcolumn.next());
            rtValue += getDataSum(rowKey, colomnKey);
        }
        return rtValue;
    }
    
    /**
     * 功能: 得到某列的数据总和
     *
     * @param rowKey
     * @return
     */
    public double getColumnDataSum(String columnKey) {
        double rtValue = 0;
        for(Iterator itMStatisticRow = mStatisticRow.keySet().iterator(); itMStatisticRow.hasNext(); ) {
            String rowKey = String.valueOf(itMStatisticRow.next());
            rtValue += getDataSum(rowKey, columnKey);
        }
        return rtValue;
    }
    

    /**
     * 功能: 得到所有数据个数综合
     *
     * @return
     */
    public double getAllDataSum() {
        double rtValue = 0;
        for(Iterator itMStatisticRow = mStatisticRow.keySet().iterator(); itMStatisticRow.hasNext(); ) {
            String rowKey = String.valueOf(itMStatisticRow.next());
            Map tempColumnMap = (Map)mStatisticRow.get(rowKey);
            for(Iterator itTempColumnMap = tempColumnMap.keySet().iterator(); itTempColumnMap.hasNext(); ) {
                String columnKey = String.valueOf(itTempColumnMap.next());
                rtValue += getDataSum(rowKey, columnKey);
            }
        }
        return rtValue;
    }
    
    public String toHtml() {

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
        StringBuilder sb = new StringBuilder();
        //table开始
        sb.append("<table width='100%' height='100%' cellspacing='10' bgcolor='#FFFFFF'>\n");
        sb.append("<tr><td>\n");
        sb.append("	<table width='100%'  height='100%' border='0' cellspacing='1' bgcolor='#B8D5F5'>\n");
        //开始表头行
        sb.append("        <tr align='center' bgcolor='#FFFFFF'>\n");
        sb.append("          <td>" + rowColumnKeyFieldDisplay + "</td>\n");
        for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
            String tempColumnValue = String.valueOf(itSColumnKey.next());
            sb.append("          <td>" + tempColumnValue + "</td>\n");
        }

        sb.append("          <td>合计</td>\n");
        sb.append("          <td>所占份额</td>\n");
        sb.append("        </tr>\n");
        //开始行循环
        for(Iterator itSRowKey = sRowKey.iterator(); itSRowKey.hasNext(); ) {  //循环列
            String tempRowValue = String.valueOf(itSRowKey.next());
            sb.append("        <tr align='center' bgcolor='#FFFFFF'>\n");
            sb.append("          <td>" + tempRowValue + "</td>\n");
            for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
                String tempColumnValue = String.valueOf(itSColumnKey.next());
                double thisSum = getDataSum(tempRowValue, tempColumnValue);
                sb.append("          <td align=right>" + formatDouble.formatDouble(thisSum) + "</td>\n");
            }
            sb.append("          <td align=right>" + formatDouble.formatDouble(getRowDataSum(tempRowValue)) + "</td>\n");
            sb.append("          <td align=right>" + RmStringHelper.getPercentage(getRowDataSum(tempRowValue), dataSum) + "</td>\n");
        }
        //合计行
        sb.append("        <tr align='center' bgcolor='#FFFFFF'>\n");
        sb.append("          <td>合计</td>\n");
        for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
            String tempColumnValue = String.valueOf(itSColumnKey.next());
            sb.append("          <td align=right>" + formatDouble.formatDouble(getColumnDataSum(tempColumnValue)) + "</td>\n");
        }
        sb.append("          <td align=right>" + formatDouble.formatDouble(dataSum) + "</td>\n");
        sb.append("          <td></td>\n");
        sb.append("        </tr>\n");
        //所占份额行
        sb.append("        <tr align='center' bgcolor='#FFFFFF'>\n");
        sb.append("          <td>所占份额</td>\n");
        for(Iterator itSColumnKey = sColumnKey.iterator(); itSColumnKey.hasNext(); ) {  //循环列
            String tempColumnValue = String.valueOf(itSColumnKey.next());
            sb.append("          <td align=right>" + RmStringHelper.getPercentage(getColumnDataSum(tempColumnValue), dataSum) + "</td>\n");
        }

        sb.append("          <td></td>\n");
        sb.append("          <td align=right>100%</td>\n");
        sb.append("        </tr>\n");
        //table收尾
        sb.append("    </table>\n");
        sb.append("</td></tr>\n");
        sb.append("</table>\n");
        
        return sb.toString();
    }

    public String toHtml(String[] rowKeyFieldAll, String[] columnKeyFieldAll, IStatisticData myStatisticData, IFormatDouble myFormatDouble) {
        setDisplayParameters(rowKeyFieldAll, columnKeyFieldAll, myStatisticData, myFormatDouble);
        return toHtml();
    }
    
    public interface IFormatDouble {
        /**
         * 功能: 展示数据
         *
         * @param authorizeKey
         * @param targetObj
         * @return
         */
        public String formatDouble (double value);
    }
    
    public interface IStatisticData {
        /**
         * 功能: 得到要统计的有价值数值
         *
         * @param authorizeKey
         * @param targetObj
         * @return
         */
        public double getData(Object vo);
    }
    
}
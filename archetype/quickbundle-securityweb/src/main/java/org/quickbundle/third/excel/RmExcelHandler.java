/*
 * 系统名称:基于冉闵开发工具 --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.office.excel --> RmExcelHandler.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2006-11-23 19:54:16 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.third.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.dom4j.Document;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.quickbundle.tools.helper.math.RmNumberHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.util.RmSequenceMap;


/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmExcelHandler {

    private Sheet sheet;

    public RmExcelHandler(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * 功能: 从文件中获取第1个Sheet
     * 
     * @param file
     * @return
     * @throws IOException
     * @throws BiffException
     */
    public static Workbook getWorkbookFromExcelFile(File file) throws BiffException, IOException {
        InputStream is = new FileInputStream(file);
        Workbook wb = Workbook.getWorkbook(is);
        return wb;
    }

    /**
     * 功能: 从文件或目录中获取所有的sheet
     * 
     * @param file
     * @return
     */
    public static Map getSheetFromFile(File file) {
        Map mExcel = new RmSequenceMap();
        try {
            if (file.exists() && file.isDirectory()) {
                File[] childFile = file.listFiles();
                for (int i = 0; i < childFile.length; i++) {
                    mExcel.putAll(getSheetFromFile(childFile[i]));
                }
            } else if (file.exists() && file.isFile()) {
                Sheet[] sheet = getSheetFromExcelFile(file);
                if (sheet != null && sheet.length > 0) {
                    for (int j = 0; j < sheet.length; j++) {
                        String tempKey = file.getName() + "-->" + sheet[j].getName();
                        mExcel.put(tempKey, sheet[j]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mExcel;
    }

    /**
     * 功能: 从单个excel文件中获取所有的sheet
     * 
     * @param file
     * @return
     * @throws BiffException
     * @throws IOException
     */
    public static Sheet[] getSheetFromExcelFile(File file) throws BiffException, IOException {
        List validSheet = new ArrayList();
        InputStream is = new FileInputStream(file);
        Workbook wb = Workbook.getWorkbook(is);

        Sheet[] sheet = wb.getSheets();

        for (int i = 0; i < sheet.length; i++) {
        	RmLogHelper.getLogger(RmExcelHandler.class).info(file + "-->" + sheet[i].getName());
            validSheet.add(sheet[i]);
        }
        return (Sheet[]) validSheet.toArray(new Sheet[0]);
    }

    /**
     * 功能: 从doc中读取数据，初始化excel
     *
     * @param saveName
     * @param doc
     */
    public void saveAs(String saveName, Document doc) {
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new java.io.File(saveName));
            WritableSheet sh = book.createSheet(sheet.getName(), 0);
            for (int i = 0; i < sheet.getColumns(); i++) {
                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(i, j);
                    String cellValue = cell.getContents();
                    if (cellValue == null) {
                        continue;
                    }
                    //TODO 规则将来可以扩展
                    for (int k = 0; k < cellValue.indexOf("$x{");) {
                        k = cellValue.indexOf("$x{");
                        int to = cellValue.indexOf("}");
                        String xpath = cellValue.substring(k + "$x{".length(), to);
                        String targetValue = doc.valueOf(xpath);
                        cellValue = cellValue.substring(0, k) + targetValue + cellValue.substring(to + "}".length());
                    }
                    sh.addCell(new jxl.write.Label(i, j, cellValue));
                }
            }
            book.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (book != null)
                    book.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 功能:
     * 
     * @param sheet
     * @return
     * @throws IOException 
     * @throws BiffException 
     * @throws ClassNotFoundException 
     */
    public static ImportExcelVo getListDataFromSheet(File f, String className, IValidateData validate) throws Exception {
        int maxRecordSum = 10000;
        ImportExcelVo rtVo = new ImportExcelVo();
        List lData = new ArrayList();
        Workbook wb = Workbook.getWorkbook(f);
        Sheet sheet = wb.getSheets()[0];
        int tmpSum = 0;
        for(int i=3; i<sheet.getRows(); i++) {
            if(sheet.getCell(0, i).getContents().trim().length() > 0) {
                tmpSum ++;
            }
        }
        if(tmpSum > maxRecordSum + 2) {
            rtVo.setErrorMsg("您上传的Excel有" + tmpSum + "记录，请重新上传并确保最多" + maxRecordSum + "条！");
            return rtVo;
        }
        
        File errorExcel = new File(f.toString() + ".2.xls");
        WritableWorkbook wb2 = Workbook.createWorkbook(errorExcel, wb);
        WritableSheet sheet2 = wb2.getSheets()[0];
        
        Cell[] dataTypeCell = sheet.getRow(0);
        Cell[] headCell = sheet.getRow(1);

        int currentRowNumber = 3;
        int recordSum = 0;
        while (currentRowNumber < sheet.getRows()) {
            if(sheet.getCell(0, currentRowNumber).getContents().trim().length() == 0) {
                ++ currentRowNumber;
                continue;
            }
            recordSum ++;
            Cell[] thisCell = sheet.getRow(currentRowNumber);
            Object vo = Class.forName(className).newInstance();
            for (int i = 1; i < headCell.length; i++) {
                try {
                    String str = null;
                    if(thisCell[i] instanceof DateCell){
                    	DateCell dc = (DateCell) thisCell[i];
                    	str = new Timestamp(dc.getDate().getTime()).toString();
                    } else {
                    	str = thisCell[i].getContents().trim();
                    	str = str.replaceAll("[　 �]", "");
                    }
                    Object data = str;
                    Class dataClazz = null;
                    String dataType = (dataTypeCell[i]).getContents().trim();
                    if(dataType.length() > 0) {
                    	if(int.class.getName().equals(dataType.trim())) {
                            data = Integer.parseInt(str);
                            dataClazz = int.class;
                        } if(short.class.getName().equals(dataType.trim())) {
                            data = Short.parseShort(str);
                            dataClazz = short.class;
                        } if(long.class.getName().equals(dataType.trim())) {
                            data = Long.parseLong(str);
                            dataClazz = long.class;
                        } if(float.class.getName().equals(dataType.trim())) {
                            data = Float.parseFloat(str);
                            dataClazz = float.class;
                        } if(double.class.getName().equals(dataType.trim())) {
                            data = Double.parseDouble(str);
                            dataClazz = double.class;
                        } if(byte.class.getName().equals(dataType.trim())) {
                            data = Byte.parseByte(str);
                            dataClazz = byte.class;
                        } else if(Timestamp.class.getName().equals(dataType)) {
                            data = RmDateHelper.getTimestamp(str);
                        } else if(BigDecimal.class.getName().equals(dataType)) {
                            if(str.length() == 0) {
                                data = null;
                            } else {
                                data = new BigDecimal(RmNumberHelper.formatFromThousandth(str));                                
                            }
                        }
                    }
                    if(vo instanceof Map) {
                        ((Map)vo).put(headCell[i].getContents().trim(), data.toString());
                    } else {
                    	if(dataClazz == null) {
                    		RmVoHelper.setVoFieldValue(vo, headCell[i].getContents().trim(), data);          
                    	} else {
                    		RmVoHelper.setVoFieldValue(vo, headCell[i].getContents().trim(), data, dataClazz);          
                    	}
                    }
                } catch (Exception e) {
                    RmLogHelper.getLogger(RmExcelHandler.class).warn(e.toString());
                }
            }
            String errorMsg = validate.isValid(vo);
            if(errorMsg == null || errorMsg.length() == 0) {
                lData.add(vo);
                sheet2.removeRow(currentRowNumber);
                sheet2.insertRow(currentRowNumber);
            } else {
                sheet2.addCell(new Label(0, currentRowNumber, errorMsg));
            }
            ++ currentRowNumber;
        }
        try {
            wb2.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wb2.close();
			} catch (Exception e2) {
			}
		}

        rtVo.setLData(lData);
        rtVo.setErrorExcel(errorExcel);
        rtVo.setRecordSum(recordSum);
        return rtVo;
    }

    public static void main(String[] args) {
        try {
            Document doc = RmXmlHelper.parse("E:\\platform\\myProject\\physic_subject\\report\\test.xml");

            RmExcelHandler eh = new RmExcelHandler(getSheetFromExcelFile(new File(
                    "/testReport.xls"))[0]);

            eh.saveAs("E:\\platform\\myProject\\physic_subject\\report\\testReport222222.xls", doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public interface IValidateData {
        /**
         * 校验对象，合法则返回null，非法则返回错误提示信息
         * 
         * @param obj
         * @return
         */
        public String isValid(Object obj);
    }

}
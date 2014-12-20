
package org.quickbundle.third.excel;

import java.io.File;
import java.util.List;

public class ImportExcelVo {
    private List lData;
    
    private File errorExcel;
    
    private int recordSum;
    
    private String errorMsg;

    public File getErrorExcel() {
        return errorExcel;
    }

    public void setErrorExcel(File errorExcel) {
        this.errorExcel = errorExcel;
    }

    public List getLData() {
        return lData;
    }

    public void setLData(List data) {
        lData = data;
    }

    public int getRecordSum() {
        return recordSum;
    }

    public void setRecordSum(int recordSum) {
        this.recordSum = recordSum;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    
    
}

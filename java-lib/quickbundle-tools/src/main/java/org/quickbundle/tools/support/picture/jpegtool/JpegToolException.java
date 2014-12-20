package org.quickbundle.tools.support.picture.jpegtool;
/*
 * 创建日期 2006-2-15
 */
/**
 * 
 * <p>
 * Description: JpegTool 使用的异常类
 * </p>
 * 
 * @author abnerchai
 * 
 * @version 1.0
 *  
 */

public class JpegToolException extends Exception {

    private String errMsg = "";

    public JpegToolException(String errMsg) {

        this.errMsg = errMsg;

    }

    public String getMsg() {

        return "JpegToolException:" + this.errMsg;

    }

}


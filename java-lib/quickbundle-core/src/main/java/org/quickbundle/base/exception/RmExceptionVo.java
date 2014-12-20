/*
 * 系统名称:quickbundle.cn --> yidaba_sicms
 * 
 * 文件名称: org.quickbundle.base.exception --> RmExceptionVo.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2008-10-19 下午05:53:22 创建1.0.0版 (qb)
 * 
 */
package org.quickbundle.base.exception;

import org.quickbundle.base.vo.RmValueObject;

public class RmExceptionVo extends RmValueObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
    private String description;
    
    /**
     * @deprecated
     * @return
     */
    public String getDedescription() {
    	return getDescription();
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

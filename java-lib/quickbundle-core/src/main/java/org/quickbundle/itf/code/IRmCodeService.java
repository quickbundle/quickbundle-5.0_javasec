package org.quickbundle.itf.code;

import java.net.MalformedURLException;

public interface IRmCodeService {
	/**
	 * 初始化数据库的内置表
	 */
	public void executeInitTable();
	
    /**
     * 功能：从initCodeTypeData.xml中初始化编码数据
     * @throws DocumentException 
     * @throws MalformedURLException 
     */
    public void executeInitCodeTypeDataByXml();
}

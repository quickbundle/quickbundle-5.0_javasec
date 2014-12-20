package org.quickbundle.base.beans;

public class TableIdRuleVo {
	private String tableCode;
	private String tableName;
	private String idName;
	private boolean multiDb;
	private String wrapperClass;
	private String wrapperClassFormat;
	
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public boolean isMultiDb() {
		return multiDb;
	}
	public void setMultiDb(boolean multiDb) {
		this.multiDb = multiDb;
	}
	public String getWrapperClass() {
		return wrapperClass;
	}
	public void setWrapperClass(String wrapperClass) {
		this.wrapperClass = wrapperClass;
	}
	public String getWrapperClassFormat() {
		return wrapperClassFormat;
	}
	public void setWrapperClassFormat(String wrapperClassFormat) {
		this.wrapperClassFormat = wrapperClassFormat;
	}
}

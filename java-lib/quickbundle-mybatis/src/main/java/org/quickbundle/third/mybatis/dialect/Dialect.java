package org.quickbundle.third.mybatis.dialect;

public interface Dialect {
	public static enum Type{
		MYSQL,
		POSTGRESQL,
		ORACLE,
		DB2,
		SQLSERVER,
		H2,
		HSQL
	}
	
	public String getLimitString(String sql, int skipResults, int maxResults);
	
}

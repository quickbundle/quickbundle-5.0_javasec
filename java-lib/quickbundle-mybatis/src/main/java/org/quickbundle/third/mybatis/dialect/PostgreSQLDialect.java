package org.quickbundle.third.mybatis.dialect;


public class PostgreSQLDialect implements Dialect{
	public String getLimitString(String sql, int offset, int limit) {
		StringBuilder result = new StringBuilder(sql);
		result.append(sql).append(" limit ").append(offset).append(" offset ").append(limit);
		return result.toString();
	}

}
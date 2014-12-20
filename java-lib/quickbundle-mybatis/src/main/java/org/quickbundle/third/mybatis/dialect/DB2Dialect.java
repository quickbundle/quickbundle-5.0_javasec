package org.quickbundle.third.mybatis.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class DB2Dialect implements Dialect {
	
	static Pattern patternSelect = Pattern.compile("select\\s", Pattern.CASE_INSENSITIVE);
	static Pattern patternOrder = Pattern.compile("order\\s+by\\s+.*", Pattern.CASE_INSENSITIVE);

	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer result = new StringBuffer(sql.length() + 100);
		
		result.append("select * from(");
		String orderString = getOrderString(sql);
		if(orderString != null && orderString.length() > 0) {
			sql = sql.substring(0, sql.length()-orderString.length());
		}
		
		Matcher matcher = patternSelect.matcher(sql);		
		if(matcher.find()) {
			matcher.appendReplacement(result, matcher.toMatchResult().group());
			result.append(" ROW_NUMBER() OVER(").append(orderString).append(") as rownum_,");
		}
		matcher.appendTail(result);
		
		result.append(")where rownum_>"+offset+" and rownum_<="+(offset + limit));

		if (isForUpdate) {
			result.append(" for update");
		}
		
		return result.toString();
	}
	
	String getOrderString(String sql) {
		Matcher matcher = patternOrder.matcher(sql);
		if(matcher.find()) {
			return matcher.group();
		}
		return "";
	}
	
	public static void main(String[] args) {
		DB2Dialect db2d = new DB2Dialect();
		System.out.println(db2d.getLimitString("select a.aaa,a.b, a.* from aTable a", 0, 20));
		System.out.println(db2d.getLimitString("select a.aaa,a.b, a.* from aTable a order by a.orderstr desc", 0, 20));
	}
}

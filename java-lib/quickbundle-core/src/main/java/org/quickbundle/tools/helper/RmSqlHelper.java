package org.quickbundle.tools.helper;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.quickbundle.ICoreConstants;
import org.quickbundle.config.RmBaseConfig;

public class RmSqlHelper {
	/**
	 *  NAME='张三'
	 */
	public final static int TYPE_CHAR_EQUAL = 0;
	/**
	 * NAME like '%张三%'
	 */
	public final static int TYPE_CHAR_LIKE = 1;
	/**
	 * NAME like '张三%'
	 */
	public final static int TYPE_CHAR_LIKE_START = 6;
	/**
	 * AGE = 18
	 */
	public final static int TYPE_NUMBER_EQUAL = 7;
	/**
	 * AGE >= 18
	 */
	public final static int TYPE_NUMBER_GREATER_EQUAL = 2;
	/**
	 * AGE <= 18
	 */
	public final static int TYPE_NUMBER_LESS_EQUAL = 3;
	/**
	 * BIRTH >= 1990-01-01
	 */
	public final static int TYPE_DATE_GREATER_EQUAL = 4;
	/**
	 * BIRTH <= 1990-01-01
	 */
	public final static int TYPE_DATE_LESS_EQUAL = 5;
	/**
	 * 自定义类型 {int type(类型) [, String prefixValue(指定的查询值前缀), String affixValue(指定的查询值后缀)]}
	 */
	public final static int TYPE_CUSTOM = 99;
	
	/**
	 * escape转义sql输入值，防止sql注入
	 * 
	 * @param inputValue 待转移的输入值
	 * @param type 类型
	 * @return 转义后的sql值
	 */
	public static String escapeSqlValue(Object inputValue, int type) {
		if(inputValue == null) {
			return null;
		}
		String value = inputValue.toString();
		switch (type) {
		//字符串和日期：转义'，保证输入值在''中间，就是安全的
		case TYPE_CHAR_EQUAL: case TYPE_CHAR_LIKE: case TYPE_CHAR_LIKE_START: case TYPE_DATE_GREATER_EQUAL: case TYPE_DATE_LESS_EQUAL :
			value = value.replaceAll("(['])", "'$1");
			break;
		//数字：去掉空白类符号，就是安全的
		case TYPE_NUMBER_EQUAL: case TYPE_NUMBER_GREATER_EQUAL: case TYPE_NUMBER_LESS_EQUAL:
			//空白符忽略
			value = value.replaceAll("(\\s+)", "");
			//疑似函数的一律忽略
			value = value.replaceAll("([\\w_]+\\(.*\\))", "");
			break;
		default:
			break;
		}
		return value;
	}
	
	/**
	 * 构建SQL查询条件
	 * 
	 * @param columnName COLUMN的名称
	 * @param value 查询值
	 * @param type 数据库
	 * @param args {int type(类型) [, String prefixValue(指定的查询值前缀), String affixValue(指定的查询值后缀)]}
	 * @return
	 */
	public static String buildQueryStr(String columnName, Object inputValue, Object... args) {
		int type = TYPE_CHAR_EQUAL;
		if(args.length > 0) {
			type = (Integer)args[0];
		}
		String value = escapeSqlValue(inputValue, type);
		if(value != null && value.toString().length() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(columnName);
			sb.append(" ");
			switch (type) {
			case TYPE_CHAR_EQUAL:
				sb.append("= '");
				sb.append(value);
				sb.append("'");
				break;
			case TYPE_CHAR_LIKE: case TYPE_CHAR_LIKE_START:
				sb.append("like '");
				if(type == TYPE_CHAR_LIKE) { //LIKE开头加%，而LIKE_START开头不加%
					sb.append("%");
				}
				//如果value中含有%或_，对其转义
				if(value.indexOf("%") > -1 || value.indexOf("_") > -1) {
					sb.append(value.replaceAll("([%_/])", "/$1"));
					sb.append("%'");
					sb.append(" ESCAPE '/'");
				} else {
					sb.append(value);
					sb.append("%'");
				}
				break;
			case TYPE_NUMBER_EQUAL: 
				sb.append("= ");
				sb.append(value);
				break;
			case TYPE_NUMBER_GREATER_EQUAL:
				sb.append(">= ");
				sb.append(value);
				break;
			case TYPE_NUMBER_LESS_EQUAL:
				sb.append("<= ");
				sb.append(value);
				break;
			case TYPE_DATE_GREATER_EQUAL:
				String dateStr1 = getSqlDateStr(value.toString());
				if(dateStr1.length() == 0) {
					return "";
				}
				sb.append(">= ");
				sb.append(dateStr1);
				break;
			case TYPE_DATE_LESS_EQUAL:
				if(value.toString().length() == 10) {
					value =  value.toString() + " 23:59:59";
				}
				String dateStr2 = getSqlDateStr(value.toString());
				if(dateStr2.length() == 0) {
					return "";
				}
				sb.append("<= ");
				sb.append(dateStr2);
				break;
			case TYPE_CUSTOM:
				sb.append(args[1]);
				sb.append(value);
				sb.append(args[2]);
				break;
			default:
				break;
			}
			return sb.toString();
		} else {
			return "";			
		}
	}
	
	/**
	 * 获得SQL中日期的查询字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String getSqlDateStr(Object value) {
		if(value == null || value.toString().trim().length() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		if(ICoreConstants.DatabaseProductType.ORACLE.getDatabaseProductName().equalsIgnoreCase(RmBaseConfig.getSingleton().getDatabaseProductName())) {
			String valueStr = value.toString().trim();
			sb.append("to_date('");
			switch (valueStr.length()) {
			case 4: //2010
				sb.append(valueStr);
				sb.append("', 'YYYY')");
				break;
			case 7: //2010-01
				sb.append(valueStr);
				sb.append("', 'YYYY-MM')");
				break;
			case 10: //2010-01-01
				sb.append(valueStr);
				sb.append("', 'YYYY-MM-DD')");
				break;
			case 19: //2010-01-01 01:01:01
				sb.append(valueStr);
				sb.append("', 'YYYY-MM-DD HH24:MI:SS')");
				break;
			case 23: //2010-01-01 01:01:01 001
				sb.append(valueStr.substring(0, 19));
				sb.append("', 'YYYY-MM-DD HH24:MI:SS')");
				break;
			default:
				return "";
			}
		} else {
			sb.append("'");
			sb.append(value.toString().trim());
			sb.append("'");
		}
		return sb.toString();
	}
	
	/**
	 * 构建完整SQL查询片段，不带where
	 * 
	 * @param aQueryStr 类似new String[]{"NAME like '%张三%'"}, {"AGE=18"} }
	 * @return
	 */
	public static String appendQueryStr(String[] aQueryStr) {
		StringBuilder sb = new StringBuilder();
		for (String queryStr : aQueryStr) {
			if(queryStr == null || queryStr.trim().length() == 0) {
				continue;
			}
			if (sb.length() == 0) {
				// 第一个条件不加and
			} else {
				sb.append(" and");
			}
			sb.append(" ");
			sb.append(queryStr);
		}
		return sb.toString();
	}
	
	/**
	 * 从.sql文件中提取sql语句
	 * 
	 * @param sqlFile
	 * @return
	 */
	public static String[] loadSql(String sqlFile) {
		String str =  RmStringHelper.readStringFromFile(new File(sqlFile), RmBaseConfig.getSingleton().getDefaultEncode());
		str = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL).matcher(str).replaceAll("");
		str = Pattern.compile("^\\s*(#|\\-\\-).*", Pattern.MULTILINE).matcher(str).replaceAll("");
		String[] aSql = Pattern.compile(";|(^\\s*/\\s*$)|(\\n\\s*go(\\s*\\n|$))", Pattern.MULTILINE|Pattern.CASE_INSENSITIVE).split(str);
		List<String> lSql = new ArrayList<String>();
		for (int i = 0; i < aSql.length; i++) {
			if(aSql[i].trim().length() > 0) {
				lSql.add(Pattern.compile("^\\s*(.*?)\\s*$", Pattern.DOTALL|Pattern.MULTILINE).matcher(aSql[i]).replaceAll("$1"));
			}
		}
		return lSql.toArray(new String[0]);
	}
	

	public static String getSqlPage4Mysql(String strsql, int startIndex, int size) {
		return strsql + " limit " + (startIndex - 1) + "," + size;
	}

	public static String getSqlPage4Oracle(String strsql, int startIndex, int size) {
		return "select * from (select rownum as rmrn, a.* from(" + strsql + ") a where rownum<=" + (startIndex + size - 1) + ")where rmrn >=" + startIndex;
	}
	
	/**
	 *
153ms-176ms
select * from(select row_number() over(order by uid asc) as rownumber, * from moa_user ) as tb where rownumber between 100000 and 100200

156ms-210ms
select top 200 * from(select row_number() over(order by uid asc) as rownumber,* from moa_user ) as tb where rownumber>100000

135ms
select top 200 * FROM moa_user WHERE (uid > (SELECT MAX(uid) FROM (SELECT TOP 100000 uid FROM moa_user ORDER BY uid) AS temp_moa_user)) ORDER BY uid

270ms-290ms
select top 200 * from moa_user a  where uid  not in(select top 100000  uid  from moa_user  b order by uid)

950ms
select * from ( select top 200 * from ( select TOP 100000 * from moa_user order by uid) as amoaUser ORDER BY uid DESC ) as bmoaUser ORDER BY uid ASC
	 * 
	 * @param strsql
	 * @param startIndex
	 * @param size
	 * @return
	 */
	static String getSqlPage4Sqlserver(String strsql, int startIndex, int size) {
		return null;
	}

	/**
	 * 得到数据库常用函数
	 * @param func 函数名枚举值
	 * @param databaseProductName 数据库名称
	 * @param args 可选的参数列表
	 * @return
	 */
	public static String getFunction(Function func, String databaseProductName, Object... args) {
		if(ICoreConstants.DatabaseProductType.MYSQL.getDatabaseProductName().equals(databaseProductName)) {
			return getFunctionMysql(func, args);
		} else if(ICoreConstants.DatabaseProductType.ORACLE.getDatabaseProductName().equals(databaseProductName)) {
			return getFunctionOracle(func, args);
		} else if(ICoreConstants.DatabaseProductType.SQLSERVER.getDatabaseProductName().equals(databaseProductName)) {
			return getFunctionSqlserver(func, args);
		}
		return null;
	}
	/**
	 * 常用函数名枚举
	 */
	public enum Function {
		TO_NUMBER,
		TO_CHAR,
		SYSDATE,
		LENGTH,
		SUBSTR,
		NVL,
		CONCAT,
		WM_CONCAT
	}
	static String getFunctionOracle(Function func, Object... args) {
		StringBuilder result = new StringBuilder();
		if(Function.TO_NUMBER.name().equals(func.name())) {
			result.append("to_number(");
			result.append(args[0]);
			result.append(")");
			return result.toString();
		} else if(Function.TO_CHAR.name().equals(func.name())) {
			result.append("to_char(");
			result.append(args[0]);
			result.append(")");
			return result.toString();
		} else if(Function.SYSDATE.name().equals(func.name())) {
			return "sysdate";
		} else if(Function.LENGTH.name().equals(func.name())) {
			return "length";
		} else if(Function.SUBSTR.name().equals(func.name())) {
			return "substr";
		} else if(Function.NVL.name().equals(func.name())) {
			return "nvl";
		} else if(Function.CONCAT.name().equals(func.name())) {
			for(int i=0; args!=null && i<args.length; i++) {
				if(i > 0) {
					result.append("||");
				}
				result.append(args[i]);
			}
			return result.toString();
		} else if(Function.WM_CONCAT.name().equals(func.name())) {
			return "wm_concat";
		} 
		return null;
	}
	static String getFunctionMysql(Function func, Object... args) {
		StringBuilder result = new StringBuilder();
		if(Function.TO_NUMBER.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as signed integer)");
			return result.toString();
		} else if(Function.TO_CHAR.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as char)");
			return result.toString();
		} else if(Function.SYSDATE.name().equals(func.name())) {
			return "sysdate()";
		} else if(Function.LENGTH.name().equals(func.name())) {
			return "length";
		} else if(Function.SUBSTR.name().equals(func.name())) {
			return "substring";
		} else if(Function.NVL.name().equals(func.name())) {
			return "ifnull";
		} else if(Function.CONCAT.name().equals(func.name())) {
			result.append("concat(");
			for(int i=0; args!=null && i<args.length; i++) {
				if(i > 0) {
					result.append(",");
				}
				result.append(args[i]);
			}
			result.append(")");
			return result.toString();
		} else if(Function.WM_CONCAT.name().equals(func.name())) {
			return "group_concat";
		} 
		return null;
	}
	static String getFunctionSqlserver(Function func, Object... args) {
		StringBuilder result = new StringBuilder();
		if(Function.TO_NUMBER.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as decimal(30,2))");
			return result.toString();
		} else if(Function.TO_CHAR.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as char)");
			return result.toString();
		} else if(Function.SYSDATE.name().equals(func.name())) {
			return "getdate()";
		} else if(Function.LENGTH.name().equals(func.name())) {
			return "len";
		} else if(Function.SUBSTR.name().equals(func.name())) {
			return "substring";
		} else if(Function.NVL.name().equals(func.name())) {
			return "isnull";
		} else if(Function.CONCAT.name().equals(func.name())) {
			for(int i=0; args!=null && i<args.length; i++) {
				if(i > 0) {
					result.append("+");
				}
				result.append(args[i]);
			}
			return result.toString();
		} else if(Function.WM_CONCAT.name().equals(func.name())) {
			return "group_concat";
		} 
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static<T> List<T[]> splitPagingArray(T[] array, int maxSqlInCount) {
		if(array == null) {
			return null;
		}
		List<T[]> result = new ArrayList<T[]>();
		int position = 0;
		while(position < array.length) {
			int end = position + maxSqlInCount;
			if(end > array.length) {
				end = array.length;
			}
			T[] split = copyOfRange(array, position, end, (Class<T[]>)array.getClass());
			result.add(split);
			position += maxSqlInCount;
		}
		
		return result;
	}
	
    @SuppressWarnings("unchecked")
	public static <T,U> T[] copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, from, copy, 0,
                         Math.min(original.length - from, newLength));
        return copy;
    }
	
	public static void main(String[] args) {
		List<String> lvo = new ArrayList<String>();
		for(int i=1; i<=1000; i++) {
			lvo.add(i + "");
		}
		System.out.println(lvo);
		List<String[]> result = splitPagingArray(lvo.toArray(new String[0]), 100);
		for(String[] array : result) {
			System.out.println(Arrays.deepToString(array));
		}
	}
}
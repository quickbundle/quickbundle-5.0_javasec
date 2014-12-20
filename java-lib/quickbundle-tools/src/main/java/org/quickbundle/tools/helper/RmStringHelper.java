package org.quickbundle.tools.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Map;

import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.support.cn2spell.Cn2Spell;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.unicode.UnicodeReader;

/**
 * 功能: 帮助实现一些通用的字符串处理
 * @version 1.0.0
 */
public class RmStringHelper {

	/**
	 * 将Object[]中的对象的字符串，以逗号分割后拼成一个字符串,不带有单引号
	 * 
     * @param strArray 输入字符串数组
	 * @return String
	 */
    public static<T> String parseToSQLString(T[] strArray) {
        if (strArray == null || strArray.length == 0) {
			return "-1"; //为了让长度为0的数组返回的sql不报错
        }
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArray.length - 1; i++) {
        	if(strArray[i] != null) {
            	sb.append(strArray[i]);
            	sb.append(",");
        	}
		}
        if(strArray[strArray.length - 1] != null) {
        	sb.append(strArray[strArray.length - 1]);        	
        }
        if(sb.toString().trim().length() == 0) {
        	return "-1";
        }
        return sb.toString();
	}

	/**
	 * 将Object[]中的对象的字符串，以以逗号分割后拼成一个字符串,带有单引号''
	 * 
     * @param strArray 输入字符串数组
	 * @return String
	 */
	public static<T> String parseToSQLStringApos(T[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "'-1'"; //为了让长度为0的数组返回的sql不报错
		}
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArray.length - 1; i++) {
        	if(strArray[i] != null) {
            	sb.append("'");
            	sb.append(strArray[i].toString());
            	sb.append("',");
        	}
		}
        if(strArray[strArray.length - 1] != null) {
        	sb.append("'");
        	sb.append(strArray[strArray.length - 1].toString());
        	sb.append("'");
        }
        if(sb.toString().trim().length() == 0) {
        	return "-1";
        }
        return sb.toString();
	}


	/**
	 * 将String[]中字符串以","分割后拼成一个字符串
	 * 
     * @param strArray-->输入字符串数组
	 * @return String
	 */
	public static<T> String parseToString(T[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "";
		} else if (strArray.length == 1) {
			return String.valueOf(strArray[0]);
		}

		return parseToSQLString(strArray);
	}
	
	/**
	 * 将String[]中字符串以","分割后拼成一个字符串, 带有单引号''
	 * 
	 * @param strArray-->输入字符串数组
	 * @return String
	 */
	public static<T> String parseToStringApos(T[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "";
		} else if (strArray.length == 1) {
			return String.valueOf(strArray[0]);
		}
		
		return parseToSQLStringApos(strArray);
	}
	
	/**
	 * 功能: 把"123,234,567"转为new String[]{"123", "234", "567"}
	 * 
	 * @param str
	 * @return
	 */
	public static String[] parseToArray(String str) {
		return parseToArray(str, ",");
	}
	
	/**
	 * 功能: 把"123,234,567"转为new String[]{"123", "234", "567"}
	 * 
	 * @param str
	 * @return
	 */
	public static Long[] parseToLongArray(String str) {
		return parseToLongArray(str, ",");
	}

	/**
	 * 功能: 把"123,234,567"转为new String[]{"123", "234", "567"}
	 * 
	 * @param str
	 * @param splitKey
	 * @return
	 */
	public static String[] parseToArray(String str, String splitKey) {
		String[] result = null;
		if (str != null && str.length() > 0) {
			result = str.split(splitKey, -1);
		}
		if (result == null) {
			result = new String[0];
		}
		return result;
	}
	

	/**
	 * 字符串转成数组，并过滤空值
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] parseToArrayIgnoreEmpty(String strs, String splitKey) {
		if (RmStringHelper.checkEmpty(strs)) {
			return null;
		}
		String[] str1s = strs.split(splitKey);
		StringBuilder strAlls = new StringBuilder();
		for (int i = 0; i < str1s.length; i++) {
			if (RmStringHelper.checkNotEmpty(str1s[i])) {
				strAlls.append(str1s[i]);
				strAlls.append(",");
			}
		}
		if (strAlls.length() > 1) {
			return strAlls.substring(0, strAlls.length() - 1).split(",");
		} else {
			return null;
		}
	}
	
	/**
	 * 功能: 把"123,234,567"转为new Long[]{"123", "234", "567"}
	 * 
	 * @param str
	 * @param splitKey
	 * @return
	 */
	public static Long[] parseToLongArray(String str, String splitKey) {
		Long[] result = null;
		if (str != null && str.length() > 0) {
			String[] strArray = str.split(splitKey, -1);
			result = new Long[strArray.length];
			for(int i=0; i<strArray.length; i++) {
				result[i] = new Long(strArray[i]);
			}
		}
		if (result == null) {
			result = new Long[0];
		}
		return result;
	}

	/**
	 * 功能: 把指定字符串original 从encode1 转化到encode2
	 * 
	 * @param original
	 * @param encode1
	 * @param encode2
	 * @return
	 */
	public static String encode2Encode(String original, String encode1,
			String encode2) {
		if (original != null) {
			try {
				return new String(original.getBytes(encode1), encode2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 把指定字符串strSource 中的strFrom 全部替换为strTo,不是正则表达式
	 * 
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @return
	 */
    public static String replaceAll(String strSource, String strFrom, String strTo) {
    	if(strSource == null) {
    		return null;
    	}
    	if(strFrom == null || strFrom.length() == 0 || strTo == null) {
    		return strSource;
    	}
		StringBuilder sbDest = new StringBuilder();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) > -1) {
			sbDest.append(strSource.substring(0, intPos));
			sbDest.append(strTo);
			strSource = strSource.substring(intPos + strFrom.length());
		}
		sbDest.append(strSource);
		return sbDest.toString();
	}

	/**
	 * 把str中的第1个sequence1替换为sequence2
	 * 
	 * @param str
	 * @param sequence1	the old character sequence
	 * @param sequence2	the new character sequence
	 * @return
	 */
	public static String replaceFirst(String str, CharSequence sequence1, CharSequence sequence2) {
		if (sequence2 == null)
			throw new NullPointerException();
		int sequence1Length = sequence1.length();
		if (sequence1Length == 0) {
			StringBuilder result = new StringBuilder(str.length() + sequence2.length());
			result.append(sequence2);
			result.append(str);
			return result.toString();
		}
		StringBuilder result = new StringBuilder();
		char first = sequence1.charAt(0);
		int start = 0, copyStart = 0, firstIndex;
		while (start < str.length()) {
			if ((firstIndex = str.indexOf(first, start)) == -1)
				break;
			boolean found = true;
			if (sequence1.length() > 1) {
				if (firstIndex + sequence1Length > str.length())
					break;
				for (int i=1; i<sequence1Length; i++) {
					if (str.charAt(firstIndex + i) != sequence1.charAt(i)) {
						found = false;
						break;
					}
				}
			}
			if (found) {
				result.append(str.substring(copyStart, firstIndex));
				result.append(sequence2);
				copyStart = start = firstIndex + sequence1Length;
				break; //只发现一次就退出
			} else {
				start = firstIndex + 1;
			}
		}
		if (result.length() == 0 && copyStart == 0)
			return str;
		result.append(str.substring(copyStart));
		return result.toString();
	}

	/**
	 * 功能: 过滤Html页面中的敏感字符,用于在script脚本中显示
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToScript(Object obj) {
		return replaceStringToScript(obj == null ? "" : obj.toString());
	}

	/**
	 * 功能: 过滤Html页面中的敏感字符,用于在script脚本中显示
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToScript(String value) {
		return replaceStringByRule(value, new String[][] { 
				{ "'", "\\'" },{ "\"", "\\\"" }, { "\\", "\\\\" }, { "\r", "\\r" },
				{ "\n", "\\n" }, { "\t", "\\t" }, { "\f", "\\f" }, { "\b", "\\b" } 
			});
	}
	
    /**
     * 对象
     * 
     * @param tempValue
     * @return
     */
    public static String parseToJsValue(Object tempValue) {
    	if(tempValue == null) {
    		return null;
    	}
    	StringBuilder sb = new StringBuilder();
        if (tempValue instanceof String || tempValue instanceof Integer || tempValue instanceof Long) { //如果是String、int、 long单值
        	sb.append("\"");
        	sb.append(replaceStringToScript(tempValue.toString())); //从数据库中取出来以后需要转换1次
        	sb.append("\"");
        } else if (tempValue instanceof BigDecimal) { //如果是数字，直接注入
        	sb.append("\"");
            BigDecimal tmpB = new BigDecimal(tempValue.toString()).setScale(RmBaseConfig.getSingleton().getDefaultNumberScale(), BigDecimal.ROUND_HALF_UP);
            sb.append(replaceStringToScript(tmpB.toString()));
            sb.append("\"");
        } else if (tempValue instanceof String[] || tempValue instanceof int[] || tempValue instanceof long[]) { //如果是多值，放入数组
        	sb.append("[");
            String[] myArray = (String[]) tempValue;
            for (int i = 0; i < myArray.length; i++) {
                if (i > 0) {
                	sb.append(", ");
                }
                sb.append("\"");
                sb.append(replaceStringToScript(myArray[i]));
                sb.append("\"");
            }
            sb.append("]");
        } else if (tempValue instanceof Timestamp) { //如果是时间戳
        	sb.append("\"");
        	String str = tempValue.toString().substring(0,19);
        	if(" 00:00:00".equals(str.substring(10))) {
        		sb.append(replaceStringToScript(str.substring(0, 10)));
        	} else {
        		sb.append(replaceStringToScript(str));
        	}
        	sb.append("\"");
        }  else if (tempValue instanceof Date) { //如果是日期戳
        	sb.append("\"");
        	sb.append(replaceStringToScript(tempValue.toString().substring(0,10)));
        	sb.append("\"");
        } else if(tempValue instanceof Map || tempValue instanceof Collection) { //跳过Map
            return null;
        } else {
            RmLogHelper.warn(RmStringHelper.class, "从Object转化为js，遇到了未知java类型:" + tempValue);                    
            return null;
        }
        return sb.toString();
    }

	/**
	 * 过滤Html页面中的敏感字符
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToHtml(Object obj) {
		return replaceStringToHtml(obj == null ? "" : obj.toString());
	}

	/**
	 * 过滤Html页面中的敏感字符
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToHtml(String value) {
		return replaceStringByRule(value, new String[][] { { "<", "&lt;" },
				{ ">", "&gt;" }, { "&", "&amp;" }, { "\"", "&quot;" },
				{ "'", "&#39;" }, { "\n", "<BR>" }, { "\r", "<BR>" } });
	}

	/**
	 * 把<替换成&lt;，应对编辑html代码
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToEditHtml(String value) {
		if (value == null) {
			value = "";
		}
		return replaceStringByRule(value, new String[][] { { "<", "&lt;" } });
	}

	/**
	 * 过滤Html页面中的敏感字符，接受过滤的字符列表和转化后的值
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringByRule(String value, String[][] aString) {
		if (value == null) {
			return ("");
		}
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);

		for (int i = 0; i < content.length; i++) {
			boolean isTransct = false;
			for (int j = 0; j < aString.length; j++) {
				if (String.valueOf(content[i]).equals(aString[j][0])) {
					result.append(aString[j][1]);
					isTransct = true;
					break;
				}
			}
			if (!isTransct) {
				result.append(content[i]);
			}
		}
		return result.toString();
	}

	/**
	 * 显示数据前过滤掉null
	 * 
	 * @param myString
	 * @return
	 */
	public static String prt(String myString) {
		if (myString != null) {
			return myString;
		} else {
			return "";
		}
	}

	public static String prt(Object obj) {
		if (obj != null) {
			return prt(obj.toString());
		} else {
			return "";
		}
	}

	/**
	 * 显示数据前过滤掉null，截取一定位数
	 * 
	 * @param myString
	 * @param index 最大显示的长度
	 * @return
	 */
	public static String prt(String myString, int index) {
		if (myString != null) {
			if (myString.length() >= index) {
				return myString.substring(0, index);
			} else {
				return myString;
			}
		} else {
			return "";
		}
	}

	public static String prt(Object obj, int index) {
		if (obj != null) {
			return prt(obj.toString(), index);
		} else {
			return "";
		}
	}

	/**
	 * 显示数据前过滤掉null，截取一定位数，并加上表示，如省略号
	 * 
	 * @param myString
	 * @param index
	 *            最大显示的长度
	 * @return
	 */
	public static String prt(String myString, int index, String accessional) {
		int accessionalLength = 0;
		if (index < 0) {
			return myString;
		}
		if (accessional == null || "".equals(accessional)) {
			accessional = "...";
		}
		accessionalLength = accessional.length();
		if (myString != null) {
			if (index <= accessionalLength) {
				return myString.substring(0, index);
			} else if (myString.length() >= index - accessionalLength) {
				return myString.substring(0, index - accessionalLength)
						+ accessional;
			} else {
				return myString;
			}
		} else {
			return "";
		}
	}

	public static String prt(Object obj, int index, String accessional) {
		if (obj != null)
			return prt(obj.toString(), index, accessional);
		else {
			return "";
		}
	}

	/**
	 * 判断一个数组是否包含一个字符串
	 * 
	 * @param arrayString
	 * @param str
	 * @return
	 */
	public static boolean arrayContainString(String[] arrayString, String str) {
		if (arrayString == null || arrayString.length == 0) {
			return false;
		}
		for (int i = 0; i < arrayString.length; i++) {
			if (arrayString[i].equals(str))
				return true;
		}
		return false;
	}

	/**
	 * 功能: 把new String[]{"abc", null, "123"}转化为 "abc,123"
	 * 
	 * @param arrayString
	 * @param splitStr
	 * @return
	 */
	public static String arrayToString(String[] arrayString, String splitStr) {
		StringBuilder sb = new StringBuilder();
		if (arrayString == null || arrayString.length == 0) {
			return null;
		}
		for (int i = 0; i < arrayString.length; i++) {
			if (arrayString[i] != null && arrayString[i].length() > 0) {
				if (sb.length() > 0) {
					sb.append(splitStr);
				}
				sb.append(arrayString[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 功能: 测试各种编码之间的转化，找出乱码原因
	 * 
	 * @param original
	 * @return
	 */
	public static String testAllEncode(String original) {
        return testAllEncode(original, new String[] { "GBK", "iso8859-1", "gb2312", "UTF-8" });
	}

	/**
	 * 功能: 测试各种编码之间的转化，找出乱码原因
	 * 
	 * @param original
	 * @param encode
	 * @return
	 */
	public static String testAllEncode(String original, String[] encode) {
		StringBuilder rtValue = new StringBuilder();
		rtValue.append("original = ");
		rtValue.append(original);
		rtValue.append("\n");
		if (encode == null || encode.length < 2) {
			return rtValue.toString();
		}
		for (int i = 0; i < encode.length; i++) {
			rtValue.append("\n");
			rtValue.append(encode[i]);
			rtValue.append("-->\n");
			for (int j = 0; j < encode.length; j++) {
				rtValue.append(encode[i]);
				rtValue.append("-->");
				rtValue.append(encode[j]);
				rtValue.append(" = ");
				rtValue.append(encode2Encode(original, encode[i], encode[j]));
				rtValue.append("\n");
			}
		}
		return rtValue.toString();
	}

	/**
	 * 功能: 对url编码
	 * 
	 * @param url
	 * @return
	 */
	public static String encodeUrl(String url) {
		String rtStr = "";
		try {
			if (url != null && url.length() >= 0) {
				rtStr = URLEncoder.encode(url, RmBaseConfig.getSingleton().getDefaultEncode());
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return rtStr;
	}

	public static String decodeUrl(String url) {
		String rtStr = "";
		try {
			if (url != null && url.length() >= 0) {
				rtStr = URLDecoder.decode(url, RmBaseConfig.getSingleton().getDefaultEncode());
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return rtStr;
	}

	/**
	 * 功能: 从一个文件中读出字符串
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readStringFromFile(File file, String encode) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in;
		try {
            in = new BufferedReader(new UnicodeReader(new FileInputStream(file), encode));
			boolean isFirstLine = true;
			String tempStr = "";
			while ((tempStr = in.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
				} else {
					sb.append("\n");
				}
				sb.append(tempStr);
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("readStringFromFile error", e);
		} catch (IOException e) {
			throw new RuntimeException("readStringFromFile error", e);
		}
		return sb.toString();
	}

	/**
	 * 功能: 写一个字符串到文件中去
	 * 
	 * @param str
	 * @param file
	 */
	public static File writeStringToFile(String str, File file) {
		try {
			BufferedReader in4 = new BufferedReader(new StringReader(str));
            PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			String tempStr = null;
			while ((tempStr = in4.readLine()) != null) {
				out1.println(tempStr);
			}
			out1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 功能: 得到str的首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toFirstUpperCase(String str) {
		if (str == null || str.length() == 0) {
			return str;
		} else {
			String firstStr = str.substring(0, 1);
			return firstStr.toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 功能: 得到百分比的显示
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public static String getPercentage(int numerator, int denominator) {
		return getPercentage(numerator * 1.00, denominator * 1.00);
	}

	/**
	 * 功能: 得到百分比的显示
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public static String getPercentage(double numerator, double denominator) {
		double percentage = numerator * 1.00 / denominator;
		if (String.valueOf(percentage).endsWith(String.valueOf(Double.NaN))) {
			return "";
		}
		percentage = percentage * 100;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		return nf.format(percentage) + "%";
	}

	/**
	 * 功能:
	 * 
	 * @param value
	 * @param fractionDigits
	 * @return
	 */
	public static String defaultFormatDouble(BigDecimal value) {
		return defaultFormatDouble(value, 2);
	}
	/**
	 * 功能:
	 * 
	 * @param value
	 * @param fractionDigits
	 * @return
	 */
	public static String defaultFormatDouble(BigDecimal value, int fractionDigits) {
		if(value == null){
			return "";
		}
		return defaultFormatDouble(value.doubleValue(), fractionDigits);
	}
	/**
	 * 功能:
	 * 
	 * @param value
	 * @param fractionDigits
	 * @return
	 */
	public static String defaultFormatDouble(double value, int fractionDigits) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumFractionDigits(fractionDigits);
		nf.setMaximumFractionDigits(fractionDigits);
		return nf.format(value);
	}

	/**
	 * 得到Throwable的堆栈信息
	 * 
	 * @param t
     * @param rows 最长多少行
	 * @return
	 */
	public static String getStackTraceStr(Throwable t, int rows) {
		StringBuilder result = new StringBuilder();
		Throwable currentE = t;
		int count = 0;
		while(currentE != null) {
			if(currentE != t) {
				result.append("Caused by: ");
			}
			result.append(currentE.toString());
			result.append("\n");
			for (int i = 0; i < currentE.getStackTrace().length; i++) {
				count ++;
				if (rows > 0 && count > rows) {
					result.append("......\n");
					break;
				}
				result.append(currentE.getStackTrace()[i]);
				result.append("\n");
			}
			currentE = currentE.getCause();
		}
		return result.toString();
	}
	
	/**
	 * 得到调用栈信息
	 * @param rows 最长多少行
	 * @return
	 */
	public static String getStackTrace(int rows) {
		StringBuilder result = new StringBuilder();
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    	for(int i=0; i<sts.length; i++) {
    		if(i == 0 
    				&& Thread.class.getName().equals(sts[i].getClassName()) 
    				&& "getStackTrace".equals(sts[i].getMethodName())) {
    			continue;
    		}
    		if(i == 1 
    				&& RmStringHelper.class.getName().equals(sts[i].getClassName()) 
    				&& "getStackTrace".equals(sts[i].getMethodName())) {
    			continue;
    		}
    		if(result.length() > 0) {
    			result.append("\n");
    		}
			if (rows > 0 && i > rows) {
				result.append("......");
				break;
			}
    		result.append(sts[i]);
    	}
    	return result.toString();
	}

	/**
	 * 功能:
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getOrOperator(String str1, String str2) {
		if (str1 == null || str2 == null) {
			return null;
		}
		if (str1.length() > str2.length()) {
			return getOrOperator(str2, str1);
		}
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < str1.length(); i++) {
			if ("1".equals(str1.substring(i, i + 1)) || "1".equals(str2.substring(i, i + 1))) {
				str.append("1");
			} else {
				str.append("0");
			}
		}
		if (str2.length() > str1.length()) {
			str.append(str2.substring(str1.length(), str2.length()));
		}
		return str.toString();
	}

	/**
	 * 判断不为空null 和 “”
	 * 
	 * @param eStr
	 * @return
	 */
	public static boolean checkNotEmpty(String eStr) {
		if (eStr == null || "".equals(eStr)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断为空
	 * 
	 * @param eStr
	 * @return
	 */
	public static boolean checkEmpty(String eStr) {
		if (eStr == null || "".equals(eStr)) {
			return true;
		}
		return false;
	}
	
    /**
     * 得到首字母集合，简称
     * @param cnStr
     * @return
     */
	public static String getFirstSpellCollection(String cnStr) {
		return Cn2Spell.getFirstSpellCollection(cnStr);
	}
}
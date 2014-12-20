package org.quickbundle.tools.helper.math;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quickbundle.config.RmBaseConfig;

public class RmNumberHelper {
	
	/**
	 * 千分位匹配
	 */
	private static Pattern thousandsSeparatePattern = Pattern.compile("(\\d{1,3})(?=(\\d{3})+(?:$|\\D))");

	/**
	 * 格式化为千分位
	 * 
	 * @param value
	 * @return
	 */
	public static String formatToThousandth(String value) {
		return formatToThousandth(value, RmBaseConfig.getSingleton().getDefaultNumberScale(), ",");
	}
	
	/**
	 * 格式化为千分位
	 * 
	 * @param value
	 * @param decimalPrecision
	 * @param thousandsSeparator
	 * @return
	 */
	public static String formatToThousandth(String value, int decimalPrecision, String thousandsSeparator) {
		if (value == null || value.length() == 0) {
			value = "0";
		} else {
			value = formatFromThousandth(value);
		}
		if(decimalPrecision < 0) {
			decimalPrecision = 0;
		}
		StringBuilder pattern = new StringBuilder();
		pattern.append("0.");
		for (int i = 0; i < decimalPrecision; i++) {
			pattern.append("0");
		}
		value = new DecimalFormat(pattern.toString()).format(Double.parseDouble(value));
		if(decimalPrecision > 0) {
			String integerPart = value.split("\\.")[0];
			String decimalPart = value.split("\\.")[1];
			Matcher matcher = thousandsSeparatePattern.matcher(integerPart);
			integerPart = matcher.replaceAll("$1" + thousandsSeparator);
			value = integerPart + "." + decimalPart;
		} else {
			Matcher matcher = thousandsSeparatePattern.matcher(value.replaceAll("\\.$", ""));
			value = matcher.replaceAll("$1" + thousandsSeparator);
		}

		return value;
	}
	
	/**
	 * 从千分位转化为普通数字格式
	 * 
	 * @param str
	 * @return
	 */
	public static String formatFromThousandth(String value) {
		return value.replaceAll(",", "");
	}
}
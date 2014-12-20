package org.quickbundle.util.numeral;

import java.math.BigInteger;

/**
 * X进制数体系的配置类，默认36进制 
 *
 */
public class RmNumeralConfig {
	
	/**
	 * //0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz（62进制）
	 * 
	 * 默认支持36个字符，考虑到数据库可以对大小写不敏感
	 */
	private char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	/**
	 * 默认长度为3，每个父节点下最多有46655个兄弟子节点
	 */
	private int charLength = 3;
	
	//默认的全局配置单实例
	private static RmNumeralConfig defaultConfig = new RmNumeralConfig();

	/**
	 * 获得默认的全局配置实例
	 * @return
	 */
	public static RmNumeralConfig getDefaultConfig() {
		return defaultConfig;
	}

	public RmNumeralConfig() {

	}

	/**
	 * 获得支持的字符
	 * @return
	 */
	public char[] getChars() {
		return chars;
	}

	/**
	 * 设置支持的字符串
	 * @param chars
	 */
	public void setChars(char[] chars) {
		int previousChar = -1;
		for(int i=0; i<chars.length; i++) {
			int thisChar = (int)chars[i];
			if(thisChar <= previousChar) {
				throw new RuntimeException(String.valueOf(chars) + "[" + i + "] is not larger then previous one!" );
			}
			previousChar = thisChar;
		}
		this.chars = chars;
	}

	/**
	 * 获得编码长度
	 * @return
	 */
	public int getCharLength() {
		return charLength;
	}

	/**
	 * 设置编码长度
	 * @param charLength
	 */
	public void setCharLength(int charLength) {
		if(charLength < 2) {
			throw new RuntimeException("must 2 digit at least!");
		}
		this.charLength = charLength;
	}
	
	/**
	 * 获得最小值
	 * @return
	 */
	public String getMinValue() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<charLength; i++) {
			sb.append(chars[0]);
		}
		return sb.toString();
	}
	
	/**
	 * 获得最大值
	 * @return
	 */
	public String getMaxValue() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<charLength; i++) {
			sb.append(chars[chars.length-1]);
		}
		return sb.toString();
	}
	
	/**
	 * 获得最大值的十进制数
	 * @return
	 */
	public String getMaxValueNumber() {
		BigInteger result = BigInteger.valueOf(1);
		BigInteger value = BigInteger.valueOf(chars.length);
		for(int i=0; i<charLength; i++) {
			result = result.multiply(value);
		}
		return result.toString();
	}
}
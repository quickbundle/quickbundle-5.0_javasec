package org.quickbundle.util.numeral;

/**
 * 实现一个可定制的X进制数字体系，步长至少2位，最长不限，可表示极大的数
 * 默认是36进制，步长3位，每个父节点下最多有46656个兄弟子节点
 */
public class RmBaseXNumeral {
	
	//配置类的对象
	protected RmNumeralConfig config = null;
	
	//冗余RmBaseXConfig的chars值，是一个引用对象
	private char[] chars = null;

	/**
	 * 存储值的核心变量
	 */
	private String value;
	
	/**
	 * 内部的构造函数
	 * @param config_
	 */
	protected RmBaseXNumeral(RmNumeralConfig config_) {
		if(config_ != null) {
			this.config = config_;
		} else {
			this.config = RmNumeralConfig.getDefaultConfig();
		}
		chars = config.getChars();
	}
	
	/**
	 * 通过字符串构建
	 * @param config_
	 * @param value_
	 */
	public RmBaseXNumeral(RmNumeralConfig config_, String value_) {
		this(config_);
		if(value_ == null || value_.length() == 0) {
			value_ = config.getMinValue();
		}
		if(value_.length() == 1) {
			throw new RuntimeException("must 2 digit at least!");
		}
		if(value_.matches(".*[^" + String.valueOf(chars) + "].*")) {
			throw new RuntimeException("valid char only include:" + String.valueOf(chars));
		}
		this.value = value_;
	}
	
	/**
	 * 通过字符串构建
	 * @param value_
	 */
	public RmBaseXNumeral(String value_) {
		this(null, value_);
	}
	
	/**
	 * 通过long构建
	 * @param config_
	 * @param lValue
	 */
	public RmBaseXNumeral(RmNumeralConfig config_, long lValue) {
		this(config_);
		if(lValue < 0) {
			throw new RuntimeException("can't construct negative long number!");
		}
		this.value = long2String(lValue);
	}
	
	/**
	 * 通过long构建
	 * @param lValue
	 */
	public RmBaseXNumeral(long lValue) {
		this(null, lValue);
	}
	
	/**
	 * 返回下一个值，对象本身不改变
	 * @return
	 */
	public String getNext() {
		return getNext(1)[0];
	}
	
	/**
	 * 高性能的批量返回下一个值，对象本身不改变
	 * @param len
	 * @return
	 */
	public String[] getNext(int len) {
		if(len == 0) {
			return new String[0];
		} else if(len < 0) {
			throw new RuntimeException("can't getNext for negative number!");
		}
		char[][] result = new char[len][value.length()];
		long[][] aCarry = new long[value.length()-1][3]; //进位数，进位前增长了几位
		for(int i=0; i<aCarry.length; i++) {
			long hex = 1L;
			for(int k=0; k<(aCarry.length-i-1); k++) {
				hex *= chars.length;
			}
			aCarry[i][2] = hex;
		}
		{ //个位数
			int indexLast = value.length()-1; //个位数的位置
			int charPos = pos(value.charAt(indexLast)); //起始位置
			for(int j=0; j<len; j++) {
				if(charPos == chars.length-1) {
					aCarry[indexLast-1][0] ++; //上一位进1位
					charPos = 0; //退到首位
				} else {
					if(aCarry[indexLast-1][0] == 0) {
						aCarry[indexLast-1][1] ++; //个位数增长1位
					}
					charPos ++;
				}
				result[j][indexLast] = chars[charPos];
			}
		}
		
		for(int i=0; i<value.length()-1; i++) {
			int digitPos = value.length()-2-i; //当前处理的位数
			int charPos = pos(value.charAt(digitPos)); //起始位置
			for(int j=0; j<len; j++) {
				if(aCarry[digitPos][1] > 0) { //消费后1位的不进位增长
					aCarry[digitPos][1] --;
					if(digitPos > 0 && aCarry[digitPos-1][0] == 0) {
						aCarry[digitPos-1][1] ++; //上一位增长1位
					}
				} else if(aCarry[digitPos][0] > 0){ //消费后1位的进位
					if(charPos == chars.length-1) {
						if(digitPos > 0) {
							aCarry[digitPos-1][0] ++; //上一位进1位
						}
						charPos = 0; //退到首位
					} else {
						if(digitPos > 0 && aCarry[digitPos-1][0] == 0) {
							aCarry[digitPos-1][1] ++; //上一位增长1位
						}
						charPos ++;
					}
					aCarry[digitPos][0] --;
					aCarry[digitPos][1] += (chars.length * aCarry[digitPos][2]) - 1;
				}
				result[j][digitPos] = chars[charPos]; //消费完毕，原样复制
			}
		}
		String[] finalResult = new String[len];
		for(int i=0; i<len; i++) {
			finalResult[i] = new String(result[i]);
		}
		return finalResult;
	}
	
	
	//long转化为62进制字符串格式
	private String long2String(long lValue) {
		char[] cs = new char[config.getCharLength()];
		long newValue = lValue;
		for(int i=0; i<cs.length; i++) {
			long thisDigit = 1L;
			for(int k=0; k<cs.length-i-1; k++) {
				thisDigit *= chars.length;
			}
			int pos = (int)(newValue / thisDigit);
			cs[i] = chars[pos];
			newValue = newValue % thisDigit;
		}
		return String.valueOf(cs);
	}
	
	/**
	 * long形式的值展现
	 * @return
	 */
	public long longValue() {
		long result = 0L;
		result += pos(value.charAt(value.length()-1));
		for(int i=0; i<value.length()-1; i++) {
			long thisDigit = pos(value.charAt(i));
			for(int k=0; k<value.length()-i-1; k++) {
				thisDigit *= chars.length;
			}
			result += thisDigit;
		}
		return result;
	}
	
	/**
	 * 执行加法运算，会改变对象，返回字符串格式
	 * @param len
	 * @return
	 */
	public String add(long addend) {
		long lValue = longValue();
		this.value = long2String(lValue + addend);
		return this.value;
	}
	
	/**
	 * 执行减法运算，会改变对象，返回字符串格式
	 * @param len
	 * @return
	 */
	public String subtract(long subtrahend) {
		long lValue = longValue();
		this.value = long2String(lValue - subtrahend);
		return this.value;
	}
	
	//取位置
	private int pos(char c) {
		//开始二分法查找
		int start=0, end=chars.length-1;
		while(end > start + 1) {
			int middle = (int)Math.floor((start+end)/2.0);
			if(chars[middle] == c) {
				start = middle;
				break;
			} else if(chars[middle] > c) {
				end = middle;
			} else {
				start = middle;
			}
		}
		if(chars[start] != c) {
			if(chars[end] == c) {
				start = end;
			} else {
				throw new RuntimeException("not find '" + c + "' in: " + String.valueOf(chars));
			}
		}
		return start;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public String toStringSimple() {
		return value.replaceFirst("^" + chars[0] + "+", "");
	}
	
	/**
	 * @return 配置实例
	 */
	public RmNumeralConfig getConfig() {
		return config;
	}
}

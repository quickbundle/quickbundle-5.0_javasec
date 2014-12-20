package org.quickbundle.util;

import java.util.Set;
import java.util.TreeSet;


/**
 * 实现一个多编码的排序 
 * @author Administrator
 */
public class RmOrderCodes implements Comparable<RmOrderCodes> {
	private Object[] codes;
	/**
	 * @param codes_
	 */
	public RmOrderCodes(Object... codes_) {
		this.codes = codes_;
	}
	
	public Object[] getCodes() {
		return codes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<codes.length; i++) {
			if(i > 0) {
				sb.append("|");
			}
			sb.append(codes[i]);
		}
		return sb.toString();
	}

	public int compareTo(RmOrderCodes obj2) {
		if(codes.length == 0) {
			if(obj2.codes.length == 0) {
				return 0;
			} else {
				return -1;
			}
		}
			
		for(int i=0; i<codes.length-1; i++) {
			Object code1 = codes[i];
			if(obj2.codes.length < i+1) {
				return 1;
			} else {
				Object code2 = obj2.codes[i];
				if(code1 == null) {
					if(code2 != null) {
						return -1;
					}
				} else {
					if(code2 == null) {
						return 1;
					} else if(!code1.equals(code2)) {
						return compareObj(code1, code2);
					}
				}
			}
		}
		
		Object lastCode1 = codes[codes.length-1];
		if(obj2.codes.length < codes.length) {
			return 1;
		} else {
			Object lastCode2 = obj2.codes[codes.length-1];
			if(lastCode1 == null) {
				if(lastCode2 != null) {
					return -1;
				} else {
					return 0;
				}
			} else {
				if(lastCode2 == null) {
					return 1;
				} else {
					return compareObj(lastCode1, lastCode2);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private int compareObj(Object obj1, Object obj2) {
		if(obj1 instanceof Comparable && obj2 instanceof Comparable) {
			return ((Comparable<Object>)obj1).compareTo(obj2);
		} else {
			throw new RuntimeException("对象不能比较");
		}
	}
	
	public final static void main(String[] args) {
		Set<RmOrderCodes> s = new TreeSet<RmOrderCodes>();
		s.add(new RmOrderCodes(new Object[]{"a", "2"}));
		s.add(new RmOrderCodes(new Object[]{"", "2"}));
		s.add(new RmOrderCodes(new Object[]{null, "bbn"}));
		s.add(new RmOrderCodes(new Object[]{"booo", null}));
		s.add(new RmOrderCodes(new Object[]{"zzz", null}));
		s.add(new RmOrderCodes(new Object[]{"booo", "ddd"}));
		s.add(new RmOrderCodes("fdsafdsa", "a", "bbbbbbbb"));

		s.add(new RmOrderCodes("fdsafdsa", "a", "bbbbbbbb"));
		System.out.println(s);
	}
}

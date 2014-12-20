package org.quickbundle.base.beans.idwrapper;

import org.quickbundle.base.beans.TableIdRuleVo;

public class ShardingInCacheCharWrapper extends ShardingInCacheWrapper {
	
	private int charLength;
	private final static String BEGIN_KEY = "char(";
	private final static String END_KEY = ")";
	
	public ShardingInCacheCharWrapper(TableIdRuleVo ruleVo) {
		super(ruleVo);
		String format = ruleVo.getWrapperClassFormat();
		if(format != null && format.indexOf(BEGIN_KEY) > -1 && format.indexOf(END_KEY) > format.indexOf(BEGIN_KEY)) {
			charLength = Integer.parseInt(format.substring(format.indexOf(BEGIN_KEY) + BEGIN_KEY.length(), format.indexOf(END_KEY)));
		}
	}

	public synchronized String[] nextValue(int length) {
		String[] old = super.nextValue(length);
		String[] newResult = new String[length];
		for(int i=0; i<length; i++) {
			StringBuilder sb = new StringBuilder(charLength);
			sb.append(old[i]);
			for(int j=0; j<charLength-old[i].length(); j++) {
				sb.append("0");
			}
			newResult[i] = sb.toString();
		}
		return newResult;
	}
	
}

package org.quickbundle.third.mybatis;

import java.util.HashMap;

public class ParaMap extends HashMap<String, Object>{
	public ParaMap(String queryCondition) {
		this(queryCondition, null);
	}
			
	/**
	 * 
	 * 
	 * @param queryCondition
	 * @param orderStr
	 * @param paras
	 */
	public ParaMap(String queryCondition, String orderStr, Object... paras) {
		put("queryCondition", queryCondition);
		put("orderStr", orderStr);
		int index = 0;
		for(Object para : paras) {
			String key = "para" + ++index ;
			put(key, para);
		}
	}
}

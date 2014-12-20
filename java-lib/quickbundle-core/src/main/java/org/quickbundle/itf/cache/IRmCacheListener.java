package org.quickbundle.itf.cache;



public interface IRmCacheListener {
	
	/**
	 * 刷新缓存的值，将keys对应的数据设置为已过期（或未初始化）状态
	 * 
	 * @param refreshType 缓存的刷新类型
	 * @param keys 缓存的key值，所有参数应可能使用String(如Java基本类型)
	 * @return 返回执行结果: -1表示错误, 0表示没找到删除的对象, 大于0的值表示影响的行数
	 */
	public String flushCache(String flushType, Object... keys);
	
	public enum RefreshType {
		COMMON("0"),
		INSERT("1"),
		DELETE("2"),
		UPDATE("3");
	    private String value;
	    RefreshType(String value_) {
    		value = value_;
    	}
		public String value() {
			return value;
		}
	}
	
	public enum Result {
		OK("^[1-9]\\d*"),
		EMPTY("0"),
		FAIL("-1"),
		UNKNOWN(".*");
	    private String pattern;
		Result(String pattern_) {
			pattern = pattern_;
    	}
		public String pattern() {
			return pattern;
		}
	}
}
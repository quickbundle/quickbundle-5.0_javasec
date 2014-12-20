package org.quickbundle.project.cache;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quickbundle.itf.cache.IRmCacheListener;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmSqlCountCache implements IRmCacheListener {
	
	/**
	 * 分布式缓存的log入口类
	 */
	public final static Logger logCache = RmLogHelper.getLogger("rmcache");
	
	//每个tableName对应的缓存的最大queryCondition数量，防止溢出
	private static final int MAX_CACHE_COUNT = 1000;
	
	private RmSqlCountCache() {
	}
	
	/**
	 * 缓存查询条件对应的count(*)记录数
	 */
	private volatile Map<String, Map<String, Integer>> mCount = new ConcurrentHashMap<String, Map<String, Integer>>();
	
	/**
	 * 得到缓存中查询条件对应的count(*)记录数，如缓存无返回-1，抛异常返回-2
	 * 
	 * @param tableName
	 * @param queryCondition
	 * @return 大于等于0表示记录数。-1表示当前表的缓存未初始化。-2表示抛异常
	 */
	private int getCountInner(String tableName, String queryCondition) {
		int result = -1;
    	try {
        	if(mCount.get(tableName) != null && mCount.get(tableName).get(queryCondition) != null) {
        		result = mCount.get(tableName).get(queryCondition);
        	}
    	} catch(Exception e) {
    		result = -2;
    		logCache.error(RmSqlCountCache.class.getName() + ".getCount(" + tableName + ", " + queryCondition + "): " + e.toString());
    	}
    	return result;
	}

	/**
	 * 得到缓存中查询条件对应的count(*)记录数，如缓存无返回-1，抛异常返回-2
	 * 
	 * @param tableName
	 * @param queryCondition
	 * @return 大于等于0表示记录数。-1表示当前表的缓存未初始化。-2表示抛异常
	 */
	public static int getCount(String tableName, String queryCondition) {
		return singleton.getCountInner(tableName, queryCondition);
	}
	
	/**
	 * 向缓存中放入查询条件对应的count(*)记录数
	 * 
	 * @param tableName
	 * @param queryCondition
	 * @param count
	 */
	public void putCountInner(String tableName, String queryCondition, int count) {
		try {
	    	if(mCount.get(tableName) == null) {
	    		synchronized (mCount) {
	    			if(mCount.get(tableName) == null) {
	    				Map<String, Integer> mCountTableName = new ConcurrentHashMap<String, Integer>();
	    				mCount.put(tableName, mCountTableName);
	    			}
				}
	    	}
	    	//如果达到最大缓存数，清空原有数据，缓存重新开始
	    	if(mCount.get(tableName).size() >= MAX_CACHE_COUNT) {
	    		mCount.get(tableName).clear();
	    	}
	    	mCount.get(tableName).put(queryCondition, count);
		} catch (Exception e) {
			logCache.error(RmSqlCountCache.class.getName() + ".putCount(" + tableName + ", " + queryCondition + ", " + count + "): " + e.toString());
		}
	}
	
	/**
	 * 向缓存中放入查询条件对应的count(*)记录数
	 * 
	 * @param tableName
	 * @param queryCondition
	 * @param count
	 */
	public static void putCount(String tableName, String queryCondition, int count) {
		singleton.putCountInner(tableName, queryCondition, count);
	}
	
	/**
	 * 清除tableName表对应的count记录数缓存
	 * 
	 * @param tableName
	 */
	private void clearCountInner(String tableName) {
		try {
			mCount.remove(tableName);
		} catch (Exception e) {
			logCache.error(RmSqlCountCache.class.getName() + ".clearCount(" + tableName + "): " + e.toString());
		}
	}
	
	/**
	 * 清除tableName表对应的count记录数缓存
	 * 
	 * @param tableName
	 */
	public static void clearCount(String tableName) {
		singleton.clearCountInner(tableName);
		//不会被flushCache调用的方法，才能安全执行flushOtherNodes
		RmCacheHandler.getSingleton().flushOtherNodes(RmSqlCountCache.class, IRmCacheListener.RefreshType.DELETE.value(), tableName);
	}
	
	private static RmSqlCountCache singleton = new RmSqlCountCache();
	
	public static RmSqlCountCache getSingleton() {
		return singleton;
	}
	
	/**
	 * 刷新缓存的值，将keys对应的数据设置为已过期（或未初始化）状态
	 * 
	 * @param refreshType 缓存的刷新类型
	 * @param keys 缓存的key值
	 * @return 返回执行结果: -1表示错误, 0表示没找到删除的对象, 大于0的值表示影响的行数
	 */
	public String flushCache(String flushType, Object... keys) {
		String result = null;
		if(IRmCacheListener.RefreshType.DELETE.value().equals(flushType)) {
			if(keys.length > 0) {
				clearCountInner(keys[0].toString());
				result = "1";
			}
		} else {
			result = "0";
		}
		logCache.info(this.getClass().getName() + ".flushCache(" + flushType + ", " + Arrays.deepToString(keys) + "): result=" + result);
		return result;
	}
}
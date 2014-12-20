package org.quickbundle.base.beans.factory;

import org.quickbundle.itf.base.IRmIdGenerator;

/**
 * ID生成器单例
 * 
 * @author 白小勇
 */
public class RmIdFactory {
    /**
     * 获得单例
     * @return
     */
    public static IRmIdGenerator getIdGenerator() {
        if(!isInitId) {
            synchronized (RmIdFactory.class) {
                if(!isInitId) {
                	idGenerator = (IRmIdGenerator) RmBeanFactory.getBean("org.quickbundle.itf.base.IRmIdGenerator");
                	idGenerator.init();
                    isInitId = true;
                }
            }
        }
        return idGenerator;
    }
    
    /**
     * 获取单个唯一ID，Long格式
     * 
     * @param tableName 表名
     * @return 返回内存中自增长的ID，未找到返回null
     */
    public static Long requestIdLong(String tableName) {
    	return new Long(requestId(tableName));
    }

    /**
     * 获取单个唯一ID
     * 
     * @param tableName 表名
     * @return 返回内存中自增长的ID，未找到返回null
     */
    public static String requestId(String tableName) {
    	String[] ids = requestId(tableName, 1);
    	if(ids == null || ids.length == 0) {
    		return null;
    	}
    	return ids[0];
    }
    
    /**
     * 批量获取唯一ID
     * @param tableName 表名
     * @param length 批量数
     * @return 返回内存中自增长的ID，未找到返回null
     */
    public static String[] requestId(String tableName, int length) {
    	if(length < 1) {
    		return new String[0];
    	}
    	IRmIdGenerator instance = getIdGenerator();
        if(idGenerator == null && isInitId) {
            return null;
        }
    	return instance.requestIdInner(tableName.toUpperCase(), length);
    }
    
    /**
     * 批量获取唯一ID，Long[]格式
     * @param tableName 表名
     * @param length 批量数
     * @return 返回内存中自增长的ID，未找到返回null
     */
    public static Long[] requestIdLong(String tableName, int length) {
    	String[] ids = requestId(tableName, length);
    	Long[] result = new Long[length];
    	for (int i = 0; i < ids.length; i++) {
    		result[i] = new Long(ids[i]);
		}
    	return result;
    }
    
	//全局单例
	private static IRmIdGenerator idGenerator = null;
	//全局单例的初始化标记，用于双检锁安全判断
	private static volatile boolean isInitId = false;
}
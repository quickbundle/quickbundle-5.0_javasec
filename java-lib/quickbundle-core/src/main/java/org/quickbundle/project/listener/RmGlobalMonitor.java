package org.quickbundle.project.listener;

import java.util.HashMap;
import java.util.Map;


public class RmGlobalMonitor {
    
    /**
     * 全局唯一的UUID，
     * 产生时机: request(表示一次请求)进入，或栈顶的Service(表示一次事务)进入
     * 销毁时机: Request结束，或Service结束
     */
    public static final ThreadLocal<String> uniqueUUID = new ThreadLocal<String>();
    
    /**
     * 单事务产生的UUID，
     * 产生时机: Connection.setAutoCommit(true)
     * 销毁时机: Connection.commit()
     */
    public static final ThreadLocal<Map<String, String>> transactionUUID = new ThreadLocal<Map<String, String>>();
    
    static String formatUUID(String uuid) {
    	if(uuid == null) {
    		return "";
    	} else {
    		return uuid.replaceAll("-", "");
    	}
    }
    
    /**
     * 返回uniqueUUID，如果是null返回""
     */
    public static String getShortUniqueUUID() {
    	return formatUUID(uniqueUUID.get());
    }
    
    /**
     * 
     */
    public static void putTransactionUUID(String conn, String uuid) {
    	if(transactionUUID.get() == null) {
    		transactionUUID.set(new HashMap<String, String>());
    	}
    	transactionUUID.get().put(conn, uuid);
    }

    public static void removeTransactionUUID(String conn) {
    	if(transactionUUID.get() != null) {
    		transactionUUID.get().remove(conn);
    	}
    }

    /**
     * 返回transactionUUID，如果是null返回""
     */
    public static String getShortTransactionUUID(String conn) {
    	if(conn == null || transactionUUID.get() == null) {
    		return "";
    	}
    	return formatUUID(transactionUUID.get().get(conn));
    }
}

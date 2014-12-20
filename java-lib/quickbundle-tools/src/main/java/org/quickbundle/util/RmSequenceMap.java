/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.util --> RanminSequenceMap.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-4-8 21:36:30 创建1.0.0版 (Administrator)
 * 
 */
package org.quickbundle.util;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现一个标准的java.util.Map，按照put的先后顺序读取，key值对大小写敏感
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmSequenceMap<K,V> extends HashMap<K,V> {

    List<K> lKeyIndex = null;

    /**
     * 构造函数:
     *  
     */
    public RmSequenceMap() {
        super();
        lKeyIndex = new ArrayList<K>();
    }

    public V put(K key, V value) {
        if (!lKeyIndex.contains(key)) {
            this.lKeyIndex.add(key);
        }

        return super.put(key, value);
    }

    public V remove(Object key) {
        lKeyIndex.remove(key);
        return super.remove(key);
    }
    
    @Override
    public void clear() {
    	lKeyIndex.clear();
    	super.clear();
    }

    public Set<K> keySet() {
        Set<K> sKey = new RmSequenceSet<K>();
        for (K key : lKeyIndex) {
            sKey.add(key);
        }
        return sKey;
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("{");
        Iterator iterator = keySet().iterator();
        boolean flag = iterator.hasNext();
        do {
            if (!flag) {
                break;            	
            }
            Object key = iterator.next();
            Object obj = key;
            Object obj1 = this.get(key);
            stringbuffer.append((obj != this ? obj : "(this Map)") + "=" + (obj1 != this ? obj1 : "(this Map)"));
            flag = iterator.hasNext();
            if (flag) {
                stringbuffer.append(", ");            	
            }
        } while (true);
        stringbuffer.append("}");
        return stringbuffer.toString();
    }
    
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
    	Set<K> sKey = keySet();
    	Set<java.util.Map.Entry<K, V>> result = new RmSequenceSet<Map.Entry<K,V>>();
    	final RmSequenceMap<K,V> thisObj = this;
    	for(final K k : sKey) {
    		result.add(new java.util.Map.Entry() {
				public Object getKey() {
					return k;
				}

				public Object getValue() {
					return thisObj.get(k);
				}

				public Object setValue(Object value) {
					return thisObj.put(k, (V)value);
				}
    			
			});
    	}
    	return result;
    }

    final static class TestRmSequenceSet {
        public static void main(String[] args) {
            Map<String, Object> testMap = new RmSequenceMap<String, Object>();
            testMap.put("123", "3424231");
            testMap.put("234", "3424231");
            testMap.put("943", "3424231");
            testMap.put("417", "3424231");
            testMap.put("22", "3424231");
            testMap.put("823", "3424231");
            System.out.println(testMap);
            for (Iterator itTestMap = testMap.keySet().iterator(); itTestMap.hasNext();) {
                String key = itTestMap.next().toString();
                System.out.println(key + "=" + testMap.get(key));
            }
        }
    }
}
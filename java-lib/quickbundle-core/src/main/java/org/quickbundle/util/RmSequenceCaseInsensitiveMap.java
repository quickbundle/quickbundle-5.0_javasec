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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * 实现一个标准的java.util.Map，按照put的先后顺序读取，key值对大小写不敏感
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmSequenceCaseInsensitiveMap<K,V> extends CaseInsensitiveMap {
	private static final long serialVersionUID = 1L;
	List lKeyIndex = null;

    /**
     * 构造函数:
     *  
     */
    public RmSequenceCaseInsensitiveMap() {
        super();
        lKeyIndex = new ArrayList();
    }

    public Object put(Object key, Object value) {
        if (!lKeyIndex.contains(key)) {
            this.lKeyIndex.add(key);
        }

        return super.put(key, value);
    }

    public Object remove(Object key) {
        lKeyIndex.remove(key);
        return super.remove(key);
    }

    public Set keySet() {
        Set sKey = new RmSequenceSet();
        for (Object key : lKeyIndex) {
            sKey.add(key);
        }
        return sKey;
    }
}
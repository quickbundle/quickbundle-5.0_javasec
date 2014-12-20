/*
 * 系统名称:基于冉闵开发工具 --> rmdemo
 * 
 * 文件名称: org.quickbundle.util --> RmString.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-9-11 15:21:39 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现一个带有attribute的String对象
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmString {
    private StringBuilder str;
    private Map<String, Object> attributes;
    /**
     * 构造函数:
     * 
     */
    public RmString() {
        this.str = new StringBuilder();
        this.attributes = new HashMap<String, Object>();
    }

    /**
     * 构造函数:
     * @param str
     */
    public RmString(String str) {
        this();
        this.str = new StringBuilder(str);
    }
    
    public String toString() {
        return str.toString();
    }
    
    public Object addAttribute(String key, Object obj) {
        return attributes.put(key, obj);
    }
    
    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }
    
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
    
    public void setValue(String str) {
        this.str = new StringBuilder(str);
    }
    
    public void append(Object obj) {
    	str.append(obj);
    }
}

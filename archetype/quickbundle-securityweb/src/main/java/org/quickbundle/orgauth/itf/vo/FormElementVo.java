/*
 * 系统名称:基于冉闵开发工具 --> rmdemo
 * 
 * 文件名称: org.quickbundle.project.authorize --> FormElementVo.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-7-2 20:20:34 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.orgauth.itf.vo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class FormElementVo {
    
    /**
     * key 表示: 表单元素的name关键字
     */
    private String key;
    
    /**
     * value 表示: 表单元素的值
     */
    private Object value;
    
    /**
     * accessTypeAlias 表示: 表单元素的访问类型别名, 可能有READ, DISABLED, WRITE
     */
    private String accessTypeAlias;
    
    
    /**
     * @return 返回 accessTypeAlias。 表单元素的访问类型别名, 可能有READ, DISABLED, WRITE
     */
    public String getAccessTypeAlias() {
        return accessTypeAlias;
    }
    /**
     * @param accessTypeAlias 要设置的 accessTypeAlias。 表单元素的访问类型别名, 可能有READ, DISABLED, WRITE
     */
    public void setAccessTypeAlias(String accessTypeAlias) {
        this.accessTypeAlias = accessTypeAlias;
    }
    /**
     * @return 返回 key。 表单元素的name关键字
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key 要设置的 key。 表单元素的name关键字
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return 返回 value。 表单元素的值
     */
    public Object getValue() {
        return value;
    }
    /**
     * @param value 要设置的 value。 表单元素的值
     */
    public void setValue(Object value) {
        this.value = value;
    }
}

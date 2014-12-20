/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.au.tools.util --> ITransctVoField.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2005-10-12 9:57:39 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.itf;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITransctVoField {
    public int transctVo(BeanWrapper bw, PropertyDescriptor pd);
}

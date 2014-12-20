/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.test --> RmTestCase.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-6-5 20:44:44 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.project.test;

import junit.framework.TestCase;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.project.init.InitDatabaseHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmWebTestCase extends TestCase {
	protected void setUp() throws Exception {
	    init();
	}
    
    /**
     * 初始化log4j和Spring配置
     */
    public final synchronized static void init() {
        RmBeanFactory.getBeanFactory();
        InitDatabaseHelper.initDatabaseProductName();
    }
}
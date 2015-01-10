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
import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.project.init.CustomSystemProperties;
import org.quickbundle.project.init.InitDatabaseHelper;
import org.quickbundle.project.init.LoadProjectConfig;
import org.quickbundle.project.listener.RmContextLoaderListener;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.path.RmPathHelper;
import org.slf4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmWebTestCase extends TestCase {
	private static Logger getLogger() {
		return RmLogHelper.getLogger(RmWebTestCase.class);
	}
	
	protected void setUp() throws Exception {
	    init();
	}
    
    /**
     * 初始化log4j和Spring配置
     */
    public final synchronized static void init() {
		String initWarRootMsg = RmPathHelper.initWarRoot();
		
		//更新RmConfig配置
		CustomSystemProperties.getInstance().init();
		LoadProjectConfig.initRmConfig();
		

        RmBeanFactory.getBeanFactory();
		InitDatabaseHelper.initDatabase();
		
		LoadProjectConfig.initClusterConfig();

    }
}
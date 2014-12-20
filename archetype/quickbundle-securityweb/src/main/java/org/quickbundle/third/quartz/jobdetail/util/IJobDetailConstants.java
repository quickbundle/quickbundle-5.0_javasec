//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobdetail/util/IJobDetailConstants.java
//代码生成时,系统时间: 2012-04-02 22:28:47
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobdetail.util --> IJobDetailConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:47 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobdetail.util;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.third.quartz.util.ISchedulerConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IJobDetailConstants extends IGlobalConstants, ISchedulerConstants {
    
    //Service的规范化名称
    public final static String SERVICE_KEY = "IJobDetailService";
    
    //表名
    public final static String TABLE_NAME = "JOB_DETAIL";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "作业定义";
    
    //列名汉化
    @SuppressWarnings("unchecked")
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("job_name","作业名称");
		put("job_group","作业组");
		put("description","描述");
		put("job_class_name","作业类名");
		put("is_durable","持久化");
		put("requests_recovery","可恢复");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"job_name", "job_group"}; 
}
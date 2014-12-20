//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/lock/rmlock/vo/RmLockVo.java
//代码生成时,系统时间: 2010-11-30 22:19:59
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.lock.rmlock.vo --> RmLockVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:19:59 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.lock.rmlock.vo;


import java.sql.Timestamp;


import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmLockVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * user_id 表示: 用户ID
	 * 数据库注释: 
     */
    private String user_id;
	/**
     * bs_keyword 表示: 业务关键字
	 * 数据库注释: 
     */
    private String bs_keyword;
	/**
     * lock_content 表示: 加锁关键值
	 * 数据库注释: 
     */
    private String lock_content;
	/**
     * lock_date 表示: 锁定时间
	 * 数据库注释: 
     */
    private Timestamp lock_date;
	/**
     * usable_status 表示: 数据可用状态
	 * 数据库注释: 
     */
    private String usable_status;
	/**
     * modify_date 表示: 修改日期
	 * 数据库注释: 
     */
    private Timestamp modify_date;
	/**
     * modify_ip 表示: 修改IP
	 * 数据库注释: 
     */
    private String modify_ip;
	/**
     * modify_user_id 表示: 修改用户ID
	 * 数据库注释: 
     */
    private String modify_user_id;        
    //结束rm_code_type的属性
        
        
    //开始rm_code_type的setter和getter方法
    
    /**
     * 获得主键
     * 
     * @return 主键
     */
	public String getId(){
		return id;
	}
	
    /**
     * 设置主键
     * 
     * @param id 主键
     */
	public void setId(String id){
		this.id = id;
	}
	
    /**
     * 获得用户ID
     * 
     * @return 用户ID
     */
	public String getUser_id(){
		return user_id;
	}
	
    /**
     * 设置用户ID
     * 
     * @param user_id 用户ID
     */
	public void setUser_id(String user_id){
		this.user_id = user_id;
	}
	
    /**
     * 获得业务关键字
     * 
     * @return 业务关键字
     */
	public String getBs_keyword(){
		return bs_keyword;
	}
	
    /**
     * 设置业务关键字
     * 
     * @param bs_keyword 业务关键字
     */
	public void setBs_keyword(String bs_keyword){
		this.bs_keyword = bs_keyword;
	}
	
    /**
     * 获得加锁关键值
     * 
     * @return 加锁关键值
     */
	public String getLock_content(){
		return lock_content;
	}
	
    /**
     * 设置加锁关键值
     * 
     * @param lock_content 加锁关键值
     */
	public void setLock_content(String lock_content){
		this.lock_content = lock_content;
	}
	
    /**
     * 获得锁定时间
     * 
     * @return 锁定时间
     */
	public Timestamp getLock_date(){
		return lock_date;
	}
	
    /**
     * 设置锁定时间
     * 
     * @param lock_date 锁定时间
     */
	public void setLock_date(Timestamp lock_date){
		this.lock_date = lock_date;
	}
	
    /**
     * 获得数据可用状态
     * 
     * @return 数据可用状态
     */
	public String getUsable_status(){
		return usable_status;
	}
	
    /**
     * 设置数据可用状态
     * 
     * @param usable_status 数据可用状态
     */
	public void setUsable_status(String usable_status){
		this.usable_status = usable_status;
	}
	
    /**
     * 获得修改日期
     * 
     * @return 修改日期
     */
	public Timestamp getModify_date(){
		return modify_date;
	}
	
    /**
     * 设置修改日期
     * 
     * @param modify_date 修改日期
     */
	public void setModify_date(Timestamp modify_date){
		this.modify_date = modify_date;
	}
	
    /**
     * 获得修改IP
     * 
     * @return 修改IP
     */
	public String getModify_ip(){
		return modify_ip;
	}
	
    /**
     * 设置修改IP
     * 
     * @param modify_ip 修改IP
     */
	public void setModify_ip(String modify_ip){
		this.modify_ip = modify_ip;
	}
	
    /**
     * 获得修改用户ID
     * 
     * @return 修改用户ID
     */
	public String getModify_user_id(){
		return modify_user_id;
	}
	
    /**
     * 设置修改用户ID
     * 
     * @param modify_user_id 修改用户ID
     */
	public void setModify_user_id(String modify_user_id){
		this.modify_user_id = modify_user_id;
	}
	
    //结束rm_code_type的setter和getter方法
    
}
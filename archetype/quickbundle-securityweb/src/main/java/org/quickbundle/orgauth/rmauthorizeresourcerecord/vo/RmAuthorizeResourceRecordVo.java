//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorizeresourcerecord/vo/RmAuthorizeResourceRecordVo.java
//代码生成时,系统时间: 2010-11-27 22:08:43
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorizeresourcerecord.vo --> RmAuthorizeResourceRecordVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:43 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorizeresourcerecord.vo;


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

public class RmAuthorizeResourceRecordVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * authorize_resource_id 表示: 授权资源ID
	 * 数据库注释: 
     */
    private String authorize_resource_id;
	/**
     * party_id 表示: 访问者团体ID
	 * 数据库注释: 
     */
    private String party_id;
	/**
     * authorize_status 表示: 授权情况(允许或拒绝)
	 * 数据库注释: $RM_AUTHORIZE_STATUS=授权情况{ 0=拒绝, 1=允许 }
     */
    private String authorize_status;
	/**
     * is_affix_data 表示: 有附加数据
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_affix_data;
	/**
     * is_recursive 表示: 递归传播权限
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_recursive;
	/**
     * access_type 表示: 访问方式代码
	 * 数据库注释: 
     */
    private String access_type;
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
     * 获得授权资源ID
     * 
     * @return 授权资源ID
     */
	public String getAuthorize_resource_id(){
		return authorize_resource_id;
	}
	
    /**
     * 设置授权资源ID
     * 
     * @param authorize_resource_id 授权资源ID
     */
	public void setAuthorize_resource_id(String authorize_resource_id){
		this.authorize_resource_id = authorize_resource_id;
	}
	
    /**
     * 获得访问者团体ID
     * 
     * @return 访问者团体ID
     */
	public String getParty_id(){
		return party_id;
	}
	
    /**
     * 设置访问者团体ID
     * 
     * @param party_id 访问者团体ID
     */
	public void setParty_id(String party_id){
		this.party_id = party_id;
	}
	
    /**
     * 获得授权情况(允许或拒绝)
     * 数据库注释: $RM_AUTHORIZE_STATUS=授权情况{ 0=拒绝, 1=允许 }
     * @return 授权情况(允许或拒绝)
     */
	public String getAuthorize_status(){
		return authorize_status;
	}
	
    /**
     * 设置授权情况(允许或拒绝)
     * 数据库注释: $RM_AUTHORIZE_STATUS=授权情况{ 0=拒绝, 1=允许 }
     * @param authorize_status 授权情况(允许或拒绝)
     */
	public void setAuthorize_status(String authorize_status){
		this.authorize_status = authorize_status;
	}
	
    /**
     * 获得有附加数据
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 有附加数据
     */
	public String getIs_affix_data(){
		return is_affix_data;
	}
	
    /**
     * 设置有附加数据
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_affix_data 有附加数据
     */
	public void setIs_affix_data(String is_affix_data){
		this.is_affix_data = is_affix_data;
	}
	
    /**
     * 获得递归传播权限
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 递归传播权限
     */
	public String getIs_recursive(){
		return is_recursive;
	}
	
    /**
     * 设置递归传播权限
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_recursive 递归传播权限
     */
	public void setIs_recursive(String is_recursive){
		this.is_recursive = is_recursive;
	}
	
    /**
     * 获得访问方式代码
     * 
     * @return 访问方式代码
     */
	public String getAccess_type(){
		return access_type;
	}
	
    /**
     * 设置访问方式代码
     * 
     * @param access_type 访问方式代码
     */
	public void setAccess_type(String access_type){
		this.access_type = access_type;
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
package org.quickbundle.orgauth.itf.vo;

public interface IRmAuthorizeResourceVo{

	/**
	 * @return RM_AUTHORIZE_RESOURCE的id
	 */
	public String getAuthorize_resource_id();
	
    /**
     * @return RM_AUTHORIZE的id
     */
    public String getAuthorize_id();
    
    /**
     * @return 资源原始id
     */
    public String getOld_resource_id();
    
    /**
     * @return 默认能否直接访问（公开权限）
     */
    public String getDefault_access();
    
    /**
     * @return 有无附加数据
     */
    public String getIs_affix_data();
    
    /**
     * @return 递归传播权限
     */
    public String getIs_recursive();
    
    /**
     * @return 访问方式代码
     */
    public String getAccess_type();
    
    /**
     * @return 树形编码
     */
    public String getTotal_code();
    
    /**
     * @return 名称
     */
    public String getName();
}

package org.quickbundle.orgauth.util;

import java.util.List;
import java.util.Set;

import org.quickbundle.project.common.vo.RmCommonVo;

public interface IRmOrgService {
	
	/**
	 * 取parent_party_id的后代中的指定类型列表
	 * 
	 * @param parent_party_id
	 * @param party_view_id
	 * @param aParty_type_bs_keyword
	 * @return
	 */
	public List<RmCommonVo> listDescendant(String parent_party_id, String view_id, String[] aShow_bk, boolean lazyInit);
	
	/**
	 * 获取partyId的父团体
	 * @param partyId
	 * @param viewId
	 * @return
	 */
	public List<RmCommonVo> listParent(String partyId,String viewId);
	
	/**
	 * 取employeeId对应的所有祖先团体(包括父)
	 * @param employeeId
	 * @return
	 */
	public List<RmCommonVo> listAncestor(String employeeId,String viewId);
	
	/**
	 * 取employeeId对应的所有祖先团体(包括父)
	 * @param employeeId
	 * @param lParent
	 * @return
	 */
	public List<RmCommonVo> listAncestor(String employeeId,String viewId, List<RmCommonVo> lParent);
	
	/**
	 * 获取partyId所属公司（例如：用户登录上级的所属组织公司）
	 * @param userId
	 * @param viewId
	 * @return
	 */
	public List<String> findAncestor4Bk(String partyId, String viewId, String bk);
	
	/**
	 * 取所有祖先(包含父节点)的party_id
	 * @param lAncestor
	 * @return
	 */
	public Set<String> listAncestor_party_id(List<RmCommonVo> lAncestor);
	
	/**
	 * 从下往上取祖先，取到bs_keyword这一级为止
	 * @param lAncestor
	 * @param bs_keyword
	 * @return
	 */
	public Set<String> listOwner_party_id(List<RmCommonVo> lAncestor, String bs_keyword);
	
	/**
	 * 从下往上取祖先，返回bs_keyword这一级
	 * @param lAncestor
	 * @param bs_keyword
	 * @return
	 */
	public String findOwner_party_id(List<RmCommonVo> lAncestor, String bs_keyword);
	
	/**
	 * 根据员工获取所在的公司（递归上一级查询,如果上一级是部门继续向上查找）
	 * @param employeeId 员工ID
	 * @return RmCommonVo 公司对象集合(id,name,bs_keyword)
	 */
	public String getCompany(List<RmCommonVo> lAncestor, String employeeId);
	
	/**
	 * 根据员工ID获取所对应的角色
	 * @param employeeId 员工ID
	 * @return List<RmCommonVo> 角色结果集(id,name)
	 */
	public List<RmCommonVo> getRmRole(String  employeeId);
	
	/**
	 * 根据员工ID、所在公司ID 获取所对应的角色+全局角色
	 * @param employeeId 员工ID
	 * @return List<RmCommonVo> 角色结果集(id,name)
	 */
	public List<RmCommonVo> getRmRole(String  employeeId, String companyId,String viewId);
	/**
	 * 根据公司ID获取所有下级公司
	 * @param companyId 公司ID
	 * @return List<RmCommonVo> 公司结果集(id,name,unit_level,company_code)
	 */
	public List<RmCommonVo> getCompanyAllList(String companyId,String viewId);
	
	/**
	 * 根据公司ID获取下一级的公司
	 * @param companyId 公司ID
	 * @return List<RmCommonVo> 公司结果集(id,name,unit_level,company_code)
	 */
	public List<RmCommonVo> getCompanyNextList(String companyId, String viewId);
	
	/**
	 * 根据用户所在公司ID，从RM_ROLE和RM_PARTY_ROLE表同时过滤后的 ROLE_ID列表
	 * @param userId 用户ID
	 * @param companyId 公司ID
	 * @return List<RmCommonVo> 角色结果集(id,name)
	 */
	public List<RmCommonVo> getRoleFilterByCompany(String  userId, String companyId,String viewId);
}
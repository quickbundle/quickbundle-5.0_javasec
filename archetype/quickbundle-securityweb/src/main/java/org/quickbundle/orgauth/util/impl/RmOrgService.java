package org.quickbundle.orgauth.util.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.util.RmOrderCodes;
import org.quickbundle.util.RmSequenceSet;
import org.springframework.jdbc.core.RowMapper;

import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmPartyTypeCache;
import org.quickbundle.orgauth.util.IRmOrgService;
import org.quickbundle.orgauth.util.RmPartyRelationCode;

public class RmOrgService extends RmService implements IRmOrgService , IOrgauthConstants  {
	
	/**
	 * 获得实例
	 * @return
	 */
	public static IRmOrgService getInstance() {
		return (IRmOrgService)RmBeanFactory.getBean("IRmOrgService");
	}
	
	/**
	 * 取parent_party_id的后代中的指定类型列表
	 * 
	 * @param parent_party_id
	 * @param party_view_id
	 * @param aParty_type_bs_keyword
	 * @param lazyInit
	 * @return
	 */
	public List<RmCommonVo> listDescendant(String parent_party_id, String viewId, String[] aShow_bk, boolean lazyInit) {
		List<RmCommonVo> lReturn = new ArrayList<RmCommonVo>();
		//查询树节点的sql语句
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select PR.PARENT_PARTY_ID, PR.CHILD_PARTY_ID, PR.PARENT_PARTY_CODE, PR.CHILD_PARTY_CODE, " +
				"PR.CHILD_IS_LEAF, PR.CHILD_PARTY_NAME, P.PARTY_TYPE_ID as CHILD_PARTY_TYPE_ID, PT.ICON as CHILD_ICON, " +
				"PT.BS_KEYWORD as CHILD_BS_KEYWORD, PR.CHILD_IS_MAIN_RELATION, PR.ORDER_CODE from RM_PARTY_RELATION PR " +
				"join RM_PARTY P on PR.CHILD_PARTY_ID=P.ID " +
				"join RM_PARTY_TYPE PT on P.PARTY_TYPE_ID=PT.ID where");
		
		//懒加载，只获取1层子节点
		if(lazyInit) {
			//如果parent_party_code未指定，按parent_party_id查询
			if(parent_party_id == null || "".equals(parent_party_id)) {
				sbSql.append(" (PR.PARENT_PARTY_ID is null or PR.PARENT_PARTY_ID='')");
			} else {
				sbSql.append(" PR.PARENT_PARTY_ID=" + parent_party_id);
			}

			//如果show_bk不为空，过滤
			if(aShow_bk.length > 0) {
				sbSql.append(" and PT.BS_KEYWORD in (" + RmStringHelper.parseToSQLStringApos(aShow_bk) + ")");
			}
		} else {
			//如果parent_party_code未指定，按parent_party_id查询
			StringBuilder sql_parentParty = new StringBuilder();
			sql_parentParty.append("select PR.CHILD_PARTY_CODE, PARENT_PARTY_CODE, CHILD_IS_MAIN_RELATION from RM_PARTY_RELATION PR where PR.PARTY_VIEW_ID=" + viewId);
			if(parent_party_id == null || "".equals(parent_party_id)) {
				sql_parentParty.append(" and (PR.PARENT_PARTY_ID is null or PR.PARENT_PARTY_ID='')");
			} else {
				sql_parentParty.append(" and PR.PARENT_PARTY_ID=" + parent_party_id);
			}
			List<String> lParentPartyCode = RmProjectHelper.getCommonServiceInstance().query(sql_parentParty.toString(), new RowMapper() {
			    public Object mapRow(ResultSet rs, int i) throws SQLException {
			    	return rs.getString("CHILD_PARTY_CODE");
			    }
			});
			int index = 0;
			if(lParentPartyCode.size() > 0) {
				for(String parentPartyCode : lParentPartyCode) {
					if(index == 0) {
						sbSql.append(" (PR.CHILD_PARTY_CODE like '" + parentPartyCode + "%'");
					} else {
						sbSql.append(" or PR.CHILD_PARTY_CODE like '" + parentPartyCode + "%'");
					}
					index ++;
				}
				sbSql.append(")");
			} else {
				sbSql.append(" PR.CHILD_PARTY_CODE like '" + NOT_EXIST_ID + "%'");
			}
			//如果show_bk不为空，过滤
			if(aShow_bk.length > 0) {
				sbSql.append(" and PT.BS_KEYWORD in (" + RmStringHelper.parseToSQLStringApos(aShow_bk) + ")");
			}
		}
		sbSql.append(" and PR.PARTY_VIEW_ID=" + viewId); 
		List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery(sbSql.toString());
		//内存排序
		Map<RmOrderCodes, RmCommonVo> mTree = new TreeMap<RmOrderCodes, RmCommonVo>();
		for(RmCommonVo cvo : lcvo) {
			RmOrderCodes orderCodes = null;
			if(lazyInit) {
				orderCodes = new RmOrderCodes(cvo.getString("order_code"), cvo.getString("child_party_code"));
			} else {
				orderCodes = new RmOrderCodes(cvo.getString("parent_party_code"), cvo.getString("order_code"), cvo.getString("child_party_code"));
			}
			mTree.put(orderCodes, cvo);
		}
		for(Map.Entry<RmOrderCodes, RmCommonVo> en : mTree.entrySet()) {
			lReturn.add(en.getValue());
		}
		return lReturn;
	}
	
	/**
	 * 获取partyId的父团体
	 * @param partyId
	 * @param viewId
	 * @return
	 */
	public List<RmCommonVo> listParent(String partyId,String viewId){
		//取当前用户绑定的的父级团体
		String sqlParent = "select PR.PARENT_PARTY_ID, PR.PARENT_PARTY_CODE from RM_PARTY_RELATION PR WHERE PR.CHILD_PARTY_ID=" + partyId + " and PR.PARTY_VIEW_ID=" + viewId;
		return RmProjectHelper.getCommonServiceInstance().doQuery(sqlParent);
	}
	
	/**
	 * 取partyId对应的所有祖先团体(包括父)
	 * @param employeeId
	 * @return
	 */
	public List<RmCommonVo> listAncestor(String partyId, String viewId) {
		return listAncestor(partyId, viewId, null);
	}
	
	/**
	 * 取partyId对应的所有祖先团体(包括父)
	 * @param employeeId
	 * @return
	 */
	public List<RmCommonVo> listAncestor(String partyId, String viewId, List<RmCommonVo> lParent) {
		List<RmCommonVo> lAncestor = new ArrayList<RmCommonVo>();
		if(lParent == null) {
			lParent = listParent(partyId, viewId);
		}
		if(lParent!=null && lParent.size()>0){
			Set<String> sAncestorChild_party_code = new RmSequenceSet<String>();
			for(RmCommonVo vo : lParent){
				String[] aAncestorChild_party_code = RmPartyRelationCode.splitPartyCode(vo.getString("parent_party_code"), viewId);
				for (int j = 0; j < aAncestorChild_party_code.length; j++) {
					sAncestorChild_party_code.add(aAncestorChild_party_code[j]);
				}
			}
			String sql_ancestor = "select PR.CHILD_PARTY_CODE, PR.CHILD_PARTY_ID, PR.CHILD_PARTY_NAME, PR.CHILD_PARTY_TYPE_ID FROM RM_PARTY_RELATION PR where PR.CHILD_PARTY_CODE in(" + RmStringHelper.parseToSQLStringApos(sAncestorChild_party_code.toArray(new String[0])) + ") and PR.PARTY_VIEW_ID=" + viewId;
			List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQuery(sql_ancestor);
			for(RmCommonVo vo : lvo) {
				vo.put("bs_keyword", RmPartyTypeCache.getPartyTypeId(vo.getString("child_party_type_id")).getBs_keyword());
			}
			lAncestor.addAll(lvo);
		}
		return lAncestor;
	}

	/**
	 * 取所有祖先(包含父节点)的party_id
	 * @param lAncestor
	 * @return
	 */
	public Set<String> listAncestor_party_id(List<RmCommonVo> lAncestor) {
		Set<String> sAncestor_party_id = new HashSet<String>();
		for (RmCommonVo vo : lAncestor) {
			sAncestor_party_id.add(vo.getString("child_party_id"));
		}
		return sAncestor_party_id;
	}
	
	/**
	 * 从下往上取祖先，取到bs_keyword这一级为止
	 * @param lAncestor
	 * @param bs_keyword
	 * @return
	 */
	public Set<String> listOwner_party_id(List<RmCommonVo> lAncestor, String bs_keyword) {
		Set<String> sAncestor_party_id = new RmSequenceSet<String>();
		Map<String, RmCommonVo> mChildCode = new TreeMap<String, RmCommonVo>();
		for (RmCommonVo vo : lAncestor) {
			mChildCode.put(vo.getString("child_party_code"), vo);
		}
		boolean findBs_keyword = false;
		String[] aChildCode = mChildCode.keySet().toArray(new String[0]);
		for (int i = 0; i < aChildCode.length; i++) {
			String thisCode = aChildCode[aChildCode.length - 1 - i];
			if(bs_keyword.equals(mChildCode.get(thisCode).getString("bs_keyword"))) {
				findBs_keyword = true;
			}
			sAncestor_party_id.add(mChildCode.get(thisCode).getString("child_party_id"));
			if(findBs_keyword) {
				break;
			}
		}
		if(!findBs_keyword) { //最终没发现bs_keyword
			RmLogHelper.warn(this.getClass(), "listOwner_party_id() not found bs_keyword");
			return new HashSet<String>(); 
		}
		return sAncestor_party_id;
	}
	
	
	/**
	 * 获取partyId所属公司（例如：用户登录上级的所属组织公司）
	 * @param userId
	 * @param viewId
	 * @return
	 */
	public List<String> findAncestor4Bk(String partyId, String viewId, String bk){
		List<String> result = new ArrayList<String>();
		Set<String> sResult = new TreeSet<String>();
		
		List<RmCommonVo> lParent = listParent(partyId, viewId);
		List<RmCommonVo> lAncestor = listAncestor(partyId, viewId, lParent);
		//用于索引的Map
		Map<String, RmCommonVo> mAncestor = new HashMap<String, RmCommonVo>();
		for(RmCommonVo vo : lAncestor) {
			mAncestor.put(vo.getString("child_party_code"), vo);
		}
		for(RmCommonVo voParent : lParent) { //循环userId直接的父团体
			//切分父节点编码为祖先编码数组
			String[] aAncestorCode = RmPartyRelationCode.splitPartyCode(voParent.getString("parent_party_code"), viewId);
			for(int i=0; i<aAncestorCode.length; i++) {
				RmCommonVo voAncestor = mAncestor.get(aAncestorCode[i]); //取到这个祖先的vo对象
				//如果这个祖先是公司，退出本循环
				if(RmPartyTypeCache.getPartyType(bk).getId().equals(voAncestor.getString("child_party_type_id"))) {
					sResult.add(aAncestorCode[i]);
					break;
				}
			}
		}
		//根据排序的结果处理result
		for(String companyChildPartyCode : sResult) {
			result.add(mAncestor.get(companyChildPartyCode).getString("child_party_id"));
		}
		return result;
	}

	/**
	 * 从下往上取祖先，返回bs_keyword这一级
	 * @param lAncestor
	 * @param bs_keyword
	 * @return
	 */
	public String findOwner_party_id(List<RmCommonVo> lAncestor, String bs_keyword) {
		Map<String, RmCommonVo> mChildCode = new TreeMap<String, RmCommonVo>();
		for (RmCommonVo vo : lAncestor) {
			mChildCode.put(vo.getString("child_party_code"), vo);
		}
		String[] aChildCode = mChildCode.keySet().toArray(new String[0]);
		for (int i = 0; i < aChildCode.length; i++) {
			String thisCode = aChildCode[aChildCode.length - 1 - i];
			if(bs_keyword.equals(mChildCode.get(thisCode).getString("bs_keyword"))) {
				return mChildCode.get(thisCode).getString("child_party_id");
			}
		}
		RmLogHelper.error(this.getClass(), "findOwner_party_id 最终没发现bs_keyword");
		return null; 
	}
	
	/**
	 * 根据子节点ID获取父节点节点详细信息（通用方法 ）
	 * @param bs_keyword 记录关键字
	 * @param childId 子节点
	 * @return
	 */
	public RmCommonVo getUpNodeInfo(String bs_keyword,String childId,String viewId){
		String strsql = "SELECT TEMP.* FROM RM_PARTY P JOIN "+bs_keyword+" TEMP ON TEMP.ID = P.ID WHERE P.ID = (SELECT PR.PARENT_PARTY_ID FROM RM_PARTY_RELATION PR WHERE PR.CHILD_PARTY_ID = "+childId+"  AND PR.PARTY_VIEW_ID = "+viewId+")";
		List<RmCommonVo> resultList = null;
		resultList = RmProjectHelper.getCommonServiceInstance().doQuery(strsql);
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据员工ID获取所对应的角色
	 * @param employeeId 员工ID
	 * @return List<RmCommonVo> 角色结果集(id,name)
	 */
	public List<RmCommonVo> getRmRole(String  employeeId){
		//TODO 必须按照org_id获得新的角色
		String strsql = "SELECT R.ID, R.NAME FROM RM_PARTY P JOIN RM_PARTY_ROLE MPR  ON P.ID = MPR.OWNER_PARTY_ID JOIN RM_ROLE R ON R.ID = MPR.ROLE_ID WHERE P.ID ="+employeeId;
		List<RmCommonVo> resultList = null;
		resultList = RmProjectHelper.getCommonServiceInstance().doQuery(strsql);
		if(resultList != null && resultList.size() > 0){
			return resultList;
		}
		return null;
	}
	/**
	 * 根据员工ID、所在公司ID 获取所对应的角色+全局角色 (只从RM_ROLE表过滤)
	 * @param employeeId 员工ID
	 * @return List<RmCommonVo> 角色结果集(id,name)
	 */
	public List<RmCommonVo> getRmRole(String  employeeId, String companyId,String viewId){
		String strsql = "select pr.ROLE_ID,r.NAME from  RM_PARTY_ROLE pr join RM_PARTY p on p.id = pr.owner_party_id join RM_ROLE r on r.id = pr.role_id where  p.id = '"+employeeId+"' and  (r.OWNER_ORG_ID = '"+companyId+"' or r.IS_SYSTEM_LEVEL = '1')"; 
		List<RmCommonVo> resultList = null;
		resultList = RmProjectHelper.getCommonServiceInstance().doQuery(strsql);
		if(resultList != null && resultList.size() > 0){
			return resultList;
		}
		return null;
	}
	
	/**
	 * 根据用户所在公司ID，从RM_ROLE和RM_PARTY_ROLE表同时过滤后的 ROLE_ID列表
	 * @param userId 用户ID
	 * @param companyId 公司ID
	 * @return List<RmCommonVo> 角色结果集(id,name)
	 */
	public List<RmCommonVo> getRoleFilterByCompany(String  userId, String companyId,String viewId){
		List<RmCommonVo> listVo= RmProjectHelper.getCommonServiceInstance().doQuery("select R.ID,R.ROLE_CODE,R.NAME from RM_ROLE R join RM_PARTY_ROLE PR on R.ID = PR.ROLE_ID where PR.OWNER_PARTY_ID = "+userId+" and ((R.IS_SYSTEM_LEVEL = '1' and (PR.OWNER_ORG_ID IS NULL or PR.OWNER_ORG_ID ='" + companyId + "')) or (r.is_system_level = '0' and R.OWNER_ORG_ID ='" + companyId + "'))");
		if(listVo.size()>0){
			return listVo;	
		}
		return null;
	}
	
	public void resetRoleFilterByCompany(HttpServletRequest request, List<RmCommonVo> lVoRole) {
		//TODO 改变session中的
		
	}
	
	/**
	 * 根据员工获取所在的公司（递归上一级查询,如果上一级是部门继续向上查找）
	 * @param employeeId 员工ID
	 * @return RmCommonVo 公司对象集合(id,name,bs_keyword)
	 */
	public String getCompany(List<RmCommonVo> lAncestor, String employeeId){
		return findOwner_party_id(lAncestor, IOrgauthConstants.OrgTree.COMPANY.value());
	}
	
	
	/**
	 * 根据公司ID获取所有下级公司
	 * @param companyId 公司ID
	 * @return List<RmCommonVo> 公司结果集(id,name,unit_level,company_code)
	 */
	public List<RmCommonVo> getCompanyAllList(String companyId,String viewId){
		String strsql = "SELECT P.ID,P.NAME,C.UNIT_LEVEL,C.OU FROM RM_PARTY P JOIN RM_PARTY_RELATION PR ON PR.CHILD_PARTY_ID = P.ID JOIN RM_PARTY_TYPE PT ON PT.ID = P.PARTY_TYPE_ID JOIN TM_COMPANY C ON C.ID=P.ID WHERE C.USABLE_STATUS='1' and PR.CHILD_PARTY_CODE LIKE '%"+companyId+"%' AND PR.PARTY_VIEW_ID='"+viewId+"' AND PT.BS_KEYWORD='"+IOrgauthConstants.OrgTree.COMPANY.value()+"' ";
		List<RmCommonVo> resultList = null;
		resultList = RmProjectHelper.getCommonServiceInstance().doQuery(strsql);
		if(resultList != null && resultList.size() > 0){
			return resultList;
		}
		return null;
	}
	
	/**
	 * 根据公司ID获取下一级的公司
	 * @param companyId 公司ID
	 * @return List<RmCommonVo> 公司结果集(id,name,unit_level,company_code)
	 */
	public List<RmCommonVo> getCompanyNextList(String companyId,String viewId){
		String strsql = "SELECT P.ID,P.NAME,C.UNIT_LEVEL,C.OU FROM RM_PARTY P JOIN RM_PARTY_RELATION PR ON PR.CHILD_PARTY_ID = P.ID JOIN RM_PARTY_TYPE PT ON PT.ID = P.PARTY_TYPE_ID JOIN TM_COMPANY C ON C.ID=P.ID WHERE C.USABLE_STATUS='1' and PR.PARENT_PARTY_CODE =(select PR2.CHILD_PARTY_CODE from RM_PARTY_RELATION PR2 WHERE PR2.CHILD_PARTY_ID="+companyId+" and PR2.PARTY_VIEW_ID="+viewId+") AND PR.PARTY_VIEW_ID='"+viewId+"' AND PT.BS_KEYWORD='"+IOrgauthConstants.OrgTree.COMPANY.value()+"' ";
		List<RmCommonVo> resultList = null;
		resultList = RmProjectHelper.getCommonServiceInstance().doQuery(strsql);
		if(resultList != null && resultList.size() > 0){
			return resultList;
		}
		return null;
	}
}
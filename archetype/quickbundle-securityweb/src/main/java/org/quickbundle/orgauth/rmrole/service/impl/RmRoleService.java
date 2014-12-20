//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmrole/service/impl/RmRoleService.java
//代码生成时,系统时间: 2010-11-27 22:08:38
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmrole.service.impl --> RmRoleService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:38 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmrole.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmPartyTypeCache;
import org.quickbundle.orgauth.rmauthorize.service.IRmAuthorizeService;
import org.quickbundle.orgauth.rmauthorize.util.IRmAuthorizeConstants;
import org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo;
import org.quickbundle.orgauth.rmparty.service.impl.RmPartyService;
import org.quickbundle.orgauth.rmparty.util.IRmPartyConstants;
import org.quickbundle.orgauth.rmparty.vo.RmPartyVo;
import org.quickbundle.orgauth.rmpartyrole.service.impl.RmPartyRoleService;
import org.quickbundle.orgauth.rmpartyrole.util.IRmPartyRoleConstants;
import org.quickbundle.orgauth.rmpartyrole.vo.RmPartyRoleVo;
import org.quickbundle.orgauth.rmrole.dao.IRmRoleDao;
import org.quickbundle.orgauth.rmrole.service.IRmRoleService;
import org.quickbundle.orgauth.rmrole.util.IRmRoleConstants;
import org.quickbundle.orgauth.rmrole.vo.RmRoleVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmRoleService extends RmService implements IRmRoleService, IRmRoleConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmRoleDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmRoleDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmRoleDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmRoleVo vo) {
    	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
    		RmPartyService pService = (RmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY);
    		RmPartyVo pvo = new RmPartyVo();
    		pvo.setParty_type_id(RmPartyTypeCache.getSingleton().getPartyType(IRmRoleConstants.TABLE_NAME).getId());
    		pvo.setName(vo.getName());
    		pvo.setIs_inherit("0");
    		String id = pService.insert(pvo);
    		//partyid设置到user
    		vo.setId(id);
    	}
    	String id = getDao().insert(vo);
    	RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入1条角色记录,id=" + String.valueOf(id)+",角色名称="+vo.getName()+",角色编码="+vo.getRole_code());
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmRoleVo[] vos) {
    	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
    		//TODO 
    	}
        String[] aId = getDao().insert(vos);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了" + vos.length + "条记录,id=" + RmStringHelper.ArrayToString(aId, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        return aId;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id) {
    	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
    		//TODO 
    	}
		int sum = getDao().delete(id);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(String ids[]) {
    	int sum = 0;
    	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
    		RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_ROLE set usable_status='0' where id in("+RmStringHelper.parseToSQLString(ids)+")");
    		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + ids.length + "条记录,id=" + RmStringHelper.parseToSQLString(ids));
    	}else{
    		//sum = getDao().delete(ids);
    	}
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmRoleVo find(String id) {
		RmRoleVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmRoleVo vo) {
    	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
    		//TODO 
    	}
		int sum = getDao().update(vo);
		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了1条角色记录,id=" + String.valueOf(vo.getId())+",角色名称="+vo.getName()+",角色编码="+vo.getRole_code());
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmRoleVo[] vos) {
    	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
    		//TODO 
    	}
		int[] sum = getDao().update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "批量更新了" + finalSum + "条记录);
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return finalSum;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmRoleVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmRoleVo> lInsert = new ArrayList<RmRoleVo>();
		List<RmRoleVo> lUpdate = new ArrayList<RmRoleVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmRoleVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmRoleVo[0]));
		}
		return sum_insert_update;
	}

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition) {
		int sum = getDao().getRecordCount(queryCondition);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "查询到了总记录数,count=" + sum + ", queryCondition=" + queryCondition);
		return sum;
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<RmRoleVo> queryByCondition(String queryCondition, String orderStr) {
        return queryByCondition(queryCondition, orderStr, -1, -1);
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<RmRoleVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
        return queryByCondition(queryCondition, orderStr, startIndex, size, false);
    }
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     */
    public List<RmRoleVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmRoleVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }
    /**
     * 插入中间表RM_PARTY_ROLE数据(适用于角色不带公司，暂时保留)
     * 
     * @param  id 团体ID
     * @param  role_ids 角色id 
     * @return 插入的owner_party_id列表
     */
    /**
    public String[] insertRm_party_roles(String id, String[] role_ids) {
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	if(role_ids!=null && role_ids.length >0 ){
        	RmPartyRoleService partyRoleService = (RmPartyRoleService)RmBeanFactory.getBean(IRmPartyRoleConstants.SERVICE_KEY);
        	List lExistId = cs.doQuery("select ROLE_ID FROM RM_PARTY_ROLE where OWNER_PARTY_ID=" + id + " and ROLE_ID in(" + RmStringHelper.parseToString(role_ids) + ")", new RowMapper() {
    			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    				return rs.getString("role_id");
    			}
    		});
        	Set<String> sOwner_party_id = new HashSet<String>();
        	for(String role_id : role_ids) {
        		if(!lExistId.contains(role_id)) {
        			sOwner_party_id.add(role_id);
        		}
        	}
        	
        	if(sOwner_party_id.size() > 0) {
        		List<RmPartyRoleVo> prList = new ArrayList<RmPartyRoleVo>();
            	for (String role_id1 : sOwner_party_id) {
                	RmPartyRoleVo partyRoleVo = new RmPartyRoleVo();
                	partyRoleVo.setRole_id(role_id1);
                	partyRoleVo.setOwner_party_id(id);
                	if(RmStringHelper.checkNotEmpty(RmProjectHelper.getCurrentThreadRequest().getParameter("company_id_"+role_id1))){
                		partyRoleVo.setOwner_org_id(RmProjectHelper.getCurrentThreadRequest().getParameter("company_id_"+role_id1));
                	}
                	RmVoHelper.markCreateStamp(RmProjectHelper.getCurrentThreadRequest(),partyRoleVo);  //打创建时间,IP戳
                	prList.add(partyRoleVo);
        		}
            	partyRoleService.insert(prList.toArray(new RmPartyRoleVo[0]));
        	}
        	//删除角色
        	if(role_ids!=null&&role_ids.length>0){
        		cs.doUpdate("delete from  RM_PARTY_ROLE PR where PR.OWNER_PARTY_ID="+id+" and PR.ROLE_ID NOT in("+RmStringHelper.parseToSQLStringApos(role_ids)+")");
        	}
        	return sOwner_party_id.toArray(new String[0]);
    	}
    	else{
    		cs.doUpdate("delete from  RM_PARTY_ROLE PR where PR.OWNER_PARTY_ID="+id+"");
    	}
        return new String[0];
    } */  
    
    /**
     * 插入中间表RM_PARTY_ROLE数据
     * 
     * @param  id 团体ID
     * @param  role_ids 角色id 
     * @return 插入的owner_party_id列表
     */
    public String[] insertRm_party_roles(String id, String[] role_ids) {
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	if(RmRequestMonitor.getCurrentThreadRequest() == null){
    		return null;
    	}
    	if(role_ids!=null && role_ids.length >0){
        	RmPartyRoleService partyRoleService = (RmPartyRoleService)RmBeanFactory.getBean(IRmPartyRoleConstants.SERVICE_KEY);
        	if(role_ids.length > 0) {
        		cs.doUpdate("delete from  RM_PARTY_ROLE where OWNER_PARTY_ID='"+id+"'"); //先删除原有的角色关系
        		List<RmPartyRoleVo> prList = new ArrayList<RmPartyRoleVo>();
            	for (String role_id1 : role_ids) {
            		if(RmStringHelper.checkNotEmpty(RmRequestMonitor.getCurrentThreadRequest().getParameter("company_id_"+role_id1))){
            			String[] companyId =  RmStringHelper.parseToArray(RmRequestMonitor.getCurrentThreadRequest().getParameter("company_id_"+role_id1)) ;
            			for(String cId:companyId){
                        	RmPartyRoleVo partyRoleVo = new RmPartyRoleVo();
                        	partyRoleVo.setRole_id(role_id1);
                        	partyRoleVo.setOwner_party_id(id);
                        	partyRoleVo.setOwner_org_id(cId);
                        	RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),partyRoleVo);  //打创建时间,IP戳
                        	prList.add(partyRoleVo);
            			}
            		}else{
                    	RmPartyRoleVo partyRoleVo = new RmPartyRoleVo();
                    	partyRoleVo.setRole_id(role_id1);
                    	partyRoleVo.setOwner_party_id(id);
                    	partyRoleVo.setOwner_org_id("");
                    	RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),partyRoleVo);  //打创建时间,IP戳
                    	prList.add(partyRoleVo);
            		}
        		}
            	partyRoleService.insert(prList.toArray(new RmPartyRoleVo[0]));
            	RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "给用户分配了角色，共" + prList.toArray(new RmPartyRoleVo[0]).length + "条记录");
        	}
        	return role_ids;
    	}
    	else{
    		cs.doUpdate("delete from RM_PARTY_ROLE where RM_PARTY_ROLE.OWNER_PARTY_ID="+id+"");
    	}
        return new String[0];
    }
       
    /**
     * 功能: 删除中间表RMM_PARTY_ROLE数据
     * 
     * @param role_id
     * @param owner_party_ids
     * @return 删除的记录数
     */
    public int deleteRm_party_role(String role_id, String[] owner_party_ids) {
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	return cs.doUpdate("delete from RM_PARTY_ROLE where ROLE_ID=" + role_id + " and OWNER_PARTY_ID IN(" + RmStringHelper.parseToString(owner_party_ids) + ")");
    }
    
    /**
     * 授权处理，删除以前的授权信息添加新的授权信息
     * @param role_id 角色/团体ID
     * @param nodeCodes  功能菜单编码
     */
	public void insertPartyResource(RmFunctionNodeVo[] vos ,String role_id) {
		IRmAuthorizeService authorizeService = (IRmAuthorizeService)RmBeanFactory.getBean(IRmAuthorizeConstants.SERVICE_KEY);
		authorizeService.insertDeleteAuthorizeResource_record(new String[]{role_id}, vos, IOrgauthConstants.Authorize.FUNCTION_NODE.id());
		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "给角色重新授权，角色ID="+role_id);
    }
}
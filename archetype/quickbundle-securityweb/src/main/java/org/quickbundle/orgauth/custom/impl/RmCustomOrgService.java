package org.quickbundle.orgauth.custom.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.config.RmConfig;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmPartyTypeCache;
import org.quickbundle.orgauth.custom.IRmCustomOrgService;
import org.quickbundle.orgauth.rmparty.service.IRmPartyService;
import org.quickbundle.orgauth.rmparty.service.impl.RmPartyService;
import org.quickbundle.orgauth.rmparty.util.IRmPartyConstants;
import org.quickbundle.orgauth.rmpartyrelation.service.impl.RmPartyRelationService;
import org.quickbundle.orgauth.rmpartyrelation.util.IRmPartyRelationConstants;
import org.quickbundle.orgauth.rmpartyrelation.vo.RmPartyRelationVo;
import org.quickbundle.orgauth.rmpartyrole.service.IRmPartyRoleService;
import org.quickbundle.orgauth.rmpartyrole.util.IRmPartyRoleConstants;
import org.quickbundle.orgauth.rmpartyrole.vo.RmPartyRoleVo;
import org.quickbundle.orgauth.rmrole.service.IRmRoleService;
import org.quickbundle.orgauth.rmrole.util.IRmRoleConstants;
import org.quickbundle.orgauth.rmrole.vo.RmRoleVo;
import org.quickbundle.orgauth.rmuser.service.IRmUserService;
import org.quickbundle.orgauth.rmuser.service.impl.RmUserService;
import org.quickbundle.orgauth.rmuser.util.IRmUserConstants;
import org.quickbundle.orgauth.rmuser.vo.RmUserVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;

public class RmCustomOrgService extends RmService implements IRmCustomOrgService {
	
	/**
	 * 获得实例
	 * @return
	 */
	public static IRmCustomOrgService getInstance() {
		return (IRmCustomOrgService)RmBeanFactory.getBean("IRmCustomOrgService");
	}
	
	/**
	 * 获得用户Service
	 * @return
	 */
	private IRmUserService getRmUserService() {
		return (IRmUserService)RmBeanFactory.getBean(IRmUserConstants.SERVICE_KEY);
	}
	
	/**
	 * 获得团体Service
	 * @return
	 */
	private IRmPartyService getRmPartyService() {
		return (IRmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY);
	}
	
    /**
     * 添加新用户并增加组织关系
     * @param vo
     * @param pratyViewId
     * @param name
     * @param partyTypeId
     * @param parentPartyIds
     * @param isInherit
     * @return
     */
    public String insertParty(RmUserVo vo,String pratyViewId,String partyTypeId,String[] parentPartyIds ,String[] parentPartyNames,String isInherit){
    	String partyId = "";
    	
    	RmUserService uService = (RmUserService)RmBeanFactory.getBean(IRmUserConstants.SERVICE_KEY);
    	RmPartyService pService = (RmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY);
    	
    	RmPartyRelationVo prvo = new  RmPartyRelationVo();
    	prvo.setParty_view_id(pratyViewId);
		//prvo.setParent_party_code(vo.getParent_party_code());
		//prvo.setParent_party_type_id(vo.getParent_party_type_id());
		prvo.setChild_party_type_id(partyTypeId);
		prvo.setChild_party_name(vo.getName());
        partyId = pService.insertParty(prvo , parentPartyIds , parentPartyNames ,isInherit);
    	vo.setId(partyId);
    	/**临时插入角色
    	RmPartyRoleService rService = (RmPartyRoleService)RmBeanFactory.getBean(IRmPartyRoleConstants.SERVICE_KEY);
    	String [] strs = new String[3];
    	strs[0] = "1000201100000403242";
    	strs[1] = "1000201100000403163";
    	strs[2] = "1000201100000403164";
    	RmPartyRoleVo[] vos = new RmPartyRoleVo[3];
    	int i = 0 ;
    	for(String s:strs){
    		RmPartyRoleVo vo1 = new RmPartyRoleVo();
    		vo1.setRole_id(s);
    		vo1.setOwner_party_id(partyId);
    		vos[i] = vo1;
    		i++;
    	}
    	rService.insert(vos);
    	*/
    	return getRmUserService().insert(vo);
    }
    
    /**
     * 更新用户信息和团体关系信息
     * @param vo
     * @param pratyViewId
     * @param partyTypeId
     * @param parentPartyIds
     * @param isInherit
     * @return
     */
    public int updateParty(RmUserVo vo,String pratyViewId,String oldName,String partyTypeId,String[] parentPartyIds ,String[] oldParentPartyIds,String[] parentPartyNames,String isInherit){
    	getRmPartyService().updateParty(vo.getId(),pratyViewId,oldName, vo.getName(), partyTypeId, parentPartyIds,parentPartyNames,oldParentPartyIds ,isInherit);
		/*根据公司删除角色记录
    	List<String> partyList = new ArrayList<String>();
    	for(String party:parentPartyIds){
    		partyList.add(party);
    	}
    	List<String> companyList = new ArrayList<String>();
    	if(partyList.size() > 0 ){
    		//过滤用户废弃的组织ID
        	for(String party:oldParentPartyIds){
        		if(!partyList.contains(party)){
        			companyList.add(party);
        		}
        	}
    	}
    	if(companyList.size() >0){
    		//List partyRoleList = RmProjectHelper.getCommonServiceInstance().doQuery("select ID from RM_PARTY_ROLE where OWNER_PARTY_ID="+vo.getId()+" and OWNER_ORG_ID in("+RmStringHelper.parseToSQLString(companyList.toArray(new String[0]))+")");
    		List partyRoleList = RmProjectHelper.getCommonServiceInstance().doQuery("select pr.id from RM_PARTY_ROLE pr join rm_role r on r.id=pr.ROLE_ID  where pr.OWNER_PARTY_ID="+vo.getId()+" and pr.OWNER_ORG_ID in(1000201100000000375) or r.owner_org_id in("+RmStringHelper.parseToSQLString(companyList.toArray(new String[0]))+") ");
    		System.out.println("=="+partyRoleList.size());
    	}
    	*/
    	RmProjectHelper.log(IRmUserConstants.TABLE_LOG_TYPE_NAME, "更新了1条记录,id=" + String.valueOf(vo.getId())+",login_id="+vo.getLogin_id()+",用户名="+vo.getName());
    	return getRmUserService().update(vo);
    }
    /**
     * 删除用户和团体团体关系
     * @param ids
     * @return
     */
    public int deleteParty(String[] ids){
    	int result = 0;
    	String[] deleteSql = new String[2];
    	deleteSql[0] = "delete from RM_PARTY_ROLE where RM_PARTY_ROLE.OWNER_PARTY_ID in ("+RmStringHelper.parseToString(ids)+")";
    	deleteSql[1] = "delete from RM_USER_ONLINE_RECORD where RM_USER_ONLINE_RECORD.USER_ID in ("+RmStringHelper.parseToString(ids)+")";
    	RmProjectHelper.getCommonServiceInstance().doUpdateBatch(deleteSql);
    	//用户物理删除
    	//result = getRmUserService().delete(ids);
    	//用户逻辑删除
    	RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_USER set USABLE_STATUS='0', LOGIN_ID =" + RmSqlHelper.getFunction(RmSqlHelper.Function.CONCAT, RmConfig.getSingleton().getDatabaseProductName(), "LOGIN_ID", "'$'", "ID") + " where ID in ("+RmStringHelper.parseToSQLString(ids)+")");
    	getRmPartyService().deleteParty(ids);
    	RmSqlCountCache.clearCount(IRmUserConstants.TABLE_NAME);  //清除用户count记录数缓存
    	RmProjectHelper.log(IRmUserConstants.TABLE_LOG_TYPE_NAME, "删除了"+ids.length+"用户记录,id=" + RmStringHelper.parseToSQLString(ids));
    	return result;
    }
    /**
     * 临时更新用户角色公司方法
     * @param diseaseVos
     * 
     *     	//临时导入数据方法
    	IRmCustomOrgService getOrgService = (IRmCustomOrgService)RmBeanFactory.getBean("IRmCustomOrgService");
    	getOrgService.insertTempRmUser();
     */
    public void insertTempRmUser(){
    	List<RmCommonVo> rmuserList  = RmProjectHelper.getCommonServiceInstance().doQuery("select u.*,um.c3,um.c1 from TMP_RM_USER_MODFIL um join rm_user u on u.email = um.c5");
    	List<RmCommonVo> companyList  = RmProjectHelper.getCommonServiceInstance().doQuery("select c.tm_code,c.id,c.name from TM_COMPANY c join RM_PARTY_RELATION pr on pr.child_party_id=c.id and c.usable_status='1' and pr.party_view_id="+IOrgauthConstants.PartyView.DEFAULT.id());
    	Map<String,RmCommonVo> mCompany = new HashMap<String, RmCommonVo>();
    	List<String> listMsg = new ArrayList<String>();
    	for(RmCommonVo cvo:companyList){
    		mCompany.put(cvo.getString("tm_code"), cvo);
    	}
    	IRmPartyRoleService rService = (IRmPartyRoleService)RmBeanFactory.getBean(IRmPartyRoleConstants.SERVICE_KEY);
    	IRmRoleService r1Service = (IRmRoleService)RmBeanFactory.getBean(IRmRoleConstants.SERVICE_KEY);
    	List<RmRoleVo>  rvoList= r1Service.queryByCondition("","",-1,-1,true);
    	Map<String,RmRoleVo> mRole = new HashMap<String, RmRoleVo>();
    	for(RmRoleVo vo1:rvoList){
    		mRole.put(vo1.getRole_code(), vo1);
    	}
    	RmPartyRelationService rrService  = (RmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
    	for(RmCommonVo vo:rmuserList){
            	String pratyViewId = IOrgauthConstants.PartyView.DEFAULT.id();
            	String partyTypeId = RmPartyTypeCache.getPartyType(IRmUserConstants.TABLE_NAME).getId();
            	String isInherit = "0";
            	String parentPartyIds = "";
            	if(mCompany.get(vo.getString("c3"))==null){
            		listMsg.add("---在管理组织视图下没有找到相关公司--ZJ编码=："+vo.getString("tm_code"));
            		continue;
            	}
            	parentPartyIds = mCompany.get(vo.getString("c3")).getString("id");
            	String parentPartyName = "";
            	parentPartyName = mCompany.get(vo.getString("c3")).getString("name");
            	
            	
				RmPartyRelationVo prvo = new RmPartyRelationVo();
				prvo.setParty_view_id("1000200700000000003");
				prvo.setChild_party_id(vo.getString("id"));
				prvo.setParent_party_id(parentPartyIds);
				prvo.setParent_party_name(parentPartyName);
				prvo.setChild_party_name(vo.getString("name"));
				//prvo.setParent_party_code(vo.getParent_party_code());
				prvo.setParent_party_type_id("1000200800000000001");
				prvo.setChild_party_type_id("1000200800000000013");
				prvo.setChild_is_main_relation("1");//主关系
				prvo.setChild_is_leaf("1");
				RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),prvo); 
				rrService.insert(prvo);
				
				//角色
				System.out.println("==角色："+vo.getString("c1"));
				String[] roles = RmStringHelper.parseToArrayIgnoreEmpty(vo.getString("c1"),"#");
				List<RmPartyRoleVo> roleVos = new ArrayList<RmPartyRoleVo>();
				for(String s:roles){
					List<RmCommonVo> rrList =RmProjectHelper.getCommonServiceInstance().doQuery("select * from RM_PARTY_ROLE rpr where rpr.role_id="+mRole.get(s).getId()+" and rpr.owner_party_id="+vo.getString("id"));
					if(rrList.size() == 0){
						RmPartyRoleVo rVo = new RmPartyRoleVo();
						//rVo.setOwner_org_id(parentPartyIds);
						rVo.setOwner_party_id(vo.getString("id"));
						rVo.setRole_id(mRole.get(s).getId());
						RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),rVo); 
						roleVos.add(rVo);
					}
				}
				rService.insert(roleVos.toArray(new RmPartyRoleVo[0]));
    	}
    	for(String s:listMsg){
    		System.out.println(s);
    	}
    }

	public int updateParty(RmUserVo vo, String pratyViewId, String oldName,
			String partyTypeId, String[] parentPartyIds,
			String[] oldParentPartyIds, String isInherit) {
    	return getRmUserService().update(vo);
	}
}

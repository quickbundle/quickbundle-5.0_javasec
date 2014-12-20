//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmparty/service/impl/RmPartyService.java
//代码生成时,系统时间: 2010-11-28 17:40:30
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmparty.service.impl --> RmPartyService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:30 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmparty.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.orgauth.itf.vo.IRmAuthorizeResourceVo;
import org.quickbundle.orgauth.rmauthorizeresource.service.IRmAuthorizeResourceService;
import org.quickbundle.orgauth.rmauthorizeresource.util.IRmAuthorizeResourceConstants;
import org.quickbundle.orgauth.rmparty.dao.IRmPartyDao;
import org.quickbundle.orgauth.rmparty.service.IRmPartyService;
import org.quickbundle.orgauth.rmparty.util.IRmPartyConstants;
import org.quickbundle.orgauth.rmparty.vo.RmPartyVo;
import org.quickbundle.orgauth.rmpartyrelation.service.IRmPartyRelationService;
import org.quickbundle.orgauth.rmpartyrelation.util.IRmPartyRelationConstants;
import org.quickbundle.orgauth.rmpartyrelation.vo.RmPartyRelationVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.springframework.jdbc.core.RowMapper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmPartyService extends RmService implements IRmPartyService, IRmPartyConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmPartyDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmPartyDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmPartyDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmPartyVo vo) {
        String id = getDao().insert(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" + String.valueOf(id));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmPartyVo[] vos) {
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
		int sum = getDao().delete(ids);
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
    public RmPartyVo find(String id) {
		RmPartyVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmPartyVo vo) {
		int sum = getDao().update(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getId()));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmPartyVo[] vos) {
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
	public int[] insertUpdateBatch(RmPartyVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmPartyVo> lInsert = new ArrayList<RmPartyVo>();
		List<RmPartyVo> lUpdate = new ArrayList<RmPartyVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmPartyVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmPartyVo[0]));
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
    public List<RmPartyVo> queryByCondition(String queryCondition, String orderStr) {
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
    public List<RmPartyVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<RmPartyVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmPartyVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }

    /**
     * 插入中间表RM_AUTHORIZE_RESOURCE_RECORD数据
     * 
     * @param party_id
     * @param authorize_resource_ids
     * @return 插入的authorize_resource_id列表
     */
    public String[] insertRm_authorize_resource_record(String bs_keyword, String party_id, String[] old_resource_ids) {
    	if(old_resource_ids.length == 0 || (old_resource_ids.length == 1 && old_resource_ids[0].trim().length() == 0)) {
    		return new String[0];
    	}
    	IRmAuthorizeResourceService arService = (IRmAuthorizeResourceService)RmBeanFactory.getBean(IRmAuthorizeResourceConstants.SERVICE_KEY);
    	Map<String, IRmAuthorizeResourceVo> mResource = arService.executeInitResource(bs_keyword, old_resource_ids);
    	String[] authorize_resource_ids = mResource.keySet().toArray(new String[0]);
    	
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	List<String> lExistId = cs.query("SELECT * FROM RM_AUTHORIZE_RESOURCE_RECORD WHERE PARTY_ID=" + party_id + " AND AUTHORIZE_RESOURCE_ID IN(" + RmStringHelper.parseToSQLStringApos(authorize_resource_ids) + ")", new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("AUTHORIZE_RESOURCE_ID");
			}
		});
    	Set<String> sAuthorize_resource_id = new HashSet<String>();
    	for(String authorize_resource_id : authorize_resource_ids) {
    		if(!lExistId.contains(authorize_resource_id)) {
    			sAuthorize_resource_id.add(authorize_resource_id);
    		}
    	}
    	if(sAuthorize_resource_id.size() > 0) {
    		String[] ids = RmIdFactory.requestId("RM_AUTHORIZE_RESOURCE_RECORD", sAuthorize_resource_id.size());
        	String[][] aaValue = new String[sAuthorize_resource_id.size()][3];
        	int index = 0;
        	for (String authorize_resource_id : sAuthorize_resource_id) {
        		aaValue[index][0] = ids[index];
        		aaValue[index][1] = party_id;
    			aaValue[index][2] = authorize_resource_id;
    			index ++;
    		}
        	cs.doUpdateBatch("INSERT INTO RM_AUTHORIZE_RESOURCE_RECORD (ID, PARTY_ID, AUTHORIZE_RESOURCE_ID, AUTHORIZE_STATUS, IS_AFFIX_DATA, IS_RECURSIVE, ACCESS_TYPE) VALUES(?, ?, ?, '1', '0', '0', '1')", aaValue);
    	}
        return sAuthorize_resource_id.toArray(new String[0]);
    }
    
    /**
     * 功能: 删除中间表RM_AUTHORIZE_RESOURCE_RECORD数据
     * 
     * @param party_id
     * @param authorize_resource_ids
     * @return 删除的记录数
     */
    public int deleteRm_authorize_resource_record(String party_id, String[] authorize_resource_ids) {
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	return cs.doUpdate("DELETE FROM RM_AUTHORIZE_RESOURCE_RECORD WHERE PARTY_ID=" + party_id + " AND AUTHORIZE_RESOURCE_ID IN(" + RmStringHelper.parseToString(authorize_resource_ids) + ")");
    }

    /**
     * 保存团体和团体关系
     * @param vo 团体关系ID
     * @param parentPartyIds
     * @param isInherit 
     * @return
     */
    public String insertParty(RmPartyRelationVo vo,String[] parentPartyIds, String [] parentPartyNames ,String is_inherit){
    	//团体
    	IRmPartyService pService = (IRmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY);
		RmPartyVo pvo = new RmPartyVo();
		pvo.setName(vo.getChild_party_name());
		pvo.setParty_type_id(vo.getChild_party_type_id());
		pvo.setIs_inherit(is_inherit);
		RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),pvo); 
		String id = pService.insert(pvo);
		//团体关系
		IRmPartyRelationService prService = (IRmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
		if(parentPartyIds != null && parentPartyIds.length > 0){
			List<RmPartyRelationVo> ppVoList = new ArrayList<RmPartyRelationVo>();
			int i = 0;
			for(String ppId:parentPartyIds){
				RmPartyRelationVo prvo = new RmPartyRelationVo();
				prvo.setParty_view_id(vo.getParty_view_id());
				prvo.setChild_party_id(id);
				prvo.setParent_party_id(ppId);
				prvo.setParent_party_name(RmStringHelper.prt(parentPartyNames[i]));
				prvo.setChild_party_name(pvo.getName());
				prvo.setParent_party_code(vo.getParent_party_code());
				prvo.setParent_party_type_id(vo.getParent_party_type_id());
				prvo.setChild_party_type_id(vo.getChild_party_type_id());
				prvo.setChild_is_main_relation("1");//主关系
				prvo.setChild_is_leaf("1");
				RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),prvo); 
				ppVoList.add(prvo);
				i++;
			}
			//保存
	    	prService.insert(ppVoList.toArray(new RmPartyRelationVo[0]));
			//更新父节点关系
			RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_PARTY_RELATION set CHILD_IS_LEAF='0' where CHILD_PARTY_ID in ("+RmStringHelper.parseToSQLString(parentPartyIds)+") and PARTY_VIEW_ID="+vo.getParty_view_id());
		}
		return id;
    }
    /**
     * 更新团体关系
     * @param id
     * @param pratyViewId
     * @param name
     * @param partyTypeId
     * @param parentPartyIds
     * @param isInherit
     * @return
     */
    public String updateParty (String id , String pratyViewId,String oldName,String name,String partyTypeId,String[] parentPartyIds ,String[] parentPartyNames,String[] oldParentPartyIds,String isInherit){
		
		//新增加团体关系
		IRmPartyRelationService prService = (IRmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
		//TODO 数组值比较方法不对
		if(parentPartyIds != null && parentPartyIds.length > 0 && !parentPartyIds.equals(oldParentPartyIds)){
			//先删除当前团体关系
			RmProjectHelper.getCommonServiceInstance().doUpdate("delete from RM_PARTY_RELATION where CHILD_PARTY_ID in (select PR.CHILD_PARTY_ID from RM_USER U join RM_PARTY_RELATION PR on U.ID=PR.CHILD_PARTY_ID where U.USABLE_STATUS='1' and U.ID="+id+") and PARTY_VIEW_ID="+pratyViewId);
			List<RmPartyRelationVo> ppVoList = new ArrayList<RmPartyRelationVo>();
			int i = 0;
			for(String ppId:parentPartyIds){
				RmPartyRelationVo prvo = new RmPartyRelationVo();
				prvo.setChild_party_id(id);
				prvo.setParty_view_id(pratyViewId);
				prvo.setParent_party_id(ppId);
				prvo.setChild_party_name(name);
				prvo.setParent_party_name(RmStringHelper.prt(parentPartyNames[i]));
				prvo.setChild_is_main_relation("1");//主关系
				prvo.setChild_is_leaf("1");
				RmVoHelper.markCreateStamp(RmRequestMonitor.getCurrentThreadRequest(),prvo); 
				ppVoList.add(prvo);
				i++;
			}
			//保存
	    	prService.insert(ppVoList.toArray(new RmPartyRelationVo[0]));
			//更新父节点关系 
			RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_PARTY_RELATION set CHILD_IS_LEAF='0' where CHILD_PARTY_ID in ("+RmStringHelper.parseToSQLString(parentPartyIds)+") and PARTY_VIEW_ID="+pratyViewId);
		}
        //更新团体名称
    	if(!name.equals(oldName)){
    		RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_PARTY set NAME='"+name+"' where ID="+id);
    	}
    	return id;
    }
    /**
     * 删除团体和所有视图下的团体关系
     * @param ids
     * @return
     */
    public int deleteParty(String[] ids){
    	int sum = 0 ;
    	List<String> deleListSql = new ArrayList<String>();
    	//得到要删除团体的父团体关系
    	List<RmCommonVo> partyRelaionList = RmProjectHelper.getCommonServiceInstance().doQuery("select PR.PARENT_PARTY_ID,PR.PARENT_PARTY_CODE,PR.CHILD_PARTY_CODE from RM_PARTY_RELATION PR where PR.CHILD_PARTY_ID in ("+RmStringHelper.parseToSQLString(ids)+")");
    	for(RmCommonVo cvo:partyRelaionList){
    		//查询当前节点的父节点下是否有子节点
    		List<RmCommonVo> listVo2=RmProjectHelper.getCommonServiceInstance().doQuery("select PR.CHILD_PARTY_CODE from RM_PARTY_RELATION PR where CHILD_PARTY_CODE!='"+cvo.getString("child_party_code")+"' and PR.PARENT_PARTY_CODE='"+cvo.getString("parent_party_code")+"'");
    		if(listVo2.size()==0){
    			//更新父节点
    			deleListSql.add("update RM_PARTY_RELATION PR set PR.CHILD_IS_LEAF='1' where CHILD_PARTY_CODE='"+cvo.getString("parent_party_code")+"'");
    		}
    	}
    	//删除团体关系
    	deleListSql.add("delete from RM_PARTY_RELATION where CHILD_PARTY_ID in ("+RmStringHelper.parseToSQLString(ids)+")");
    	//删除团体
    	deleListSql.add("delete from RM_PARTY where ID in ("+RmStringHelper.parseToSQLString(ids)+")");
    	RmProjectHelper.getCommonServiceInstance().doUpdateBatch(deleListSql.toArray(new String[0]));
    	return sum;
    }
    /**
     * 删除团体和团体关系记录(单条删除)
     * @param id
     * @param childPartyId
     * @param childPartyCode
     * @param parentPartyCode
     * @param view_id
     * @param delesql 扩展删除语句
     * @return
     */
    public String deleteParty(String childPartyId,String childPartyCode,String parentPartyCode,String view_id,String delesql){
    	String msg = "";
    	List<String> deleSqlList = new ArrayList<String>();
    	//根据视图查询当前节点下是否有子节点
    	List<RmCommonVo> listVo=RmProjectHelper.getCommonServiceInstance().doQuery("select PR.ID from RM_PARTY_RELATION PR where PR.PARENT_PARTY_CODE='"+childPartyCode+"' and PR.PARTY_VIEW_ID="+view_id);
    	if(listVo.size()>0){
    		//有子节点不能删除
    		msg = "删除失败!原因:该组织下有子节点不能删除!";
    		return msg;
    	}else{
    		//可以删除关系
    		deleSqlList.add("delete from RM_PARTY_RELATION where CHILD_PARTY_CODE='"+childPartyCode+"' and PARTY_VIEW_ID="+view_id);
    		//查询当前节点的父节点下是否有子节点
    		List<RmCommonVo> listVo2=RmProjectHelper.getCommonServiceInstance().doQuery("select PR.CHILD_PARTY_CODE from RM_PARTY_RELATION PR where CHILD_PARTY_CODE!='"+childPartyCode+"' and PR.PARENT_PARTY_CODE='"+parentPartyCode+"' and PR.PARTY_VIEW_ID="+view_id);
    		if(listVo2.size()==0){
    			//更新父节点
    			deleSqlList.add("update RM_PARTY_RELATION PR set PR.CHILD_IS_LEAF='1' where CHILD_PARTY_CODE='"+parentPartyCode+"' and PR.PARTY_VIEW_ID="+view_id);
    		}
    		//查询团体在所有视图下是否还被引用
    		List<RmCommonVo> listVo3=RmProjectHelper.getCommonServiceInstance().doQuery("select PR.CHILD_PARTY_CODE from RM_PARTY_RELATION PR where PR.CHILD_PARTY_ID="+childPartyId);
    		if(listVo3.size()==0){
    			//团体
    			deleSqlList.add("delete from RM_PARTY where ID="+childPartyId);
    			//逻辑删除
    			//deleSqlList.add("update from RM_PARTY set usable_status='0' where ID="+childPartyId);
    			if(RmStringHelper.checkNotEmpty(delesql)){
    				deleSqlList.add(delesql);
    			}
    		}
    	}
    	if(deleSqlList.size()>0){
    		RmProjectHelper.getCommonServiceInstance().doUpdateBatch(deleSqlList.toArray(new String[0]));
    		msg = "删除成功！";
    		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了1条组织团体记录,id=" + childPartyId+",childPartyCode="+childPartyCode);
    	}
    	return msg; 
    }
    /**
     * 只更新团体和团体关系名称
     * @param id 团体ID
     * @param name 名称
     * @param view_id 视图ID，如视图等于null，就更新所有视图下的团体关系
     */
    public int[] updatePartyName(String id , String name , String view_id){
    	List<String> sqlList = new ArrayList<String>();
    	sqlList.add("update RM_PARTY set NAME='"+name+"' where ID="+id);
    	if(RmStringHelper.checkNotEmpty(view_id)){
    		sqlList.add("update RM_PARTY_RELATION PR set PR.CHILD_PARTY_NAME='"+name+"' where PR.CHILD_PARTY_ID="+id+" and PR.PARTY_VIEW_ID="+view_id) ;
    	}
    	else{
    		sqlList.add("update RM_PARTY_RELATION PR set CHILD_PARTY_NAME='"+name+"' where CHILD_PARTY_ID="+id);
    	}
    	int[] sum = RmProjectHelper.getCommonServiceInstance().doUpdateBatch(sqlList.toArray(new String[0]));
    	return sum;
    }
}
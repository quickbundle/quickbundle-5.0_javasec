//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartyrelation/service/impl/RmPartyRelationService.java
//代码生成时,系统时间: 2010-11-28 17:40:29
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartyrelation.service.impl --> RmPartyRelationService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:29 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartyrelation.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.orgauth.cache.RmPartyTypeRelationRuleCache;
import org.quickbundle.orgauth.cache.RmPartyViewCache;
import org.quickbundle.orgauth.rmpartyrelation.dao.IRmPartyRelationDao;
import org.quickbundle.orgauth.rmpartyrelation.service.IRmPartyRelationService;
import org.quickbundle.orgauth.rmpartyrelation.util.IRmPartyRelationConstants;
import org.quickbundle.orgauth.rmpartyrelation.util.RmPartyRelationException;
import org.quickbundle.orgauth.rmpartyrelation.vo.RmPartyRelationVo;
import org.quickbundle.orgauth.rmpartytyperelationrule.service.IRmPartyTypeRelationRuleService;
import org.quickbundle.orgauth.rmpartytyperelationrule.util.IRmPartyTypeRelationRuleConstants;
import org.quickbundle.orgauth.rmpartytyperelationrule.util.RmPartyTypeRelationRuleException;
import org.quickbundle.orgauth.rmpartytyperelationrule.vo.RmPartyTypeRelationRuleVo;
import org.quickbundle.orgauth.util.RmPartyRelationCode;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmPartyRelationService extends RmService implements IRmPartyRelationService, IRmPartyRelationConstants {
    static Logger log = RmLogHelper.getLogger(RmPartyRelationService.class);
	
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmPartyRelationDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmPartyRelationDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmPartyRelationDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmPartyRelationVo vo) {
    	{
    		initDefaultValue(vo);
    		validatePartyRelation(vo);
    		initPartyChildCode(new RmPartyRelationVo[]{vo});
    	}
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
    public String[] insert(RmPartyRelationVo[] vos) {
    	for (int i = 0; i < vos.length; i++) {
    		initDefaultValue(vos[i]);
    		validatePartyRelation(vos[i]);
		}
    	initPartyChildCode(vos);
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
        RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条团体关系记录,id=" + RmStringHelper.parseToSQLString(ids).length());
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmPartyRelationVo find(String id) {
		RmPartyRelationVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmPartyRelationVo vo) {
    	{
    		initDefaultValue(vo);
    		validatePartyRelation(vo);
    	}
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
	public int update(RmPartyRelationVo[] vos) {
    	for (int i = 0; i < vos.length; i++) {
    		initDefaultValue(vos[i]);
    		validatePartyRelation(vos[i]);
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
	public int[] insertUpdateBatch(RmPartyRelationVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmPartyRelationVo> lInsert = new ArrayList<RmPartyRelationVo>();
		List<RmPartyRelationVo> lUpdate = new ArrayList<RmPartyRelationVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmPartyRelationVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmPartyRelationVo[0]));
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
    public List<RmPartyRelationVo> queryByCondition(String queryCondition, String orderStr) {
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
    public List<RmPartyRelationVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<RmPartyRelationVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmPartyRelationVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }
    /**
     * 根据团体ID更新相关的所有团体名称和团体关系名称
     * @param partyId 团体ID
     * @param name 修改的名称
     */
    public void updatePartyRelation(String partyId,String name ,String orderCode,String tmCode){
    	String [] updateSql = new String[2];
    	String strCode = "";
    	String sqlTmCode="";
    	if(RmStringHelper.checkNotEmpty(orderCode)){
    		strCode = orderCode;
    	}
    	if(RmStringHelper.checkNotEmpty(tmCode)){
    		sqlTmCode = " ,OLD_PARTY_ID='"+tmCode+"'";
    	}
    	updateSql[0] = "update RM_PARTY set NAME='"+name+"' "+sqlTmCode+" where ID="+partyId;
    	updateSql[1] = "update RM_PARTY_RELATION set CHILD_PARTY_NAME='"+name+"',ORDER_CODE='"+strCode+"' where CHILD_PARTY_ID="+partyId;
        RmProjectHelper.getCommonServiceInstance().doUpdateBatch(updateSql);
    }
    
	/**
	 * 从缓存取规则，验证是否符合party链表规则
	 * @param vo
	 */
	private void validatePartyRelation(RmPartyRelationVo vo) {
		if(RmPartyTypeRelationRuleCache.getSingleton().getRule(vo.getParty_view_id(), vo.getParent_party_type_id(), vo.getChild_party_type_id()) == null) {
			throw new RmPartyTypeRelationRuleException(vo.getParent_party_name() + "(" + vo.getParent_party_id() + ")->" + vo.getChild_party_name() + "(" + vo.getChild_party_id() + ") 在视图" + vo.getParty_view_id() + "下不符合规则");
		}
    }
	
    /**
     * 校验party父子关系是否符合规则
     * 
     * @param vo
     */
    @SuppressWarnings("unused")
	private void validatePartyRelationInDb(RmPartyRelationVo vo) {
    	String ptrrCondition = null;
    	if(vo.getParent_party_id() != null && vo.getParent_party_id().length() > 0) {
    		ptrrCondition = "PARENT_PARTY_TYPE_ID=(SELECT c.PARTY_TYPE_ID FROM RM_PARTY c WHERE c.ID=" + vo.getParent_party_id() + ")"
    			+ " and CHILD_PARTY_TYPE_ID=(SELECT c.PARTY_TYPE_ID FROM RM_PARTY c WHERE c.ID=" + vo.getChild_party_id() + ")"
    			+ " and PARTY_VIEW_ID=" + vo.getParty_view_id();
    	} else {
    		ptrrCondition = "(PARENT_PARTY_TYPE_ID='' or PARENT_PARTY_TYPE_ID is null)"
			+ " and CHILD_PARTY_TYPE_ID=(SELECT c.PARTY_TYPE_ID FROM RM_PARTY c WHERE c.ID=" + vo.getChild_party_id() + ")"
    		+ " and PARTY_VIEW_ID=" + vo.getParty_view_id();
    	}
    	List<RmPartyTypeRelationRuleVo> lptrr = ((IRmPartyTypeRelationRuleService)RmBeanFactory.getBean(IRmPartyTypeRelationRuleConstants.SERVICE_KEY)).queryByCondition(ptrrCondition, null);
    	if(lptrr.size() == 0) {
    		throw new RmPartyTypeRelationRuleException(vo.getParent_party_name() + "(" + vo.getParent_party_id() + ")->" + vo.getChild_party_name() + "(" + vo.getChild_party_id() + ") 在视图" + vo.getParty_view_id() + "下不符合规则");
    	}
    }
    
    /**
     * 防止调用时没有足够参数，自动初始化补齐 (查sql性能较低，尽可能在前台获得足够参数)
     * 初始化Parent_party_code和冗余的parent_party_type_id，child_party_type_id ()
     * 
     * @param vo
     */
    private void initDefaultValue(RmPartyRelationVo vo) {
    	//外键的空字符串成null
    	if("".equals(vo.getParent_party_id())) {
    		vo.setParent_party_id(null);
    	}
        //如果parent_party_code为空，则自动初始化parent_party_code (如取到多值, 取按CHILD_IS_MAIN_RELATION倒序排列的第1个)
        if(vo.getParent_party_code() == null || vo.getParent_party_code().length() == 0) {
        	if(vo.getParent_party_id() != null && vo.getParent_party_id().length() > 0) { //Parent_party_id有值，二者相加
            	List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery("select CHILD_PARTY_CODE from RM_PARTY_RELATION where PARTY_VIEW_ID=" + vo.getParty_view_id() + " and CHILD_PARTY_ID=" + vo.getParent_party_id() + " order by CHILD_IS_MAIN_RELATION desc");
            	if(lcvo.size() == 0) {
            		throw new RmPartyRelationException("未找到父Party对应的Relation记录，请先初始化" + vo.getParent_party_name() + "的Relation");
            	}
            	vo.setParent_party_code(lcvo.get(0).getString("child_party_code"));
            	log.info("initDefaultValue(), init Parent_party_code: " + vo.getParent_party_code());
        	} else { //Parent_party_id为空，则取视图的bs_keyword作为Parent_party_code
        		vo.setParent_party_code(RmPartyViewCache.getPartyView(vo.getParty_view_id()).getBs_keyword());
        	}
        }
        
        { //初始化parent和child的party_type_id(这是冗余字段)
        	if(vo.getParent_party_id() != null && vo.getParent_party_id().length() > 0 && vo.getParent_party_type_id() == null) {
        		String parent_party_type_id = String.valueOf(RmProjectHelper.getCommonServiceInstance().doQueryForLong("select PARTY_TYPE_ID from RM_PARTY where ID=" + vo.getParent_party_id()));
        		vo.setParent_party_type_id(parent_party_type_id);
        		log.info("initDefaultValue(), init Parent_party_type_id: " + vo.getParent_party_type_id());
        	}
        	if(vo.getChild_party_id() != null && vo.getChild_party_id().length() > 0 && vo.getChild_party_type_id() == null){
        		String child_party_type_id = String.valueOf(RmProjectHelper.getCommonServiceInstance().doQueryForLong("select PARTY_TYPE_ID from RM_PARTY where ID=" + vo.getChild_party_id()));
        		vo.setChild_party_type_id(child_party_type_id);
        		log.info("initDefaultValue(), init Child_party_type_id: " + vo.getChild_party_type_id());
        	}
        }

    }
    
    /**
     * 批量初始化RmPartyRelationVo的child_party_code
     * @param vos
     */
    private void initPartyChildCode(RmPartyRelationVo[] vos) {
    	if(vos == null || vos.length == 0) {
    		return;
    	}
    	//按Parent_party_code分组处理
        Map<String, List<RmPartyRelationVo>> mpr = new HashMap<String, List<RmPartyRelationVo>>();
        for(int i=0; i<vos.length; i++) {
        	String voParent_party_code = vos[i].getParent_party_code();
        	if(mpr.containsKey(voParent_party_code)) {
        		mpr.get(voParent_party_code).add(vos[i]);
        	} else {
        		List<RmPartyRelationVo> lpr = new ArrayList<RmPartyRelationVo>();
        		lpr.add(vos[i]);
        		mpr.put(voParent_party_code, lpr);
        	}
        }
        //一次性取出当前最大值
    	String sql = "select pr.parent_party_code, max(pr.child_party_code) max_child_party_code from RM_PARTY_RELATION pr where pr.parent_party_code in(" + RmStringHelper.parseToSQLStringApos(mpr.keySet().toArray(new String[0])) + ") group by pr.parent_party_code";
    	List<RmCommonVo> lvoMax_child_party_code =  RmProjectHelper.getCommonServiceInstance().doQuery(sql);
        
    	//循环初始化child_party_code
    	for(RmCommonVo vo : lvoMax_child_party_code) {
    		String parent_party_code = vo.getString("parent_party_code");
    		String max_child_party_code = vo.getString("max_child_party_code");
    		RmPartyRelationCode prc = new RmPartyRelationCode(parent_party_code, max_child_party_code);
    		List<RmPartyRelationVo> lpr = mpr.get(parent_party_code);
    		String[] newCodes = prc.getNext(lpr.size());
    		int index = 0;
    		for(RmPartyRelationVo pr : lpr) {
    			pr.setChild_party_code(parent_party_code + newCodes[index]);
    			index ++;
    		}
    		//处理完毕后从mpr删除
    		mpr.remove(parent_party_code);
    	}
    	//处理数据库中没有max(pr.child_party_code)的数据
    	for(Map.Entry<String, List<RmPartyRelationVo>> en : mpr.entrySet()) {
    		String parent_party_code = en.getKey();
    		List<RmPartyRelationVo> lpr = en.getValue();
    		RmPartyRelationCode prc = new RmPartyRelationCode(RmPartyRelationCode.MIN_VALUE);
    		String[] newCodes = prc.getNext(lpr.size());
    		int index = 0;
    		for(RmPartyRelationVo pr : lpr) {
    			pr.setChild_party_code(parent_party_code + newCodes[index]);
    			index ++;
    		}
    	}
    }  
}
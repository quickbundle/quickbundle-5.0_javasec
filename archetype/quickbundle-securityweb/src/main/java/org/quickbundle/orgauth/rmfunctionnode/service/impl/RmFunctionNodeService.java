//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmfunctionnode/service/impl/RmFunctionNodeService.java
//代码生成时,系统时间: 2010-11-27 22:08:40
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmfunctionnode.service.impl --> RmFunctionNodeService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:40 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmfunctionnode.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.config.RmConfig;
import org.quickbundle.itf.cache.IRmCacheListener;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmFunctionNodeCache;
import org.quickbundle.orgauth.rmauthorizeresource.service.IRmAuthorizeResourceService;
import org.quickbundle.orgauth.rmauthorizeresource.util.IRmAuthorizeResourceConstants;
import org.quickbundle.orgauth.rmauthorizeresourcerecord.service.IRmAuthorizeResourceRecordService;
import org.quickbundle.orgauth.rmauthorizeresourcerecord.util.IRmAuthorizeResourceRecordConstants;
import org.quickbundle.orgauth.rmfunctionnode.dao.IRmFunctionNodeDao;
import org.quickbundle.orgauth.rmfunctionnode.service.IRmFunctionNodeService;
import org.quickbundle.orgauth.rmfunctionnode.util.IRmFunctionNodeConstants;
import org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmCacheHandler;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.tools.helper.RmStringHelper;
import org.springframework.jdbc.core.RowMapper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmFunctionNodeService extends RmService implements IRmFunctionNodeService, IRmFunctionNodeConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmFunctionNodeDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmFunctionNodeDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmFunctionNodeDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmFunctionNodeVo vo) {
    	updateFunctionNode(vo.getTotal_code());
        String id = getDao().insert(vo);
        RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条新的菜单记录,id=" + String.valueOf(id)+",菜单名称="+vo.getName()+",菜单编号="+vo.getTotal_code());
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmFunctionNodeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmFunctionNodeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmFunctionNodeVo[] vos) {
    	//TODO 更新父
        String[] aId = getDao().insert(vos);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了" + vos.length + "条记录,id=" + RmStringHelper.ArrayToString(aId, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmFunctionNodeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmFunctionNodeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
        return aId;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id) {
    	//TODO 更新父
		int sum = getDao().delete(id);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmFunctionNodeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmFunctionNodeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(String ids[]) {
    	//TODO 更新父
		int sum = getDao().delete(ids);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmFunctionNodeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmFunctionNodeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmFunctionNodeVo find(String id) {
		RmFunctionNodeVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmFunctionNodeVo vo) {
		int sum = getDao().update(vo);
        RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条菜单记录,id=" + String.valueOf(vo.getId())+",菜单名称="+vo.getName()+",菜单编码="+vo.getTotal_code());
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmFunctionNodeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmFunctionNodeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmFunctionNodeVo[] vos) {
		int[] sum = getDao().update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "批量更新了" + finalSum + "条记录);
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmFunctionNodeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmFunctionNodeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return finalSum;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmFunctionNodeVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmFunctionNodeVo> lInsert = new ArrayList<RmFunctionNodeVo>();
		List<RmFunctionNodeVo> lUpdate = new ArrayList<RmFunctionNodeVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmFunctionNodeVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmFunctionNodeVo[0]));
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
    public List<RmFunctionNodeVo> queryByCondition(String queryCondition, String orderStr) {
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
    public List<RmFunctionNodeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<RmFunctionNodeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmFunctionNodeVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }


    /**
     * 判断更新父节点
     * @param totalCode
     * @param str //判断值
     */
    public void updateFunctionNode(String totalCode){
    	IRmCommonService cService = RmProjectHelper.getCommonServiceInstance();
    	if(RmStringHelper.checkNotEmpty(totalCode)&& totalCode.length()>RmConfig.getSingleton().getDefaultTreeCodeFirst().length()){
    		List<RmCommonVo> resultList =  cService.doQuery("select ID,IS_LEAF from RM_FUNCTION_NODE where  TOTAL_CODE='"+totalCode.substring(0, totalCode.length()-RmConfig.getSingleton().getDefaultTreeCodeFirst().length())+"' and ENABLE_STATUS=1");
    		if(resultList.size()>0){
    			RmCommonVo vo = resultList.get(0);
    			if(RmStringHelper.checkNotEmpty(vo.getString("is_leaf"))&&"1".equals(vo.getString("is_leaf"))){
    				cService.doUpdate("update RM_FUNCTION_NODE set IS_LEAF='0' where id="+vo.get("id"));
    			}
    		}
    	}
    }
    /**
     * 判断当前节点是否还有子节点，更新父节点为子节点
     * @param totalCode
     */
    public void updateFunctionNodeParty(String totalCode,String id){
    	IRmCommonService cService = RmProjectHelper.getCommonServiceInstance();
    	if(RmStringHelper.checkNotEmpty(totalCode)&& totalCode.length()>RmConfig.getSingleton().getDefaultTreeCodeFirst().length()){
    		List<RmCommonVo> resultList =  cService.doQuery("SELECT ID,IS_LEAF FROM RM_FUNCTION_NODE WHERE  TOTAL_CODE like '"+totalCode.substring(0, totalCode.length()-RmConfig.getSingleton().getDefaultTreeCodeFirst().length())+"%' AND ENABLE_STATUS=1 AND ID !="+id);
    		if(resultList.size()==1){ 
    			RmCommonVo vo = resultList.get(0);
    			if(RmStringHelper.checkNotEmpty(vo.getString("is_leaf"))){
    				cService.doUpdate("update RM_FUNCTION_NODE set IS_LEAF='0' where id="+vo.get("id"));
    			}
    		}
    	}
    }
    /**
     * 删除当前节点和下级节点
     * @param id
     * @param totalCode
     */
    @SuppressWarnings("unchecked")
    public void deleteFunctionNode(String id ,String totalCode){
    	IRmCommonService cService = RmProjectHelper.getCommonServiceInstance();
    	List<RmCommonVo> functionNodeList = cService.doQuery("SELECT ID,TOTAL_CODE FROM RM_FUNCTION_NODE WHERE TOTAL_CODE like '"+totalCode+"%'");
    	if(functionNodeList != null && functionNodeList.size() > 0 ){
    		List<String> functionNodeId = new ArrayList<String>();
    		List<String> functionNodeCode = new ArrayList<String>();
    		for(RmCommonVo vo:functionNodeList){
    			functionNodeId.add(vo.getString("id"));
    			functionNodeCode.add(vo.getString("total_code"));
    		}
    		//删除当前节点和子节点
    		this.delete(functionNodeId.toArray(new String[0]));
    		//删除授权资源
        	String[] authorizeResources = (String[])cService.query("select AR.ID from RM_AUTHORIZE_RESOURCE AR where AR.OLD_RESOURCE_ID in ("+RmStringHelper.parseToSQLString(functionNodeCode.toArray(new String[0]))+") and AR.AUTHORIZE_ID="+IOrgauthConstants.Authorize.FUNCTION_NODE.id() , new RowMapper() {
    			public Object mapRow(ResultSet rs, int i) throws SQLException {
    				return rs.getString("id");
    			}
    		}).toArray(new String[0]);    		
    		if(authorizeResources !=null && authorizeResources.length > 0 ){
	    		//删除授权记录
	        	String[] authorizeResourceRecords = (String[])cService.query("select RAR.ID from RM_AUTHORIZE_RESOURCE_RECORD RAR where RAR.AUTHORIZE_RESOURCE_ID in ("+RmStringHelper.parseToSQLStringApos(authorizeResources)+")" , new RowMapper() {
	    			public Object mapRow(ResultSet rs, int i) throws SQLException {
	    				return rs.getString("id");
	    			}
	    		}).toArray(new String[0]);    		
	    		if(authorizeResourceRecords !=null && authorizeResourceRecords.length > 0 ){
	    			IRmAuthorizeResourceRecordService authorizeResourceRecordService= (IRmAuthorizeResourceRecordService)RmBeanFactory.getBean(IRmAuthorizeResourceRecordConstants.SERVICE_KEY);
	    			authorizeResourceRecordService.delete(authorizeResourceRecords);
	    		}
	    		//删除资源
    			IRmAuthorizeResourceService authorizeResourceService = (IRmAuthorizeResourceService)RmBeanFactory.getBean(IRmAuthorizeResourceConstants.SERVICE_KEY);
    			authorizeResourceService.delete(authorizeResources);
    		}
    		//更新父节点
    		updateFunctionNodeParty(totalCode,id);
    		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了该（"+totalCode+"）菜单编码下的所有菜单");
    	}
    }
    
}
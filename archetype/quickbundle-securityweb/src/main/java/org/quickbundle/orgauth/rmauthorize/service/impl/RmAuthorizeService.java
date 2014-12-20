//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorize/service/impl/RmAuthorizeService.java
//代码生成时,系统时间: 2010-11-27 22:08:41
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorize.service.impl --> RmAuthorizeService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:41 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorize.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.itf.cache.IRmCacheListener;
import org.quickbundle.orgauth.cache.RmAuthorizeCache;
import org.quickbundle.orgauth.itf.vo.IRmAuthorizeResourceVo;
import org.quickbundle.orgauth.rmauthorize.dao.IRmAuthorizeDao;
import org.quickbundle.orgauth.rmauthorize.service.IRmAuthorizeService;
import org.quickbundle.orgauth.rmauthorize.util.IRmAuthorizeConstants;
import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;
import org.quickbundle.orgauth.rmauthorizeresource.service.IRmAuthorizeResourceService;
import org.quickbundle.orgauth.rmauthorizeresource.util.IRmAuthorizeResourceConstants;
import org.quickbundle.orgauth.rmauthorizeresource.vo.RmAuthorizeResourceVo;
import org.quickbundle.orgauth.rmauthorizeresourcerecord.service.IRmAuthorizeResourceRecordService;
import org.quickbundle.orgauth.rmauthorizeresourcerecord.util.IRmAuthorizeResourceRecordConstants;
import org.quickbundle.orgauth.rmauthorizeresourcerecord.vo.RmAuthorizeResourceRecordVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmCacheHandler;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.tools.helper.RmStringHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAuthorizeService extends RmService implements IRmAuthorizeService, IRmAuthorizeConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmAuthorizeDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmAuthorizeDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmAuthorizeDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmAuthorizeVo vo) {
        String id = getDao().insert(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" + String.valueOf(id));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmAuthorizeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmAuthorizeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmAuthorizeVo[] vos) {
        String[] aId = getDao().insert(vos);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了" + vos.length + "条记录,id=" + RmStringHelper.ArrayToString(aId, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmAuthorizeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmAuthorizeCache.class, IRmCacheListener.RefreshType.COMMON.value());
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
		int sum = getDao().delete(id);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmAuthorizeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmAuthorizeCache.class, IRmCacheListener.RefreshType.COMMON.value());
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
		int sum = getDao().delete(ids);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmAuthorizeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmAuthorizeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmAuthorizeVo find(String id) {
		RmAuthorizeVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmAuthorizeVo vo) {
		int sum = getDao().update(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getId()));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmAuthorizeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmAuthorizeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmAuthorizeVo[] vos) {
		int[] sum = getDao().update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "批量更新了" + finalSum + "条记录);
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        { //刷缓存的时机，选择在调用Service的更新类方法后
    		//刷新本地缓存
    		RmAuthorizeCache.getSingleton().flushCache(IRmCacheListener.RefreshType.COMMON.value());
            //通知兄弟集群节点
    		RmCacheHandler.getSingleton().flushOtherNodes(RmAuthorizeCache.class, IRmCacheListener.RefreshType.COMMON.value());
        }
		return finalSum;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmAuthorizeVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmAuthorizeVo> lInsert = new ArrayList<RmAuthorizeVo>();
		List<RmAuthorizeVo> lUpdate = new ArrayList<RmAuthorizeVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmAuthorizeVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmAuthorizeVo[0]));
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
    public List<RmAuthorizeVo> queryByCondition(String queryCondition, String orderStr) {
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
    public List<RmAuthorizeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<RmAuthorizeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmAuthorizeVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }
    
    /**
     * 初始化未注册的资源
     * 
     * @param resourceVos 授权资源vo length==0表示清理某些团体的关联授权资源
     * @param authorize_id 权限类别ID
     * @return RM_AUTHORIZE_RESOURCE资源(包含本次初始化、已被初始化)
     */
    public Map<String, IRmAuthorizeResourceVo> executeInitResource(IRmAuthorizeResourceVo[] resourceVos, String authorize_id) {
		IRmAuthorizeResourceService arService = (IRmAuthorizeResourceService)RmBeanFactory.getBean(IRmAuthorizeResourceConstants.SERVICE_KEY);
    	//新老资源的全部集合
    	Map<String, IRmAuthorizeResourceVo> result = new HashMap<String, IRmAuthorizeResourceVo>();
		//定义RM_AUTHORIZE_RESOURCE
		Map<String, IRmAuthorizeResourceVo> mResource = new HashMap<String, IRmAuthorizeResourceVo>();
		//从RM_AUTHORIZE_RESOURCE中查询出本次需要授权的资源
		for(IRmAuthorizeResourceVo vo : resourceVos) {
			mResource.put(vo.getOld_resource_id(), vo);
		}
		List<RmAuthorizeResourceVo> lResourceInDb = arService.queryByCondition("RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID IN(" + RmStringHelper.parseToSQLStringApos(mResource.keySet().toArray(new String[0])) + ") and AUTHORIZE_ID=" + authorize_id, null);
		//对比resourceVos后，筛选出RM_AUTHORIZE_RESOURCE中未注册的资源
		for(RmAuthorizeResourceVo vo : lResourceInDb) {
			if(mResource.containsKey(vo.getOld_resource_id())) {
				result.put(vo.getId(), vo);
				mResource.remove(vo.getOld_resource_id());
			}
		}
		//执行初始化未注册的资源
		List<RmAuthorizeResourceVo> lToInitResource = new ArrayList<RmAuthorizeResourceVo>();
		for(Map.Entry<String, IRmAuthorizeResourceVo> en : mResource.entrySet()) {
			IRmAuthorizeResourceVo vo = en.getValue();
			RmAuthorizeResourceVo toInitResource = new RmAuthorizeResourceVo();
			toInitResource.setAuthorize_id(vo.getAuthorize_id());
			toInitResource.setDefault_access(vo.getDefault_access());
			toInitResource.setDefault_access_type(vo.getAccess_type());
			toInitResource.setDefault_is_affix_data(vo.getIs_affix_data());
			toInitResource.setDefault_is_recursive(vo.getIs_recursive());
			toInitResource.setName(vo.getName());
			toInitResource.setOld_resource_id(vo.getOld_resource_id());
			toInitResource.setTotal_code(vo.getTotal_code());
			lToInitResource.add(toInitResource);
		}
		if(lToInitResource.size() > 0) {
			String[] newAuthorize_resource_ids = arService.insert(lToInitResource.toArray(new RmAuthorizeResourceVo[0]));
			for (int i = 0; i < newAuthorize_resource_ids.length; i++) {
				result.put(newAuthorize_resource_ids[i], lToInitResource.get(i));
			}
		}
    	return result;
    }
    
    /**
     *  授权的核心方法。先初始化未注册的资源，再批量授权(忽略已有授权记录，插入新授权记录，删除已消失的授权记录)
     *  当party_ids.length==0，表示清理resourceVos的关联授权团体
     *  当resourceVos.length==0，表示清理party_ids的关联授权资源
     *  当party_ids和resourceVos都有值，表示针对party_ids，忽略已有记录，插入新的resourceVos记录，删除消失的resourceVos记录(应用场景：针对角色进行功能授权)
     *  
	 * @param party_ids 团体id列表 
	 * @param resourceVos 授权资源vo length==0表示清理某些团体的关联授权资源
	 * @param authorize_id 权限类别ID
	 */
	public void insertDeleteAuthorizeResource_record(String[] party_ids, IRmAuthorizeResourceVo[] resourceVos, String authorize_id) {
		if(party_ids == null || resourceVos == null || (party_ids.length == 0 && resourceVos.length == 0) || authorize_id == null || authorize_id.length() == 0) {
			return;
		}
		IRmCommonService cService = RmProjectHelper.getCommonServiceInstance();
    	IRmAuthorizeResourceRecordService arrService = (IRmAuthorizeResourceRecordService)RmBeanFactory.getBean(IRmAuthorizeResourceRecordConstants.SERVICE_KEY);
    	Map<String, IRmAuthorizeResourceVo> mAllResource = executeInitResource(resourceVos, authorize_id);
		if(party_ids.length == 0) { //清理某些资源的关联授权团体
			cService.doUpdate("delete from RM_AUTHORIZE_RESOURCE_RECORD where AUTHORIZE_RESOURCE_ID in(" + RmStringHelper.parseToSQLStringApos(mAllResource.keySet().toArray(new String[0])) + ")");
		} else if(resourceVos.length == 0) { //清理某些团体的关联授权资源
			cService.doUpdate("delete from RM_AUTHORIZE_RESOURCE_RECORD where PARTY_ID in(" + RmStringHelper.parseToSQLStringApos(party_ids) + ") and AUTHORIZE_RESOURCE_ID in(select ID from RM_AUTHORIZE_RESOURCE where AUTHORIZE_ID=" + authorize_id + ")");
		} else {
			//数据库应当出现的记录
			Set<Party_AuthorizeResource> sToIntoDb = new HashSet<Party_AuthorizeResource>();
			for (int i = 0; i < party_ids.length; i++) {
				for (String authorize_resource_id : mAllResource.keySet()) {
					sToIntoDb.add(new Party_AuthorizeResource(party_ids[i], authorize_resource_id));
				}
			}
			//查询出RM_AUTHORIZE_RESOURCE_RECORD已有的相关记录
			List<RmCommonVo> lRecordInDb = cService.doQuery("select arr.ID, arr.AUTHORIZE_RESOURCE_ID, arr.PARTY_ID from RM_AUTHORIZE_RESOURCE_RECORD arr join RM_AUTHORIZE_RESOURCE ar on arr.AUTHORIZE_RESOURCE_ID=ar.ID where PARTY_ID in(" + RmStringHelper.parseToSQLString(party_ids) + ") and ar.AUTHORIZE_ID=" + authorize_id);
			
			//筛选出RM_AUTHORIZE_RESOURCE_RECORD中没有的新授权记录，和需要删除的记录
			Set<Party_AuthorizeResource> sRecordToInsert = new HashSet<Party_AuthorizeResource>();
			Set<Party_AuthorizeResource> sRecordToDelete = new HashSet<Party_AuthorizeResource>();
			for(RmCommonVo vo : lRecordInDb) {
				Party_AuthorizeResource pa = new Party_AuthorizeResource(vo.getString("party_id"), vo.getString("authorize_resource_id"));
				pa.authoroze_resource_record_id = vo.getString("id");
				if(sToIntoDb.contains(pa)) {
					sToIntoDb.remove(pa);
				} else {
					sRecordToDelete.add(pa);
				}
			}
			sRecordToInsert = sToIntoDb;
			//执行插入新授权记录
			if(sRecordToInsert.size() > 0) {
				List<RmAuthorizeResourceRecordVo> lRecordToInsert = new ArrayList<RmAuthorizeResourceRecordVo>();
				for(Party_AuthorizeResource pa : sRecordToInsert) {
					RmAuthorizeResourceRecordVo vo = new RmAuthorizeResourceRecordVo();
					vo.setAuthorize_resource_id(pa.authorize_resource_id);
					vo.setParty_id(pa.party_id);
					vo.setAuthorize_status(RM_YES);
					vo.setIs_affix_data(RM_NO);
					vo.setIs_recursive(RM_NO);
					vo.setAccess_type("1");
					lRecordToInsert.add(vo);
				}
				arrService.insert(lRecordToInsert.toArray(new RmAuthorizeResourceRecordVo[0]));
			}
			//执行删除已消失的授权记录
			if(sRecordToDelete.size() > 0) {
				Set<String> sRecordIdToDelete = new HashSet<String>();
				for(Party_AuthorizeResource pa : sRecordToDelete) {
					sRecordIdToDelete.add(pa.authoroze_resource_record_id);
				}
				arrService.delete(sRecordIdToDelete.toArray(new String[0]));
			}
		}
	}
	
	class Party_AuthorizeResource {
		public Party_AuthorizeResource(String party_id, String authorize_resource_id) {
			this.party_id = party_id;
			this.authorize_resource_id = authorize_resource_id;
		}
		private String party_id;
		private String authorize_resource_id;
		private String authoroze_resource_record_id;
		
		@Override
		public int hashCode() {
			return party_id.hashCode() + authorize_resource_id.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Party_AuthorizeResource) {
				Party_AuthorizeResource o2 = (Party_AuthorizeResource)obj;
				return (party_id == o2.party_id || party_id.equals(o2.party_id))
					&& (authorize_resource_id == o2.authorize_resource_id || authorize_resource_id.equals(o2.authorize_resource_id));
				
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return party_id + "|" + authorize_resource_id + "|" + authoroze_resource_record_id;
		}
	}
}
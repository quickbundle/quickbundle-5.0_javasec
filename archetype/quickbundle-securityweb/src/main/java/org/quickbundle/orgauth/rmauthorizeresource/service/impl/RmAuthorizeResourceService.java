//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorizeresource/service/impl/RmAuthorizeResourceService.java
//代码生成时,系统时间: 2010-11-27 22:08:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorizeresource.service.impl --> RmAuthorizeResourceService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorizeresource.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.orgauth.cache.RmAuthorizeCache;
import org.quickbundle.orgauth.itf.vo.IRmAuthorizeResourceVo;
import org.quickbundle.orgauth.rmauthorize.service.IRmAuthorizeService;
import org.quickbundle.orgauth.rmauthorize.util.IRmAuthorizeConstants;
import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;
import org.quickbundle.orgauth.rmauthorizeresource.dao.IRmAuthorizeResourceDao;
import org.quickbundle.orgauth.rmauthorizeresource.service.IRmAuthorizeResourceService;
import org.quickbundle.orgauth.rmauthorizeresource.util.IRmAuthorizeResourceConstants;
import org.quickbundle.orgauth.rmauthorizeresource.util.RmAuthorizeResourceException;
import org.quickbundle.orgauth.rmauthorizeresource.vo.RmAuthorizeResourceVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.tools.helper.RmPopulateHelper;
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

public class RmAuthorizeResourceService extends RmService implements IRmAuthorizeResourceService, IRmAuthorizeResourceConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmAuthorizeResourceDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmAuthorizeResourceDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmAuthorizeResourceDao dao) {
        this.dao = dao;
    }

    IRmAuthorizeService getAuthorizeService() {
    	return (IRmAuthorizeService)RmBeanFactory.getBean(IRmAuthorizeConstants.SERVICE_KEY);
    }

    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmAuthorizeResourceVo vo) {
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
    public String[] insert(RmAuthorizeResourceVo[] vos) {
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
    public RmAuthorizeResourceVo find(String id) {
		RmAuthorizeResourceVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmAuthorizeResourceVo vo) {
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
	public int update(RmAuthorizeResourceVo[] vos) {
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
	public int[] insertUpdateBatch(RmAuthorizeResourceVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmAuthorizeResourceVo> lInsert = new ArrayList<RmAuthorizeResourceVo>();
		List<RmAuthorizeResourceVo> lUpdate = new ArrayList<RmAuthorizeResourceVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmAuthorizeResourceVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmAuthorizeResourceVo[0]));
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
    public List<RmAuthorizeResourceVo> queryByCondition(String queryCondition, String orderStr) {
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
    public List<RmAuthorizeResourceVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<RmAuthorizeResourceVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmAuthorizeResourceVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }

    /**
     * 插入中间表RM_AUTHORIZE_RESOURCE_RECORD数据
     * 
     * @param authorize_resource_id
     * @param party_ids
     * @return 插入的party_id列表
     */
    @SuppressWarnings("unchecked")
	public String[] insertRm_authorize_resource_record(String bs_keyword, String old_resource_id, String[] party_ids) {
    	if(party_ids.length == 0 || (party_ids.length == 1 &&party_ids[0].trim().length() == 0)) {
    		return new String[0];
    	}
    	
    	Map<String, IRmAuthorizeResourceVo> mResource = executeInitResource(bs_keyword, new String[]{old_resource_id});
    	String authorize_resource_id = mResource.keySet().toArray(new String[0])[0];
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	List<String> lExistId = cs.query("SELECT * FROM RM_AUTHORIZE_RESOURCE_RECORD WHERE AUTHORIZE_RESOURCE_ID=" + authorize_resource_id + " AND PARTY_ID IN(" + RmStringHelper.parseToString(party_ids) + ")", new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PARTY_ID");
			}
		});
    	Set<String> sParty_id = new HashSet<String>();
    	for(String party_id : party_ids) {
    		if(!lExistId.contains(party_id)) {
    			sParty_id.add(party_id);
    		}
    	}
    	if(sParty_id.size() > 0) {
    		String[] ids = RmIdFactory.requestId("RM_AUTHORIZE_RESOURCE_RECORD", sParty_id.size());
        	String[][] aaValue = new String[sParty_id.size()][3];
        	int index = 0;
        	for (String party_id : sParty_id) {
        		aaValue[index][0] = ids[index];
        		aaValue[index][1] = authorize_resource_id;
    			aaValue[index][2] = party_id;
    			index ++;
    		}
        	cs.doUpdateBatch("INSERT INTO RM_AUTHORIZE_RESOURCE_RECORD (ID, AUTHORIZE_RESOURCE_ID, PARTY_ID, AUTHORIZE_STATUS, IS_AFFIX_DATA, IS_RECURSIVE, ACCESS_TYPE) VALUES(?, ?, ?, '1', '0', '0', '1')", aaValue);
    	}
        return sParty_id.toArray(new String[0]);
    }
    
    /**
     * 功能: 删除中间表RM_AUTHORIZE_RESOURCE_RECORD数据
     * 
     * @param authorize_resource_id
     * @param party_ids
     * @return 删除的记录数
     */
    public int deleteRm_authorize_resource_record(String authorize_resource_id, String[] party_ids) {
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	return cs.doUpdate("DELETE FROM RM_AUTHORIZE_RESOURCE_RECORD WHERE AUTHORIZE_RESOURCE_ID=" + authorize_resource_id + " AND PARTY_ID IN(" + RmStringHelper.parseToString(party_ids) + ")");
    }
    
    //构建SQL
    private String buildSql(RmAuthorizeVo authorize, String selectPhase, String queryCondition, boolean withNull) {
    	StringBuilder sql = new StringBuilder();
		Document authRule = authorize.getRuleCustomCode();
		String sqlAfterFrom = authRule.valueOf("/authorize/@sql_after_from");
		String sqlAfterWhere = authRule.valueOf("/authorize/@sql_after_where");
		sql.append("select ");
		sql.append(selectPhase);
		sql.append(" from ");
		sql.append(sqlAfterFrom);
		sql.append(" left join RM_AUTHORIZE_RESOURCE on ");
		sql.append(buildJoinTableColumnFull(authorize));
		sql.append("=RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID and ");
		sql.append("RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID=");
		sql.append(authorize.getId());
		//where部分开始
		if(sqlAfterWhere.length() > 0 || (queryCondition != null && queryCondition.length() > 0)) {
			sql.append(" where ");
		}
		if(queryCondition != null && queryCondition.length() > 0) {
			sql.append("(");
			sql.append(queryCondition);
			if(withNull) {
				sql.append(" or RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID is null");
			}
			sql.append(")");
		}
		if(sqlAfterWhere.length() > 0) {
			if(!sql.toString().trim().endsWith("where")) {
				sql.append(" and ");
			}
			sql.append(sqlAfterWhere);
		}
    	return sql.toString();
    }
    
    //构建查询列表的select部分
    private String buildSelectPhase(RmAuthorizeVo authorize) {
    	StringBuilder selectPhase = new StringBuilder();
		Document authRule = authorize.getRuleCustomCode();
		String joinTable = authRule.valueOf("/authorize/@join_table");
		String joinTableColumn = authRule.valueOf("/authorize/@join_table_column");
		String joinTableColumnKey = authRule.valueOf("/authorize/@join_table_column_key");
		selectPhase.append(joinTable);
		selectPhase.append(".");
		selectPhase.append(joinTableColumnKey);
		selectPhase.append(" rm_key_column, ");
		selectPhase.append(joinTable);
		selectPhase.append(".");
		selectPhase.append(joinTableColumn);
		selectPhase.append(" rm_join_column");
		selectPhase.append(", ");
		selectPhase.append(" RM_AUTHORIZE_RESOURCE.ID, RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID, RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_RECURSIVE, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS_TYPE, RM_AUTHORIZE_RESOURCE.TOTAL_CODE, RM_AUTHORIZE_RESOURCE.NAME");
		return selectPhase.toString();
    }
    
    //得到join_table.join_table_column部分
    private String buildJoinTableColumnFull(RmAuthorizeVo authorize) {
    	StringBuilder joinTableColumnFull = new StringBuilder();
		Document authRule = authorize.getRuleCustomCode();
		String joinTable = authRule.valueOf("/authorize/@join_table");
		String joinTableColumn = authRule.valueOf("/authorize/@join_table_column");
		String join_table_column_full = authRule.valueOf("/authorize/@join_table_column_full");
		if(join_table_column_full != null && join_table_column_full.length() > 0) {
			joinTableColumnFull.append(join_table_column_full);
		} else {
			joinTableColumnFull.append(joinTable);
			joinTableColumnFull.append(".");
			joinTableColumnFull.append(joinTableColumn);
		}
		return joinTableColumnFull.toString();
    }

	public int getRecordCount(String queryCondition, RmAuthorizeVo authorize) {
		String sql = buildSql(authorize, "count(*)", queryCondition, true);
		return RmProjectHelper.getCommonServiceInstance().doQueryForInt(sql.toString());
	}

	public List<RmAuthorizeResourceVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int pageSize, RmAuthorizeVo authorize) {
		final List<RmAuthorizeResourceVo> result = new ArrayList<RmAuthorizeResourceVo>();
		String sql = buildSql(authorize, buildSelectPhase(authorize), queryCondition, true);
		RmProjectHelper.getCommonServiceInstance().doQuery(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				RmAuthorizeResourceVo vo = new RmAuthorizeResourceVo();
				RmPopulateHelper.populate(vo, rs);
				if(vo.getName() == null) {
					vo.setName(rs.getString("rm_key_column"));
				}
				if(vo.getOld_resource_id() == null) {
					vo.setOld_resource_id(rs.getString("rm_join_column"));
				}
				result.add(vo);
				return null;
			}
		}, (startIndex/pageSize)+1, pageSize);

		return result;
	}

	public Map<String, IRmAuthorizeResourceVo> executeInitResource(String bs_keyword, String[] old_resource_ids) {
		final RmAuthorizeVo authorize = RmAuthorizeCache.getAuthorizeByBs_keyword(bs_keyword);
		final List<IRmAuthorizeResourceVo> lvo = new ArrayList<IRmAuthorizeResourceVo>();
		final Document authRule = authorize.getRuleCustomCode();
		if(authRule == null) {
			throw new RmAuthorizeResourceException(bs_keyword + "未设置规则，不能进行默认初始化");
		}
		String sql = buildSql(authorize, buildSelectPhase(authorize), buildJoinTableColumnFull(authorize) + " in(" + RmStringHelper.parseToSQLStringApos(old_resource_ids) + ")", false);
		RmProjectHelper.getCommonServiceInstance().query(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				RmAuthorizeResourceVo vo = new RmAuthorizeResourceVo();
				vo.setAuthorize_id(authorize.getId());
				vo.setOld_resource_id(rs.getString("rm_join_column"));
				vo.setName(rs.getString("rm_key_column"));
				vo.setDefault_access(authRule.valueOf("/authorize/rm_authorize_resource/default_access/text()"));
				vo.setDefault_is_affix_data(authRule.valueOf("/authorize/rm_authorize_resource/default_is_affix_data/text()"));
				vo.setDefault_is_recursive(authRule.valueOf("/authorize/rm_authorize_resource/default_is_recursive/text()"));
				vo.setDefault_access_type(authRule.valueOf("/authorize/rm_authorize_resource/default_access_type/text()"));
				lvo.add(vo);
				return null;
			}
		});
		return getAuthorizeService().executeInitResource(lvo.toArray(new IRmAuthorizeResourceVo[0]), authorize.getId());
	}
}
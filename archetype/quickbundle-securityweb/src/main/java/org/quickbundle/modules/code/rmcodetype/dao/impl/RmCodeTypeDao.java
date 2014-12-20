//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodetype/dao/impl/RmCodeTypeDao.java
//代码生成时,系统时间: 2010-04-08 21:15:45.828
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.dao.impl --> RmCodeTypeDao.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:45.812 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.dao.RmJdbcTemplate;
import org.quickbundle.modules.code.rmcodetype.dao.IRmCodeTypeDao;
import org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants;
import org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo;
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

public class RmCodeTypeDao extends RmJdbcTemplate implements IRmCodeTypeDao, IRmCodeTypeConstants {

    /**
     * 插入单条记录，用Oid作主键，把null全替换为""
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmCodeTypeVo vo) {
    	if(vo.getId() == null || vo.getId().length() == 0) {
    		String id = RmIdFactory.requestId(TABLE_NAME); //获得oid
    		vo.setId(id);
    	}
        Object[] obj = { vo.getId(), vo.getType_keyword(),vo.getName(),vo.getRemark(),vo.getUsable_status(),vo.getModify_date(),vo.getModify_ip(),vo.getModify_user_id() };
        update(SQL_INSERT, obj);
        return vo.getId();
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id) {
        return update(SQL_DELETE_BY_ID, new Object[] { id });
    }

    /**
     * 删除多条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id[]) {
        String strsql = " WHERE ID IN (";
        if (id == null || id.length == 0)
            return 0;
        strsql += RmStringHelper.parseToSQLStringApos(id) + ")"; //把id数组转换为id1,id2,id3
        strsql = SQL_DELETE_MULTI_BY_IDS + strsql;
        return update(strsql);
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmCodeTypeVo find(String id) {
        return (RmCodeTypeVo) queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                RmCodeTypeVo vo = new RmCodeTypeVo();
                RmPopulateHelper.populate(vo, rs);
                return vo;
            }
        });
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmCodeTypeVo vo) {
        Object[] obj = { vo.getType_keyword(),vo.getName(),vo.getRemark(),vo.getUsable_status(),vo.getModify_date(),vo.getModify_ip(),vo.getModify_user_id(), vo.getId() };
        return update(SQL_UPDATE_BY_ID, obj);
    }

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition) {
        String strsql = SQL_COUNT + DEFAULT_SQL_WHERE_USABLE;
        if (queryCondition != null && queryCondition.trim().length() > 0) {
            strsql += DEFAULT_SQL_CONTACT_KEYWORD + queryCondition; //where后加上查询条件
        }
        return queryForInt(strsql);
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     */
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
    	StringBuilder sql = new StringBuilder();
        if(selectAllClumn) {
        	sql.append(SQL_QUERY_ALL_EXPORT + DEFAULT_SQL_WHERE_USABLE);
        } else {
        	sql.append(SQL_QUERY_ALL + DEFAULT_SQL_WHERE_USABLE);
        }
        if (queryCondition != null && queryCondition.trim().length() > 0) {
        	sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
        	sql.append(queryCondition);
        }
        if(orderStr != null && orderStr.trim().length() > 0) {
        	sql.append(ORDER_BY_SYMBOL);
        	sql.append(orderStr);
        } else {
        	sql.append(DEFAULT_ORDER_BY_CODE);
        }
        return queryByCondition(sql.toString(), startIndex, size);
    }
    
    /**
     * 通过传入的sql，查询所有的VO对象列表
     * 
     * @param sql 完整的查询sql语句
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size  查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<RmCodeTypeVo> queryByCondition(String sql, int startIndex, int size) {
    	if(size <= 0) {
    		return query(sql.toString(), new RowMapper() {
    			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    				RmCodeTypeVo vo = new RmCodeTypeVo();
    				RmPopulateHelper.populate(vo, rs);
    				return vo;
    			}
    		});
    	} else {
    		return query(sql.toString(), new RowMapper() {
    			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    				RmCodeTypeVo vo = new RmCodeTypeVo();
    				RmPopulateHelper.populate(vo, rs);
    				return vo;
    			}
    		}, startIndex, size);
    	}
    }
    
    /**
     * 通用的方法，执行更新，返回更新的记录条数
     *
     * @param strsql 要执行的sql语句
     * @return 更新记录条数
     */
    public int doUpdate(String strsql) {
        return update(strsql);
    }
}
	

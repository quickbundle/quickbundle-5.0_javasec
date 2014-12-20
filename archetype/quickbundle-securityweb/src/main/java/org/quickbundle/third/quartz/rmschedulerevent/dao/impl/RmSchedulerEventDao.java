//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/rmschedulerevent/dao/impl/RmSchedulerEventDao.java
//代码生成时,系统时间: 2012-04-02 22:28:45
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.rmschedulerevent.dao.impl --> RmSchedulerEventDao.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:45 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.rmschedulerevent.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.dao.RmJdbcTemplate;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmStringHelper;
import org.springframework.jdbc.core.RowMapper;

import org.quickbundle.third.quartz.rmschedulerevent.dao.IRmSchedulerEventDao;
import org.quickbundle.third.quartz.rmschedulerevent.util.IRmSchedulerEventConstants;
import org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmSchedulerEventDao extends RmJdbcTemplate implements IRmSchedulerEventDao, IRmSchedulerEventConstants {

    /**
     * 插入单条记录，从RmIdFactory取id作主键
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmSchedulerEventVo vo) {
    	if(vo.getId() == null || vo.getId().length() == 0) {
    		vo.setId(RmIdFactory.requestId(TABLE_NAME)); //获得id
    	}
        Object[] obj = { vo.getId(), vo.getJob_name(),vo.getJob_group(),vo.getTrigger_name(),vo.getTrigger_group(),vo.getFire_instance_id(),vo.getEvent_type(),vo.getCost_millis(),vo.getResult_status(),vo.getCreate_time(),vo.getCreate_ip(),vo.getUuid(),vo.getIs_archive(),vo.getResult() };
        update(SQL_INSERT, obj);
        return vo.getId();
    }

    /**
     * 批量更新插入多条记录，用id作主键
     * 
     * @param vos 添加的VO对象数组
     * @return 若添加成功，返回新生成的id数组
     */
	public String[] insert(final RmSchedulerEventVo[] vos) {
		String[] ids = RmIdFactory.requestId(TABLE_NAME, vos.length); //获得id
		for(int i=0; i<vos.length; i++) {
			vos[i].setId(ids[i]);
		}
		batchUpdate(SQL_INSERT, vos, new RmJdbcTemplate.CircleVoArray() {
			public Object[] getArgs(Object obj) {
				RmSchedulerEventVo vo = (RmSchedulerEventVo)obj;
				return new Object[]{ vo.getId(), vo.getJob_name(),vo.getJob_group(),vo.getTrigger_name(),vo.getTrigger_group(),vo.getFire_instance_id(),vo.getEvent_type(),vo.getCost_millis(),vo.getResult_status(),vo.getCreate_time(),vo.getCreate_ip(),vo.getUuid(),vo.getIs_archive(),vo.getResult() };
			}
		});
		return ids;
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
        if (id == null || id.length == 0)
            return 0;
        StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
        sql.append(" WHERE ID IN (");
        sql.append(RmStringHelper.parseToSQLStringApos(id)); //把id数组转换为id1,id2,id3
        sql.append(")");
        return update(sql.toString());
    }

    /**
     * 根据id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmSchedulerEventVo find(String id) {
        return (RmSchedulerEventVo) queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                RmSchedulerEventVo vo = new RmSchedulerEventVo();
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
    public int update(RmSchedulerEventVo vo) {
        Object[] obj = { vo.getJob_name(),vo.getJob_group(),vo.getTrigger_name(),vo.getTrigger_group(),vo.getFire_instance_id(),vo.getEvent_type(),vo.getCost_millis(),vo.getResult_status(),vo.getCreate_time(),vo.getUuid(),vo.getIs_archive(),vo.getResult(), vo.getId() };
        return update(SQL_UPDATE_BY_ID, obj);
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 添加的VO对象数组
     * @return 成功更新的记录数组
     */
	public int[] update(final RmSchedulerEventVo[] vos) {
		return batchUpdate(SQL_UPDATE_BY_ID, vos, new RmJdbcTemplate.CircleVoArray() {
			public Object[] getArgs(Object obj) {
				RmSchedulerEventVo vo = (RmSchedulerEventVo)obj;
				return new Object[]{ vo.getJob_name(),vo.getJob_group(),vo.getTrigger_name(),vo.getTrigger_group(),vo.getFire_instance_id(),vo.getEvent_type(),vo.getCost_millis(),vo.getResult_status(),vo.getCreate_time(),vo.getUuid(),vo.getIs_archive(),vo.getResult(), vo.getId() };
			}
		});
	}

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition) {
    	StringBuilder sql = new StringBuilder(SQL_COUNT + DEFAULT_SQL_WHERE_USABLE);
        if (queryCondition != null && queryCondition.trim().length() > 0) {
        	sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
        	sql.append(queryCondition);
        }
        return queryForInt(sql.toString());
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
    public List<RmSchedulerEventVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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
    @SuppressWarnings("unchecked")
    public List<RmSchedulerEventVo> queryByCondition(String sql, int startIndex, int size) {
        if(size <= 0) {
            return query(sql.toString(), new RowMapper() {
                public Object mapRow(ResultSet rs, int i) throws SQLException {
                    RmSchedulerEventVo vo = new RmSchedulerEventVo();
                    RmPopulateHelper.populate(vo, rs);
                    return vo;
                }
            });
        } else {
            return query(sql.toString(), new RowMapper() {
                public Object mapRow(ResultSet rs, int i) throws SQLException {
                    RmSchedulerEventVo vo = new RmSchedulerEventVo();
                    RmPopulateHelper.populate(vo, rs);
                    return vo;
                }
            }, startIndex, size); 
        }
    }
}
	
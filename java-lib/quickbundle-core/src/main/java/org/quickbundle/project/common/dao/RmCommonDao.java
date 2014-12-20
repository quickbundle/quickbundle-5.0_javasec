/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: 
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-9-23 15:25:24 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.project.common.dao;

import java.util.List;
import java.util.Map;

import org.quickbundle.base.dao.RmJdbcTemplate;
import org.springframework.dao.DataAccessException;

/**
 * 功能:
 * 
 * @author 白小勇
 */
public class RmCommonDao extends RmJdbcTemplate {
	
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return query(sql, getSingleColumnRowMapper(elementType));
	}

	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		return query(sql, getColumnMapRowMapper());
	}
	
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
		return query(sql, args, argTypes, getSingleColumnRowMapper(elementType));
	}

	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
		return query(sql, args, getSingleColumnRowMapper(elementType));
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {
		return query(sql, args, getSingleColumnRowMapper(elementType));
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		return query(sql, args, argTypes, getColumnMapRowMapper());
	}

	public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
		return query(sql, args, getColumnMapRowMapper());
	}
	
    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @param requiredType 需要的类型
     * @return
     */
    public <T> T queryForObject(String sql, Class<T> requiredType) {
    	return queryForObject(sql, getSingleColumnRowMapper(requiredType));
    }
}
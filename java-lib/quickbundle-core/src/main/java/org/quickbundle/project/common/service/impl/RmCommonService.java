/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.project.common.service.impl --> RmCommonService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-9-23 15:25:23 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.project.common.service.impl;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.quickbundle.ICoreConstants;
import org.quickbundle.base.dao.RmJdbcTemplate;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.base.service.RmService;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.itf.IExecuteCode;
import org.quickbundle.project.common.dao.RmCommonDao;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

/**
 * 功能: 通用的JDBC方法，do开头的方法有特殊的业务含义，非do开头的转发JdbcTemplate
 * 
 * @author 白小勇
 */
public class RmCommonService extends RmService implements IRmCommonService {
    /**
     * dao 表示: 数据访问层的实例
     */
    private RmCommonDao dao = null;
    public RmCommonDao getDao() {
        return dao;
    }
    public void setDao(RmCommonDao dao) {
        this.dao = dao;
    }

	/**
	 * 查询sql，返回RmcommonVo
	 * 
	 * @param sql
	 * @return
	 */
    public List<RmCommonVo> doQuery(String sql) {
    	return query(sql, getDefaultRowMapper(RmCommonVo.class));
    }
    
    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @param rowMapper 回调方法
     * @return 自己控制的对象列表
     */
    public List query(String sql, RowMapper rowMapper) {
        return getDao().query(sql, rowMapper);
    }

    /**
     * 以size作为分页大小，取回第no页的记录
     * 查询sql，返回RowMapper中自定义的对象
     *
     * @param sql 要执行的sql语句
     * @param rowMapper 回调方法
     * @param no 当前页数
     * @param size 每页记录数
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<RmCommonVo> doQuery(String sql, int no, int size) {
    	return doQueryStartIndex(sql, getDefaultRowMapper(RmCommonVo.class), (no - 1) * size + 1, size);
    }
    
    /**
     * 从指定的startIndex作为第一条，获取size条记录
     * 
     * @param sql 要执行的sql语句
     * @param rowMapper 回调方法
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 获取的记录数
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
	public List<RmCommonVo> doQueryStartIndex(String sql, int startIndex, int size) {
		return doQueryStartIndex(sql, getDefaultRowMapper(RmCommonVo.class), startIndex, size);
	}
	
    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @param rowMapper 回调方法
     * @param no 当前页数
     * @param size 每页记录数
     * @return 自己控制的对象列表
     */
    public List doQuery(String sql, RowMapper rowMapper, int no, int size) {
        return doQueryStartIndex(sql, rowMapper, (no - 1) * size + 1, size);
    }
    
    /**
     * 从指定的startIndex作为第一条，获取size条记录
     * 
     * @param sql 要执行的sql语句
     * @param rowMapper 回调方法
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 获取的记录数
     * @return 对象列表
     */
    public List doQueryStartIndex(String sql, RowMapper rowMapper, int startIndex, int size) {
    	return getDao().query(sql, rowMapper, startIndex, size);
    }

	/**
	 * 查询sql，返回RmcommonVo，自动分页
	 * 
	 * @param sql SQL语句
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public List<RmCommonVo> doQueryPage(String sql) {
		return doQueryPage(sql, RmCommonVo.class);
	}

	private List doQueryPage(String sql, Class className) {
		int recordCount = doQueryForInt("select count(*) from (" + sql + ") rm_a");
		int[] aPage_size_current = (int[])RmRequestMonitor.getCurrentThreadRequest().getAttribute(ICoreConstants.RM_CURRENT_PAGE);
		RmPageVo pageVo = new RmPageVo(recordCount, aPage_size_current[0]);
		pageVo.setCurrentPage(aPage_size_current[1]);
		RmRequestMonitor.getCurrentThreadRequest().setAttribute(ICoreConstants.RM_PAGE_VO, pageVo);
		return doQuery(sql, getDefaultRowMapper(className), pageVo.getCurrentPage(), pageVo.getPageSize());
	}
	
	/**
	 * 通用的方法，返回自己控制的对象
	 * 
	 * @param sql 要执行的sql语句
	 * @param elementType 需要的类型
	 * @return 对象列表
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return getDao().queryForList(sql, elementType);
	}

	/**
	 * 通用的方法，返回自己控制的对象
	 * 
	 * @param sql 要执行的sql语句
	 * @return 对象列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		return getDao().queryForList(sql);
	}
	
	/**
	 * 通用的方法，返回自己控制的对象
	 * 
	 * @param sql 要执行的sql语句
	 * @param args ?对应的值
	 * @param argTypes ?对应的值的类型
	 * @param elementType 需要的类型
	 * @return 对象列表
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
		return getDao().queryForList(sql, args, argTypes, elementType);
	}

	/**
	 * 通用的方法，返回自己控制的对象
	 * 
	 * @param sql 要执行的sql语句
	 * @param args ?对应的值
	 * @param elementType 需要的类型
	 * @return 对象列表
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
		return getDao().queryForList(sql, args, elementType);
	}
	
    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @return RmCommonVo对象
     */
    public RmCommonVo doQueryForObject(String sql) {
        return (RmCommonVo)getDao().queryForObject(sql, getDefaultRowMapper(RmCommonVo.class));
    }
    
    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @param requiredType 需要的类型
     * @return
     */
    public <T> T queryForObject(String sql, Class<T> requiredType) {
    	return getDao().queryForObject(sql, requiredType);
    }
    
    /**
     * sql带?及参数，执行查询，返回T
     * 
     * @param sql 要执行的sql语句
     * @param args ?对应的值
     * @param requiredType 需要的类型
     * @return
     */
    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) {
    	return getDao().queryForObject(sql, args, requiredType);
    }
    
    
    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @param rowMapper 回调方法
     * @return 自己控制的对象
     */
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
        return getDao().queryForObject(sql, rowMapper);
    }
    
    /**
     * 通用的方法，执行查询，返回int
     *
     * @param sql 要执行的sql语句
     * @return 查询结果int
     */
    public int doQueryForInt(String sql) {
        Integer value = getDao().queryForObject(sql, Integer.class);
        if(value == null) {
        	return 0;
        } else {
        	return value.intValue();
        }
    }
    
    /**
     * 执行查询，返回long
     *
     * @param sql 要执行的sql语句
     * @return 查询结果long
     */
    public long doQueryForLong(String sql) {
        Long value = getDao().queryForObject(sql, Long.class);
        if(value == null) {
        	return 0;
        } else {
        	return value.longValue();
        }
    }
    
    /**
     * 执行更新，返回更新的记录条数
     *
     * @param sql 要执行的sql语句
     * @return 更新记录条数
     */
    public int doUpdate(String sql) {
    	//TODO 业务日志
        return getDao().update(sql);
    }
    
    /**
     * 执行更新，带?及参数，返回更新的记录条数
     * 
     * @param sql 带 ?的SQL语句
     * @param aObj ?对应的值
     * @return
     */
    public int doUpdate(String sql, Object[] aObj) {
    	//TODO 业务日志
        return getDao().update(sql, aObj);
    }
    
    /**
     * 执行批量更新sql，返回更新的记录条数
     * 
     * @param sql
     * @return
     */
	public int[] doUpdateBatch(String[] sql) {
		//TODO 业务日志
		return getDao().batchUpdate(sql);
	}
	
	/** 执行批量更新，带?及参数，返回更新的记录条数
	 * @param sql 带 ?的SQL语句
	 * @param aObj ?对应的值
	 * @return
	 */
	public int[] doUpdateBatch(String sql, Object[][] aaObj) {
		//TODO 业务日志
		return getDao().batchUpdate(sql, aaObj, new RmJdbcTemplate.CircleVoArray() {
			public Object[] getArgs(Object obj) {
				return (Object[])obj;
			}
		});
	}

	/**
	 * 执行自定义定义代码，在一个事务中
	 * @param executeCode
	 * @return
	 */
	public Object execute(IExecuteCode executeCode) {
		return executeCode.execute();
	}
	
	/**
	 * 查询sql语句，将第1列和第2列放入数组，结果集作为二维数组返回
	 * 
	 * @param sql select value, name from table1
	 * @return new String[count(*)][2]
	 */
	public String[][] paseToArrays(String sql) {
		return paseToArrays(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new String[]{rs.getString(1), rs.getString(2)};
			}
		});
	}
	
	/**
	 * 查询sql语句，rowMapper完成一维数组的创建，整个结果集作为二维数组返回
	 * 
	 * @param sql select value, name from table1
	 * @param rowMapper 创建一维数组的回调实现
	 * @return new String[count(*)][2]
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String[][] paseToArrays(String sql, RowMapper rowMapper) {
		return (String[][])query(sql, rowMapper).toArray(new String[0][0]);
	}
	
    private RowMapper getDefaultRowMapper(final Class className) {
    	return new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object obj = null;
				try {
					obj = className.newInstance();
					RmPopulateHelper.populate(obj, rs);
				} catch (InstantiationException e) {
					throw new RmRuntimeException("反射错误", e);
				} catch (IllegalAccessException e) {
					throw new RmRuntimeException("反射错误", e);
				}
				return obj;
			}
		};
    }
}
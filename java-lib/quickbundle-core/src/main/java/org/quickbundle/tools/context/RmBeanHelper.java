package org.quickbundle.tools.context;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.quickbundle.ICoreConstants;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.project.common.service.IRmCommonService;

public class RmBeanHelper {

	/**
	 * 获得通用的IRmCommonService
	 * 
	 * @return
	 */
	public static IRmCommonService getCommonServiceInstance() {
		return (IRmCommonService) RmBeanFactory.getBean("IRmCommonService");
	}

	/**
	 * 获得默认的DataSource
	 * 
	 * @return
	 */
	public static DataSource getDataSource() {
		DataSource ds = (DataSource) RmBeanFactory.getBean(ICoreConstants.DEFAULT_DATA_SOURCE);
		return ds;
	}

	/**
	 * 获得默认的Connection
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		DataSource ds = (DataSource) RmBeanFactory.getBean(ICoreConstants.DEFAULT_DATA_SOURCE);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new RmRuntimeException("获取默认数据源出错", e);
		}
		return conn;
	}
}

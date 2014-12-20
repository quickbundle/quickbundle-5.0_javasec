package org.quickbundle.third.quartz.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.utils.ConnectionProvider;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.tools.context.RmBeanHelper;

public class RmConnectionProvider implements ConnectionProvider {
	public Connection getConnection() throws SQLException {
		//return default dataSource
		return RmBeanHelper.getConnection();
	}
	
	public void initialize() throws SQLException{
		//TODO merge
	}

	public void shutdown() throws SQLException {
		//do nothing
	}

}

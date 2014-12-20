package org.quickbundle.base.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.quickbundle.ICoreConstants;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

public class RmJdbcTemplate extends JdbcTemplate {
	static enum EnumBoolean {
		NULL,
		TRUE,
		FALSE
	}
	private EnumBoolean absolutePage = EnumBoolean.NULL;
	public EnumBoolean getAbsolutePage() {
		return absolutePage;
	}
	/**
	 * is query page by ResultSet.absolute() mode? not spelling new sql
	 * @param absolutePage
	 */
	public void setAbsolutePage(boolean absolutePage) {
		if(absolutePage) {
			this.absolutePage = EnumBoolean.TRUE;
		} else {
			this.absolutePage = EnumBoolean.FALSE;
		}
	}

	// -------------------------------------------------------------------------
	// Methods dealing with static SQL (java.sql.Statement)
	// -------------------------------------------------------------------------
	public Object execute(StatementCallback action) throws DataAccessException {
		Assert.notNull(action, "Callback object must not be null");

		Connection con = DataSourceUtils.getConnection(getDataSource());
		Statement stmt = null;
		try {
			Connection conToUse = con;
			if (getNativeJdbcExtractor() != null &&
					getNativeJdbcExtractor().isNativeConnectionNecessaryForNativeStatements()) {
				conToUse = getNativeJdbcExtractor().getNativeConnection(con);
			}
			stmt = conToUse.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			applyStatementSettings(stmt);
			Statement stmtToUse = stmt;
			if (getNativeJdbcExtractor() != null) {
				stmtToUse = getNativeJdbcExtractor().getNativeStatement(stmt);
			}
			Object result = action.doInStatement(stmtToUse);
			handleWarnings(stmt);
			return result;
		}
		catch (SQLException ex) {
			// Release Connection early, to avoid potential connection pool deadlock
			// in the case when the exception translator hasn't been initialized yet.
			JdbcUtils.closeStatement(stmt);
			stmt = null;
			DataSourceUtils.releaseConnection(con, getDataSource());
			con = null;
			throw getExceptionTranslator().translate("StatementCallback", getSql(action), ex);
		}
		finally {
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
	}

	/**
	 * Determine SQL from potential provider object.
	 * @param sqlProvider object that's potentially a SqlProvider
	 * @return the SQL string, or <code>null</code>
	 * @see SqlProvider
	 */
	private static String getSql(Object sqlProvider) {
		if (sqlProvider instanceof SqlProvider) {
			return ((SqlProvider) sqlProvider).getSql();
		}
		else {
			return null;
		}
	}

	public int update(final String sql) throws DataAccessException {
		return super.update(appendTs(sql));
	}

	public int update(String sql, Object[] args) throws DataAccessException {
		return super.update(appendTs(sql), args, getSqlTypeFromArgs(args));
	}
	
	/**
	 * parse byte[] to Clob 对byte[]类型的字段，直接以流方式写入Blob
	 * @param sql
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public int updateWithBlob(String sql, final Object[] args) throws DataAccessException {
		return super.update(sql, new RmArgTypePreparedStatementSetter(args, getSqlTypeFromArgs(args)));
	}

	private static class RmArgTypePreparedStatementSetter implements PreparedStatementSetter, ParameterDisposer {
		private final Object[] args;
		private final int[] argTypes;

		/**
		 * Create a new ArgTypePreparedStatementSetter for the given arguments.
		 * @param args the arguments to set
		 * @param argTypes the corresponding SQL types of the arguments
		 */
		public RmArgTypePreparedStatementSetter(Object[] args, int[] argTypes) {
			if ((args != null && argTypes == null) || (args == null && argTypes != null) ||
					(args != null && args.length != argTypes.length)) {
				throw new InvalidDataAccessApiUsageException("args and argTypes parameters must match");
			}
			this.args = args;
			this.argTypes = argTypes;
		}


		public void setValues(PreparedStatement ps) throws SQLException {
			int argIndx = 1;
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					Object arg = this.args[i];
					if (arg instanceof Collection && this.argTypes[i] != Types.ARRAY) {
						Collection entries = (Collection) arg;
						for (Iterator it = entries.iterator(); it.hasNext();) {
							Object entry = it.next();
							if (entry instanceof Object[]) {
								Object[] valueArray = ((Object[])entry);
								for (int k = 0; k < valueArray.length; k++) {
									Object argValue = valueArray[k];
									StatementCreatorUtils.setParameterValue(ps, argIndx++, this.argTypes[i], argValue);
								}
							}
							else {
								StatementCreatorUtils.setParameterValue(ps, argIndx++, this.argTypes[i], entry);
							}
						}
					}
					else {
						if(this.argTypes[i] == Types.BLOB) {
							byte[] bytes = (byte[])this.args[i];
							ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
							ps.setBinaryStream(argIndx++, bais, bytes.length);
						} else {
							StatementCreatorUtils.setParameterValue(ps, argIndx++, this.argTypes[i], arg);
						}
					}
				}
			}
		}

		public void cleanupParameters() {
			StatementCreatorUtils.cleanupParameters(this.args);
		}
	}
	
	public int[] batchUpdate(final String[] sql) throws DataAccessException {
		for (int i = 0; i < sql.length; i++) {
			sql[i] = appendTs(sql[i]);
		}
		return super.batchUpdate(sql);
	}

	public int[] batchUpdate(String sql, final Object[] aObj, final CircleVoArray cva) throws DataAccessException {
		if (aObj.length == 0) {
			return new int[0];
		}
		sql = appendTs(sql);
		int[] aCount = super.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] args = cva.getArgs(aObj[i]);
				ArgTypePreparedStatementSetter atpss = new ArgTypePreparedStatementSetter(args, getSqlTypeFromArgs(args));
				atpss.setValues(ps);
			}

			public int getBatchSize() {
				return aObj.length;
			}
		});
		return aCount;
	}

	/**
	 * 自动加TS更新戳
	 * 
	 * @param sql
	 * @return
	 */
	private String appendTs(String sql) {
		// if(RmBaseConfig.sqlUpdateAutoAppendTs()) {
		// //检测是否有TS列
		// if(RmTableTsDetector.containTs(sql)) {
		// //sql = CrossDBObject.translate(sql);
		// }
		// }
		return sql;
	}

	/**
	 * @param args
	 * @return
	 */
	public static int[] getSqlTypeFromArgs(Object[] args) {
		int types[] = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				types[i] = Types.VARCHAR;
			} else if (args[i] instanceof java.sql.Timestamp) {
				types[i] = Types.TIMESTAMP;
			} else if (args[i] instanceof java.sql.Date) {
				types[i] = Types.DATE;
			} else if (args[i] instanceof java.sql.Time) {
				types[i] = Types.TIME;
			} else if (args[i] instanceof java.math.BigDecimal) {
				types[i] = Types.DECIMAL;
			} else if (args[i] instanceof Integer) {
				types[i] = Types.INTEGER;
			} else if (args[i] instanceof Long) {
				types[i] = Types.BIGINT;
			} else if (args[i] instanceof Short) {
				types[i] = Types.SMALLINT;
			} else if (args[i] instanceof Float) {
				types[i] = Types.FLOAT;
			} else if (args[i] instanceof Double) {
				types[i] = Types.DOUBLE;
			} else if (args[i] instanceof byte[]) {
				types[i] = Types.BLOB;
			} else {
				types[i] = Types.VARCHAR;
			}
		}
		return types;
	}
	
	/**
	 * 功能:
	 * 
	 * @param strsql
	 * @param rowMapper
	 * @param startIndex 开始位置(第一条是1，第二条是2...)
	 * @param size
	 * @return
	 */
	public List query(String strsql, RowMapper rowMapper, int startIndex, int size) {
		return query(strsql, rowMapper, startIndex, size, false);
	}

	/**
	 * 功能:
	 * 
	 * @param strsql
	 * @param rowMapper
	 * @param startIndex 开始位置(第一条是1，第二条是2...)
	 * @param size
	 * @param absoluteByNext circle ResultSet.next() instead of ResultSet.absolute(), because of JDBC driver not support so, such as DB2's CLOB 
	 * @return
	 */
	public List query(String strsql, RowMapper rowMapper, int startIndex, int size, boolean absoluteByNext) {
		if (RmBaseConfig.getSingleton().getDatabaseProductName() != null &&
				(getAbsolutePage().equals(EnumBoolean.FALSE) || (getAbsolutePage().equals(EnumBoolean.NULL) && !RmBaseConfig.getSingleton().isAbsolutePage()))) {
			if (ICoreConstants.DatabaseProductType.ORACLE.getDatabaseProductName().equalsIgnoreCase(RmBaseConfig.getSingleton().getDatabaseProductName())) {
				return (List) query(RmSqlHelper.getSqlPage4Oracle(strsql, startIndex, size), rowMapper);
			} else if (ICoreConstants.DatabaseProductType.MYSQL.getDatabaseProductName().equalsIgnoreCase(RmBaseConfig.getSingleton().getDatabaseProductName())) {
				return (List) query(RmSqlHelper.getSqlPage4Mysql(strsql, startIndex, size), rowMapper);
			}
		}
		return (List) query(strsql, new RmRowMapperResultSetExtractor(rowMapper, startIndex, size, absoluteByNext));
	}

	private static class RmRowMapperResultSetExtractor implements ResultSetExtractor {
		private final RowMapper rowMapper;
		private final int startIndex;
		private final int size;
		private boolean absoluteByNext;
		/**
		 * Create a new RowMapperResultSetExtractor.
		 * @param rowMapper the RowMapper which creates an object for each row
		 * @param startIndex
		 * @param size
		 * @param absolute
		 */
		public RmRowMapperResultSetExtractor(RowMapper rowMapper, int startIndex, int size, boolean absoluteByNext) {
			this.rowMapper = rowMapper;
			this.startIndex = startIndex;
			this.size = size;
			this.absoluteByNext = absoluteByNext;
		}

		public Object extractData(ResultSet rs) throws SQLException {
			List<Object> results = new ArrayList<Object>();
			int rowProcessed = 0;
			if(absoluteByNext) {
				if(startIndex > 0) {
					int pos = 1;
					while(pos < startIndex) {
						rs.next();
						pos ++;
					}
				}
			} else {
				if(startIndex > 0) {
					//moves to the given row number with respect to the beginning of the result set
					if(rs.getRow() != (startIndex - 1)) {
						rs.absolute(startIndex - 1);
					}
				} else if(startIndex < 0) {
					//moves to an absolute row position with respect to the end of the result set
					rs.absolute(startIndex - 1);
				}
			}
			while (rs.next() && rowProcessed < size) {
				results.add(this.rowMapper.mapRow(rs, rs.getRow()));
				rowProcessed++;
			}
			return results;
		}
	}

	public interface CircleVoArray {
		public Object[] getArgs(Object obj);
	}
}
package org.quickbundle.project.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.config.RmConfig;
import org.quickbundle.itf.code.IRmCodeService;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmGlobalReference;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

public class InitDatabaseHelper {
	
	/**
	 * 初始化数据库类型，同时如果app首次运行则初始化库表数据
	 */
	public static void initDatabase() {
		initDatabaseProductName();
		initDatabaseData();
	}

	/**
	 * 初始化数据库类型，必须在RmBeanFactory初始化完毕后才能被调用
	 */
	public static void initDatabaseProductName() {
		if(RmConfig.getSingleton().getDatabaseProductName() != null) {
			return;
		}
		// 初始化默认数据库类型
		Connection conn = null;
		try {
			conn = RmBeanHelper.getConnection();
			RmConfig.getSingleton().setDatabaseProductName(conn.getMetaData().getDatabaseProductName());
		} catch (Exception e) {
			try { // 尝试从jdbc.properties猜测数据库类型
				File fJdbc = new File(InitDatabaseHelper.class.getResource("config/jdbc.properties").getFile());
				if (fJdbc.exists()) {
					Properties pJdbc = new Properties();
					pJdbc.load(new FileInputStream(fJdbc));
					String dbName = pJdbc.getProperty("jdbc.driverClassName");
					if (dbName != null) {
						RmConfig.getSingleton().setDatabaseProductName(IGlobalConstants.DATABASE_PRODUCT_MAP.get(dbName));
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			if (RmConfig.getSingleton().getDatabaseProductName() == null) {
				throw new RmRuntimeException("初始化默认数据库出错", e);
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RmRuntimeException("关闭连接出错", e);
			}
		}
	}
	
	
	/**
	 * 初始化库表数据
	 */
	private static void initDatabaseData() {
		try {
			if (RmConfig.getSingleton().isSystemDebugMode()) {
				IRmCodeService codeService = (IRmCodeService) RmBeanFactory.getBean(IRmCodeService.class.getName());
				if (needExecuteInitTable()) {
					// 初始化数据库的内置表，只会执行一次。如果要再执行，需手动删除/WEB-INF/config/sql/lockInitTable文件
					codeService.executeInitTable();
				}
				// 从xml中初始化编码数据
				codeService.executeInitCodeTypeDataByXml();
			} else {
				// 初始化ID
				RmIdFactory.getIdGenerator();
				// 初始化编码表数据
				RmGlobalReference.getSingleton().initDataTotal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			RmLogHelper.error(InitDatabaseHelper.class, "init rm error: " + e.toString());
		}
	}
	

	/**
	 * 判断是否应当执行建表初始化SQL
	 * 
	 * @return
	 */
	static boolean needExecuteInitTable() {
		File fLock = new File(RmXmlHelper.formatToFile(RmConfig.getSingleton().getWarHome() + "/.lockInitTable"));
		try {
			return fLock.createNewFile();
		} catch (IOException e) {
			return false;
		}
	}
}

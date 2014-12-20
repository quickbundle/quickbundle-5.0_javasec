package org.quickbundle.config;

import java.io.File;

import org.quickbundle.config.RmBaseConfigVo;

public class RmConfigVo extends RmBaseConfigVo {
	
	private RmBaseConfigVo singleton = null;
	
	RmConfigVo(RmBaseConfigVo singleton) {
		super();
		this.singleton = singleton;
	}
	
	/**
	 * @return the warHome
	 */
	public String getWarHome() {
		return singleton.getWarHome();
	}

	/**
	 * @param warHome the warHome to set
	 */
	public void setWarHome(String warHome) {
		singleton.setWarHome(warHome);
	}

	/**
	 * @return 是集群模式？或单机？
	 */
	public boolean isClusterMode() {
		return singleton.isClusterMode();
	}

	/**
	 * @param clusterMode the clusterMode to set
	 */
	public void setClusterMode(boolean clusterMode) {
		singleton.setClusterMode(clusterMode);
	}

	/**
	 * @return 获得默认数据源的数据库类型  IGlobalConstants.DATABASE_PRODUCT_NAME_...
	 */
	public String getDatabaseProductName() {
		return singleton.getDatabaseProductName();
	}

	/**
	 * @param databaseProductName the databaseProductName to set
	 */
	public void setDatabaseProductName(String databaseProductName) {
		singleton.setDatabaseProductName(databaseProductName);
	}

	/**
	 * @return 系统是否开发调试状态(系统综合运行性能较低，优化了应用启动速度。同时sql的?替换输出，日志记录了sql真实数据)
	 */
	public boolean isSystemDebugMode() {
		return singleton.isSystemDebugMode();
	}

	/**
	 * @param systemDebugMode the systemDebugMode to set
	 */
	public void setSystemDebugMode(boolean systemDebugMode) {
		singleton.setSystemDebugMode(systemDebugMode);
	}

	/**
	 * @return 本集群节点RmIdFactory产生的主键前缀
	 */
	public String getShardingPrefix() {
		return singleton.getShardingPrefix();
	}

	/**
	 * @param shardingPrefix the shardingPrefix to set
	 */
	public void setShardingPrefix(String shardingPrefix) {
		singleton.setShardingPrefix(shardingPrefix);
	}
	
	/**
	 * @return 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	public boolean isUserUniqueLogin() {
		return singleton.isUserUniqueLogin();
	}

	/**
	 * @param userUniqueLogin the userUniqueLogin to set
	 */
	public void setUserUniqueLogin(boolean userUniqueLogin) {
		singleton.setUserUniqueLogin(userUniqueLogin);
	}

	/**
	 * @return 默认的分页条数，会被rm.xml/rm/RmJspHelper/pageSize覆盖
	 */
	public int getDefaultPageSize() {
		return singleton.getDefaultPageSize();
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		singleton.setDefaultPageSize(defaultPageSize);
	}

	/**
	 * @return the defaultBatchSize
	 */
	public int getDefaultBatchSize() {
		return singleton.getDefaultBatchSize();
	}

	/**
	 * @param defaultBatchSize the defaultBatchSize to set
	 */
	public void setDefaultBatchSize(int defaultBatchSize) {
		singleton.setDefaultBatchSize(defaultBatchSize);
	}

	/**
	 * @return 得到系统简短描述
	 */
	public String getAppDescription() {
		return singleton.getAppDescription();
	}

	/**
	 * @param appDescription the appDescription to set
	 */
	public void setAppDescription(String appDescription) {
		singleton.setAppDescription(appDescription);
	}

	/**
	 * @return 是否记录request的执行时间和SQL数量
	 */
	public boolean isLogRequest() {
		return singleton.isLogRequest();
	}

	/**
	 * @param logRequest the logRequest to set
	 */
	public void setLogRequest(boolean logRequest) {
		singleton.setLogRequest(logRequest);
	}

	/**
	 * @return the createPythonLibIfNotExist
	 */
	public boolean isCreatePythonLibIfNotExist() {
		return singleton.isCreatePythonLibIfNotExist();
	}

	/**
	 * @param createPythonLibIfNotExist the createPythonLibIfNotExist to set
	 */
	public void setCreatePythonLibIfNotExist(boolean createPythonLibIfNotExist) {
		singleton.setCreatePythonLibIfNotExist(createPythonLibIfNotExist);
	}

	/**
	 * @return the logicDeleteFile
	 */
	public boolean isLogicDeleteFile() {
		return singleton.isLogicDeleteFile();
	}

	/**
	 * @param logicDeleteFile the logicDeleteFile to set
	 */
	public void setLogicDeleteFile(boolean logicDeleteFile) {
		singleton.setLogicDeleteFile(logicDeleteFile);
	}

	/**
	 * @return the recycleBinFolder
	 */
	public String getRecycleBinFolder() {
		return singleton.getRecycleBinFolder();
	}

	/**
	 * @param recycleBinFolder the recycleBinFolder to set
	 */
	public void setRecycleBinFolder(String recycleBinFolder) {
		singleton.setRecycleBinFolder(recycleBinFolder);
	}

	public boolean isRememberPage() {
		return singleton.isRememberPage();
	}

	public void setRememberPage(boolean rememberPage) {
		singleton.setRememberPage(rememberPage);
	}

	public String getDefaultFont() {
		return singleton.getDefaultFont();
	}

	public void setDefaultFont(String defaultFont) {
		singleton.setDefaultFont(defaultFont);
	}

	
	//未加入rm.xml文件的配置
	/**
	 * 系统缓存检查周期
	 * @return
	 */
	public long getCacheCheckInterval() {
		return singleton.getCacheCheckInterval();
	}
	
	/**
	 * 是否全局监控
	 * @return
	 */
	public boolean isGlobalMonitor() {
		return singleton.isGlobalMonitor();
	}
	
	/**
	 * 系统缓存刷新周期
	 * @return
	 */
	public long getCacheFlushInterval() {
		return singleton.getCacheFlushInterval();
	}
	
    /**
     * 翻页是否用rs.absolute(index)的方案
     */
    public boolean isAbsolutePage() {
    	return singleton.isAbsolutePage();
    }
    
    /**
     * 批处理sql的最大记录日志数量
     */
    public int getMaxLogSqlBatchSize() {
    	return singleton.getMaxLogSqlBatchSize();
    }
	
	/**
	 * 系统用户登录是否DEMO状态(不校验用户数据库)
	 */
	public boolean isUserDemoMode() {
		return singleton.isUserDemoMode();
	}
	
	/**
	 * 是否给insert和update的sql语句自动加ts
	 */
	public boolean isSqlUpdateAutoAppendTs() {
		return singleton.isSqlUpdateAutoAppendTs();
	}
	
	/**
	 * 默认的临时文件夹
	 */
	public File getDefaultTempDir() {
		return singleton.getDefaultTempDir();
	}
	
	/**
	 * 默认编码
	 */
	public String getDefaultEncode() {
		return singleton.getDefaultEncode();
	}
	
	/**
	 * 默认实数数值的精度
	 */
	public int getDefaultNumberScale() {
		return singleton.getDefaultNumberScale();
	}
	
	/**
	 * 登录时是否有校验码
	 */
	public boolean isLoginValidateVerifyCode() {
		return singleton.isLoginValidateVerifyCode();
	}
	
	/**
	 * 登录是持否支持cookie
	 */
	public boolean isLoginCookie() {
		return singleton.isLoginCookie();
	}
	
	/**
	 * cookie默认值365天
	 */
	public int getDefaultCookieAge() {
		return singleton.getDefaultCookieAge();
	}
		
	/**
	 * ajax提交是否已json格式，还是post表单提交？
	 */
	public boolean isSubmitJson() {
		return singleton.isSubmitJson();
	}
	
	/**
	 * 默认的树形编码起始值，适用于简单的纯数字树，每个节点下最多有900个子节点
	 */
	public String getDefaultTreeCodeFirst() {
		return singleton.getDefaultTreeCodeFirst();
	}
    
	/**
	 * 指定最大循环次数，防止死循环
	 */
	public int getMaxCircleCount() {
		return singleton.getMaxCircleCount();
	}
	
	/**
	 * 定义单实例全局缓存的最大容量，防止溢出攻击，如公开的url列表
	 * @return
	 */
	public int getMaxCacheSize() {
		return singleton.getMaxCacheSize();
	}

	/**
	 * 是否启动任务调度
	 */
	private boolean schedulerStart = false;
	/**
	 * @return 是否启动任务调度
	 */
	public boolean isSchedulerStart() {
		return schedulerStart;
	}
	/**
	 * @param schedulerStart the schedulerStart to set
	 */
	public void setSchedulerStart(boolean schedulerStart) {
		this.schedulerStart = schedulerStart;
	}
	
	/**
	 * lazy start scheduler time(second), 0 or -1 ignore this | 延时启动scheduler的秒数
	 */
	private long schedulerStartLazy;
	/**
	 * lazy start scheduler time(second), 0 or -1 ignore this | 延时启动scheduler的秒数
	 * @return
	 */
	public long getSchedulerStartLazy() {
		return schedulerStartLazy;
	}
	/**
	 * lazy start scheduler time(second), 0 or -1 ignore this | 延时启动scheduler的秒数
	 * @param schedulerStartLazy
	 */
	public void setSchedulerStartLazy(long schedulerStartLazy) {
		this.schedulerStartLazy = schedulerStartLazy;
	}
}
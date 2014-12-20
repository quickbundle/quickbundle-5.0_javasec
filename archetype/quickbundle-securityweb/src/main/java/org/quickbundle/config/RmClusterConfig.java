package org.quickbundle.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quickbundle.project.init.AbstractClusterConfigLoader;

public class RmClusterConfig {

	public enum NodeKey {
		id,
		shardingPrefix,
		contentPath,
		baseUrl,
		webServiceUrl,
		user,
		password
	}
	
	@SuppressWarnings({ "serial" })
	public final static Map<String, String> DEFAULT_AUTH_KEY_VALUE = new HashMap<String, String>(){{
		this.put(NodeKey.user.name(), "1");
		this.put(NodeKey.password.name(), "1");
	}};
	
	private static HostInfo localhostInfo = null;
	private static volatile boolean isInit = false;
	public static HostInfo getLocalhostInfo() {
		return localhostInfo;
	}
	public static void initLocalhostInfo(HttpServletRequest request) {
		if (!isInit) {
			synchronized (RmClusterConfig.class) {
				if (!isInit) {
					localhostInfo = new HostInfo();
					localhostInfo.setScheme(request.getScheme());
					localhostInfo.setServerName(request.getServerName());
					localhostInfo.setServerPort(request.getServerPort());
					localhostInfo.setContextPath(request.getContextPath());
					singleton.refreshHostInfo(RmClusterConfig.getLocalhostInfo());
					isInit = true;
				}
			}
		}
	}
	
	/**
	 * 集群配置类的全局唯一单例
	 */
	private static AbstractClusterConfigLoader singleton;
	
	/**
	 * 得到集群配置类的全局唯一单例
	 * @return the singleton
	 */
	public static AbstractClusterConfigLoader getSingleton() {
		return singleton;
	}
	public static void setSingleton(AbstractClusterConfigLoader singleton) {
		RmClusterConfig.singleton = singleton;
	}
}
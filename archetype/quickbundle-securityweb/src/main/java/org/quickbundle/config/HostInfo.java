package org.quickbundle.config;

public class HostInfo {
	public HostInfo() {
	}

	private String scheme;
	private String serverName;
	private int serverPort;
	private String contextPath;

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	//组合方法
	/**
	 * @return 返回http://localhost:8080
	 */
	public String getLocalhostUrl() {
		return scheme + "://" + serverName + ":" + serverPort;
	}
	
	/**
	 * @return 返回http://localhost:8080/rmdemo
	 */
	public String getLocalhostUrlPath() {
		return scheme + "://" + serverName + ":" + serverPort + contextPath;
	}
}
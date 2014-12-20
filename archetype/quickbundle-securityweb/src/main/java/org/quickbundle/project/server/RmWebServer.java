package org.quickbundle.project.server;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
//import org.eclipse.jetty.security.ConstraintMapping;
//import org.eclipse.jetty.security.ConstraintSecurityHandler;
//import org.eclipse.jetty.security.HashLoginService;
//import org.eclipse.jetty.security.authentication.BasicAuthenticator;
//import org.eclipse.jetty.server.Connector;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.bio.SocketConnector;
//import org.eclipse.jetty.server.nio.SelectChannelConnector;
//import org.eclipse.jetty.server.ssl.SslSocketConnector;
//import org.eclipse.jetty.util.security.Constraint;
//import org.eclipse.jetty.util.security.Password;
//import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start up through an embedded Jetty instance.
 */
public class RmWebServer {
	
	private static int maxFindDeep4WebAppDir = 16;
//	static String defaultWebAppDir = null;
//	static {
//		defaultWebAppDir = findWebAppDir();
//	}
    protected static final Logger log = LoggerFactory.getLogger(RmWebServer.class);

    protected static final String DEFAULT_WEBAPP_DIR = System.getProperty(
            Constants.SYS_PROP_WEB_DIR, "./WebContent");

    public static final String DEFAULT_HTTP_PORT = System.getProperty(
            Constants.SYS_PROP_DEFAULT_HTTP_PORT, "80");

    public static final String DEFAULT_HTTPS_PORT = System.getProperty(
            Constants.SYS_PROP_DEFAULT_HTTPS_PORT, "443");
    
	public static final int DEFAULT_MAX_IDLE_TIME = 7200000;

	/**
	 * The type of HTTP connection to create for this web server
	 */
	public enum Mode {
		HTTP, HTTPS, MIXED;
	}

//	private Server server;
//
//	private WebAppContext webapp;
//
//	protected boolean join = true;
//
//    protected boolean createJmxServer = Boolean.parseBoolean(System.getProperty(
//            Constants.SYS_PROP_CREATE_JMX_SERVER, "false"));
//
//	protected String webHome = "/";
//
//	protected int maxIdleTime = DEFAULT_MAX_IDLE_TIME;
//
//	protected int httpPort = -1;
//
//	protected int httpsPort = -1;
//
//	protected String basicAuthUsername = null;
//
//	protected String basicAuthPassword = null;
//
//	protected String propertiesFile = null;
//
//	protected String host = null;
//
//	protected boolean noNio = false;
//
//	protected boolean noDirectBuffer = false;
//
//	protected String webAppDir = DEFAULT_WEBAPP_DIR;
//
//	protected String name = "rm-server";
//
//	public RmWebServer() {
//	}
//
//	public RmWebServer(String propertiesUrl) {
//		this(propertiesUrl, DEFAULT_WEBAPP_DIR);
//	}
//
//	public RmWebServer(String propertiesUrl, String webappDir) {
//		this.propertiesFile = propertiesUrl;
//		this.webAppDir = webappDir;
//	}
//
//    public RmWebServer(String webDirectory, int maxIdleTime, String propertiesUrl,
//            boolean join, boolean noNio, boolean noDirectBuffer) {
//		this(propertiesUrl, webDirectory);
//		this.maxIdleTime = maxIdleTime;
//		this.join = join;
//		this.noDirectBuffer = noDirectBuffer;
//		this.noNio = noNio;
//	}
//
//    public RmWebServer(int maxIdleTime, String propertiesUrl) {
//		this(propertiesUrl, DEFAULT_WEBAPP_DIR);
//		this.maxIdleTime = maxIdleTime;
//	}
//
//    public RmWebServer start(int port, String propertiesUrl) throws Exception {
//		this.propertiesFile = propertiesUrl;
//        return start(port);
//	}
//
//    public RmWebServer start() throws Exception {
//		if (httpPort > 0 && httpsPort > 0) {
//			return startMixed(httpPort, httpsPort);
//		} else if (httpPort > 0) {
//			return start(httpPort);
//		} else if (httpsPort > 0) {
//			return startSecure(httpsPort);
//		} else {
//            throw new IllegalStateException(
//                    "Either an http or https port needs to be set before starting the server.");
//		}
//	}
//
//    public RmWebServer start(int port) throws Exception {
//        return start(port, 0, Mode.HTTP);
//	}
//
//    public RmWebServer startSecure(int port) throws Exception {
//        return start(0, port, Mode.HTTPS);
//	}
//
//    public RmWebServer startMixed(int port, int securePort) throws Exception {
//        return start(port, securePort, Mode.MIXED);
//	}
//
//    public RmWebServer start(int port, int securePort, Mode mode) throws Exception {
//
//		// indicate to the app that we are in stand alone mode
//        System.setProperty(Constants.SYS_PROP_STANDALONE_WEB, "true");
//
//		server = new Server();
//
//        server.setConnectors(getConnectors(port, securePort, mode));
//        setupBasicAuthIfNeeded(server);
//
//		webapp = new WebAppContext();
//		webapp.setParentLoaderPriority(true);
//		webapp.setContextPath(webHome);
//		
//		if(webAppDir != null) {
//			webAppDir = defaultWebAppDir;
//		} else {
//			throw new RuntimeException("webAppDir is null");
//		}
//		webapp.setWar(webAppDir);
//
//		webapp.getSessionHandler().getSessionManager().setMaxInactiveInterval(maxIdleTime / 1000);
//        server.setHandler(webapp);
//
//        if (!StringUtils.isBlank(propertiesFile)) {
//            System.setProperty(Constants.OVERRIDE_PROPERTIES_FILE_1, propertiesFile);
//        }
//
//		server.start();
//
//        if (createJmxServer) {
//            int httpJmxPort = port != 0 ? port + 1 : securePort + 1;
//            registerHttpJmxAdaptor(httpJmxPort);
//        }
//
//		if (join) {
//            log.info("WebServerAboutToJoin");
//			server.join();
//		}
//
//		return this;
//	}
//
//	protected ServletContext getServletContext() {
//        return webapp != null ? webapp.getServletContext() : null;
//    }
//
//    protected void setupBasicAuthIfNeeded(Server server) {
//        if (StringUtils.isNotBlank(basicAuthUsername)) {
//            ConstraintSecurityHandler sh = new ConstraintSecurityHandler();
//
//            Constraint constraint = new Constraint();
//            constraint.setName(Constraint.__BASIC_AUTH);
//            ;
//            constraint.setRoles(new String[] { Constants.EMBEDDED_WEBSERVER_DEFAULT_ROLE });
//            constraint.setAuthenticate(true);
//
//            ConstraintMapping cm = new ConstraintMapping();
//            cm.setConstraint(constraint);
//            cm.setPathSpec("/*");
//            // sh.setConstraintMappings(new ConstraintMapping[] {cm});
//            sh.addConstraintMapping(cm);
//
//            sh.setAuthenticator(new BasicAuthenticator());
//
//            HashLoginService loginService = new HashLoginService();
//            loginService.putUser(basicAuthUsername, new Password(basicAuthPassword), null);
//            sh.setLoginService(loginService);
//
//            server.setHandler(sh);
//
//        }
//    }
//
//    protected Connector[] getConnectors(int port, int securePort, Mode mode) {
//        ArrayList<Connector> connectors = new ArrayList<Connector>();
//        String keyStoreFile = System.getProperty(Constants.SYSPROP_KEYSTORE);
//        String keyStoreType = System.getProperty(Constants.SYSPROP_KEYSTORE_TYPE);
//
//        if (mode.equals(Mode.HTTP) || mode.equals(Mode.MIXED)) {
//            Connector connector = null;
//            if (noNio) {
//                connector = new SocketConnector();
//            } else {
//                SelectChannelConnector nioConnector = new SelectChannelConnector();
//                nioConnector.setUseDirectBuffers(!noDirectBuffer);
//                connector = nioConnector;
//            }
//            connector.setPort(port);
//            connector.setHost(host);
//            connector.setMaxIdleTime(maxIdleTime);
//            connectors.add(connector);
//            log.info("WebServerStarting", name, port);
//        }
//        if (mode.equals(Mode.HTTPS) || mode.equals(Mode.MIXED)) {
//            Connector connector = new SslSocketConnector();
//            String keyStorePassword = System
//                    .getProperty(Constants.SYSPROP_KEYSTORE_PASSWORD);
//            keyStorePassword = (keyStorePassword != null) ? keyStorePassword
//                    : Constants.KEYSTORE_PASSWORD;
//            ((SslSocketConnector) connector).setKeystore(keyStoreFile);
//            ((SslSocketConnector) connector).setKeyPassword(keyStorePassword);
//            if (keyStoreType != null) {
//                ((SslSocketConnector) connector).setKeystoreType(keyStoreType);
//            }
//
//            ((SslSocketConnector) connector).setMaxIdleTime(maxIdleTime);
//            connector.setPort(securePort);
//            connector.setHost(host);
//            connectors.add(connector);
//            log.info("WebServerSecureStarting", securePort);
//        }
//        return connectors.toArray(new Connector[connectors.size()]);
//    }
//
//    protected void registerHttpJmxAdaptor(int jmxPort) throws Exception {
////        if (isSystemPropertySet(Constants.JMX_HTTP_CONSOLE_ENABLED, true)) {
////            log.info("JMXConsoleStarting", jmxPort);
////            MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
////            ObjectName name = getHttpJmxAdaptorName();
////            mbeanServer.createMBean(HttpAdaptor.class.getName(), name);
////            if (!isSystemPropertySet(Constants.JMX_HTTP_CONSOLE_LOCALHOST_ENABLED,
////                    true)) {
////                mbeanServer.setAttribute(name, new Attribute("Host", "0.0.0.0"));
////            } else if (StringUtils.isNotBlank(host)) {
////                mbeanServer.setAttribute(name, new Attribute("Host", host));
////            } 
////            mbeanServer.setAttribute(name, new Attribute("Port", new Integer(jmxPort)));
////            ObjectName processorName = getXslJmxAdaptorName();
////            mbeanServer.createMBean(XSLTProcessor.class.getName(), processorName);
////            mbeanServer.setAttribute(name, new Attribute("ProcessorName", processorName));
////            mbeanServer.invoke(name, "start", null, null);
////        }
//    }
//
//    protected ObjectName getHttpJmxAdaptorName() throws MalformedObjectNameException {
//        return new ObjectName("Server:name=HttpAdaptor");
//    }
//
//    protected ObjectName getXslJmxAdaptorName() throws MalformedObjectNameException {
//        return new ObjectName("Server:name=XSLTProcessor");
//    }
//
//    protected void removeHttpJmxAdaptor() {
//        if (isSystemPropertySet(Constants.JMX_HTTP_CONSOLE_ENABLED, true)) {
//            try {
//                MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
//                mbeanServer.unregisterMBean(getHttpJmxAdaptorName());
//                mbeanServer.unregisterMBean(getXslJmxAdaptorName());
//            } catch (Exception e) {
//                log.warn("JMXAdaptorUnregisterFailed");
//            }
//        }
//    }
//
//    public void stop() throws Exception {
//        if (server != null) {
//            if (createJmxServer) {
//                removeHttpJmxAdaptor();
//            }
//            server.stop();
//        }
//    }
//
//    public boolean isJoin() {
//        return join;
//    }
//
//    public void setJoin(boolean join) {
//        this.join = join;
//    }
//
//    public void setWebHome(String webHome) {
//        this.webHome = webHome;
//    }
//
//    public int getMaxIdleTime() {
//        return maxIdleTime;
//    }
//
//    public void setMaxIdleTime(int maxIdleTime) {
//        this.maxIdleTime = maxIdleTime;
//    }
//
//    public void setHttpPort(int httpPort) {
//        this.httpPort = httpPort;
//    }
//
//    public int getHttpPort() {
//        return httpPort;
//    }
//
//    public void setHttpsPort(int httpsPort) {
//        this.httpsPort = httpsPort;
//    }
//
//    public int getHttpsPort() {
//        return httpsPort;
//    }
//
//    public void setPropertiesFile(String propertiesFile) {
//        this.propertiesFile = propertiesFile;
//    }
//
//    public void setCreateJmxServer(boolean createJmxServer) {
//        this.createJmxServer = createJmxServer;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public void setBasicAuthPassword(String basicAuthPassword) {
//        this.basicAuthPassword = basicAuthPassword;
//    }
//
//    public void setBasicAuthUsername(String basicAuthUsername) {
//        this.basicAuthUsername = basicAuthUsername;
//    }
//
//    public void setWebAppDir(String webAppDir) {
//        this.webAppDir = webAppDir;
//    }
//
//    public void setNoNio(boolean noNio) {
//        this.noNio = noNio;
//    }
//
//    public void setNoDirectBuffer(boolean noDirectBuffer) {
//        this.noDirectBuffer = noDirectBuffer;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }

	/**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     * 
     * @param filePath
     * @param osSeparatorStr 指定当前操作系统分隔符
     * @return
     */
    private static String formatToFile(String filePath, String osSeparatorStr) {
    	if(filePath == null) {
    		return null;
    	}
        String osSeparatorRegex = ("\\".equals(osSeparatorStr)) ? "\\\\" : osSeparatorStr;
        filePath = filePath.replaceFirst("file:[/\\\\]*", "");
        filePath = filePath.replaceAll("[/\\\\]+", osSeparatorRegex);
        //自动补齐Linux下的/
        if("/".equals(osSeparatorStr) && !filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        return filePath;
    }
    
    /**
     * 功能: 将路径格式化为url --> file:///c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrl(String filePath) {
    	if(filePath == null) {
    		return null;
    	}
        filePath = filePath.replaceFirst("file:[/\\\\]*", "");
        filePath = filePath.replaceAll("[/\\\\]+", "/");
        if (filePath.startsWith("/")) {
            filePath = "file://" + filePath;        	
        } else {
        	filePath = "file:///" + filePath;        	
        }
        return filePath;
    }
    
    private static boolean isSystemPropertySet(String propName, boolean defaultValue) {
        return "true"
                .equalsIgnoreCase(System.getProperty(propName, Boolean.toString(defaultValue)));
    }

    /**
     * 自动向父目录递归找WebAppDir
     * @return
     */
    private static String findWebAppDir() {
		File classLocation = new File(formatToFile(RmWebServer.class.getResource("").toString(), File.separator));
		int deep = 0;
		File webInfoDir = null;
		File currentDir = classLocation;
		while(deep < RmWebServer.maxFindDeep4WebAppDir && currentDir != null) {
			deep ++;
			if("WEB-INF".equals(currentDir.getName()) && new File(currentDir + File.separator + "web.xml").exists()) {
				webInfoDir = currentDir;
				break;
			}
			currentDir = currentDir.getParentFile();
		}
		if(webInfoDir != null) {
			return webInfoDir.getParentFile().toString();
		}
		return null;
	}
}
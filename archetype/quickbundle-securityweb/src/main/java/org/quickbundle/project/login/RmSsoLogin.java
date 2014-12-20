package org.quickbundle.project.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Element;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.base.vo.RmValueObject;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmLoadConfig;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.secure.RmCryptoHelper;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmUUIDHelper;
import org.quickbundle.tools.support.encrypt.Md5Token;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmSsoLogin {
	private static final Logger log = RmLogHelper.getLogger(RmSsoLogin.class);
	
	public final static String ssoKey = "rm_sso";
	public final static String splictKey = "$"; 
	public final static String splictKeyRegex = "\\$";
	public final static long defaultExpired = 1000 * 60 * 30; 
	private static String privateKey = RmUUIDHelper.generateUUID();
	
	
	/**
	 * 处理待跳转的url
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException 
	 */
	public static boolean doRedirectUrl(ServletRequest request, ServletResponse response, FilterChain filterChain, String pUrl) {
		try {
			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse res = (HttpServletResponse)response;
			Element thisNode = null;
			for(Object nodeObj : RmLoadConfig.getRmClusterDoc().selectNodes("/rm/org.quickbundle.project.login.RmSsoLogin/redirectGroup[@enable='true']/redirectUrls/url")) {
				Element node = (Element)nodeObj;
				if(node.getText().equals(pUrl)) {
					thisNode = node;
					break;
				}
			}
			if(thisNode == null) {
				throw new RmRuntimeException("配置文件读取错误");
			}
			String targetUrlPrefix = null;
			for(Object baseUrlObj : thisNode.selectNodes("../../redirectTargets/baseUrl")) {
				//TODO 可扩展为负载均衡算法
				Element eleUrlPrefix = (Element)baseUrlObj;
				targetUrlPrefix = eleUrlPrefix.getText();
				break;
			}
			
			if(targetUrlPrefix.length() == 0) {
				throw new RmRuntimeException("未配置跳转到的目标地址");
			}
			//带着sso信息跳转到目标服务器
			if(RmClusterConfig.getLocalhostInfo() != null 
					&& targetUrlPrefix.startsWith(RmClusterConfig.getLocalhostInfo().getLocalhostUrlPath())) {
				//throw new RmRuntimeException("不能跳转到自身，可能导致循环跳转");
				//如果判断为跳到本机，忽略跳转
				filterChain.doFilter(request, response);
				return true;
			}
			res.sendRedirect(rebuildUri(req, targetUrlPrefix));
			return true;
		} catch (Exception e) {
			log.error("doRedirectUrl():" + e.toString() + " cause:" + e.getCause());
			//save error
			request.setAttribute("org.apache.struts.action.EXCEPTION", e);
			return false;
		}
	}
	
	static String rebuildUri(HttpServletRequest request, String redirectUrlPrefix) {
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		StringBuilder url = new StringBuilder();
		if(redirectUrlPrefix.endsWith("/")) {
			redirectUrlPrefix = redirectUrlPrefix.substring(0, redirectUrlPrefix.length()-1);
		}
		url.append(redirectUrlPrefix);
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		url.append(uri);
		url.append("?");
		if(request.getQueryString() != null) {
			url.append(request.getQueryString());
			url.append("&");
		}
		url.append(ssoKey);
		url.append("=");
		try {
			url.append(RmStringHelper.encodeUrl(RmCryptoHelper.encryptDesBase64(RmSsoLogin.createInstance(session.getId()).toString())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url.toString();
	}
	
	public static boolean doSsoLogin(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		try {
			HttpSession session = ((HttpServletRequest)request).getSession(true);
			//临时登录超时时间
			session.setMaxInactiveInterval(60 * 3);
			String ssoValue = request.getParameter(ssoKey);
			try {
				ssoValue = RmCryptoHelper.decryptDesBase64(ssoValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] ssoValueArgs = ssoValue.split(splictKeyRegex);
			String nodeId = ssoValueArgs[0];
			String sessionId = ssoValueArgs[2];
			String callWsUrl = RmClusterConfig.getSingleton().getSelfNode().get(RmClusterConfig.NodeKey.webServiceUrl.name());
			String address = callWsUrl + "RmSsoLogin";
			JaxWsProxyFactoryBean jw = new JaxWsProxyFactoryBean();
			jw.setServiceClass(IRmSsoService.class);
			jw.setAddress(address);
			Object obj = jw.create();
			IRmSsoService ssoService = (IRmSsoService) obj;
			
			RmUserVo userVo = ssoService.copyLogin(sessionId, ssoValue);
			session.setAttribute(IGlobalConstants.RM_USER_VO, userVo);
			session.setAttribute(IGlobalConstants.RM_SSO_TEMP, IGlobalConstants.RM_YES);
			return true;
		} catch (Exception e) {
			log.error("doSsoLogin():" + e.toString() + " cause:" + e.getCause());
			//save error
			request.setAttribute("org.apache.struts.action.EXCEPTION", e);
			return false;
		}
	}
	
	public static RmSsoVo createInstance(String sessionId) {
		RmSsoVo instance = new RmSsoVo();
		instance.setNodeId(RmClusterConfig.getSingleton().getSelfId());
		instance.setExpired(String.valueOf(System.currentTimeMillis() + defaultExpired));
		instance.setSessionId(sessionId);
		instance.setHash(Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(instance.nodeId + instance.expired + instance.sessionId) + privateKey));
		return instance;
	}
	
	public static class RmSsoVo extends RmValueObject {
		private RmSsoVo() {
		}
		
		public RmSsoVo(String ssoValue) {
			String[] args = ssoValue.split(splictKeyRegex);
			nodeId = args[0];
			expired = args[1];
			sessionId = args[2];
			hash = args[3];
		}
		
		public boolean validateSsoVo() {
			String hash_ = Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(nodeId + expired + sessionId) + privateKey);
			if(hash_.equals(hash)) {
				return true;
			} else {
				return false;
			}
		}
		
		private String nodeId;
		private String expired;
		private String sessionId;
		private String hash;
		/**
		 * @return the nodeId
		 */
		public String getNodeId() {
			return nodeId;
		}
		/**
		 * @param nodeId the nodeId to set
		 */
		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}
		/**
		 * @return the expired
		 */
		public String getExpired() {
			return expired;
		}
		/**
		 * @param expired the expired to set
		 */
		public void setExpired(String expired) {
			this.expired = expired;
		}
		/**
		 * @return the sessionId
		 */
		public String getSessionId() {
			return sessionId;
		}
		/**
		 * @param sessionId the sessionId to set
		 */
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		/**
		 * @return the hash
		 */
		public String getHash() {
			return hash;
		}
		/**
		 * @param hash the hash to set
		 */
		public void setHash(String hash) {
			this.hash = hash;
		}
		@Override
		public String toString() {
			return nodeId + splictKey + expired + splictKey + sessionId + splictKey + hash;
		}
	}
	public static void main(String[] args) {
		try {
			String value = "中国";
//			byte[] tmp = RmProjectHelper.encrypt(value.getBytes(), desKey.getBytes());
//			System.out.println(new String(tmp));
//			System.out.println(Base64.encodeBase64String(tmp));
//			System.out.println(new String());
//			System.out.println(new String(RmProjectHelper.decrypt(Base64.decodeBase64(Base64.encodeBase64String(tmp)), desKey.getBytes())));
			
			value = RmCryptoHelper.encryptDesBase64(value);
			System.out.println(value);
			System.out.println(RmCryptoHelper.decryptDesBase64(value));
			
			value = "我爱北京天安门34";
			value = RmCryptoHelper.encryptBase64(value);
			System.out.println(value);
			System.out.println(RmCryptoHelper.decryptBase64(value));
			System.out.println("5oiR54ix5YyX5Lqs5aSp5a6J6ZeoMzQ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package org.quickbundle.project.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Node;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmConfig;
import org.quickbundle.config.RmLoadConfig;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmFunctionNodeCache;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.listener.RmGlobalMonitor;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.project.login.RmSsoLogin;
import org.quickbundle.project.login.RmUserVo;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmUUIDHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

/**
 * @author   
 * 实现权限过滤器
 */
public class RmPrivilegeFilter implements Filter {
	//用于记录当前类的报错信息
	private static final Logger logError = RmLogHelper.getLogger(RmPrivilegeFilter.class);
   
    //开始url过滤
	private static final Logger logUrl = RmLogHelper.getLogger("url");
	
	private FilterConfig filterConfig;

	public RmPrivilegeFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	

	/**
	 * 缓存经过正则运算后、可公开访问的资源，最大容量为RmConfig.maxCacheSize()
	 */
	private static List<String> lCacheRequestUri = new CopyOnWriteArrayList<String>();
	/**
	 * 可以公开访问的资源url正则集合，从rm.xml读取
	 */
	private static Set<String> sIgnoreUrl = new HashSet<String>();
	/**
	 * 真正的业务url请求(排除静态资源)
	 */
	private static Pattern validBsUrlMatch = null;
	/**
	 * 双检锁标志，判断是否已从rm.xml初始化ignoreUrls配置完成
	 */
	private static volatile boolean isInitIgnoreUrlConf = false;
	private static byte[] lockIgnoreUrlConf = new byte[0];
	
	/**
	 * 双检锁标志，判断是否已从rmCluster.xml初始化redirectUrls配置完成
	 */
	private static volatile boolean isInitRedirectUrlsConf = false;
	private static byte[] lockRedirectUrlsConf = new byte[0];
	private static Set<String> sRedirectUrl = null;

	/**
	 * 主要处理部分
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		boolean needClearTl = false;
		try {
			doInitConfig();
            
            //处理权限 begin
    		HttpServletRequest req = (HttpServletRequest) request;
    		HttpServletResponse res = (HttpServletResponse) response;
    		
    		{ 	//P3P http://www.w3.org/TR/p3pdeployment P3P: policyref="http://catalog.example.com/P3P/PolicyReferences.xml",CP="NON DSP COR CURa ADMa DEVa CUSa TAIa OUR SAMa IND"
    			//•policyref - this field gives the location of the policy reference file; its value is a URL where the site´s policy reference file can be located
    			//•CP - this field gives a compact policy for the resource (URL) that has been requested. Its value is the text of the compact P3P policy.
    			//res.setHeader("P3P","CP=\"NON DSP COR CURa ADMa DEVa CUSa TAIa OUR SAMa IND\"");
    		}
    		
    		boolean isLogin = false;
    		HttpSession session = RmJspHelper.getSession(request, false);
            if(session != null && session.getAttribute(IGlobalConstants.RM_USER_VO) != null) {
                isLogin = true; 
            }
    		String uri = req.getRequestURI();
    		StringBuilder fullUrl = new StringBuilder();
    		{ //log url
    			fullUrl.append(uri);
    			if (req.getQueryString() != null) {
    				fullUrl.append("?");
    				fullUrl.append(req.getQueryString());
    			}
    			logUrl.debug(fullUrl.toString());
    		}
    		String shortUri = uri.substring(req.getContextPath().length());
    		if(uri.equals(request.getAttribute("javax.servlet.forward.request_uri"))) {
    			RmProjectHelper.logFatal(uri + "可能存在forward死循环", null);
    		}
    		if(request.getAttribute("javax.servlet.forward.request_uri") == null && 
    				validBsUrlMatch.matcher(uri).find()) {
    			//1, 产生全局唯一的UUID
    			RmGlobalMonitor.uniqueUUID.set(RmUUIDHelper.generateUUID());
    			
    			//2, 绑定request到当前线程
    			RmRequestMonitor.tlCurrentRequest.set(request);
    			
    			if(RmConfig.getSingleton().isLogRequest()) {
    				//3, 初始化sqlCount
    				RmRequestMonitor.tlSqlCount.set(new long[]{0, 0, 0, 0});
    			}
    			
    			needClearTl = true;
    		}
    		try {
    			RmClusterConfig.initLocalhostInfo(req);
    		} catch(Throwable e) {
    			logError.warn("initLocalhostInfo: " + e.toString() + " cause:" + e.getCause());
    		}
    		
			//校验是否合法访问
			if(RmConfig.getSingleton().isUserDemoMode()) { //demo模式不校验登录
				filterChain.doFilter(request, response);
			} else if(lCacheRequestUri.contains(shortUri) || setContainMatchString(sIgnoreUrl, shortUri)) { //如果是匿名用户允许访问的地址
				filterChain.doFilter(request, response);
			} else if(isLogin) { //如果是已登录
				if(isValidAccessUrl(req, fullUrl.toString())) {
		    		//处理待跳转到其它服务器的url
		    		if(sRedirectUrl != null) {
		    			for(String pUrl : sRedirectUrl) {
		    				if(!shortUri.matches(pUrl)) { //不符合跳转到其它服务器的url，则继续
		    					continue;
		    				}
	    					if(RmSsoLogin.doRedirectUrl(request, response, filterChain, pUrl)) {
	    						return; //跳转到其它服务器后, 忽略剩余过滤器
	    					} else {
	    						request.getRequestDispatcher("/jsp/common/err.jsp").forward(request, response);
	    					}
	    					break;
		    			}
		    		}
		    		//验证通过，正常情况
					filterChain.doFilter(request, response);
				} else {
					//res.sendRedirect(req.getContextPath() +  "/jsp/util/noPermission.jsp");
					request.getRequestDispatcher("/jsp/util/noPermission.jsp").forward(request, response);
				}
			} else { //非法访问
				boolean ssoSuccess = false;
	    		//处理跳转到当前服务器的url
	    		if(request.getParameter(RmSsoLogin.ssoKey) != null && sRedirectUrl != null) {
	    			for(String pUrl : sRedirectUrl) {
	    				if(!shortUri.matches(pUrl)) { //不符合跳转到其它服务器的url，则继续
	    					continue;
	    				}
    					if(RmSsoLogin.doSsoLogin(request, response, filterChain)) {
    						ssoSuccess = true;
    						filterChain.doFilter(request, response);
    					} else {
    						request.getRequestDispatcher("/jsp/common/err.jsp").forward(request, response);
    						return;
    					}
    					break;
	    			}
	    		}
	    		if(!ssoSuccess) {
	    			logError.warn("Access denied url: " + fullUrl.toString());
	    			//RmJspHelper.setProfile(req, res, "toUrl", fullUrl.substring(req.getContextPath().length()), 60);
	    			res.sendRedirect(req.getContextPath() +  "/");
	    		}
			}
			//处理权限end
		} finally {
			if(needClearTl) {
				if(RmConfig.getSingleton().isLogRequest()) {
					try {
						//记录线程绑定的sql日志
						RmRequestMonitor.logTlSqlCount((HttpServletRequest)request);
					} catch (Exception e) {
						e.printStackTrace();
						logError.error("logTlSqlCount request: " + e.toString());
					}
					try {
						//清空sql和time
						RmRequestMonitor.tlSqlCount.remove();
					} catch (Exception e) {
						logError.error("remove tlSqlCount: " + e.toString());
					}
				}
				try {
					//清空当前线程绑定的request
					RmRequestMonitor.tlCurrentRequest.remove();
				} catch (Exception e) {
					logError.error("remove tlCurrentRequest: " + e.toString());
				}
				try {
					//清空UUID
					RmGlobalMonitor.uniqueUUID.remove();
				} catch (Exception e) {
					logError.error("remove uniqueUUID: " + e.toString());
				}
			}
		}
	}
	
	/**
	 * 初始化配置
	 */
	private void doInitConfig() {
		if(!isInitIgnoreUrlConf) { //双检锁初始化
			synchronized (lockIgnoreUrlConf) {
				if(!isInitIgnoreUrlConf) {
					List<Node> lUrl = RmLoadConfig.getRmDoc().selectNodes("/rm/org.quickbundle.project.filter.RmPrivilegeFilter/ignoreUrls/url");
					for(Node node : lUrl) {
						sIgnoreUrl.add(node.getText().trim());
					}
					validBsUrlMatch = Pattern.compile(RmLoadConfig.getRmDoc().valueOf("/rm/org.quickbundle.project.filter.RmPrivilegeFilter/validBsUrlMatch/text()"));
					isInitIgnoreUrlConf = true;
				}
			}
		}
		if(!isInitRedirectUrlsConf) { //双检锁初始化处理待跳转的url
			synchronized (lockRedirectUrlsConf) {
				if(!isInitRedirectUrlsConf) {
					Document rmClusterDoc = RmLoadConfig.getRmClusterDoc();
					if(rmClusterDoc.selectNodes("/rm/org.quickbundle.project.login.RmSsoLogin/redirectGroup[@enable='true']").size() > 0) {
						sRedirectUrl = new HashSet<String>();
						List<Node> lUrl = rmClusterDoc.selectNodes("/rm/org.quickbundle.project.login.RmSsoLogin/redirectGroup[@enable='true']/redirectUrls/url");
						for(Node node : lUrl) {
							sRedirectUrl.add(node.getText().trim());
						}
					}
					isInitIgnoreUrlConf = true;
				}
			}
		}
	}
	
	/**
	 * 用户登录后，判断其是否能访问这个url（普通页面 or 管理员登录返回true）
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static boolean isValidAccessUrl(HttpServletRequest request, String url) {
//		return true; //不做进一步校验
		//orgauth begin
		if(url.startsWith(request.getContextPath())) {//去掉/webAppName前缀
			url = url.substring(request.getContextPath().length());
		}
		RmUserVo userVo = RmProjectHelper.getRmUserVo(request);
		if(userVo != null && IOrgauthConstants.UserAdminType.ADMIN.value().equals(userVo.getAdmin_type())) { //管理员都能访问
			return true;
		} else if(url.matches("^/admin/.*$")) { //监控界面严格按权限访问
			return false;
		} else {
//			return true;
		}
		Set<String> sTotal_code = RmFunctionNodeCache.getTotal_code(url);
		if(sTotal_code == null || sTotal_code.size() == 0 || userVo == null) {
			return true;
		}
		List<String> menuList = userVo.getMenuList();
		for(String total_code : sTotal_code) {
			if(menuList.contains(total_code)) {
				return true;
			}
		}
		return false;
		//orgauth end
	}
	
	/**
	 * 功能: 判断Set中是否有使str符合的正则匹配值
	 *
	 * @param set
	 * @param str
	 * @return
	 */
	private boolean setContainMatchString(Set<String> set, String url) {
	    if(set == null) {
	        return true;
	    }
	    for (String p : set) {
            if(!url.matches(p)) {
            	continue;
            }
            if(!lCacheRequestUri.contains(url)) {
            	if(lCacheRequestUri.size() > RmConfig.getSingleton().getMaxCacheSize()) {
            		lCacheRequestUri.remove(0);
            	}
            	lCacheRequestUri.add(url);
            }
            return true;
        }
	    return false;
	}

	public void destroy() {
		this.filterConfig = null;
	}
}
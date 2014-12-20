package org.quickbundle.project.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.login.IRmLoginService;

public class RmSessionListener implements ServletContextListener, HttpSessionListener, Serializable {
    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    public IRmLoginService getLoginService() {
        return (IRmLoginService) RmBeanFactory.getBean(IRmLoginService.class.getName());  //得到Service对象,受事务控制
    }
    
	private static final long serialVersionUID = 8124944319058843901L;
	private static final String SESSION_ACTIVATION_KEY = "rm.session_activation_key";
	private static final AtomicInteger SESSION_COUNT = new AtomicInteger();

	private static final Map<String, HttpSession> SESSION_MAP_BY_ID = new ConcurrentHashMap<String, HttpSession>();
	
	private static int defaultMaxInactiveInterval;
	/**
	 * @return the defaultMaxInactiveInterval
	 */
	public static int getDefaultMaxInactiveInterval() {
		return defaultMaxInactiveInterval;
	}

	public RmSessionListener() {
		super();
	}

	/**
	 * 得到session总数
	 * @return
	 */
	public static int getSessionCount() {
		return SESSION_COUNT.get();
	}

	//初始化web application
	public void contextInitialized(ServletContextEvent event) {

	}

	//销毁web application
	public void contextDestroyed(ServletContextEvent event) {
		SESSION_MAP_BY_ID.clear();
		SESSION_COUNT.set(0);
	}

	public void sessionCreated(HttpSessionEvent event) {
		final HttpSession session = event.getSession();
		if (session.getAttribute(SESSION_ACTIVATION_KEY) == this) {
			for (final Map.Entry<String, HttpSession> entry : SESSION_MAP_BY_ID.entrySet()) {
				final String id = entry.getKey();
				final HttpSession other = entry.getValue();
				if (!id.equals(other.getId())) {
					SESSION_MAP_BY_ID.remove(id);
				}
			}
		} else {
			session.setAttribute(SESSION_ACTIVATION_KEY, this);
			SESSION_COUNT.incrementAndGet();
		}

		SESSION_MAP_BY_ID.put(session.getId(), session);
		//初始化defaultMaxInactiveInterval
		if(defaultMaxInactiveInterval == 0) {
			defaultMaxInactiveInterval = session.getMaxInactiveInterval();
		}
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		final HttpSession session = event.getSession();
		SESSION_COUNT.decrementAndGet();
		SESSION_MAP_BY_ID.remove(session.getId());
		
		autoCompleteLogout(session);
	}

	/** {@inheritDoc} */
	public void sessionDidActivate(HttpSessionEvent event) {
		SESSION_COUNT.incrementAndGet();
		SESSION_MAP_BY_ID.put(event.getSession().getId(), event.getSession());
	}

	/** {@inheritDoc} */
	public void sessionWillPassivate(HttpSessionEvent event) {
		SESSION_COUNT.decrementAndGet();
		SESSION_MAP_BY_ID.remove(event.getSession().getId());
	}
	
	/**
	 * 得到所有在线用户
	 * @return
	 */
	public static Map<String, HttpSession> getSessions() {
		return SESSION_MAP_BY_ID;
	}
	
	/**
	 * 根据sessionId获得session
	 * @param sessionId
	 * @return
	 */
	public static HttpSession getSessionById(String sessionId) {
		return SESSION_MAP_BY_ID.get(sessionId);
	}

	
	/**
	 * session超时或调用session.invalidate()时，找出本session关联的用户登录信息，完成用户的在线记录
	 * @param session
	 */
	private void autoCompleteLogout(HttpSession session) {
		if(session.getAttribute(IGlobalConstants.RM_USER_VO) != null) {
			getLoginService().executeDestroyUserInfo(session);
		}
	}
}
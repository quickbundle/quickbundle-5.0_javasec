package org.quickbundle.project;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.config.RmLoadConfig;
import org.quickbundle.modules.log.ActionLog2DbService;
import org.quickbundle.modules.log.ILogConstants;
import org.quickbundle.modules.log.RmLogTypeCache;
import org.quickbundle.modules.log.rmlog.vo.RmLogVo;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.project.listener.RmGlobalMonitor;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.project.login.IRmLoginVo;
import org.quickbundle.project.login.RmUserVo;
import org.quickbundle.project.mail.IRmMailService;
import org.quickbundle.project.test.RmAlarmCollector;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public final class RmProjectHelper implements IGlobalConstants{
	private static Logger log = RmLogHelper.getLogger(RmProjectHelper.class);
	private static Logger logFatal = RmLogHelper.getLogger("rmfatal");

    private RmProjectHelper() {
    }
    
    /**
     * 获得通用的IRmCommonService
     * @return
     */
    public static IRmCommonService getCommonServiceInstance() {
        return RmBeanHelper.getCommonServiceInstance();
    }
    
    /**
     * 功能: 获取当前线程的HttpServletRequest(request)对象
     *
     * @return
     */
    public static HttpServletRequest getCurrentThreadRequest() {
    	return RmRequestMonitor.getCurrentThreadRequest();
    }

    /**
     * 返回登录名，用于校验是否有效登录
     * 
     * @param request
     * @return
     */
    public static String getRmLoginId(ServletRequest request) {
    	return getRmLoginId(request, false);
    }
    
    /**
     * 返回登录名，用于校验是否有效登录
     * 
     * @param request
     * @param create
     * @return
     */
    public static String getRmLoginId(ServletRequest request, boolean createSession) {
    	HttpSession session = RmJspHelper.getSession(request, createSession);
    	if(session == null) {
    		return null;
    	}
    	Object obj = session.getAttribute(RM_USER_VO);
    	if(obj == null) {
    		return null;
    	}
        return ((IRmLoginVo)obj).getLogin_id();
    }
    
    /**
     * 返回user_id，用于vo自动打戳 
     *
     * @param request
     * @return
     */
    public static String getRmUserId(ServletRequest request) {
    	return getRmUserId(request, false);
    }
    
    /**
     * 返回user_id，用于vo自动打戳 
     * 
     * @param request
     * @param createSession
     * @return
     */
    public static String getRmUserId(ServletRequest request, boolean createSession) {
    	return getRmUserVo(request, createSession).getId();
    }

    /**
     * 获得用户信息
     * 
     * @param request
     * @return
     */
    public static RmUserVo getRmUserVo(ServletRequest request) {
    	return getRmUserVo(request, false);
    }
    
    /**
     * 获得用户信息
     * 
     * @param request
     * @param createSession
     * @return
     */
    public static RmUserVo getRmUserVo(ServletRequest request, boolean createSession) {
        Object obj = RmJspHelper.getSession(request, createSession).getAttribute(RM_USER_VO);
        if (obj == null) {
            return null;
        } else {
            return (RmUserVo)obj;
        }
    }

	/**
	 * 获得当前线程绑定的用户信息
	 * 
	 * @param request
	 * @return
	 */
	public static RmUserVo getCurrentUser() {
		HttpServletRequest request = RmRequestMonitor.getCurrentThreadRequest();
		if (request == null) {
			return null;
		}
		Object obj = RmJspHelper.getSession(request).getAttribute(RM_USER_VO);
		if (obj == null) {
			return null;
		}

		return (RmUserVo) obj;

	}

	/**
	 * 功能: 从request得到IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIp(ServletRequest request) {
		String ip = null;
		if(request instanceof HttpServletRequest) {
			ip = ((HttpServletRequest)request).getHeader("X-Forwarded-For");
			if(ip != null && ip.trim().indexOf(",") > 0) {
				ip = ip.trim().substring(0, ip.trim().indexOf(","));
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = ((HttpServletRequest)request).getHeader("X-Real-IP");
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
    /**
     * 手动记录日志进入数据库，适用于记录重要的业务日志
     *
     * @param action_module 日志模块名
     * @param content 日志内容
     */
    public static void log(String action_module, String format, Object... arguments) {
    	String message = null;
    	if(arguments.length > 0) {
    		FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    		message = ft.getMessage();
    	} else {
    		message = format;
    	}
    	//system businessLog begin
    	StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    	String callMethodName = null;
    	boolean findSelfMethod = false;
    	for(int i=0; i<sts.length; i++) { //倒序查找
    		StackTraceElement st = sts[i];
    		if(RmProjectHelper.class.getName().equals(st.getClassName()) && "log".equals(st.getMethodName())) {
    			findSelfMethod = true;
    		} else if(findSelfMethod) {
    			//如果RmProjectHelper的上一层是RmLogHelper，则再往上取一层才是业务方法
    			if(RmLogHelper.class.getName().equals(st.getClassName()) && "log".equals(st.getMethodName()) && sts.length > i+1) {
    				callMethodName = sts[i+1].getMethodName();
    			} else {
    				callMethodName = st.getMethodName();
    			}
    			break;
    		}
    	}
    	String uuid = RmGlobalMonitor.uniqueUUID.get();
    	RmLogVo logVo = new RmLogVo();
    	{ //初始化logVo
    		//时间戳
    		logVo.setAction_date(RmDateHelper.getSysTimestamp());
    		//从request中注值
    		HttpServletRequest request = RmRequestMonitor.getCurrentThreadRequest();
    		if(request != null) {
        		logVo.setAction_ip(RmProjectHelper.getIp(request));
    			RmUserVo userVo = RmProjectHelper.getRmUserVo(request);
    			if(userVo != null) {
    				logVo.setUser_id(userVo.getId());
    				logVo.setUser_id_name(userVo.getName());
    				logVo.setOwner_org_id(userVo.getParty_id_org());
    			}
    		}
    		logVo.setAction_module(action_module);
    		{ //智能匹配日志类型
    			logVo.setLog_type_id(RmLogTypeCache.getSingleton().matchLogType(action_module).getId());
    		}
    		{ //自动匹配操作类型
    			logVo.setAction_type(getActionType(callMethodName));
    		}
    		logVo.setAction_uuid(uuid);
    		logVo.setContent(message);
    	}
    	insertDbLog(logVo);
    	//system businessLog end
    }
    
    /**
     * 记录严重错误信息到单独的fatal.log，并发送邮件给指定人员
     * @param msg
     * @param e
     */
    public static boolean logFatal(Object msg, Throwable e) {
    	try {
    		String[] subject_content = RmAlarmCollector.createInfo(msg, e);
    		String subject = subject_content[0];
    		String content = subject_content[1];
    		logFatal.error(subject);
    		logFatal.error(content);
    		IRmMailService mailService = (IRmMailService)RmBeanFactory.getBean("IRmMailService");
    		mailService.send(RmLoadConfig.getRmDoc().valueOf("/rm/org.quickbundle.project.RmProjectHelper/logFatal/mailTo"), 
    				subject, content, null, null);
    		return true;
		} catch (Exception e2) {
			e2.printStackTrace();
			log.warn(e2.toString());
			return false;
		}
    }
    
    //system businessLog begin
    /**
     * 自动判断操作类型
     * @param callMethodName
     * @return
     */
    private static String getActionType(String callMethodName) {
    	ILogConstants.ActionType[] ats = ILogConstants.ActionType.values();
    	for(int i=0; i<ats.length; i++) {
        	if(callMethodName.matches(ats[i].pattern())) {
        		return ats[i].value();
        	}
    	}
    	return ILogConstants.ActionTypeDefault;
    }
    
	/**
	 * 记录业务日志
	 * @param logVo
	 */
	public static void insertDbLog(RmLogVo logVo) {
		RmBeanFactory.getBean(ActionLog2DbService.class).add(logVo);
	}
    //system businessLog end
}

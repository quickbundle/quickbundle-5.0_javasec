package org.quickbundle.project.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.config.RmConfig;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.listener.RmSessionListener;
import org.quickbundle.third.struts.RmActionHelper;
import org.quickbundle.third.struts.actions.RmDispatchAction;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmLoginAction extends RmDispatchAction implements IRmLoginConstants {
	private static final Logger logLogin = RmLogHelper.getLogger("rmlogin");
	
    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    public IRmLoginService getLoginService() {
        return (IRmLoginService) RmBeanFactory.getBean(IRmLoginService.class.getName());  //得到Service对象,受事务控制
    }
    
    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //登录vo
        IRmLoginVo loginVo = null;
        //获得session，有可能不存在
        HttpSession session = RmJspHelper.getSession(request, false);
        if(RmConfig.getSingleton().isUserDemoMode()) { //demo模式手动设置IRmLoginVo，用户login_id放入session，用于判断是否登录
        	loginVo = new RmUserVo();
        	loginVo.setLogin_id(request.getParameter(Para.login_id.name()));
        	//确保产生session，并往session放入loginVo
        	session = createLongSession(request);
        	session.setAttribute(IGlobalConstants.RM_USER_VO, loginVo);
        	//demo模式不做任何校验，不初始化Service，不记录cookie，直接登录跳转
        	return directForward(mapping, form, request, response);
        } else if(RmConfig.getSingleton().isUserUniqueLogin() && session != null && session.getAttribute(FORCE_LOGIN_VO) != null) { //有FORCE_LOGIN_VO是用户确认后二次登录
        	loginVo = (IRmLoginVo)session.getAttribute(FORCE_LOGIN_VO);
        	if(RM_YES.equals(request.getParameter(IRmLoginConstants.Para.is_force_login.name()))) {
        		//执行强制登录
        		loginVo = loginForce(mapping, form, request, response, loginVo);
        	} else {
        		loginVo.setLoginFailed("您取消了强制登录，请重新输入用户和密码登录!");
        	}
        	//二次登录处理完毕，删除session的loginVo
        	session.removeAttribute(FORCE_LOGIN_VO);
    	} else {
    		loginVo = loginNormal(mapping, form, request, response);
    		if(RmConfig.getSingleton().isUserUniqueLogin() && loginVo.getLoginFailed() == null) {
    			//检查用户是否唯一登录
    			UserUniqueLoginVo uniqueLoginVo = getLoginService().checkUniqueLogin(request, loginVo);
    			if(uniqueLoginVo != null) {
    				//往session放入uniqueLoginVo
    				request.setAttribute(USER_UNIQUE_LOGIN_VO, uniqueLoginVo);
    	        	//确保产生session，并往session放入loginVo
    	        	session = RmJspHelper.getSession(request, true);
    				session.setAttribute(FORCE_LOGIN_VO, loginVo);
    				//如果用户已经在线，跳转到确认强制登录的页面
    				return mapping.findForward(IRmLoginConstants.LoginForward.LOGIN_FORCE_CONFIRM.value());
    			}
    		}
    	}
        if(loginVo.getLoginFailed() != null) { //登录失败
        	//清除cookie，不初始化Service跳转到重登录界面
        	if(RmConfig.getSingleton().isLoginCookie()) {
        		RmJspHelper.clearProfile(request, response, Para.login_id.name());
        		RmJspHelper.clearProfile(request, response, Para.password.name());
        	}
            request.setAttribute(Para.alertStr.name(), loginVo.getLoginFailed());
            request.setAttribute(Para.login_id.name(), loginVo.getLogin_id());
            request.setAttribute(Para.password.name(), loginVo.getPassword());
            return mapping.findForward(IRmLoginConstants.LoginForward.TO_LOGIN.value());
        }
        createLongSession(request);
    	//已经成功登录，初始化登录session信息
        getLoginService().executeInitUserInfo(request, loginVo);

        //把此次成功登录的帐号和密码写入cookie
        if(RmConfig.getSingleton().isLoginCookie()) {
        	if(request.getParameterValues(Para.is_cookie_login_status.name()) != null 
        			&& request.getParameterValues(Para.is_cookie_login_status.name()).length > 0
        			&& RM_YES.equals(request.getParameterValues(Para.is_cookie_login_status.name())[0])) {
                //设置Cookie
            	RmJspHelper.setProfile(request, response, Para.login_id.name(), request.getParameter(Para.login_id.name()));
            	RmJspHelper.setProfile(request, response, Para.password.name(), request.getParameter(Para.password.name()));
        	}
        }
        return directForward(mapping, form, request, response);
    }
    
    //如果是超时短的临时session，加长时间为一个正常超时的session
    //当成功登录系统时才被调用
	private HttpSession createLongSession(HttpServletRequest request) {
    	HttpSession session = RmJspHelper.getSession(request, false);
    	if(session == null) {
    		return RmJspHelper.getSession(request, true);
    	} else if(session.getMaxInactiveInterval() <= IRmLoginConstants.SESSION_TIMEOUT_SHORT 
    			&& session.getMaxInactiveInterval() < RmSessionListener.getDefaultMaxInactiveInterval()) {
    		session.setMaxInactiveInterval(RmSessionListener.getDefaultMaxInactiveInterval());
    	}
    	return session;
    }
    
    //处理智能跳转
    private ActionForward directForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //智能跳转
        if(request.getParameter(Para.toUrl.name()) != null) {
        	return RmActionHelper.getForwardInstance(request.getParameter(Para.toUrl.name()));
        } else {
        	String toUrl = RmJspHelper.getProfile(request, Para.toUrl.name());
        	RmJspHelper.clearProfile(request, response, Para.toUrl.name());
        	if(toUrl != null && toUrl.length() > 0) {
            	return RmActionHelper.getForwardInstance(toUrl);
        	}
        }
        return mapping.findForward(IRmLoginConstants.LoginForward.TO_LOGIN.value());
    }
    
    /**
     * 正常登录 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private IRmLoginVo loginNormal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	IRmLoginVo loginVo = null;
        //获得session，有可能不存在
        HttpSession session = RmJspHelper.getSession(request, false);
    	boolean fromCookie = RM_YES.equals(request.getParameter(Para.is_cookie.name()));
    	String login_id = request.getParameter(Para.login_id.name());
        String password = request.getParameter(Para.password.name());
        { //初始化loginVo
        	loginVo = new RmUserVo();
        	loginVo.setLogin_id(login_id);
        	loginVo.setPassword(password);
        }
        String verifyCode = request.getParameter(Para.verifyCode.name());
        String alertStr = null;
        StringBuilder sbLoginLog = new StringBuilder();
        sbLoginLog.append("login_id=" + login_id + ",datetime=" + RmDateHelper.getSysDateTime() + ",ip=" + RmProjectHelper.getIp(request) + ",sessionid=" + (session != null ? session.getId() : "") + ",");
        while(true) {
            if(login_id == null  || login_id.trim().length() == 0 ) {
                alertStr = "请您输入用户名!";
                sbLoginLog.append("failed, login_id  is null");
                break;
            }
            if(password == null || password.trim().length() == 0) {
                alertStr = "请您输入密码!";
                sbLoginLog.append("failed, password is null");
                break;
            }

        	if(!fromCookie && RmConfig.getSingleton().isLoginValidateVerifyCode()) {
            	if(verifyCode == null || verifyCode.trim().length() == 0) {
                    alertStr = "请您输入验证码!";
                    sbLoginLog.append("failed, validate code is null");
                    break;
            	}
            	if(session == null) {
                    alertStr = "您输入的验证码已过期，请重新输入";
                    sbLoginLog.append("failed, validate code expired");
                    break; 
            	}
                if(!String.valueOf(verifyCode).toLowerCase().equals(String.valueOf(session.getAttribute("randomStr")).toLowerCase())) {
                    alertStr = "您输入的验证码不正确，请重新输入";
                    sbLoginLog.append("failed, validate code is wrong");
                    break; 
                }
        	}
            //查询登录用户
            List<IRmLoginVo> lRmUser = getLoginService().findUsersByLoginId(login_id);
            if(lRmUser == null || lRmUser.size() == 0) {
                alertStr = "您输入的密码或用户名不正确，请重新输入！";
                sbLoginLog.append("failed, no this login_id");
                break; 
            }
            loginVo = lRmUser.get(0);
            String md5Password = null;
            if(fromCookie) {
            	md5Password = password;
            } else {
            	md5Password = password;
            	//TODO 替换成加密过的密文
            	//md5Password = RmCryptoHelper.digestPassword(password, loginVo.getId());            	
            }
            if(!md5Password.equals(loginVo.getPassword())) {
                alertStr = "您输入的密码或用户名不正确，请重新输入！";
                sbLoginLog.append("failed, password is wrong");
                break; 
            }
            
            { //如果通过其他方式登录(例如：来自portal门户的登录)，在这里扩展校验。如果登录有错执行 alertStr="错误信息"

            }
            
            //验证用户登录的合法性
            String validateStr = getLoginService().validateLogin(request, loginVo);
            if(validateStr != null) {
            	alertStr = validateStr;
            	sbLoginLog.append("failed, validateLogin wrong");
            	break;
            }
            
            //退出循环
            break;
        }
        //登录日志记录
        logLogin.info(sbLoginLog.toString());
        //设置异常信息
        loginVo.setLoginFailed(alertStr);
        return loginVo;
    }
    
    /**
     * 强制登录
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param loginVo
     * @throws Exception
     */
    private IRmLoginVo loginForce(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, IRmLoginVo loginVo) throws Exception {
    	getLoginService().executeLoginForce(request, loginVo);
    	return loginVo;
    }
    
    /**
     * 退出登录
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获得session，有可能不存在
        HttpSession session = RmJspHelper.getSession(request, false);
        if(session != null) {
        	session.setAttribute(IRmLoginConstants.LOGOUT_TYPE, IRmLoginConstants.LogoutType.NOTMAL.value());
        	getLoginService().executeDestroyUserInfo(session);
			//清除session
			session.invalidate();
        }
    	request.setAttribute(NO_COOKIE, RM_YES);
    	RmJspHelper.clearProfile(request, response, Para.login_id.name());
    	RmJspHelper.clearProfile(request, response, Para.password.name());
        if(request.getParameter(Para.toUrl.name()) != null) {
        	return RmActionHelper.getForwardInstance("", request.getParameter(Para.toUrl.name()), false);
        } else {
            return mapping.findForward(IRmLoginConstants.LoginForward.TO_LOGIN.value());
        }
    }
}

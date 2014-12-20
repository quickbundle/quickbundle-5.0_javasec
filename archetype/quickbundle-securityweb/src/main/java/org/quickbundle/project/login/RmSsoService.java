package org.quickbundle.project.login;

import javax.jws.WebService;

import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.listener.RmSessionListener;
import org.quickbundle.project.login.RmSsoLogin.RmSsoVo;

@WebService(targetNamespace="http://login.project.quickbundle.org/", endpointInterface = "org.quickbundle.project.login.IRmSsoService")
public class RmSsoService implements IRmSsoService  {
	
	public RmUserVo copyLogin(String sessionId, String ssoValue) {
		RmSsoVo ssoVo = new RmSsoVo(ssoValue);
		if(ssoVo.validateSsoVo()) {
			return (RmUserVo)RmSessionListener.getSessionById(sessionId).getAttribute(IGlobalConstants.RM_USER_VO);
		} else {
			return null;
		}
	}

}

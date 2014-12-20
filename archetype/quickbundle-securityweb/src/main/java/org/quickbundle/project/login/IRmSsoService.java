package org.quickbundle.project.login;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IRmSsoService {

	public RmUserVo copyLogin(@WebParam(name = "sessionId", mode = WebParam.Mode.IN) String sessionId, 
			@WebParam(name = "ssoValue", mode = WebParam.Mode.IN) String ssoValue);
	
}
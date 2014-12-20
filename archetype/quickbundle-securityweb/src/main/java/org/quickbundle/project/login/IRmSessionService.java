package org.quickbundle.project.login;

import java.util.List;

import javax.jws.WebService;

import org.quickbundle.project.login.RmUserVo.RmUserSessionVo;

@WebService
public interface IRmSessionService {
	public int getRecordCount(String queryCondition);
	
	public List<RmUserSessionVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size);
	
	public boolean forceLogoutUser(String userId, String sessionId);
	
	public List<RmUserSessionVo> listSessionLocal(String[] sessionIds);
	
	public RmUserSessionVo findSessionLocal(String sessionId);
	
	public int forceLogoutUserLocal(String[] sessionIds, String message);
	
	
}

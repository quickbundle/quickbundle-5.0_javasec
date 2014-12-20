package org.quickbundle.project.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpSession;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmConfig;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.project.listener.RmSessionListener;
import org.quickbundle.project.login.RmUserVo.RmUserSessionVo;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.util.RmSequenceMap;
import org.springframework.jdbc.core.RowMapper;

@WebService(targetNamespace="http://login.project.quickbundle.org/", endpointInterface = "org.quickbundle.project.login.IRmSessionService")
public class RmSessionService implements IRmSessionService {
	public static IRmSessionService getRemoteSessionService(String clusterNodeId) {
		String callWsUrl = RmClusterConfig.getSingleton().getSelfNode().get(RmClusterConfig.NodeKey.webServiceUrl.name());
		if(callWsUrl == null) {
			return null;
		}
		String address = callWsUrl + "RmSession";
		JaxWsProxyFactoryBean jw = new JaxWsProxyFactoryBean();
		jw.setServiceClass(IRmSessionService.class);
		jw.setAddress(address);
		Object obj = jw.create();
		IRmSessionService ss = (IRmSessionService) obj;
		return ss;
	}
	
    private IRmLoginService getLoginService() {
        return (IRmLoginService) RmBeanFactory.getBean(IRmLoginService.class.getName());  //得到Service对象,受事务控制
    }
	
	@WebMethod(exclude=true)
	public int getRecordCount(String queryCondition) {
		String yesterday = new Timestamp(System.currentTimeMillis() - 1000*60*60*24*1).toString().substring(0, 19);
		String sql = "select count(*) from RM_USER u " +
				"join RM_USER_ONLINE_RECORD uor on (u.id=uor.user_id and u.last_login_date=uor.login_time and uor.logout_time is null) " +
				"where u.usable_status='1' and u.login_status='1' and u.last_login_date>" + RmSqlHelper.getSqlDateStr(yesterday);
		return RmProjectHelper.getCommonServiceInstance().doQueryForInt(sql);
	}
	
	@WebMethod(exclude=true)
	public List<RmUserSessionVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
		List<RmUserSessionVo> result = new ArrayList<RmUserVo.RmUserSessionVo>();
		final Map<String, HttpSession> mSession = RmSessionListener.getSessions();
		String yesterday = new Timestamp(System.currentTimeMillis() - 1000*60*60*24*1).toString().substring(0, 19);
		String sql = "select uor.cluster_node_id, uor.login_sign, uor.login_uuid, u.* from RM_USER u " +
				"join RM_USER_ONLINE_RECORD uor on (u.id=uor.user_id and u.last_login_date=uor.login_time and uor.logout_time is null) " +
				"where u.usable_status='1' and u.login_status='1' and u.last_login_date>" + RmSqlHelper.getSqlDateStr(yesterday) + 
				" order by u.last_login_date desc";
		
		//sessionId -> RmUserSessionVo对象
		final Map<String, RmUserSessionVo> mResult = new RmSequenceMap<String, RmUserSessionVo>();
		
		//对clusterNodeId分组存放
		final Map<String, List<String>> mOther = new RmSequenceMap<String, List<String>>();
		
		RmProjectHelper.getCommonServiceInstance().doQueryStartIndex(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				RmUserSessionVo vo = new RmUserSessionVo();
				RmPopulateHelper.populate(vo, rs);
	    		vo.setSessionId(rs.getString("login_sign"));
	    		//登录服务器主机名
	    		vo.setClusterNodeId(rs.getString("cluster_node_id"));
	    		if(mSession.containsKey(vo.getSessionId())) { //如果在本机
	    			HttpSession session = mSession.get(vo.getSessionId());
	    			populateSessionVo(vo, session);
	    		} else {
	    			if(!mOther.containsKey(vo.getClusterNodeId())) {
	    				mOther.put(vo.getClusterNodeId(), new ArrayList<String>());
	    			}
	    			mOther.get(vo.getClusterNodeId()).add(vo.getSessionId());
	    		}
	    		mResult.put(vo.getSessionId(), vo);
				return null;
			}
		}, startIndex, size);
		
		//通过soa查询其他节点的session
		for(String clusterNodeId : mOther.keySet()) {
			String[] sessionIds = mOther.get(clusterNodeId).toArray(new String[0]);
			try {
				IRmSessionService remoteSs = getRemoteSessionService(clusterNodeId);
				if(remoteSs == null) {
					continue;
				}
				List<RmUserSessionVo> lBrother = remoteSs.listSessionLocal(sessionIds);
				for(RmUserSessionVo sourceVo : lBrother) {
					if(sourceVo == null) {
						continue;
					}
					RmUserSessionVo destinationVo = mResult.get(sourceVo.getSessionId());
					if(sourceVo.getId() != null) {
						RmPopulateHelper.populate(destinationVo, sourceVo);
					} else {
						populateSessionVo(destinationVo, sourceVo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(String sessionId : mResult.keySet()) {
			result.add(mResult.get(sessionId));
		}
		return result;
	}
	
	public RmUserSessionVo findSessionLocal(String sessionId) {
		List<RmUserSessionVo> lvo = listSessionLocal(new String[]{sessionId});
		if(lvo.size() > 0) {
			return lvo.get(0);
		} else {
			return null;
		}
	}
	
	private void populateSessionVo(RmUserSessionVo target, HttpSession session) {
		target.setSessionId(session.getId());
		//session创建时间
		target.setCreationTime(session.getCreationTime());
		//最后访问时间
		target.setLastAccessedTime(session.getLastAccessedTime());
		//最大非活动间隔
		target.setMaxInactiveInterval(session.getMaxInactiveInterval() * 1000);
		{ //custom owner org this login, cluster begin
			if(session.getAttribute(IGlobalConstants.RM_USER_VO) != null) {
				target.setParty_id_org_name(((org.quickbundle.project.login.RmUserVo)session.getAttribute(IGlobalConstants.RM_USER_VO)).getParty_id_org_name());
			}
		} //custom owner org this login, cluster end
	}
	
	private void populateSessionVo(RmUserSessionVo target, RmUserSessionVo source) {
		target.setCreationTime(source.getCreationTime());
		target.setLastAccessedTime(source.getLastAccessedTime());
		target.setMaxInactiveInterval(source.getMaxInactiveInterval());
		target.setParty_id_org_name(source.getParty_id_org_name());
	}

	@WebMethod(exclude=true)
	public boolean forceLogoutUser(String user_id, String session_id) {
		String msg = "您被管理员强制退出了，请重新登录。如有帐号异常，请联系管理员。";
		boolean sendRemoteSuccess = false;
		if(RmConfig.getSingleton().isClusterMode()) {
			//销毁集群下兄弟节点的session
			if(user_id != null && user_id.length() > 0) {
				List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQuery("select * from RM_USER_ONLINE_RECORD where user_id='" + user_id + "' and login_sign='" + session_id + "'");
				if(lvo.size() > 0) {
					RmCommonVo vo = lvo.get(0);
					String cluster_node_id = vo.getString("cluster_node_id");
					IRmSessionService remoteSs = getRemoteSessionService(cluster_node_id);
					if(remoteSs != null) {
						int result = remoteSs.forceLogoutUserLocal(new String[]{session_id}, msg);
						sendRemoteSuccess = result == 1;
					}
				}
			}
		}
		if(!sendRemoteSuccess) { //如果单机模式或集群模式下调用远程失败，本地执行清理动作
			forceLogoutUserLocal(new String[]{session_id}, msg);
		}
		return true;
	}

	public List<RmUserSessionVo> listSessionLocal(String[] sessionIds) {
		final Map<String, HttpSession> mSession = RmSessionListener.getSessions();
		List<RmUserSessionVo> result = new ArrayList<RmUserVo.RmUserSessionVo>();
		for(String sessionId : sessionIds) {
			RmUserSessionVo vo = null;
			HttpSession session = mSession.get(sessionId);
			if(session != null) {
				vo = new RmUserSessionVo();
				if(session.getAttribute(IGlobalConstants.RM_USER_VO) != null) {
					org.quickbundle.project.login.RmUserVo userVo = (org.quickbundle.project.login.RmUserVo)session.getAttribute(IGlobalConstants.RM_USER_VO);
					RmPopulateHelper.populate(vo, userVo);
					populateSessionVo(vo, session);
				}
			}
			result.add(vo);
		}
		return result;
	}

	public int forceLogoutUserLocal(String[] sessionIds, String message) {
		int result = 0;
		for(String sessionId : sessionIds) {
			HttpSession session = RmSessionListener.getSessionById(sessionId);
			if(session != null) {
				session.setAttribute(IRmLoginConstants.LOGOUT_TYPE, IRmLoginConstants.LogoutType.FORCE_LOGOUT.value());
				getLoginService().executeDestroyUserInfo(session);
				session.setAttribute(IGlobalConstants.SystemPara.system_message.name(), message);
				result ++;
			}
		}
		return result;
	}
}
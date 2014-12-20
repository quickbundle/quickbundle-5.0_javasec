package org.quickbundle.project.login;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

public interface IRmLoginService {

	/**
	 * 根据login_id查用户
	 * 
	 * @param login_id
	 * @return
	 */
	public List<IRmLoginVo> findUsersByLoginId(String login_id);
	
	/**
	 * 验证用户登录的合法性
	 * 
	 * @param request
	 * @param loginVo
	 * @return 校验异常时，返回异常反馈信息，正常返回null
	 */
	public String validateLogin(ServletRequest request, IRmLoginVo loginVo);
	
	/**
	 * 判断这个用户是否已经在线
	 * @param request
	 * @param loginVo
	 * @return 如果未登录，返回null；如果已经登录在线，返回vo信息
	 */
	public UserUniqueLoginVo checkUniqueLogin(ServletRequest request, IRmLoginVo loginVo);
	
	/**
	 * 执行强制登录（踢出已在线用户，清理在线记录）
	 * @param request
	 * @param loginVo
	 * @return 强制登录是否成功
	 */
	public boolean executeLoginForce(ServletRequest request, IRmLoginVo loginVo);
	
	/**
	 * 成功登录后，初始化登录session信息; 插入用户在线记录，并更新状态为--"已登录"、记录登录时间、IP、登录次数
	 * 
	 * @param request
	 * @param loginVo
	 */
	public void executeInitUserInfo(ServletRequest request, IRmLoginVo loginVo);
	
	/**
	 * 清除登录session信息; 完成用户在线记录，并更新状态为--"未登录"
	 * 
	 * @param session 要清理的session
	 */
	public void executeDestroyUserInfo(HttpSession session);
}

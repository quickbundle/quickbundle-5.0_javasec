<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<!--startFunctionNodes-->

node_org = insFld(foldersTree, gFld ("&nbsp;通用组织结构", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
	
	/* 团体类型生成在功能树中 */
	rmCodeGenerationRmPartyType_maintenance = insDoc(node_org, gLnk("0","&nbsp;团体类型", "<%=request.getContextPath()%>/RmPartyTypeAction.do?cmd=queryAll", "ftv2link.gif"));
	
	/* 团体视图生成在功能树中 */
	rmCodeGenerationRmPartyView_maintenance = insDoc(node_org, gLnk("0","&nbsp;团体视图", "<%=request.getContextPath()%>/RmPartyViewAction.do?cmd=queryAll", "ftv2link.gif"));

	rm_orgtree = insDoc(node_org, gLnk("0","&nbsp;组织树维护", "<%=request.getContextPath()%>/orgauth/tree/managePartyTab.jsp?cmd=org", "ftv2link.gif"));
	
	/* 团体生成在功能树中 */
	//rmCodeGenerationRmParty_maintenance = insDoc(node_org, gLnk("0","&nbsp;团体", "<%=request.getContextPath()%>/RmPartyAction.do?cmd=queryAll", "ftv2link.gif"));

	/* 团体关系生成在功能树中 */
	//rmCodeGenerationRmPartyRelation_maintenance = insDoc(node_org, gLnk("0","&nbsp;团体关系", "<%=request.getContextPath()%>/RmPartyRelationAction.do?cmd=queryAll", "ftv2link.gif"));
	
	/* 用户生成在功能树中 */
	rmCodeGenerationRmUser_maintenance = insDoc(node_org, gLnk("0","&nbsp;用户", "<%=request.getContextPath()%>/RmUserAction.do?cmd=queryAll", "ftv2link.gif"));

	rm_orgtree = insDoc(node_org, gLnk("0","&nbsp;组织相关说明", "<%=request.getContextPath()%>/orgauth/tree/demoParty.jsp", "ftv2link.gif"));

	/* 用户在线记录生成在功能树中 */
	//rmCodeGenerationRmUserOnlineRecord_maintenance = insDoc(node_org, gLnk("0","&nbsp;用户在线记录", "<%=request.getContextPath()%>/RmUserOnlineRecordAction.do?cmd=queryAll", "ftv2link.gif"));
	node_user_online = insDoc(node_org, gLnk("0","&nbsp;在线用户", "<%=request.getContextPath()%>/RmUserAction.do?cmd=queryOnlineUser", "ftv2link.gif"));

	node_auth = insFld(foldersTree, gFld ("&nbsp;通用权限", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
	/* 权限类别生成在功能树中 */
	rmCodeGenerationRmAuthorize_maintenance = insDoc(node_auth, gLnk("0","&nbsp;权限类别", "<%=request.getContextPath()%>/RmAuthorizeAction.do?cmd=queryAll", "ftv2link.gif"));
	/* 授权资源生成在功能树中 */
	//rmCodeGenerationRmAuthorizeResource_maintenance = insDoc(node_auth, gLnk("0","&nbsp;授权资源", "<%=request.getContextPath()%>/RmAuthorizeResourceAction.do?cmd=queryAll", "ftv2link.gif"));
		

	/* 功能节点生成在功能树中 */
	//rmCodeGenerationRmFunctionNode_maintenance = insDoc(node_auth, gLnk("0","&nbsp;功能节点", "<%=request.getContextPath()%>/RmFunctionNodeAction.do?cmd=queryAll", "ftv2link.gif"));
	
	rmCodeGenerationRmFunctionNode_tree = insDoc(node_auth, gLnk("0","&nbsp;菜单与按钮树", "<%=request.getContextPath()%>/orgauth/tree/functionNode.jsp", "ftv2link.gif"));

	/* 角色生成在功能树中 */
	rmCodeGenerationRmRole_maintenance = insDoc(node_auth, gLnk("0","&nbsp;角色管理", "<%=request.getContextPath()%>/RmRoleAction.do?cmd=queryAll", "ftv2link.gif"));
	
	rmCodeGenerationRmCodeType_maintenance = insDoc(node_auth, gLnk("0","&nbsp;带权限编码表", "<%=request.getContextPath()%>/RmCodeTypeConditionAction.do?cmd=queryAll", "ftv2link.gif"));

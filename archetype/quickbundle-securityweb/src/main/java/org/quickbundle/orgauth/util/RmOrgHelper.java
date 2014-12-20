package org.quickbundle.orgauth.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.quickbundle.config.RmConfig;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmFunctionNodeCache;
import org.quickbundle.orgauth.itf.IUtOrgTree;
import org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.quickbundle.tools.support.tree.DeepTreeVo;
import org.quickbundle.tools.support.tree.DeepTreeXmlHandler;
import org.quickbundle.util.RmOrderCodes;
import org.springframework.jdbc.core.RowMapper;

public class RmOrgHelper implements IGlobalConstants{
	
	/**
	 * 组织树基础方法
	 * @param request
	 * @param orgTree
	 * @return
	 */
	public static DeepTreeXmlHandler getOrgTree(HttpServletRequest request, IUtOrgTree orgTree) {
		return getOrgTree(request, orgTree, new DeepTreeXmlHandler());
	}
	/**
	 * 组织树基础方法,可能获取的参数:
	 * view_id: 视图id
	 * parent_party_code: 父团体全编码
	 * parent_party_id: 父团体id
	 * show_bk: 指定树要查询出来的团体类型，逗号分隔
	 * submit_bk: 指定树要submit勾选的团体类型，逗号分隔
	 * lazy_init: 是否懒加载
	 * 
	 * @param request
	 * @param orgTree
	 * @return
	 */
	public static DeepTreeXmlHandler getOrgTree(HttpServletRequest request, IUtOrgTree orgTree, DeepTreeXmlHandler dt) {
		//获取view_id
		String view_id = request.getParameter("view_id");
		if(view_id == null || view_id.trim().length() == 0) {
			//view_id的默认值是RM_PARTY_VIEW表的第一条记录
			List<RmCommonVo> lPartyViewId = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT ID FROM RM_PARTY_VIEW ORDER BY ID", 1, 1);
			if(lPartyViewId.size() > 0) {
				view_id = lPartyViewId.get(0).getString("id");
			} else {
				view_id = IOrgauthConstants.PartyView.DEFAULT.id();			
			}
		}

		//优先获取parent_party_code
		String parent_party_code = request.getParameter("parent_party_code");
		
		//如果parent_party_code为null，获取parent_party_id且默认为""
		String parent_party_id = null;
		if(parent_party_code == null) {
			parent_party_id = request.getParameter("parent_party_id");
			if(parent_party_id == null) {
				parent_party_id = "";
			}
		}
		
		//是否包含自身，默认为0。只有懒加载时有效
		boolean include_self = false;
		include_self = "1".equals(request.getParameter("include_self"));
		
		//获取show_bk
		String[] aShow_bk = new String[0];
		String show_bk = request.getParameter("show_bk");
		if(show_bk != null && show_bk.trim().length() > 0) {
			String tempBk = show_bk.replaceAll("^,+", "").replaceAll(",+$", "");
			if(tempBk.trim().length() > 0) {
				aShow_bk = tempBk.trim().split(",");				
			}
		}
		
		//获取lazy_init，默认为1
		String lazy_init = request.getParameter("lazy_init");
		if(lazy_init == null || lazy_init.length() == 0 || (!"1".equals(lazy_init) && !"0".equals(lazy_init))) { 
			lazy_init = "1";
		}
		
		//查询树节点的sql语句
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select PR.PARENT_PARTY_ID, PR.CHILD_PARTY_ID, PR.PARENT_PARTY_CODE, PR.CHILD_PARTY_CODE, " +
				"PR.CHILD_IS_LEAF, PR.CHILD_PARTY_NAME, P.PARTY_TYPE_ID as CHILD_PARTY_TYPE_ID, PT.ICON as CHILD_ICON, " +
				"PT.BS_KEYWORD as CHILD_BS_KEYWORD, PR.CHILD_IS_MAIN_RELATION, PR.ORDER_CODE from RM_PARTY_RELATION PR " +
				"join RM_PARTY P on PR.CHILD_PARTY_ID=P.ID " +
				"join RM_PARTY_TYPE PT on P.PARTY_TYPE_ID=PT.ID where ");
		
		//懒加载，只获取1层子节点
		if("1".equals(lazy_init)) {
			//如果parent_party_code未指定，按parent_party_id查询
			if(parent_party_code == null) {
				if("".equals(parent_party_id)) {
					sbSql.append(" (PR.PARENT_PARTY_ID is null or PR.PARENT_PARTY_ID='')");
				} else {
					if(include_self) { //查自身
						sbSql.append(" PR.CHILD_PARTY_ID=" + parent_party_id);
					} else { //查父节点
						sbSql.append(" PR.PARENT_PARTY_ID=" + parent_party_id);
					}
				}
			} else { //parent_party_code不为空则按parent_party_code查询
				if("".equals(parent_party_code)) {
					sbSql.append(" (PR.PARENT_PARTY_CODE is null or PR.PARENT_PARTY_CODE='')");
				} else {
					if(include_self) { //查自身
						sbSql.append(" PR.CHILD_PARTY_CODE='" + parent_party_code + "'");
					} else { //查父节点
						sbSql.append(" PR.PARENT_PARTY_CODE='" + parent_party_code + "'");
					}
				}
			}
			//如果show_bk不为空，过滤
			if(aShow_bk.length > 0) {
				sbSql.append(" and PT.BS_KEYWORD in (" + RmStringHelper.parseToSQLStringApos(aShow_bk) + ")");
			}
		} else {
			//如果parent_party_code未指定，按parent_party_id查询
			if(parent_party_code == null) {
				StringBuilder sql_parentParty = new StringBuilder();
				sql_parentParty.append("select PR.CHILD_PARTY_CODE, PARENT_PARTY_CODE, CHILD_IS_MAIN_RELATION from RM_PARTY_RELATION PR where PR.PARTY_VIEW_ID=" + view_id);
				if("".equals(parent_party_id)) {
					sql_parentParty.append(" and (PR.PARENT_PARTY_ID is null or PR.PARENT_PARTY_ID='')");
				} else {
					sql_parentParty.append(" and PR.PARENT_PARTY_ID=" + parent_party_id);
				}
				List<String> lParentPartyCode = RmProjectHelper.getCommonServiceInstance().query(sql_parentParty.toString(), new RowMapper() {
				    public Object mapRow(ResultSet rs, int i) throws SQLException {
				    	return rs.getString("CHILD_PARTY_CODE");
				    }
				});
				int index = 0;
				if(lParentPartyCode.size() > 0) {
					for(String parentPartyCode : lParentPartyCode) {
						if(index == 0) {
							sbSql.append(" (PR.CHILD_PARTY_CODE like '" + parentPartyCode + "%'");
						} else {
							sbSql.append(" or PR.CHILD_PARTY_CODE like '" + parentPartyCode + "%'");
						}
						index ++;
					}
					sbSql.append(")");
				} else {
					sbSql.append(" PR.CHILD_PARTY_CODE like '" + NOT_EXIST_ID + "%'");
				}
			} else {
				sbSql.append(" PR.CHILD_PARTY_CODE like '" + parent_party_code + "%'");
			}
			//如果show_bk不为空，过滤
			if(aShow_bk.length > 0) {
				sbSql.append(" and PT.BS_KEYWORD in (" + RmStringHelper.parseToSQLStringApos(aShow_bk) + ")");
			}
		}
		sbSql.append(" and PR.PARTY_VIEW_ID=" + view_id); 
		List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery(sbSql.toString());
		//内存排序
		Map<RmOrderCodes, RmCommonVo> mTree = new TreeMap<RmOrderCodes, RmCommonVo>();
		for(RmCommonVo cvo : lcvo) {
			RmOrderCodes orderCodes = null;
			if("1".equals(lazy_init)) {
				orderCodes = new RmOrderCodes(cvo.getString("order_code"), cvo.getString("child_party_code"));
				//System.out.println("c=:"+cvo.getString("order_code"));
				//System.out.println("==:"+orderCodes);
			} else {
				orderCodes = new RmOrderCodes(cvo.getString("parent_party_code"), cvo.getString("order_code"), cvo.getString("child_party_code"));
			}
			mTree.put(orderCodes, cvo);
		}
		for(Map.Entry<RmOrderCodes, RmCommonVo> en : mTree.entrySet()) {
			RmCommonVo cvo = en.getValue();
			String child_is_leaf = "0";
			if("1".equals(cvo.getString("child_is_leaf"))){
				child_is_leaf= "1";
			}
			DeepTreeVo dtv = new DeepTreeVo(cvo.getString("child_party_code"), cvo.getString("child_party_name"), "1".equals(child_is_leaf) ? "0" : "1", "");
			if(orgTree == null) {
				orgTree = defaultOrgTree;
			}
			dtv = orgTree.getDeepTreeVo(request, view_id, dtv, cvo);
			if("1".equals(lazy_init)
				||(parent_party_code != null && parent_party_code.equals(cvo.getString("parent_party_code")))
				|| (parent_party_code == null && parent_party_id.equals(cvo.getString("parent_party_id")))) {
				//如果指定了parentId，优先插入dtv.getParentId()
				if(dtv.getParentId() != null && dtv.getParentId().length() > 0) {
					if(dt.addTreeNode(dtv.getParentId(), dtv) == null) {
						dt.addTreeNode(dtv);
					}
				} else {
					dt.addTreeNode(dtv);
				}
			} else {
				dt.addTreeNode(cvo.getString("parent_party_code"), dtv);
			}
		}
		return dt;
	}

	
	/**
	 * 默认回调的IUtOrgTree
	 */
	public final static IUtOrgTree defaultOrgTree = new IUtOrgTree() {
		public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String view_id, DeepTreeVo dtv, RmCommonVo cvo) {
			dtv.setReturnValue(cvo.getString("child_party_id"));
			if(cvo.getString("child_icon").length() > 0){
				dtv.setLogoImagePath(request.getContextPath() + "/jsp/support/deeptree/image/" + cvo.getString("child_icon"));
			}
			//获取submit_bk
			if(request.getParameter("submit_bk") == null) {
				dtv.setIsSubmit("1");
			} else {
				String submit_bk = request.getParameter("submit_bk").trim();
				if(submit_bk.length() == 0) {
					dtv.setIsSubmit("0");
				} else {
					String child_bs_keyword = cvo.getString("child_bs_keyword");
					if(submit_bk.matches("^(" + child_bs_keyword + "|.*," + child_bs_keyword + ")($|,.*$)")) {
						dtv.setIsSubmit("1");
					} else {
						dtv.setIsSubmit("0");
					}
				}
				
			}
			return dtv;
		}
	};

    /**
     * 把request的参数，粘贴到sbUrl上
     * 
     * @param sb
     * @param request
     * @param ignoreName
     * @return
     */
    public static String appendRequestParameter(StringBuilder sbUrl, HttpServletRequest request, String[] ignoreName) {
    	return appendRequestParameter(sbUrl, RmVoHelper.getMapFromRequest(request), ignoreName);
    }
    /**
     * 把Map的参数，粘贴到sbUrl上
     * 
     * @param sb
     * @param pMap
     * @param ignoreName
     * @return
     */
    public static String appendRequestParameter(StringBuilder sbUrl, Map<String, Object> pMap, String[] ignoreName) {
    	int index = 0;
    	for(Map.Entry<String, Object> en : pMap.entrySet()) {
    		String key = en.getKey();
    		String value = en.getValue() == null ? "" : en.getValue().toString();
    		String keyValue = key + "=" + value;
    		if(RmStringHelper.arrayContainString(ignoreName, key) || sbUrl.indexOf(keyValue) > -1) {
    			continue;
    		}
    		if(index == 0 && sbUrl.indexOf("?") == -1) {
    			sbUrl.append("?");
    		} else {
    			sbUrl.append("&");
    		}
    		sbUrl.append(keyValue);
    		index ++;
    	}
    	
    	return sbUrl.toString();
    }
	
	/**
	 * 功能菜单按钮
	 * @param request
	 * @param orgTree
	 * @return
	 */
	public static DeepTreeXmlHandler getMenuTree(HttpServletRequest request, IUtOrgTree orgTree){
		return getMenuTree(request,orgTree,false);
	}
	
	/**
	 * 功能菜单按钮
	 * @param request
	 * @param orgTree
	 * @param authorityMenuTree
	 * @return
	 */
	public static DeepTreeXmlHandler getMenuTree(HttpServletRequest request, IUtOrgTree orgTree,boolean authorityMenuTree){
		DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
		String totalCode =request.getParameter("total_code");
		if(totalCode==null){
			totalCode="";
		}
		if(!authorityMenuTree){
			//获取lazy_init，默认为1
			String lazy_init = request.getParameter("lazy_init");
			if(lazy_init == null || lazy_init.length() == 0 || (!"1".equals(lazy_init) && !"0".equals(lazy_init))) { 
				lazy_init = "1";
			}
			StringBuilder sql = new StringBuilder();
			sql.append("select ID,NODE_TYPE,ENABLE_STATUS,NAME,DATA_VALUE,TOTAL_CODE,IS_LEAF,ICON from RM_FUNCTION_NODE ");
			//启用禁用: 0=禁用,1=启用
			String enableStatus = request.getParameter("enable_status");
			//节点类型：1=子系统、一级模块,2=子模块、功能,3=页面按钮
			String nodeType = request.getParameter("node_type");
			//懒加载，只获取1层子节点
			if("1".equals(lazy_init)){
				sql.append(" WHERE ");
				if(RmStringHelper.checkNotEmpty(totalCode)){
					sql.append("TOTAL_CODE like '"+totalCode+"%' and ");
				}
				sql.append(" ");
				sql.append(RmSqlHelper.getFunction(RmSqlHelper.Function.LENGTH, RmConfig.getSingleton().getDatabaseProductName()));
				sql.append("(TOTAL_CODE) = "+(RmConfig.getSingleton().getDefaultTreeCodeFirst().length()+totalCode.length()));
				if(RmStringHelper.checkNotEmpty(enableStatus)&&"1".equals(enableStatus)) { 
					sql.append(" and ENABLE_STATUS='1'");
				}
				if(RmStringHelper.checkNotEmpty(nodeType)&&"3".equals(nodeType)){
					sql.append(" and NODE_TYPE!='3'");
				}
				sql.append(" order by ");
				sql.append(RmSqlHelper.getFunction(RmSqlHelper.Function.SUBSTR, RmConfig.getSingleton().getDatabaseProductName()));
				sql.append("(TOTAL_CODE, 1, ");
				sql.append(RmSqlHelper.getFunction(RmSqlHelper.Function.LENGTH, RmConfig.getSingleton().getDatabaseProductName()));
				sql.append("(TOTAL_CODE)-3)");
				sql.append(", ORDER_CODE");
				//sql.append(" order by TOTAL_CODE");

				List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery(sql.toString());
				for(RmCommonVo cvo : lcvo) {
					String isLeaf = "1";
					if("1".equals(cvo.getString("is_leaf"))){
						isLeaf = "0";
					}
					DeepTreeVo dtv = new DeepTreeVo(cvo.getString("total_code"),  cvo.getString("name"),  isLeaf, "");
					if(orgTree == null) {
						orgTree = defaultOrgTree;
					}
					dt.addTreeNode(orgTree.getDeepTreeVo(request,"", dtv, cvo));
				}
			}else{
				if(RmStringHelper.checkNotEmpty(enableStatus)&&"1".equals(enableStatus)) { 
					sql.append(" where ENABLE_STATUS='1'");
				}
				sql.append(" order by TOTAL_CODE");
				List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery(sql.toString());
				dt = addAllTreeNode(lcvo,"0");
			}
		} else { //从全局缓存读取菜单项
			List<String> lcvo = RmProjectHelper.getRmUserVo(request).getMenuList();
			for(String thisCode : lcvo) {
				if(thisCode.startsWith(totalCode)) {
					RmFunctionNodeVo fnvo = RmFunctionNodeCache.getFunctionNode(thisCode);
					if(fnvo!=null){
						String hasChild = "1";
						DeepTreeVo dtv = new DeepTreeVo(thisCode, fnvo.getName(), hasChild, "");
						if(fnvo.getData_value()!=null && fnvo.getData_value().length()>0) {
//							String finalHref = RmUrlHelper.replaceParameter(fnvo.getData_value(), request);
							String finalHref = fnvo.getData_value();
							dtv.setHrefPath(request.getContextPath() + finalHref);
						}  
						dtv.setTarget("contentFrame");
						if(fnvo.getIcon() != null && fnvo.getIcon().startsWith("/")) {
							dtv.setLogoImagePath(request.getContextPath() + fnvo.getIcon());
						}
						if(thisCode.length() == totalCode.length() + RmConfig.getSingleton().getDefaultTreeCodeFirst().length()) {
							dt.addTreeNode(dtv);
						} else {
							dt.addTreeNode(thisCode.substring(0, thisCode.length()-RmConfig.getSingleton().getDefaultTreeCodeFirst().length()), dtv);
						}
					}
				}
			}
		}
		return dt;
	}
	
	/**
	 * 添加菜单树数据
	 * @param listVo
	 * @param defaultOpen 是否全部自动展开 1=是
	 * @return
	 */
	private static DeepTreeXmlHandler addAllTreeNode(List<RmCommonVo> listVo,String defaultOpen){
		DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
		for(RmCommonVo cvo : listVo) {
			//cvo.getString("is_leaf")
			String isLeaf = "1";
			if("1".equals(cvo.getString("is_leaf"))){
				isLeaf = "0";
			}
			DeepTreeVo dtv = new DeepTreeVo(cvo.getString("total_code"), cvo.getString("name"),isLeaf, "");
			dtv.setDefaultOpen(defaultOpen);
			if(cvo.getString("total_code").length() == RmConfig.getSingleton().getDefaultTreeCodeFirst().length()) {
				dt.addTreeNode(dtv);
			} else if(cvo.getString("total_code").length() > RmConfig.getSingleton().getDefaultTreeCodeFirst().length()) {
				dt.addTreeNode(cvo.getString("total_code").substring(0, cvo.getString("total_code").length() - RmConfig.getSingleton().getDefaultTreeCodeFirst().length()), dtv);
			}
		}
		return dt;
	}
}
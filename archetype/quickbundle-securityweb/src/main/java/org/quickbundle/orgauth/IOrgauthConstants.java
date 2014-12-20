package org.quickbundle.orgauth;

import org.quickbundle.project.IGlobalConstants;

import org.quickbundle.config.RmConfig;
import org.quickbundle.orgauth.rmrole.util.IRmRoleConstants;
import org.quickbundle.orgauth.rmuser.util.IRmUserConstants;

public interface IOrgauthConstants extends IGlobalConstants {
    
    /**
     * 定义当前系统的根节点编码
     */
    public final static String FUNCTION_NODE_ROOT_TOTAL_CODE = RmConfig.getSingleton().getDefaultTreeCodeFirst();
    
    /**
     * 用户的类型，分辨用户级别
     */
    public enum UserAdminType {
    	ADMIN("3");
    	private String value;
    	UserAdminType(String value_) {
    		value = value_;
    	}
    	
		public String value() {
			return value;
		}
    }
    
    /**
     * 定义组织权限相关的boolean配置
     */
    public enum Config {
    	isUserRelationParty(true), //定义是否用户关联party
    	isRoleRelationParty(true); //定义是否角色关联party
    	private boolean value;
    	Config(boolean value_) {
    		value = value_;
		}
    	public boolean value() {
    		return value;
    	}
    }
    
    /**
     * 定义权限类别
     */
    public enum Authorize {
    	FUNCTION_NODE("FUNCTION_NODE", "1000202400000000001", "功能节点权限"),
    	RM_CODE_TYPE("RM_CODE_TYPE", "1000202400000000002", "数据权限-编码表");
    	private String bsKeword;
    	private String id;
    	private String authorizeName;
    	Authorize(String bsKeword_, String id_, String authorizeName_) {
    		bsKeword = bsKeword_;
    		id = id_;
    		authorizeName = authorizeName_;
    	}
		/**
		 * 返回权限类别的关键字
		 * @return
		 */
		public String bsKeyword() {
			return bsKeword;
		}
		/**
		 * 返回权限类别的id
		 * @return
		 */
		public String id() {
			return id;
		}
		/**
		 * 返回权限类别的名称
		 * @return
		 */
		public String authorizeName() {
			return authorizeName;
		}
    }
    
    /**
     * 定义视图
     */
    public enum PartyView {
    	DEFAULT("1000200700000000001", "行政视图");
    	private String id;
    	private String viewName;
    	PartyView(String id_, String viewName_) {
    		id = id_;
    		viewName = viewName_;
    	}
    	
		/**
		 * 返回视图的ID
		 * @return
		 */
		public String id() {
			return id;
		}
		
		/**
		 * 返回视图名称
		 * @return
		 */
		public String viewName() {
			return viewName;
		}
    }
    
    /**
     * 定义一级模块的节点类型(RM_FUNCTION_NODE.NODE_TYPE)
     */
    public enum FunctionNodeType {
    	MODULE("1"),
    	MENU("2"),
    	BUTTON("3");
    	private String value;
    	FunctionNodeType(String value_) {
    		value = value_;
    	}
    	
		public String value() {
			return value;
		}
    }
    
    /**
     * 定义组织树类型
     */
    public enum OrgTree {
    	DEFAULT("DEFAULT", "默认树"),
    	COMPANY("COMPANY", "公司树"),
    	DEPARTMENT("DEPARTMENT", "部门树"),
    	STATION("STATION", "岗位树"),
    	EMPLOYEE("EMPLOYEE","员工"),
    	USER(IRmUserConstants.TABLE_NAME,"用户"),
    	ROLE(IRmRoleConstants.TABLE_NAME, "角色"),
    	ROLE_EMPLOYEE("ROLE_EMPLOYEE", "角色+用户树");
    	//以下是项目扩展
    	
    	private String value;
		private String title;
		OrgTree(String value_, String title_) {
			this.value = value_;
			this.title = title_;
		}
		
		public String value() {
			return this.value;
		}
		
		public String title() {
			return title;
		}
    }
    
    public enum EnumPartyType {
    	DEPARTMENT("1000200800000000004");
    	private String id;
    	private EnumPartyType(String id) {
    		this.id = id;
		}
		public String getId() {
			return id;
		}
    	
    }
}
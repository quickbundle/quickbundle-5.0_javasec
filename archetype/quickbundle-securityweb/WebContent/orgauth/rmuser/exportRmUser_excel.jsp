<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.List, java.util.Iterator" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.format.Alignment" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.write.WritableCellFormat" %>
<%@ page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@ page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@ page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.orgauth.rmuser.vo.RmUserVo" %>
<%@ page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants" %>
<%@ page import="org.quickbundle.orgauth.rmuser.service.IRmUserService" %>
<%
	IRmUserService service = (IRmUserService)RmBeanFactory.getBean(IRmUserConstants.SERVICE_KEY);	
	List<RmUserVo> lResult = null;  //定义结果列表的List变量
	String queryCondition = request.getParameter("queryCondition");
	if(request.getParameterValues("export_all") == null) {
	    int noFrom = Integer.parseInt(request.getParameter("no_from"));
	    int noTo = Integer.parseInt(request.getParameter("no_to"));
	    lResult = service.queryByCondition(queryCondition, null, noFrom, noTo, true);  
	} else {
	    lResult = service.queryByCondition(queryCondition, null, -1, -1, true);
	}
	String check_all = request.getParameter("export_all");

	RmUserVo resultVo = null;  //定义一个临时的vo变量
	Set<String> sColumn = new HashSet<String>();
	String[] aColumn = request.getParameterValues("custom_column");
	for(int i=0; i<aColumn.length; i++) {
	    sColumn.add(aColumn[i]);
	}
	try {
		response.setContentType("application/msexcel");
	    response.setHeader("Content-disposition", "attachment; filename=" + RmDateHelper.getJoinedSysDateTime() + "export.xls");
        WritableWorkbook wbook = Workbook.createWorkbook(response.getOutputStream());  //建立excel文件
        WritableSheet wsheet = wbook.createSheet("第一页", 0); //sheet名称
        WritableCellFormat cellFormatNumber = new WritableCellFormat();
        cellFormatNumber.setAlignment(Alignment.RIGHT);
        
		int rowIndex = 0;
		int columnIndex = 0;
		wsheet.addCell(new Label(columnIndex, rowIndex, "dt"));
		wsheet.addCell(new Label(columnIndex, rowIndex+1, "sn"));
		wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "序"));

		if(sColumn.contains("name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")));
		}
		if(sColumn.contains("lock_status")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "lock_status"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status")));
		}
		if(sColumn.contains("lock_status_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "lock_status_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status") + "_name"));
		}
		
		if(sColumn.contains("login_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "login_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")));
		}
		if(sColumn.contains("password")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "password"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("password")));
		}
		if(sColumn.contains("authen_type")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "authen_type"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("authen_type")));
		}
		if(sColumn.contains("organization_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "organization_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("organization_id")));
		}
		if(sColumn.contains("employee_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "employee_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("employee_id")));
		}
		if(sColumn.contains("email")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "email"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")));
		}
		if(sColumn.contains("admin_type")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "admin_type"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type")));
		}
		if(sColumn.contains("admin_type_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "admin_type_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type") + "_name"));
		}
		
		if(sColumn.contains("description")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "description"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("description")));
		}
		if(sColumn.contains("agent_status")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "agent_status"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("agent_status")));
		}
		if(sColumn.contains("agent_status_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "agent_status_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("agent_status") + "_name"));
		}
		
		if(sColumn.contains("login_status")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "login_status"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_status")));
		}
		if(sColumn.contains("last_login_date")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, "java.sql.Timestamp"));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "last_login_date"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_date")));
		}
		if(sColumn.contains("last_login_ip")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "last_login_ip"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_ip")));
		}
		if(sColumn.contains("login_sum")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, "java.math.BigDecimal"));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "login_sum"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_sum")));
		}
		if(sColumn.contains("last_custom_css")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "last_custom_css"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_custom_css")));
		}
		if(sColumn.contains("is_affix")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_affix"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("is_affix")));
		}
		if(sColumn.contains("function_permission")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "function_permission"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("function_permission")));
		}
		if(sColumn.contains("data_permission")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "data_permission"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("data_permission")));
		}
		if(sColumn.contains("custom1")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "custom1"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom1")));
		}
		if(sColumn.contains("custom2")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "custom2"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom2")));
		}
		if(sColumn.contains("custom3")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "custom3"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom3")));
		}
		if(sColumn.contains("custom4")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "custom4"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom4")));
		}
		if(sColumn.contains("custom5")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "custom5"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom5")));
		}
		if(sColumn.contains("custom_xml")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "custom_xml"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom_xml")));
		}
		wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
		
		RmPageVo pageVo = new RmPageVo();
		if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
			pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
		}
		int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
		rowIndex = 2;
        //开始行循环
        for(Iterator<RmUserVo> itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
			resultVo = itLResult.next();
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, (startIndex ++) + "" ));

			if(sColumn.contains("name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getName())));
		
			}
		
			if(sColumn.contains("lock_status")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLock_status())));
			}
			if(sColumn.contains("lock_status_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_LOCK_STATUS, resultVo.getLock_status())));
			
			}
		
			if(sColumn.contains("login_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLogin_id())));
		
			}
		
			if(sColumn.contains("password")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getPassword())));
		
			}
		
			if(sColumn.contains("authen_type")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getAuthen_type())));
		
			}
		
			if(sColumn.contains("organization_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getOrganization_id())));
		
			}
		
			if(sColumn.contains("employee_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getEmployee_id())));
		
			}
		
			if(sColumn.contains("email")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getEmail())));
		
			}
		
			if(sColumn.contains("admin_type")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getAdmin_type())));
			}
			if(sColumn.contains("admin_type_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_ADMIN_TYPE, resultVo.getAdmin_type())));
			
			}
		
			if(sColumn.contains("description")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getDescription())));
		
			}
		
			if(sColumn.contains("agent_status")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getAgent_status())));
			}
			if(sColumn.contains("agent_status_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_AGENT_STATUS, resultVo.getAgent_status())));
			
			}
		
			if(sColumn.contains("login_status")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLogin_status())));
		
			}
		
			if(sColumn.contains("last_login_date")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLast_login_date(), 19)));
			
			}
		
			if(sColumn.contains("last_login_ip")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLast_login_ip())));
		
			}
		
			if(sColumn.contains("login_sum")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLogin_sum())));
		
			}
		
			if(sColumn.contains("last_custom_css")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getLast_custom_css())));
		
			}
		
			if(sColumn.contains("is_affix")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getIs_affix())));
		
			}
		
			if(sColumn.contains("function_permission")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getFunction_permission())));
		
			}
		
			if(sColumn.contains("data_permission")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getData_permission())));
		
			}
		
			if(sColumn.contains("custom1")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCustom1())));
		
			}
		
			if(sColumn.contains("custom2")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCustom2())));
		
			}
		
			if(sColumn.contains("custom3")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCustom3())));
		
			}
		
			if(sColumn.contains("custom4")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCustom4())));
		
			}
		
			if(sColumn.contains("custom5")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCustom5())));
		
			}
		
			if(sColumn.contains("custom_xml")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCustom_xml())));
		
			}
		
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getId())));
		}
        wbook.write();
        if(wbook != null) {
            wbook.close();
        }
		out.clear();
    	pageContext.pushBody();
	} catch(Exception e) {
		e.printStackTrace();
	}
%>
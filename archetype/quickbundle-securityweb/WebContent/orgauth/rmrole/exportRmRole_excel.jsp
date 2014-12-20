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
<%@ page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo" %>
<%@ page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants" %>
<%@ page import="org.quickbundle.orgauth.rmrole.service.IRmRoleService" %>
<%
	IRmRoleService service = (IRmRoleService)RmBeanFactory.getBean(IRmRoleConstants.SERVICE_KEY);	
	List<RmRoleVo> lResult = null;  //定义结果列表的List变量
	String queryCondition = request.getParameter("queryCondition");
	if(request.getParameterValues("export_all") == null) {
	    int noFrom = Integer.parseInt(request.getParameter("no_from"));
	    int noTo = Integer.parseInt(request.getParameter("no_to"));
	    lResult = service.queryByCondition(queryCondition, null, noFrom, noTo, true);  
	} else {
	    lResult = service.queryByCondition(queryCondition, null, -1, -1, true);
	}
	String check_all = request.getParameter("export_all");

	RmRoleVo resultVo = null;  //定义一个临时的vo变量
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

		if(sColumn.contains("role_code")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "role_code"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")));
		}
		if(sColumn.contains("name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")));
		}
		if(sColumn.contains("enable_status")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "enable_status"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status")));
		}
		if(sColumn.contains("enable_status_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "enable_status_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status") + "_name"));
		}
		
		if(sColumn.contains("is_system_level")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_system_level"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")));
		}
		if(sColumn.contains("is_system_level_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_system_level_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level") + "_name"));
		}
		
		if(sColumn.contains("owner_org_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "owner_org_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")));
		}
		if(sColumn.contains("is_recursive")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_recursive"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")));
		}
		if(sColumn.contains("is_recursive_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_recursive_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive") + "_name"));
		}
		
		if(sColumn.contains("matrix_code")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "matrix_code"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")));
		}
		if(sColumn.contains("description")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "description"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")));
		}
		if(sColumn.contains("function_permission")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "function_permission"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("function_permission")));
		}
		if(sColumn.contains("data_permission")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "data_permission"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmRoleConstants.TABLE_COLUMN_CHINESE.get("data_permission")));
		}
		wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
		
		RmPageVo pageVo = new RmPageVo();
		if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
			pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
		}
		int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
		rowIndex = 2;
        //开始行循环
        for(Iterator<RmRoleVo> itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
			resultVo = itLResult.next();
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, (startIndex ++) + "" ));

			if(sColumn.contains("role_code")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getRole_code())));
		
			}
		
			if(sColumn.contains("name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getName())));
		
			}
		
			if(sColumn.contains("enable_status")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getEnable_status())));
			}
			if(sColumn.contains("enable_status_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_ENABLE_STATUS, resultVo.getEnable_status())));
			
			}
		
			if(sColumn.contains("is_system_level")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getIs_system_level())));
			}
			if(sColumn.contains("is_system_level_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, resultVo.getIs_system_level())));
			
			}
		
			if(sColumn.contains("owner_org_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getOwner_org_id())));
		
			}
		
			if(sColumn.contains("is_recursive")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getIs_recursive())));
			}
			if(sColumn.contains("is_recursive_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, resultVo.getIs_recursive())));
			
			}
		
			if(sColumn.contains("matrix_code")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getMatrix_code())));
		
			}
		
			if(sColumn.contains("description")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getDescription())));
		
			}
		
			if(sColumn.contains("function_permission")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getFunction_permission())));
		
			}
		
			if(sColumn.contains("data_permission")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getData_permission())));
		
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
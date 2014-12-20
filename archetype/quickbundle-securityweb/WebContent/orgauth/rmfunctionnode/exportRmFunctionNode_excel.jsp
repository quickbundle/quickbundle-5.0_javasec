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
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.util.IRmFunctionNodeConstants" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.service.IRmFunctionNodeService" %>
<%
	IRmFunctionNodeService service = (IRmFunctionNodeService)RmBeanFactory.getBean(IRmFunctionNodeConstants.SERVICE_KEY);	
	List<RmFunctionNodeVo> lResult = null;  //定义结果列表的List变量
	String queryCondition = request.getParameter("queryCondition");
	if(request.getParameterValues("export_all") == null) {
	    int noFrom = Integer.parseInt(request.getParameter("no_from"));
	    int noTo = Integer.parseInt(request.getParameter("no_to"));
	    lResult = service.queryByCondition(queryCondition, null, noFrom, noTo, true);  
	} else {
	    lResult = service.queryByCondition(queryCondition, null, -1, -1, true);
	}
	String check_all = request.getParameter("export_all");

	RmFunctionNodeVo resultVo = null;  //定义一个临时的vo变量
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

		if(sColumn.contains("node_type")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "node_type"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("node_type")));
		}
		if(sColumn.contains("node_type_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "node_type_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("node_type") + "_name"));
		}
		
		if(sColumn.contains("function_property")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "function_property"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("function_property")));
		}
		if(sColumn.contains("function_property_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "function_property_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("function_property") + "_name"));
		}
		
		if(sColumn.contains("name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("name")));
		}
		if(sColumn.contains("enable_status")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "enable_status"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("enable_status")));
		}
		if(sColumn.contains("enable_status_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "enable_status_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("enable_status") + "_name"));
		}
		
		if(sColumn.contains("total_code")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "total_code"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("total_code")));
		}
		if(sColumn.contains("order_code")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "order_code"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("order_code")));
		}
		if(sColumn.contains("funcnode_authorize_type")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "funcnode_authorize_type"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("funcnode_authorize_type")));
		}
		if(sColumn.contains("funcnode_authorize_type_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "funcnode_authorize_type_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("funcnode_authorize_type") + "_name"));
		}
		
		if(sColumn.contains("description")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "description"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("description")));
		}
		if(sColumn.contains("data_value")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "data_value"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("data_value")));
		}
		if(sColumn.contains("pattern_value")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "pattern_value"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")));
		}
		if(sColumn.contains("is_leaf")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_leaf"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("is_leaf")));
		}
		if(sColumn.contains("is_leaf_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_leaf_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("is_leaf") + "_name"));
		}
		
		if(sColumn.contains("icon")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "icon"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("icon")));
		}
		if(sColumn.contains("help_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "help_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("help_name")));
		}
		wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
		
		RmPageVo pageVo = new RmPageVo();
		if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
			pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
		}
		int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
		rowIndex = 2;
        //开始行循环
        for(Iterator<RmFunctionNodeVo> itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
			resultVo = itLResult.next();
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, (startIndex ++) + "" ));

			if(sColumn.contains("node_type")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getNode_type())));
			}
			if(sColumn.contains("node_type_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCTION_NODE_TYPE, resultVo.getNode_type())));
			
			}
		
			if(sColumn.contains("function_property")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getFunction_property())));
			}
			if(sColumn.contains("function_property_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_FUNCTION_PROPERTY, resultVo.getFunction_property())));
			
			}
		
			if(sColumn.contains("name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getName())));
		
			}
		
			if(sColumn.contains("enable_status")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getEnable_status())));
			}
			if(sColumn.contains("enable_status_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_ENABLE_STATUS, resultVo.getEnable_status())));
			
			}
		
			if(sColumn.contains("total_code")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getTotal_code())));
		
			}
		
			if(sColumn.contains("funcnode_authorize_type")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getFuncnode_authorize_type())));
			}
			if(sColumn.contains("funcnode_authorize_type_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCNODE_AUTHORIZE_TYPE, resultVo.getFuncnode_authorize_type())));
			
			}
		
			if(sColumn.contains("description")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getDescription())));
		
			}
		
			if(sColumn.contains("data_value")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getData_value())));
		
			}
		
			if(sColumn.contains("pattern_value")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getPattern_value())));
		
			}
		
			if(sColumn.contains("is_leaf")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getIs_leaf())));
			}
			if(sColumn.contains("is_leaf_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_YES_NOT, resultVo.getIs_leaf())));
			
			}
		
			if(sColumn.contains("icon")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getIcon())));
		
			}
		
			if(sColumn.contains("help_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getHelp_name())));
		
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
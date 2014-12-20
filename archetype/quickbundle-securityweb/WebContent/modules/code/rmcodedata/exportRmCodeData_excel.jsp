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
<%@ page import="org.quickbundle.modules.code.rmcodedata.vo.RmCodeDataVo" %>
<%@ page import="org.quickbundle.modules.code.rmcodedata.util.IRmCodeDataConstants" %>
<%@ page import="org.quickbundle.modules.code.rmcodedata.service.IRmCodeDataService" %>
<%
	IRmCodeDataService service = (IRmCodeDataService)RmBeanFactory.getBean(IRmCodeDataConstants.SERVICE_KEY);	
	List lResult = null;  //定义结果列表的List变量
	String queryCondition = request.getParameter("queryCondition");
	if(request.getParameterValues("export_all") == null) {
	    int noFrom = Integer.parseInt(request.getParameter("no_from"));
	    int noTo = Integer.parseInt(request.getParameter("no_to"));
	    lResult = service.queryByCondition(queryCondition, null, noFrom, noTo, true);  
	} else {
	    lResult = service.queryByCondition(queryCondition, null, -1, -1, true);
	}
	String check_all = request.getParameter("export_all");

	RmCodeDataVo resultVo = null;  //定义一个临时的vo变量
	Set sColumn = new HashSet();
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

		if(sColumn.contains("code_type_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, "java.lang.Long"));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "code_type_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "编码类型ID"));
		}
		if(sColumn.contains("data_key")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "data_key"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "数据关键字"));
		}
		if(sColumn.contains("enable_status")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "enable_status"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "启用状态"));
		}
		if(sColumn.contains("enable_status_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "enable_status_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "启用状态_name"));
		}
		
		if(sColumn.contains("data_value")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "data_value"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "数据值"));
		}
		if(sColumn.contains("total_code")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "total_code"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "完整编码"));
		}
		if(sColumn.contains("remark")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "remark"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "备注"));
		}
		wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
		
		RmPageVo pageVo = new RmPageVo();
		if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
			pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
		}
		int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
		rowIndex = 2;
        //开始行循环
        for(Iterator itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
			resultVo = (RmCodeDataVo) itLResult.next();
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, (startIndex ++) + "" ));

		if(sColumn.contains("code_type_id")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getCode_type_id())));
		
		}
		
		if(sColumn.contains("data_key")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getData_key())));
		
		}
		
		if(sColumn.contains("enable_status")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getEnable_status())));
		}
		if(sColumn.contains("enable_status_name")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, org.quickbundle.project.RmGlobalReference.get(IRmCodeDataConstants.DICTIONARY_RM_YES_NOT, resultVo.getEnable_status())));
			
		}
		
		if(sColumn.contains("data_value")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getData_value())));
		
		}
		
		if(sColumn.contains("total_code")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getTotal_code())));
		
		}
		
		if(sColumn.contains("remark")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getRemark())));
		
		}
		
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getId())));
		}
        wbook.write();
        if(wbook != null) {
            wbook.close();
        }
	} catch(Exception e) {
		e.printStackTrace();
	}
%>

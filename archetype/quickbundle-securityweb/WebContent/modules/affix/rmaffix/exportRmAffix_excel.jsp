<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="jxl.write.Number"%>
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
<%@ page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo" %>
<%@ page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants" %>
<%@ page import="org.quickbundle.modules.affix.rmaffix.service.IRmAffixService" %>
<%
	IRmAffixService service = (IRmAffixService)RmBeanFactory.getBean(IRmAffixConstants.SERVICE_KEY);	
	List<RmAffixVo> lResult = null;  //定义结果列表的List变量
	String queryCondition = request.getParameter("queryCondition");
	if(request.getParameterValues("export_all") == null) {
	    int noFrom = Integer.parseInt(request.getParameter("no_from"));
	    int noTo = Integer.parseInt(request.getParameter("no_to"));
	    lResult = service.queryByCondition(queryCondition, null, noFrom, noTo, true);  
	} else {
	    lResult = service.queryByCondition(queryCondition, null, -1, -1, true);
	}
	String check_all = request.getParameter("export_all");

	RmAffixVo resultVo = null;  //定义一个临时的vo变量
	Set<String> sColumn = new HashSet();
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

		if(sColumn.contains("bs_keyword")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "bs_keyword"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "业务关键字"));
		}
		if(sColumn.contains("record_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "record_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "主记录ID"));
		}
		if(sColumn.contains("order_number")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, "java.math.BigDecimal"));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "order_number"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "顺序数"));
		}
		if(sColumn.contains("title")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "title"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "标题"));
		}
		if(sColumn.contains("old_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "old_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "原文件名"));
		}
		if(sColumn.contains("save_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "save_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "真实储存路径"));
		}
		if(sColumn.contains("save_size")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, "java.math.BigDecimal"));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "save_size"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "真实文件大小"));
		}
		if(sColumn.contains("mime_type")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "mime_type"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "内容类型"));
		}
		if(sColumn.contains("mime_type_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "mime_type_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "内容类型_name"));
		}
		
		if(sColumn.contains("encoding")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "encoding"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "编码"));
		}
		if(sColumn.contains("description")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "description"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "描述"));
		}
		if(sColumn.contains("author")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "author"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "作者"));
		}
		wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
		
		RmPageVo pageVo = new RmPageVo();
		if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
			pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
		}
		int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
		rowIndex = 2;
        //开始行循环
        for(Iterator<RmAffixVo> itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
			resultVo = itLResult.next();
            wsheet.addCell(new Number(columnIndex ++ , rowIndex, (startIndex ++)));

		if(sColumn.contains("bs_keyword")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getBs_keyword())));
		
		}
		
		if(sColumn.contains("record_id")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getRecord_id())));
		
		}
		
		if(sColumn.contains("order_number")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getOrder_number())));
		
		}
		
		if(sColumn.contains("title")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getTitle())));
		
		}
		
		if(sColumn.contains("old_name")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getOld_name())));
		
		}
		
		if(sColumn.contains("save_name")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getSave_name())));
		
		}
		
		if(sColumn.contains("save_size")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getSave_size())));
		
		}
		
		if(sColumn.contains("mime_type")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getMime_type())));
		}
		if(sColumn.contains("mime_type_name")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, resultVo.getMime_type())));
			
		}
		
		if(sColumn.contains("encoding")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getEncoding())));
		
		}
		
		if(sColumn.contains("description")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getDescription())));
		
		}
		
		if(sColumn.contains("author")) {
			wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getAuthor())));
		
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
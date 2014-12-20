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
<%@ page import="org.quickbundle.modules.message.vo.RmMessageVo" %>
<%@ page import="org.quickbundle.modules.message.IRmMessageConstants" %>
<%@ page import="org.quickbundle.modules.message.service.RmMessageService" %>
<%
	RmMessageService service = RmBeanFactory.getBean(RmMessageService.class);
	List<RmMessageVo> lResult = null;  //定义结果列表的List变量
	String queryCondition = request.getParameter("queryCondition");
	if(request.getParameterValues("export_all") == null) {
	    int noFrom = Integer.parseInt(request.getParameter("no_from"));
	    int noTo = Integer.parseInt(request.getParameter("no_to"));
	    lResult = service.list(queryCondition, null, noFrom, noTo, true);  
	} else {
	    lResult = service.list(queryCondition, null, -1, -1, true);
	}
	String check_all = request.getParameter("export_all");

	RmMessageVo resultVo = null;  //定义一个临时的vo变量
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

		if(sColumn.contains("biz_keyword")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "biz_keyword"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")));
		}
		if(sColumn.contains("sender_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "sender_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")));
		}
		if(sColumn.contains("parent_message_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "parent_message_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")));
		}
		if(sColumn.contains("owner_org_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "owner_org_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")));
		}
		if(sColumn.contains("template_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "template_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")));
		}
		if(sColumn.contains("is_affix")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "is_affix"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")));
		}
		if(sColumn.contains("record_id")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "record_id"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")));
		}
		if(sColumn.contains("message_xml_context")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "message_xml_context"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")));
		}
		wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
		
		RmPageVo pageVo = new RmPageVo();
		if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
			pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
		}
		int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
		rowIndex = 2;
        //开始行循环
        for(Iterator<RmMessageVo> itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
			resultVo = itLResult.next();
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, (startIndex ++) + "" ));

			if(sColumn.contains("biz_keyword")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getBiz_keyword())));
		
			}
		
			if(sColumn.contains("sender_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getSender_id())));
		
			}
		
			if(sColumn.contains("parent_message_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getParent_message_id())));
		
			}
		
			if(sColumn.contains("owner_org_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getOwner_org_id())));
		
			}
		
			if(sColumn.contains("template_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getTemplate_id())));
		
			}
		
			if(sColumn.contains("is_affix")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getIs_affix())));
		
			}
		
			if(sColumn.contains("record_id")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getRecord_id())));
		
			}
		
			if(sColumn.contains("message_xml_context")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.getMessage_xml_context())));
		
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
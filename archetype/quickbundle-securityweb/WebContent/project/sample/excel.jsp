<%@ page language="java" contentType="application/x-msdownload" pageEncoding="utf-8"  %>
<%@ page errorPage="/jsp/common/err.jsp" %>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.URLEncoder"%>
<%
	//提供Excel下载
  response.reset();
  response.setContentType("application/x-download");
  String filedownload = null;//(String)request.getAttribute(IExcelConstants.FILE_DOWNLOAD);
  String filedisplay = null;//(String)request.getAttribute(IExcelConstants.FILE_DISPLAY);
  filedisplay = URLEncoder.encode(filedisplay,"UTF-8");
  response.addHeader("Content-Disposition","attachment;filename=" + filedisplay); 
  OutputStream os=null;
  FileInputStream in=null;
  try{
	 os=response.getOutputStream();
	  in=new FileInputStream(filedownload);
	  byte[] buf=new byte[1024];
	  int i=0;
	  while((i=in.read(buf))>0){
		 os.write(buf,0,i);
	  }
	  os.flush();
	  out.clear();
	  pageContext.pushBody();
 }finally{
	  if(in!=null) {
		  in.close(); 
	  }
	  File file=new File(filedownload);
	  file.delete();
  }
%>
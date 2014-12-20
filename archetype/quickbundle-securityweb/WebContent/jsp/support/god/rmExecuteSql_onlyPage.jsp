<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!--%@ include file="/jsp/support/god/rmExecuteHelper.jsp" %-->


<%!
	public static String getParameter_iso(HttpServletRequest request, String name) {
        String strValue = request.getParameter(name);
        if (strValue == null) {
            strValue = "";
        } else if (strValue.equals("null")) {
            strValue = "";
        } else {
            try {
                //strValue = new String(strValue.getBytes("ISO8859_1"), "GBK");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strValue;
    }
%>


<%!
	public static Object getDocByObj(Object obj, String str) {
		try {
	        if (obj == null) {
				str += obj;
	        } else if (obj instanceof String || obj instanceof Integer || obj instanceof java.sql.Timestamp) {  //基本类型
	           str = obj.toString();
	        } else if (obj instanceof java.util.List) {
	            java.util.List lResult = (java.util.List) obj;
				int index = 0;
	            for (java.util.Iterator itLResult = lResult.iterator(); itLResult.hasNext();) {
					index ++;
	                Object vo = itLResult.next();
	                str += "\n\nNo." + index + " record:  " + vo.getClass().getName() + getDocByObj(vo, str);
	            }
	        } else if (obj instanceof Object[][]) {
				Object[][] aObj = (Object[][]) obj;
				String tempStr = "";
				for(int i=0; i<aObj.length; i++) {
					tempStr += "\n" + (i+1) + ": " + aObj[i][0] + "='" + aObj[i][1] + "'";
				}
				str = tempStr;
	        } else if (obj instanceof java.io.Serializable) {
				str = obj.toString();
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return str;
    }
%>

<%! 
	public static String getStackTraceStr(Throwable e) {
		String str = "";
		StackTraceElement[] st = e.getStackTrace();
		for(int i=0; i<st.length; i++) {
			str += st[i].toString() + "\n";
		}
		return str;
	}
%>

<%  //判断是否为修改页面
	boolean isShowResult = false;  //定义变量,标识本页面是否是初始化页面
	if("1".equals(request.getParameter("isShowResult"))) {
		isShowResult = true;
	}
%>

<%


	String strsql = getParameter_iso(request, "strsql");

	String dbType = getParameter_iso(request, "dbType");
	if("".equals(dbType)) {
		dbType = getParameter_iso(request, "dbType2");
	}

	String dbType2 = getParameter_iso(request, "dbType2");
	String url = getParameter_iso(request, "url");
	String user = getParameter_iso(request, "user");
	String password = getParameter_iso(request, "password");
    Object result = null;
	if(isShowResult) {
		java.sql.Connection conn = null;
	    try {
            Class.forName(dbType);
            conn = java.sql.DriverManager.getConnection(url, user, password);

			if(strsql != null && strsql.length() > 0 && strsql.trim().length() > 0) {
				strsql = strsql.trim();
	            if (strsql.toUpperCase().startsWith("INSERT")
	                    || strsql.toUpperCase().startsWith("DELETE")
	                    || strsql.toUpperCase().startsWith("UPDATE")
	                    || strsql.toUpperCase().startsWith("CREATE")
						|| strsql.toUpperCase().startsWith("ALTER")
	                    || strsql.toUpperCase().startsWith("DROP")) {
					int sum = conn.createStatement().executeUpdate(strsql);
					result = "执行sql语句更新了" + sum + "行记录!";
	            } else if (strsql.toUpperCase().startsWith("SELECT")) {
					java.sql.ResultSet rs = conn.createStatement().executeQuery(strsql);
					java.util.List lResult = new java.util.ArrayList();
					java.sql.ResultSetMetaData rsmd = rs.getMetaData();
					while(rs.next()) {
						Object[][] aVo = new Object[rsmd.getColumnCount()][2];
				        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							aVo[i-1][0] = rsmd.getColumnName(i);
							aVo[i-1][1] = rs.getString(i);
				        }
						lResult.add(aVo);
					}
					result = lResult;
	            } else {
	                result = "无效的sql语句！";
	            }
			}
	    } catch (Exception e) {
	        //e.printStackTrace();

	        result = "操作时出现了异常：" + e.getMessage() + "\n\n" + getStackTraceStr(e);
	    } finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				result = result + "\n关闭连接时出现了异常：" + e.getMessage();
			}
		}
	}
%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm execute</title>
<script type="text/javascript">
	function execute_onClick() {  //插入单条数据
		form.action="rmExecuteSql_onlyPage.jsp?isShowResult=1";
	    form.submit();
	}

	function initDefaultUrl(thisSelect) {
		if(thisSelect.value == "") {
			document.getElementById("tr_dbType2").style.display = "block";
		} else {
			document.getElementById("tr_dbType2").style.display = "none";
			form.url.value = thisSelect.options[thisSelect.selectedIndex].defaultUrl;
		}
	}
</script>
</head>
<body>

<form name="form" method="post">
<table class="table_noFrame">
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="button_save" class="button_ellipse" type="button" value="执行" onClick="javascript:execute_onClick()">
			&nbsp;&nbsp;
			<input name="button_cancel" class="button_ellipse" type="button" value="关闭"  onClick="javascript:window.close()" >
		</td>
	</tr>
</table>


<div id="ccChild1"> 
<table class="table_div_content">
<tr><td> 
	<table class="table_div_content_inner">
		<tr>
			<td width="10%" align="right">SQL</td>
			<td width="90%" colspan="3" align="left">
				<textarea class="textarea_limit_words" style="width:800px" cols="60" rows="10" name="strsql" maxLength="5000" id="remarkId"><%=isShowResult ? strsql : ""%></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">&nbsp;</td>
			<td colspan="3" align="left">&nbsp;</td>
		</tr>
		<tr>
			<td align="right">结果展示</td>
			<td colspan="3" align="left">
				<textarea class="textarea_limit_words" style="width:800px" cols="60" rows="20" name="execute_result" maxLength="5000"  id="remarkId"><%=isShowResult ? getDocByObj(result, "") : ""%></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">历史执行</td>
			<td colspan="3" align="left">
<%
	if(isShowResult) {
		int maxCode = 100;
		if(session.getAttribute("lHistory") == null) {
			session.setAttribute("lHistory", new java.util.LinkedList());
		}
		if(session.getAttribute("sHistory") == null) {
			session.setAttribute("sHistory", new java.util.HashSet());
		}
		java.util.List lHistory = (java.util.List)session.getAttribute("lHistory");
		java.util.Set sHistory = (java.util.Set)session.getAttribute("sHistory");
		if(strsql != null && strsql.length() > 0) {
			if(!sHistory.contains(strsql)) {
				lHistory.add(strsql);
				sHistory.add(strsql);
			}
		}
		if(lHistory.size() > maxCode) {
			int size = lHistory.size();
			for(int i=0; i<size - maxCode; i++) {
				sHistory.remove(lHistory.get(0));
				lHistory.remove(0);
			}
		}
		int index=0;
		for(java.util.Iterator itLHistory = lHistory.iterator(); itLHistory.hasNext(); ) {
			index ++;
			out.println("<br>" + index + ":  " + itLHistory.next() + "<br>");
		}
	}
%>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right">&nbsp;</td>
		</tr>
		<tr>
			<td width="10%" align="right">db类型</td>
			<td width="20%" align="left">
				<select name='dbType' onChange="initDefaultUrl(this)" >
					<option value="oracle.jdbc.driver.OracleDriver" defaultUrl="jdbc:oracle:thin:@localhost:1521:test">Oracle</option>
					<option value="com.ibm.db2.jcc.DB2Driver" defaultDriver.old="COM.ibm.db2.jdbc.app.DB2Driver" defaultUrl="jdbc:db2://localhost:50000/test">db2</option>
					<option value="com.microsoft.jdbc.sqlserver.SQLServerDriver" defaultUrl="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=test" selected>SQL Server</option>
					<option value="com.mysql.jdbc.Driver" defaultUrl="jdbc:mysql://localhost/test">MySQL</option>
					<option value="com.sybase.jdbc2.jdbc.SybDriver" defaultUrl="jdbc:sybase:Tds:localhost:2638/">Sybase</option>
					<option value="com.informix.jdbc.IfxDriver" defaultUrl="jdbc:informix-sqli://localhost:1533/test:INFORMIXSERVER=myserver">informix</option>
					<option value="org.postgresql.Driver" defaultUrl="jdbc:postgresql://localhost/test">PostgreSQL</option>
					<option value="sun.jdbc.odbc.JdbcOdbcDriver" defaultUrl="jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=yourApplicationRealPath/Data/ReportDemo.mdb">access(*)</option>
					<option value="" defaultUrl="">其它</option>
				</select> 
			</td>
			<td width="10%" align="right">地址</td>
			<td width="60%" align="left">
				<input type="text" class="text_field" style="width:500px"  name="url" value="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=test" maxLength="100" />
			</td>
		</tr>
		<tr>
			<td align="right">用户名</td>
			<td align="left">
				<input type="text" name="user" value="" maxLength="300" />
			</td>
			<td align="right">密码</td>
			<td align="left">
				<input type="password" name="password" value="" maxLength="100" />
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr id="tr_dbType2" style="display:none">
			<td align="right">自定义驱动</td>
			<td align="left" colspan="3">
				<input type="text" style="width:500px" name="dbType2" value="" maxLength="300" />
			</td>
		</tr>
	</table>
</td></tr>
</table>
</div>
</form>			
</body>
</html>
<script type="text/javascript">
<%
	if(isShowResult) {
		out.println("	form.dbType.value='" + dbType + "';\n");
		out.println("	form.url.value='" + url + "';\n");
		out.println("	form.user.value='" + user + "';\n");
		out.println("	form.password.value='" + password + "';\n");
		out.println("	form.dbType2.value='" + dbType2 + "';\n");
	}
%>
</script>


<!--jsp:include page="rmExecuteHelper.jsp" /-->
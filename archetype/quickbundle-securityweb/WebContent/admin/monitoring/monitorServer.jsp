<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.Timestamp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Collection"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<title>监控服务器页面（<%=new Timestamp(System.currentTimeMillis())%>）</title>
</head>
<body>
<%
response.setHeader("Pragma", "No-Cache");
response.setHeader("Cache-Control", "No-Cache");
response.setDateHeader("Expires", 0);
try {
	Map<String, Map> mNode = new RmSequenceMap<String, Map>();
	{//定义节点1
		Map<String, Object> mIndex = new RmSequenceMap<String, Object>();
		mIndex.put("CPU", true);
		mIndex.put("内存", true);
		mIndex.put("数据库", true);
		mIndex.put("磁盘", true);
		mIndex.put("|", "|");
		mIndex.put("报账", true);
		mIndex.put("银企", true);
		mIndex.put("总帐", true);
		mIndex.put("客开", false);
		mIndex.put("系统", true);
		mIndex.put("用户", true);
		mNode.put("10.20.0.1 WAS主节点", mIndex);
	}
	{
		Map<String, Object> mIndex = new RmSequenceMap<String, Object>();
		mIndex.put("报账", true);
		mIndex.put("银企", true);
		mIndex.put("总帐", true);
		mIndex.put("客开", false);
		mIndex.put("系统", true);
		mIndex.put("用户", true);
		mNode.put("10.20.0.99 WAS集群节点", mIndex);
	}
	{//定义节点3
		Map<String, Object> mIndex = new RmSequenceMap<String, Object>();
		mIndex.put("CPU", true);
		mIndex.put("内存", true);
		mIndex.put("数据库", true);
		mIndex.put("磁盘", true);
		mNode.put("10.20.99.99 数据库", mIndex);
	}
	
	
	for(String nodeKey : mNode.keySet()){
		Map<String, Object> mIndex = mNode.get(nodeKey);
%>
<div align="center">
	<h2><%=nodeKey%> 状态</h2>
	<table>
		<tr>
<%
		for(String indexKey : mIndex.keySet()){
%>
			<td><%=indexKey%></td>
<%
		}
%>
		</tr>
		<tr>
<%
		for(String indexKey : mIndex.keySet()){
			Object value = mIndex.get(indexKey);
			String outValue = null;
			if(value instanceof Boolean) {
				outValue = ((Boolean)value).booleanValue() ? "<img src=\"./yes.png\"/>" : "<img src=\"./no.png\"/>";
			} else {
				outValue = String.valueOf(value);
			}
%>
			<td><%=outValue%></td>
<%
		}
%>
		</tr>
	</table>
</div>
<div>&nbsp;</div>
<%	} %>
</body>
</html>
<%
} catch(Exception e) {
	e.printStackTrace();
%>
<div align="center">
	<h1>页面出错了，请联系管理员！</h1>
	<p><%=e%></p>
</div>
<%
}
%>

<%!
class RmSequenceSet extends HashSet {

    List lKeyIndex = null;
    public RmSequenceSet() {
        super();
        lKeyIndex = new ArrayList();
    }

    public boolean add(Object key) {
        if (!lKeyIndex.contains(key)) {
            this.lKeyIndex.add(key);
        }
        return super.add(key);
    }

    public boolean remove(Object key) {
        lKeyIndex.remove(key);
        return super.remove(key);
    }

    public boolean removeAll(Collection coll) {
        lKeyIndex.removeAll(coll);
        return super.removeAll(coll);
    }

    public Iterator iterator() {
        return lKeyIndex.iterator();
    }
}
class RmSequenceMap<K,V> extends HashMap {
    List lKeyIndex = null;
    public RmSequenceMap() {
        super();
        lKeyIndex = new ArrayList();
    }
    public Object put(Object key, Object value) {
        if (!lKeyIndex.contains(key)) {
            this.lKeyIndex.add(key);
        }
        return super.put(key, value);
    }
    public Object remove(Object key) {
        lKeyIndex.remove(key);
        return super.remove(key);
    }
    public Set keySet() {
        Set sKey = new RmSequenceSet();
        for (Iterator itLKeyIndex = lKeyIndex.iterator(); itLKeyIndex.hasNext();) {
            sKey.add(itLKeyIndex.next());
        }
        return sKey;
    }
}
%>
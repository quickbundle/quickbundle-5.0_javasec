/*
 * 创建日期 2005-5-29
 */
package org.quickbundle.tools.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.quickbundle.ICoreConstants;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.itf.IObject2Object;
import org.quickbundle.tools.support.log.RmLogHelper;


/**
 * @author   帮助实现一些有关Jsp页面的处理
 */
public class RmJspHelper implements ICoreConstants {

    /**
     * 从表单中获取值，从ISO转到RmConfig.defaultEncode()默认编码，如果是null则返回""
     * 
     * @param request HttpServletRequest
     * @param name 需要获取的input名字
     * @return 表单中的实际内容
     */
    public static String getParameter(HttpServletRequest request, String name) {
        String strValue = request.getParameter(name);
        if (strValue == null) {
            strValue = "";
        } else if (strValue.equals("null")) {
            strValue = "";
        } else {
            try {
                strValue = new String(strValue.getBytes("ISO8859_1"), RmBaseConfig.getSingleton().getDefaultEncode());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return strValue;
    }
    
    /**
     * 功能: 先从request.getAttribute，没有的话再从request.getParameter取
     *
     * @param request
     * @param key
     * @return
     */
    public static String getValueFromRequest_attributeParameter(HttpServletRequest request, String key) {
        String value = null;
        if(request.getAttribute(key) != null) {  //如果request.getAttribute中有，就不取request.getParameter
            value = request.getAttribute(key).toString();
        } else {
        	//如果有多个key值，取最后一个
        	if(request.getParameterValues(key) != null && request.getParameterValues(key).length > 1) {
        		value = request.getParameterValues(key)[request.getParameterValues(key).length-1];
        	} else {
                value = request.getParameter(key);  //从request的parameter获得
        	}

        }
        return value;
    }

    /**
     * 从表单中获取整数值，如果是null或 "null"，则过滤为0
     * 
     * @param request-->HttpServletRequest
     * @param name-->需要获取的input名字
     * @return 表单中的实际内容
     */
    public static int getParameterInt(HttpServletRequest request, String name) {
        String strValue = request.getParameter(name);
        if (strValue == null) {
            strValue = "0";
        } else if ("null".equals(strValue)) {
            strValue = "0";
        }
        int returnValue = 0;
        try {
            returnValue = Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    /**
     * 功能: 如果有一个参数重复提交，取最后一个，J2EE默认取第一个
     *
     * @param request
     * @param name
     * @return
     */
    public static String getLastParameter(HttpServletRequest request, String name) {
        if(request.getParameterValues(name) == null) {
            return null;
        } else {
            if(request.getParameterValues(name).length == 1) {
                return request.getParameter(name);
            } else {
                return request.getParameterValues(name)[request.getParameterValues(name).length-1];
            }
        }

    }

    /**
     * 将某字符替换为某字符串, 例如将 \n替换为 <br>
     * 
     * @param pStr 被处理的字符串
     * @param pC 被替换的字符
     * @param rep 替换后的字符串
     * @return 字符串
     */
    public static String replaceAllChar2String(String pStr, char pC, String rep) throws Exception {
        java.text.StringCharacterIterator sciter = new java.text.StringCharacterIterator(pStr);
        StringBuilder rt = new StringBuilder();
        for (char c = sciter.first(); c != java.text.StringCharacterIterator.DONE; c = sciter.next()) {
            if (c == pC) {
            	rt.append(rep);
            } else {
            	rt.append(c);
            }
        }
        return rt.toString();
    }

    /**
     * 将String输出处理, 用于直接显示在Jsp页面
     * 
     * @param pStr-->被处理的字符串
     * @return 字符串
     */
    public static String filterJspString(String pStr) throws Exception {
        pStr = replaceAllChar2String(pStr, '<', "&lt;");
        pStr = replaceAllChar2String(pStr, '>', "&gt;");
        pStr = replaceAllChar2String(pStr, '\n', "<br>");
        pStr = replaceAllChar2String(pStr, ' ', "&nbsp;");
        return pStr;
    }

    /**
     * 将String输出处理, 用于直接显示在Jsp页面中的textarea中
     * 
     * @param pStr-->被处理的字符串
     * @return 字符串
     */
    public static String filterJspString4Textarea(String pStr) throws Exception {
        pStr = replaceAllChar2String(pStr, '<', "&lt;");
        pStr = replaceAllChar2String(pStr, '>', "&gt;");
        pStr = replaceAllChar2String(pStr, ' ', "&nbsp;");
        return pStr;
    }

    /**
     * 弹出信息，确认后跳转到上一页
     * 
     * @param out
     * @param msg
     * @throws java.lang.Exception
     */
    public static void showHtmlAlertHistory(JspWriter out, String msg) throws Exception {
        out.write("<html>");
        out.write("<head><title></title>");
        out.write("<meta http-equiv='Content-Type' content='text/html; charset=" + RmBaseConfig.getSingleton().getDefaultEncode() + "'>");
        out.write("</head>");
        out.write("<body>");
        out.write("<SCRIPT type=\"text/javascript\">");
        out.write("<!--\n");
        out.write("alert(\"" + msg + "\");\n");
        out.write("history.go(-1)");
        out.write("\n//-->");
        out.write("</SCRIPT>");
        out.write("</body>");
        out.write("</html>");
    }

    /**
     * 弹出信息，确认后跳转到doAfterStr指定页面并且覆盖父页面
     * 
     * @param out
     * @param msg
     * @param doAfterStr
     * @throws java.lang.Exception
     */
    public static void showHtmlAlertParent(JspWriter out, String msg, String doAfterStr) throws Exception {
        out.write("<html>");
        out.write("<head><title></title>");
        out.write("<meta http-equiv='Content-Type' content='text/html; charset=GBK'>");
        out.write("</head>");
        out.write("<body>");
        out.write("<SCRIPT type=\"text/javascript\">");
        out.write("<!--\n");
        out.write("alert(\"" + msg + "\");\n");
        out.write("parent.navigate(\"" + doAfterStr + "\")");
        //out.write("window.location=\"" + doAfterStr + "\"");
        out.write("\n//-->");
        out.write("</SCRIPT>");
        out.write("</body>");
        out.write("</html>");
    }

    /**
     * 弹出信息，确认后跳转到doAfterStr指定页面
     * 
     * @param out
     * @param msg
     * @param doAfterStr
     * @throws java.lang.Exception
     */
    public static void showHtmlAlert(JspWriter out, String msg, String doAfterStr) throws Exception {
        out.write("<html>");
        out.write("<head><title></title>");
        out.write("<meta http-equiv='Content-Type' content='text/html; charset=GBK'>");
        out.write("</head>");
        out.write("<body>");
        out.write("<SCRIPT type=\"text/javascript\">");
        out.write("<!--\n");
        out.write("alert(\"" + msg + "\");\n");
        out.write("window.location=\"" + doAfterStr + "\"");
        out.write("\n//-->");
        out.write("</SCRIPT>");
        out.write("</body>");
        out.write("</html>");
    }

    /**
     * 弹出信息，确认后关闭页面
     * 
     * @param out
     * @param msg
     * @throws java.lang.Exception
     */
    public static void showHtmlAlertClose(JspWriter out, String msg) throws Exception {
        out.write("<html>");
        out.write("<head><title></title>");
        out.write("<meta http-equiv='Content-Type' content='text/html; charset=GBK'>");
        out.write("</head>");
        out.write("<body>");
        out.write("<SCRIPT type=\"text/javascript\">");
        out.write("<!--\n");
        out.write("alert(\"" + msg + "\");\n");
        out.write("window.self.close();");
        out.write("\n//-->");
        out.write("</SCRIPT>");
        out.write("</body>");
        out.write("</html>");
    }
    
    /**
     * 实现从Map到二位数组的转换
     *
     * @return
     */
    private static IObject2Object map2StringArray() {
        return new IObject2Object() {
            /**
             * 实现从Map到二位数组的转换
             *
             * @param thisObj
             * @return
             */
            public Object object2Object(Object thisObj) {
                Map mOptionValue = (Map) thisObj;
                String[][] aOptionValue = new String[mOptionValue.size()][2];
                Iterator itMOptionValue = mOptionValue.keySet().iterator();
                int index = 0;
                while (itMOptionValue.hasNext()) {
                    String tempKey = (String) itMOptionValue.next();
                    String tempValue = (String) mOptionValue.get(tempKey);
                    aOptionValue[index][0] = tempKey;
                    aOptionValue[index][1] = tempValue;
                    index++;
                }
                return aOptionValue;
            }
        };
    }
    
    
    /**
     * 创建一个select,接受Map
     * 
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param mOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param hasEmptyValue 是否有""空字符串 
     * @return select的HTML代码
     */
    public static String getSelectField(String strName, int nDisplaySize, Map mOptionValue, String strCompare, String strProperty, boolean hasEmptyValue) {
    	return getSelectField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty, hasEmptyValue, null);
    }
    
    /**
     * 创建一个select,接受Map
     * 
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param mOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param hasEmptyValue 是否有""空字符串 
     * @param pleaseSelectStr hasEmptyValue==true时，指定""对应的显示名称
     * @return select的HTML代码
     */
    public static String getSelectField(String strName, int nDisplaySize, Map mOptionValue, String strCompare, String strProperty, boolean hasEmptyValue, String pleaseSelectStr) {
        if (mOptionValue != null) {
            return getSelectField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty, hasEmptyValue, pleaseSelectStr);
        } else {
            return "";
        }
    }
    
    /**
     * 创建一个select，接受Object和IObject2Object
     * 
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param thisObj 要显示的option列表
     * @param objectHandler 把object变成String[][]的回调实现
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param hasEmptyValue 是否有""空字符串
     * @param pleaseSelectStr hasEmptyValue==true时，指定""对应的显示名称
     * @return select的HTML代码
     */
    public static String getSelectField(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String strCompare, String strProperty, boolean hasEmptyValue, String pleaseSelectStr) {
    	return getSelectField(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty, hasEmptyValue, pleaseSelectStr);
    }
    
    /**
     * 创建一个select，接受二维数组
     * 
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param aOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param hasEmptyValue 是否有""空字符串
     * @param pleaseSelectStr hasEmptyValue==true时，指定""对应的显示名称
     * @return select的HTML代码
     */
    public static String getSelectField(String strName, int nDisplaySize, String[][] aOptionValue, String strCompare, String strProperty, boolean hasEmptyValue, String pleaseSelectStr) {
        StringBuffer returnString = new StringBuffer();
        try {
            if (strProperty == null)
                strProperty = "";
            if (strCompare == null)
                strCompare = "";
            returnString.append("<select name='" + strName + "' ");
            returnString.append(strProperty);
            returnString.append(" >\r\n");
            if(hasEmptyValue) {
                returnString.append("<option value='' ");
                if (strCompare.length() == 0) {
                    returnString.append(" selected ");
                }
                returnString.append(">");
                if(pleaseSelectStr == null) {
                	returnString.append("--请选择--");
                } else {
                	returnString.append(pleaseSelectStr);
                }
                returnString.append("</option>\r\n");
            }
            if (aOptionValue != null) {
                for (int i = 0; i < aOptionValue.length; i++) {
                    String tempKey = aOptionValue[i][0];
                    String tempValue = aOptionValue[i][1];
                    returnString.append("<option value='" + tempKey + "' ");
                    if (tempKey.equals(strCompare)) {
                        returnString.append(" selected ");
                    }
                    //截去超长的字符
                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
                        tempValue = tempValue.substring(0, nDisplaySize);
                    }
                    returnString.append(">" + tempValue + "</option>\r\n");
                }
            }
            returnString.append("</select> ");
            return returnString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 创建一个select多选，接受Map
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param mOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param showType 展示类型  0，排斥；1，显示；2，都显示，但strCompare要被选中
     * @return select的HTML代码
     */
    public static String getSelectFieldMultiple(String strName, int nDisplaySize, Map mOptionValue, String[] strCompare, String strProperty, int showType) {
        if (mOptionValue != null) {
            return getSelectFieldMultiple(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty, showType);
        } else {
            return "";
        }
    }
    
    /**
     * 创建一个select多选，接受Object和IObject2Object
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param thisObj 要显示的option列表
     * @param objectHandler 把object变成String[][]的回调实现
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param showType 展示类型  0，排斥；1，显示；2，都显示，但strCompare要被选中
     * @return select的HTML代码
     */
    public static String getSelectFieldMultiple(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String[] strCompare, String strProperty, int showType) {
        if(thisObj != null && objectHandler != null) {
            return getSelectFieldMultiple(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty, showType);
        } else {
            return "";
        }
    }
    
    /**
     * 功能: 创建一个select多选，接受二维数组
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param aOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @param showType 展示类型  0:排斥strCompare；1:只显示strCompare；2:都显示，strCompare要被选中
     * @return select的HTML代码
     */
    public static String getSelectFieldMultiple(String strName, int nDisplaySize, String[][] aOptionValue, String[] strCompare, String strProperty, int showType) {
        if (showType != 0 && showType != 1 && showType != 2)
            return "";
        StringBuffer returnString = new StringBuffer();
        try {
            if (strProperty == null) {
            	strProperty = "";
            }
            if (strCompare == null) {
            	strCompare = new String[0];
            }
            returnString.append("<select multiple name='" + strName + "' ");
            returnString.append(strProperty);
            returnString.append(" >\r\n");

            if (aOptionValue != null) {
                for (int i = 0; i < aOptionValue.length; i++) {
                    String tempKey = aOptionValue[i][0];
                    String tempValue = aOptionValue[i][1];
                    /* 截去超长的字符 */
                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
                        tempValue = tempValue.substring(0, nDisplaySize);
                    }
                    switch (showType) {
                    case 0: {
                        if (RmStringHelper.arrayContainString(strCompare, tempKey)) {
                            break;
                        } else {
                            returnString.append("<option value='" + tempKey + "'>" + tempValue + "</option>\r\n");
                        }
                        break;
                    }
                    case 1: {
                        if (RmStringHelper.arrayContainString(strCompare, tempKey)) {
                            returnString.append("<option value='" + tempKey + "'>" + tempValue + "</option>\r\n");
                        } else {
                            break;
                        }
                        break;
                    }
                    case 2: {
                        returnString.append("<option value='" + tempKey + "' ");
                        if (RmStringHelper.arrayContainString(strCompare, tempKey)) {
                            returnString.append(" selected ");
                        }
                        returnString.append(">" + tempValue + "</option>\r\n");
                        break;
                    }
                    default:
                        break;
                    }
                }
            }
            returnString.append("</select> ");
            return returnString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 功能:  创建一个radio列表，多选，接受Map
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param mOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @return radio的HTML代码
     */
    public static String getRadioField(String strName, int nDisplaySize, Map mOptionValue, String strCompare, String strProperty) {
        if (mOptionValue != null) {
            return getRadioField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty);
        } else {
            return "";
        }
    }
    
    /**
     * 功能: 接受Object和IObject2Object，生成表单创建一个radio列表，多选
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param thisObj 要显示的option列表
     * @param objectHandler 把object变成String[][]的回调实现
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @return radio的HTML代码
     */
    public static String getRadioField(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String strCompare, String strProperty) {
        if(thisObj != null && objectHandler != null) {
            return getRadioField(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty);
        } else {
            return "";
        }
    }
    
    /**
     * 功能: 创建一个radio列表
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param aOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @return radio的HTML代码
     */
    public static String getRadioField(String strName, int nDisplaySize, String[][] aOptionValue, String strCompare, String strProperty) {
        StringBuffer returnStr = new StringBuffer();
        try {
            if (strProperty == null)
                strProperty = "";
            if (strCompare == null)
                strCompare = "";

            if (aOptionValue != null) {
                for (int i = 0; i < aOptionValue.length; i++) {
                    String tempKey = aOptionValue[i][0];
                    String tempValue = aOptionValue[i][1];
                    if (!"".equals(returnStr.toString())) {
                        returnStr.append("&nbsp;&nbsp;");
                    }
                    /* 截去超长的字符 */
                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
                        tempValue = tempValue.substring(0, nDisplaySize);
                    }
                    returnStr.append("<input type='radio' name='" + strName + "' value='" + tempKey + "' ");
                    if (tempKey.equals(strCompare)) {
                        returnStr.append(" checked ");
                    }
                    returnStr.append(strProperty);
                    returnStr.append("/>");
                    returnStr.append(tempValue);
                }
            }
            return returnStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 功能: 创建一个Checkbox列表，多选，接受Map
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param mOptionValue 要显示的option列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @return checkbox的HTML代码
     */
    public static String getCheckboxField(String strName, int nDisplaySize, Map mOptionValue, String[] strCompare, String strProperty) {
        if (mOptionValue != null) {
            return getCheckboxField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty);
        } else {
            return "";
        }
    }
    
    /**
     * 功能: 接受Object和IObject2Object，生成表单创建一个Checkbox列表，多选
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param thisObj 要显示的option列表
     * @param objectHandler 把object变成String[][]的回调实现
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @return checkbox的HTML代码
     */
    public static String getCheckboxField(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String[] strCompare, String strProperty) {
        if(thisObj != null && objectHandler != null) {
            return getCheckboxField(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty);
        } else {
            return "";
        }
    }
    

    /**
     * 功能: 创建一个Checkbox列表
     *
     * @param strName 名称
     * @param nDisplaySize 显示的长度，超过则截取
     * @param aOptionValue 要显示的radio列表
     * @param strCompare 要比较的字符串
     * @param strProperty 额外的属性,例如"onchange='change()'"
     * @return checkbox的HTML代码
     */
    public static String getCheckboxField(String strName, int nDisplaySize, String[][] aOptionValue, String[] strCompare, String strProperty) {
        StringBuffer returnStr = new StringBuffer();
        try {
            if (strProperty == null)
                strProperty = "";
            if (strCompare == null)
                strCompare = new String[] {};

            if (aOptionValue != null) {
                for (int i = 0; i < aOptionValue.length; i++) {
                    String tempKey = aOptionValue[i][0];
                    String tempValue = aOptionValue[i][1];
                    if (!"".equals(returnStr.toString())) {
                        returnStr.append("&nbsp;&nbsp;");
                    }
                    //截去超长的字符
                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
                        tempValue = tempValue.substring(0, nDisplaySize);
                    }
                    returnStr.append("<input type='checkbox' name='" + strName + "' value='" + tempKey + "' ");
                    if (RmStringHelper.arrayContainString(strCompare, tempKey)) {
                        returnStr.append(" checked ");
                    }
                    returnStr.append(strProperty);
                    returnStr.append("/>");
                    returnStr.append(tempValue);
                }
            }
            return returnStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
	/**
	 * 功能: 从request中获取session，如果没有session会自动创建新session
	 *
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(ServletRequest request) {
		return getSession(request, true);
	}
	
	/**
	 * 功能: 从request中获取session，根据create判断是否创建新session
	 *
	 * @param request
	 * @param create 当session为null，如果true则自动创建新session，如果false返回null
	 * @return
	 */
	public static HttpSession getSession(ServletRequest request, boolean create) {
		if(request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession(create);
			return session;
		} else {
			return null;
		}
	}
	
	/**
	 * 功能: 从request获取服务器根的http地址，如http://127.0.0.1:9999
	 *
	 * @param request
	 * @return
	 */
	public static String getRootHttpUrl(HttpServletRequest request) {
	    String baseProjectPath = request.getScheme() + "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort());
	    return baseProjectPath;
	}
	
	/**
	 * 功能: 从request获取war应用的http地址，如http://127.0.0.1:9999/rmweb
	 *
	 * @param request
	 * @return
	 */
	public static String getWarHttpUrl(HttpServletRequest request) {
	    String baseProjectPath = request.getScheme() + "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort()) + request.getContextPath();
	    return baseProjectPath;
	}
	
    /**
     * 根据总记录数重新计算PageVo，并用偏移值调整PageVo
     *
     * @param request
     * @param modifyCount
     * @param recordCount
     */
    public static RmPageVo transctPageVo(HttpServletRequest request, int recordCount) {
        int pageSize = RmBaseConfig.getSingleton().getDefaultPageSize();
        String requestValue = getValueFromRequest_attributeParameter(request, RM_PAGE_SIZE);
        try {
            if(requestValue != null && requestValue.trim().length() > 0) {
                pageSize = Integer.parseInt(requestValue);
            } else if(request.getParameter("limit") != null && request.getParameter("limit").trim().length() > 0) {
            	pageSize = Integer.parseInt(request.getParameter("limit").trim());
            }
        } catch (Exception e) {
            RmLogHelper.getLogger(RmJspHelper.class).error(e.toString());
        }
        RmPageVo pageVo = new RmPageVo(recordCount, pageSize);
        //第几页
        int currentPage = 1;
        String uri = request.getRequestURI();
        boolean rememberPage = false;
        try {
        	rememberPage = RmBaseConfig.getSingleton().isRememberPage();
		} catch (Throwable e) {
			//ignore
		}
        try {
            String requestCurrentPage = getValueFromRequest_attributeParameter(request, RM_CURRENT_PAGE);
            if(requestCurrentPage != null && requestCurrentPage.trim().length() > 0) {
                currentPage = Integer.parseInt(requestCurrentPage);
                if(rememberPage) {
                	RmJspHelper.getSession(request).setAttribute("RmGlobalCurrentPage", new String[]{uri, requestCurrentPage});
                }
            } else if(request.getParameter("start") != null && request.getParameter("start").trim().length() > 0){
            	int start = Integer.parseInt(request.getParameter("start").trim());
            	currentPage= (start + 1) / pageSize + 1;
            } else if(rememberPage && RmJspHelper.getSession(request).getAttribute("RmGlobalCurrentPage") != null) {
                String[] aUrlQc = (String[])RmJspHelper.getSession(request).getAttribute("RmGlobalCurrentPage");
                if(uri.equals(aUrlQc[0])) {
                    currentPage = Integer.parseInt(aUrlQc[1]);
                }
            }
        } catch (Exception e) {
        	RmLogHelper.getLogger(RmJspHelper.class).warn(e.toString());
        }
        pageVo.setCurrentPage(currentPage);
        request.setAttribute(RM_PAGE_VO, pageVo);
        return pageVo;
    }
    
    /**
     * 功能: 获取RmPageVo
     *
     * @param request
     * @return
     */
    public static RmPageVo getPageVo(HttpServletRequest request) {
        if(request.getAttribute(RM_PAGE_VO) != null) {
            return (RmPageVo)request.getAttribute(RM_PAGE_VO);
        } else {
            return new RmPageVo(0,1);
        }
    }
    
    /**
     * 从request中获得排序关键字
     * @param request
     * @return
     */
    public static String getOrderStr(HttpServletRequest request) {
        StringBuilder orderStr = null;
        String requestValue = RmJspHelper.getValueFromRequest_attributeParameter(request, RM_ORDER_STR);
        if(requestValue != null && requestValue.trim().length() > 0) {
            orderStr = new StringBuilder(requestValue);
        } else if(request.getParameter("sort") != null && request.getParameter("sort").trim().length() > 0){
        	orderStr = new StringBuilder(request.getParameter("sort").trim());
        	orderStr.append(request.getParameter("dir") != null ? " " + request.getParameter("dir").trim() : "");
        }
        return orderStr == null ? null : orderStr.toString();
    }
    
    /**
     * 从 request 获取可能为多个值的数组表示, 所有的""会被忽略
     *
     * @param request
     * @param inputName
     * @return
     */
    public static String[] getArrayFromRequest(HttpServletRequest request, String inputName) {
    	List<String> result = new ArrayList<String>();
        String[] reqArray = null;
        String tempStr = request.getParameter(inputName);
        if(tempStr != null && tempStr.length() > 0) {
        	reqArray = tempStr.split(",");
        }
        if(reqArray == null) {
        	reqArray = new String[0];
        }
        for(String str : reqArray) {
        	if(str.length() > 0) {
        		result.add(str);
        	}
        }
        return result.toArray(new String[0]);
    }
    
    /**
     * 从 request 获取可能为多个值的数组表示, 所有的""会被忽略
     *
     * @param request
     * @param inputName
     * @return
     */
    public static Long[] getLongArrayFromRequest(HttpServletRequest request, String inputName) {
    	String[] strResult = getArrayFromRequest(request, inputName);
    	Long[] result = new Long[strResult.length];
    	for (int i = 0; i < strResult.length; i++) {
    		result[i] = new Long(strResult[i]);
		}
    	return result;
    }
    
    /**
     * 功能: 从 request 获取可能为多个值的数组表示，""也是有效的单个值，null会返回0数组
     * 
     * @param request
     * @param inputName
     * @return
     */
    public static String[] getArrayWithEmptyFromRequest(HttpServletRequest request, String inputName) {
        String[] returnStrArray = null;
        String tempStr = request.getParameter(inputName);
        if(tempStr != null && tempStr.length() > 0) {
            returnStrArray = tempStr.split(",", -1);
        }
        if(returnStrArray == null) {
            returnStrArray = new String[0];
        }
        return returnStrArray;
    }
    
    /**
     * 功能: 返回alert代码
     *
     * @param str
     * @return
     */
    public static String getJavaScriptAlertCode(String str) {
        StringBuilder jsStr = new StringBuilder("\n<script type=\"text/javascript\">");
        jsStr.append("\n	alert('");
        jsStr.append(RmStringHelper.replaceStringToScript(str));
        jsStr.append("');");
        jsStr.append("\n</script>");
        return jsStr.toString();
    }
    
    /**
     * 回写表单
     *
     * @param mRequest
     * @param ignoreName 定义哪些key值的input不回写
     * @return
     */
    public static String setupFormElementAuthorize(Map mAuthorize) {
        if(mAuthorize == null) {
            return "";
        }
        StringBuffer rtValue = new StringBuffer();
        rtValue.append("	function rmSetupAllAuthorize() {\n");
        rtValue.append("		var aRmFormAuthorize = new Array();\n");
        rtValue.append("		var mRmFormAuthorize = new Object();\n");
        for(Iterator itMAuthorize = mAuthorize.keySet().iterator(); itMAuthorize.hasNext(); ) {
            String tempKey = (String) itMAuthorize.next();
            String tempValue  = (mAuthorize.get(tempKey) == null) ? "" : String.valueOf(mAuthorize.get(tempKey));
            rtValue.append("		rmSetupAuthorize(\"" + tempKey + "\",\"" + tempValue + "\");\n");
        }
        rtValue.append("	}\n");
        rtValue.append("	rmSetupAllAuthorize();\n");
        return rtValue.toString();
    }

    public static void saveOrderStr(String orderStr, HttpServletRequest request) {
        request.setAttribute(RM_ORDER_STR, orderStr);
        
    }
    
    public static void setProfile(HttpServletRequest request, HttpServletResponse response, String key, String value) {
    	setProfile(request, response, key, value, RmBaseConfig.getSingleton().getDefaultCookieAge());
    }
    
    public static void setProfile(HttpServletRequest request, HttpServletResponse response, String key, String value, int cookieAge) {
        request.setAttribute(key, value);
        
        //TODO 导致sessionid失效 IE8
    	if(!"1".equals(request.getAttribute("RM_RESPONSE_WRITE_BACK"))) {
            //回写原来的cookie
        	Cookie[] aCookie = request.getCookies();
        	if(aCookie != null) {
        		for(Cookie c : aCookie) {
        			if(!key.equals(c.getName())) {
        				response.addCookie(c);
        			}
        		}
        	}
        	request.setAttribute("RM_RESPONSE_WRITE_BACK", "1");
    	}
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(cookieAge);
        try {
            response.addCookie(cookie);
		} catch (Exception e) {
			//不处理cookie的异常
		}
    }
    
    public static String getProfile(HttpServletRequest request, String key) {
    	if(request.getAttribute(key) != null) {
    		return request.getAttribute(key).toString();
    	} else {
    		Cookie[] aCookie = request.getCookies();
    		if(aCookie != null) {
        		for(Cookie c : aCookie) {
        			if(key.equals(c.getName()) && c.getValue() != null && c.getValue().length() > 0) {
        				return c.getValue();
        			}
        		}
    		}

    	}
        return null;
    }
    
    public static void clearProfile(HttpServletRequest request, HttpServletResponse response, String key) {
    	request.removeAttribute(key);
    	if(!"1".equals(request.getAttribute("RM_RESPONSE_WRITE_BACK"))) {
            //回写原来的cookie
        	Cookie[] aCookie = request.getCookies();
        	if(aCookie != null) {
        		for(Cookie c : aCookie) {
        			if(!key.equals(c.getName())) {
        				response.addCookie(c);
        			}
        		}
        	}
        	request.setAttribute("RM_RESPONSE_WRITE_BACK", "1");
    	}


    	Cookie cookie = new Cookie(key, "");
    	cookie.setPath(request.getContextPath());
    	cookie.setMaxAge(0);
    	response.addCookie(cookie);
    }
    
    /**
	 * 
	 * 功能: 在跳往指定页面前alert一个信息
	 *
	 * @param response
	 * @param alertStr alert的信息
	 * @param forwardPath 想要跳往的页面url
	 */
	public static void goUrlWithAlert(HttpServletRequest request, HttpServletResponse response, String alertStr, String forwardPath) {
	    try {
			response.sendRedirect(request.getContextPath() + "/jsp/util/rmAlertForward.jsp?rm_alertStr=" + RmStringHelper.encodeUrl(alertStr) + "&rm_targetForwardPath=" + RmStringHelper.encodeUrl(forwardPath));
		} catch (IOException e) {
			throw new RmRuntimeException("", e);
		}
	}
}
/*
 * 閸掓稑缂撻弮銉︽埂 2005-5-29
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
 * @author   鐢喖濮�鐐靛箛娑擄拷绨洪張澶婂彠Jsp妞ょ敻娼伴惃鍕樀閻烇拷
 */
public class RmJspHelper implements ICoreConstants {

    /**
     * 娴犲氦銆冮崡鏇氳厬閼惧嘲褰囬崐纭风礉娴犲丢SO鏉烆剙鍩孯mConfig.defaultEncode()姒涙顓荤紓鏍垳閿涘苯顪嗛弸婊勬Цnull閸掓瑨绻戦崶锟�
     * 
     * @param request HttpServletRequest
     * @param name 闂囷拷顪呴懢宄板絿閻ㄥ埇nput閸氬秴鐡�
     * @return 鐞涖劌宕熸稉顓犳畱鐎圭偤妾崘鍛啇
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
     * 閸旂喕鍏� 閸忓牅绮爎equest.getAttribute閿涘本鐥呴張澶屾畱鐠囨繂鍟�禒宸杄quest.getParameter閸欙拷
     *
     * @param request
     * @param key
     * @return
     */
    public static String getValueFromRequest_attributeParameter(HttpServletRequest request, String key) {
        String value = null;
        if(request.getAttribute(key) != null) {  //婵″倹鐏塺equest.getAttribute娑擃厽婀侀敍灞芥皑娑撳秴褰噐equest.getParameter
            value = request.getAttribute(key).toString();
        } else {
        	//婵″倹鐏夐張澶婎檵娑撶尛ey閸婄》绱濋崣鏍ㄦ付閸氬簼绔存稉锟�        	if(request.getParameterValues(key) != null && request.getParameterValues(key).length > 1) {
        		value = request.getParameterValues(key)[request.getParameterValues(key).length-1];
        	} else {
                value = request.getParameter(key);  //娴犲窎equest閻ㄥ埦arameter閼惧嘲绶�
        	}

        }
        return value;
    }

    /**
     * 娴犲氦銆冮崡鏇氳厬閼惧嘲褰囬弫瀛樻殶閸婄》绱濇俊鍌涚亯閺勭棴ull閹达拷"null"閿涘苯鍨潻鍥ㄦ姢娑擄拷
     * 
     * @param request-->HttpServletRequest
     * @param name-->闂囷拷顪呴懢宄板絿閻ㄥ埇nput閸氬秴鐡�
     * @return 鐞涖劌宕熸稉顓犳畱鐎圭偤妾崘鍛啇
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
     * 閸旂喕鍏� 婵″倹鐏夐張澶夌娑擃亜寮弫浼村櫢婢跺秵褰佹禍銈忕礉閸欐牗娓堕崥搴濈娑擃亷绱滼2EE姒涙顓婚崣鏍儑娑擄拷閲�
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
     * 鐏忓棙鐓囩�妤冾儊閺囨寧宕叉稉鐑樼厙鐎涙顑佹稉锟�娓氬顪嗙亸锟絓n閺囨寧宕叉稉锟�br>
     * 
     * @param pStr 鐞氼偄顦甸悶鍡欐畱鐎涙顑佹稉锟�     * @param pC 鐞氼偅娴涢幑銏㈡畱鐎涙顑�
     * @param rep 閺囨寧宕查崥搴ｆ畱鐎涙顑佹稉锟�     * @return 鐎涙顑佹稉锟�     */
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
     * 鐏忓摖tring鏉堟挸鍤径鍕倞, 閻劋绨惄瀛樺复閺勫墽銇氶崷鈫梥p妞ょ敻娼�
     * 
     * @param pStr-->鐞氼偄顦甸悶鍡欐畱鐎涙顑佹稉锟�     * @return 鐎涙顑佹稉锟�     */
    public static String filterJspString(String pStr) throws Exception {
        pStr = replaceAllChar2String(pStr, '<', "&lt;");
        pStr = replaceAllChar2String(pStr, '>', "&gt;");
        pStr = replaceAllChar2String(pStr, '\n', "<br>");
        pStr = replaceAllChar2String(pStr, ' ', "&nbsp;");
        return pStr;
    }

    /**
     * 鐏忓摖tring鏉堟挸鍤径鍕倞, 閻劋绨惄瀛樺复閺勫墽銇氶崷鈫梥p妞ょ敻娼版稉顓犳畱textarea娑擄拷
     * 
     * @param pStr-->鐞氼偄顦甸悶鍡欐畱鐎涙顑佹稉锟�     * @return 鐎涙顑佹稉锟�     */
    public static String filterJspString4Textarea(String pStr) throws Exception {
        pStr = replaceAllChar2String(pStr, '<', "&lt;");
        pStr = replaceAllChar2String(pStr, '>', "&gt;");
        pStr = replaceAllChar2String(pStr, ' ', "&nbsp;");
        return pStr;
    }

    /**
     * 瀵懓鍤穱鈩冧紖閿涘瞼鈥樼拋銈呮倵鐠哄疇娴嗛崚棰佺瑐娑擄拷銆�
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
     * 瀵懓鍤穱鈩冧紖閿涘瞼鈥樼拋銈呮倵鐠哄疇娴嗛崚鐧瞣AfterStr閹稿洤鐣炬い鐢告桨楠炴湹绗栫憰鍡欐磰閻栧爼銆夐棃锟�     * 
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
     * 瀵懓鍤穱鈩冧紖閿涘瞼鈥樼拋銈呮倵鐠哄疇娴嗛崚鐧瞣AfterStr閹稿洤鐣炬い鐢告桨
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
     * 瀵懓鍤穱鈩冧紖閿涘瞼鈥樼拋銈呮倵閸忔娊妫存い鐢告桨
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
     * 鐎圭偟骞囨禒宥產p閸掗绨╂担宥嗘殶缂佸嫮娈戞潪顒佸床
     *
     * @return
     */
    private static IObject2Object map2StringArray() {
        return new IObject2Object() {
            /**
             * 鐎圭偟骞囨禒宥產p閸掗绨╂担宥嗘殶缂佸嫮娈戞潪顒佸床
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
     * 閸掓稑缂撴稉锟介嚋select,閹恒儱褰圡ap
     * 
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param mOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param hasEmptyValue 閺勵垰鎯侀張锟�缁屽搫鐡х粭锔胯 
     * @return select閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getSelectField(String strName, int nDisplaySize, Map mOptionValue, String strCompare, String strProperty, boolean hasEmptyValue) {
    	return getSelectField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty, hasEmptyValue, null);
    }
    
    /**
     * 閸掓稑缂撴稉锟介嚋select,閹恒儱褰圡ap
     * 
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param mOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param hasEmptyValue 閺勵垰鎯侀張锟�缁屽搫鐡х粭锔胯 
     * @param pleaseSelectStr hasEmptyValue==true閺冭绱濋幐鍥х暰""鐎电懓绨查惃鍕▔缁�搫鎮曠粔锟�     * @return select閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getSelectField(String strName, int nDisplaySize, Map mOptionValue, String strCompare, String strProperty, boolean hasEmptyValue, String pleaseSelectStr) {
        if (mOptionValue != null) {
            return getSelectField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty, hasEmptyValue, pleaseSelectStr);
        } else {
            return "";
        }
    }
    
    /**
     * 閸掓稑缂撴稉锟介嚋select閿涘本甯撮崣妗筨ject閸滃瓥Object2Object
     * 
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param thisObj 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param objectHandler 閹跺bject閸欐ɑ鍨歋tring[][]閻ㄥ嫬娲栫拫鍐ㄧ杽閻滐拷
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param hasEmptyValue 閺勵垰鎯侀張锟�缁屽搫鐡х粭锔胯
     * @param pleaseSelectStr hasEmptyValue==true閺冭绱濋幐鍥х暰""鐎电懓绨查惃鍕▔缁�搫鎮曠粔锟�     * @return select閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getSelectField(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String strCompare, String strProperty, boolean hasEmptyValue, String pleaseSelectStr) {
    	return getSelectField(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty, hasEmptyValue, pleaseSelectStr);
    }
    
    /**
     * 閸掓稑缂撴稉锟介嚋select閿涘本甯撮崣妞剧癌缂佸瓨鏆熺紒锟�     * 
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param aOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param hasEmptyValue 閺勵垰鎯侀張锟�缁屽搫鐡х粭锔胯
     * @param pleaseSelectStr hasEmptyValue==true閺冭绱濋幐鍥х暰""鐎电懓绨查惃鍕▔缁�搫鎮曠粔锟�     * @return select閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getSelectField(String strName, int nDisplaySize, String[][] aOptionValue, String strCompare, String strProperty, boolean hasEmptyValue, String pleaseSelectStr) {
        StringBuffer returnString = new StringBuffer();
        try {
            if (strProperty == null)
                strProperty = "";
            if (strCompare == null)
                strCompare = "";
            returnString.append("<select name='" + strName + "' ");
            returnString.append("class=" + "\"" + "small m-wrap" + "\"" + " " + "tabindex=" + "\"" + 1 + "\"");
            returnString.append(strProperty);
            returnString.append(" >\r\n");
            if(hasEmptyValue) {
                returnString.append("<option value='' ");
                if (strCompare.length() == 0) {
                    returnString.append(" selected ");
                }
                returnString.append(">");
                if(pleaseSelectStr == null) {
                	returnString.append("--鐠囩兘锟介幏锟�");
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
                    //閹搭亜骞撶搾鍛存毐閻ㄥ嫬鐡х粭锟�                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
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
     * 閸掓稑缂撴稉锟介嚋select婢舵岸锟介敍灞惧复閸欐〉ap
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param mOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param showType 鐏炴洜銇氱猾璇茬�  0閿涘本甯撻弬銉幢1閿涘本妯夌粈鐚寸幢2閿涘矂鍏橀弰鍓с仛閿涘奔绲緎trCompare鐟曚浇顤嗛柅澶夎厬
     * @return select閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getSelectFieldMultiple(String strName, int nDisplaySize, Map mOptionValue, String[] strCompare, String strProperty, int showType) {
        if (mOptionValue != null) {
            return getSelectFieldMultiple(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty, showType);
        } else {
            return "";
        }
    }
    
    /**
     * 閸掓稑缂撴稉锟介嚋select婢舵岸锟介敍灞惧复閸欐」bject閸滃瓥Object2Object
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param thisObj 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param objectHandler 閹跺bject閸欐ɑ鍨歋tring[][]閻ㄥ嫬娲栫拫鍐ㄧ杽閻滐拷
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param showType 鐏炴洜銇氱猾璇茬�  0閿涘本甯撻弬銉幢1閿涘本妯夌粈鐚寸幢2閿涘矂鍏橀弰鍓с仛閿涘奔绲緎trCompare鐟曚浇顤嗛柅澶夎厬
     * @return select閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getSelectFieldMultiple(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String[] strCompare, String strProperty, int showType) {
        if(thisObj != null && objectHandler != null) {
            return getSelectFieldMultiple(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty, showType);
        } else {
            return "";
        }
    }
    
    /**
     * 閸旂喕鍏� 閸掓稑缂撴稉锟介嚋select婢舵岸锟介敍灞惧复閸欐ぞ绨╃紒瀛樻殶缂侊拷
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param aOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @param showType 鐏炴洜銇氱猾璇茬�  0:閹烘帗鏋約trCompare閿涳拷:閸欘亝妯夌粈绨妕rCompare閿涳拷:闁姤妯夌粈鐚寸礉strCompare鐟曚浇顤嗛柅澶夎厬
     * @return select閻ㄥ嚗TML娴狅絿鐖�
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
                    /* 閹搭亜骞撶搾鍛存毐閻ㄥ嫬鐡х粭锟�/
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
     * 閸旂喕鍏�  閸掓稑缂撴稉锟介嚋radio閸掓銆冮敍灞筋檵闁绱濋幒銉ュ綀Map
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param mOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @return radio閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getRadioField(String strName, int nDisplaySize, Map mOptionValue, String strCompare, String strProperty) {
        if (mOptionValue != null) {
            return getRadioField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty);
        } else {
            return "";
        }
    }
    
    /**
     * 閸旂喕鍏� 閹恒儱褰圤bject閸滃瓥Object2Object閿涘瞼鏁撻幋鎰�閸楁洖鍨卞杞扮娑撶尯adio閸掓銆冮敍灞筋檵闁拷
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param thisObj 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param objectHandler 閹跺bject閸欐ɑ鍨歋tring[][]閻ㄥ嫬娲栫拫鍐ㄧ杽閻滐拷
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @return radio閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getRadioField(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String strCompare, String strProperty) {
        if(thisObj != null && objectHandler != null) {
            return getRadioField(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty);
        } else {
            return "";
        }
    }
    
    /**
     * 閸旂喕鍏� 閸掓稑缂撴稉锟介嚋radio閸掓銆�
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param aOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @return radio閻ㄥ嚗TML娴狅絿鐖�
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
                    /* 閹搭亜骞撶搾鍛存毐閻ㄥ嫬鐡х粭锟�/
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
     * 閸旂喕鍏� 閸掓稑缂撴稉锟介嚋Checkbox閸掓銆冮敍灞筋檵闁绱濋幒銉ュ綀Map
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param mOptionValue 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @return checkbox閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getCheckboxField(String strName, int nDisplaySize, Map mOptionValue, String[] strCompare, String strProperty) {
        if (mOptionValue != null) {
            return getCheckboxField(strName, nDisplaySize, mOptionValue,map2StringArray(), strCompare, strProperty);
        } else {
            return "";
        }
    }
    
    /**
     * 閸旂喕鍏� 閹恒儱褰圤bject閸滃瓥Object2Object閿涘瞼鏁撻幋鎰�閸楁洖鍨卞杞扮娑撶嫝heckbox閸掓銆冮敍灞筋檵闁拷
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param thisObj 鐟曚焦妯夌粈铏规畱option閸掓銆�
     * @param objectHandler 閹跺bject閸欐ɑ鍨歋tring[][]閻ㄥ嫬娲栫拫鍐ㄧ杽閻滐拷
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @return checkbox閻ㄥ嚗TML娴狅絿鐖�
     */
    public static String getCheckboxField(String strName, int nDisplaySize, Object thisObj, IObject2Object objectHandler, String[] strCompare, String strProperty) {
        if(thisObj != null && objectHandler != null) {
            return getCheckboxField(strName,nDisplaySize,(String[][])objectHandler.object2Object(thisObj),strCompare,strProperty);
        } else {
            return "";
        }
    }
    

    /**
     * 閸旂喕鍏� 閸掓稑缂撴稉锟介嚋Checkbox閸掓銆�
     *
     * @param strName 閸氬秶袨
     * @param nDisplaySize 閺勫墽銇氶惃鍕毐鎼达讣绱濈搾鍛扮箖閸掓瑦鍩呴崣锟�     * @param aOptionValue 鐟曚焦妯夌粈铏规畱radio閸掓銆�
     * @param strCompare 鐟曚焦鐦潏鍐畱鐎涙顑佹稉锟�     * @param strProperty 妫版繂顧囬惃鍕潣閹拷娓氬顪�onchange='change()'"
     * @return checkbox閻ㄥ嚗TML娴狅絿鐖�
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
                    //閹搭亜骞撶搾鍛存毐閻ㄥ嫬鐡х粭锟�                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
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
	 * 閸旂喕鍏� 娴犲窎equest娑擃叀骞忛崣鏉漞ssion閿涘苯顪嗛弸婊勭梾閺堝』ession娴兼俺鍤滈崝銊ュ灡瀵ょ儤鏌妔ession
	 *
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(ServletRequest request) {
		return getSession(request, true);
	}
	
	/**
	 * 閸旂喕鍏� 娴犲窎equest娑擃叀骞忛崣鏉漞ssion閿涘本鐗撮幑鐢eate閸掋倖鏌囬弰顖氭儊閸掓稑缂撻弬鐨奺ssion
	 *
	 * @param request
	 * @param create 瑜版悞ession娑撶皠ull閿涘苯顪嗛弸娓ue閸掓瑨鍤滈崝銊ュ灡瀵ょ儤鏌妔ession閿涘苯顪嗛弸娓嘺lse鏉╂柨娲杗ull
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
	 * 閸旂喕鍏� 娴犲窎equest閼惧嘲褰囬張宥呭閸ｃ劍鐗撮惃鍒猼tp閸︽澘娼冮敍灞筋渾http://127.0.0.1:9999
	 *
	 * @param request
	 * @return
	 */
	public static String getRootHttpUrl(HttpServletRequest request) {
	    String baseProjectPath = request.getScheme() + "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort());
	    return baseProjectPath;
	}
	
	/**
	 * 閸旂喕鍏� 娴犲窎equest閼惧嘲褰噖ar鎼存梻鏁ら惃鍒猼tp閸︽澘娼冮敍灞筋渾http://127.0.0.1:9999/rmweb
	 *
	 * @param request
	 * @return
	 */
	public static String getWarHttpUrl(HttpServletRequest request) {
	    String baseProjectPath = request.getScheme() + "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort()) + request.getContextPath();
	    return baseProjectPath;
	}
	
    /**
     * 閺嶈宓侀幀鏄忣唶瑜版洘鏆熼柌宥嗘煀鐠侊紕鐣籔ageVo閿涘苯鑻熼悽銊ヤ焊缁夎锟界拫鍐╂殻PageVo
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
        //缁楊剙鍤戞い锟�        int currentPage = 1;
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
     * 閸旂喕鍏� 閼惧嘲褰嘡mPageVo
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
     * 娴犲窎equest娑擃叀骞忓妤佸笓鎼村繐鍙ч柨顔肩摟
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
     * 娴狅拷request 閼惧嘲褰囬崣顖濆厴娑撳搫顧嬫稉顏勶拷閻ㄥ嫭鏆熺紒鍕�缁�拷 閹碉拷婀侀惃锟�娴兼俺顤嗚箛鐣屾殣
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
     * 娴狅拷request 閼惧嘲褰囬崣顖濆厴娑撳搫顧嬫稉顏勶拷閻ㄥ嫭鏆熺紒鍕�缁�拷 閹碉拷婀侀惃锟�娴兼俺顤嗚箛鐣屾殣
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
     * 閸旂喕鍏� 娴狅拷request 閼惧嘲褰囬崣顖濆厴娑撳搫顧嬫稉顏勶拷閻ㄥ嫭鏆熺紒鍕�缁�尨绱�"娑旂喐妲搁張澶嬫櫏閻ㄥ嫬宕熸稉顏勶拷閿涘ull娴兼俺绻戦崶锟介弫鎵矋
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
     * 閸旂喕鍏� 鏉╂柨娲朼lert娴狅絿鐖�
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
     * 閸ョ偛鍟撶悰銊ュ礋
     *
     * @param mRequest
     * @param ignoreName 鐎规矮绠熼崫顏冪昂key閸婅偐娈慽nput娑撳秴娲栭崘锟�     * @return
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
        
        //TODO 鐎佃壈鍤essionid婢惰鲸鏅�IE8
    	if(!"1".equals(request.getAttribute("RM_RESPONSE_WRITE_BACK"))) {
            //閸ョ偛鍟撻崢鐔告降閻ㄥ垻ookie
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
			//娑撳秴顦甸悶鍝籵okie閻ㄥ嫬绱撶敮锟�		}
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
            //閸ョ偛鍟撻崢鐔告降閻ㄥ垻ookie
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
	 * 閸旂喕鍏� 閸︺劏鐑﹀锟藉瘹鐎规岸銆夐棃銏犲alert娑擄拷閲滄穱鈩冧紖
	 *
	 * @param response
	 * @param alertStr alert閻ㄥ嫪淇婇幁锟�	 * @param forwardPath 閹疇顪呯捄鍐茬窔閻ㄥ嫰銆夐棃顣況l
	 */
	public static void goUrlWithAlert(HttpServletRequest request, HttpServletResponse response, String alertStr, String forwardPath) {
	    try {
			response.sendRedirect(request.getContextPath() + "/jsp/util/rmAlertForward.jsp?rm_alertStr=" + RmStringHelper.encodeUrl(alertStr) + "&rm_targetForwardPath=" + RmStringHelper.encodeUrl(forwardPath));
		} catch (IOException e) {
			throw new RmRuntimeException("", e);
		}
	}
}
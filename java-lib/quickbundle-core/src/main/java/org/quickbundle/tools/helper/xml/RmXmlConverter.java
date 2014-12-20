package org.quickbundle.tools.helper.xml;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.quickbundle.itf.ITransctVoField;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.quickbundle.util.RmSequenceMap;
import org.springframework.beans.BeanWrapper;

public class RmXmlConverter {

    /**
     * 功能: 
     *
     * @param obj
     * @param element 可能是Document或Element
     * @return
     */
    /**
     * 把obj的内容转化为xml格式
     * 
     * @param obj
     * @return xml格式
     */
    public static Document getDocByObj(Object obj) {
        Document docObj = org.dom4j.DocumentHelper.createDocument();
        object2Xml(obj, docObj);
        return docObj;
    }
    
    /**
     * 功能: 注意Map的key值必须是String
     *
     * @param obj
     * @param element
     */
    public static void object2Xml(Object obj, Object element) {
        Element rootElement = null;
        if(element == null) {
            Document doc = org.dom4j.DocumentHelper.createDocument();
            rootElement = doc.addElement("rmdatas");
        } else if(element instanceof Document) {
            rootElement = ((Document)element).addElement("rmdatas");
        } else if(element instanceof Element) {
            rootElement = (Element) element;
        }

        if (obj == null) {
            //如果对象为空，什么也不作
        } else if (obj instanceof String) {  //字符串类型
            rootElement.setText(obj.toString());
        } else if (obj instanceof Integer || obj instanceof BigDecimal || obj instanceof Timestamp) {  //其它等同于字符串的类型
            rootElement.setText(obj.toString());
            rootElement.addAttribute("type", obj.getClass().getName());                
        } else if (obj instanceof List) {
            List lResult = (List) obj;
            rootElement.addAttribute("type", obj.getClass().getName());
            for (Iterator itLResult = lResult.iterator(); itLResult.hasNext();) {
                Object vo = itLResult.next();
                Element childEle = rootElement.addElement("rmdata");
                object2Xml(vo, childEle);
            }
        } else if (obj instanceof Map) {
            Map mObj = (Map) obj;
            rootElement.addAttribute("type", obj.getClass().getName());
            for (Iterator itMObj = mObj.keySet().iterator(); itMObj.hasNext();) {
                String key = (String) itMObj.next();
                Object value = mObj.get(key);
                Element childEle = rootElement.addElement(key);
                object2Xml(value, childEle);
            }
        } else if (obj instanceof Serializable) {
            final Element thisElement = rootElement;
            thisElement.addAttribute("type", obj.getClass().getName());
            RmVoHelper.accessVo(obj, new ITransctVoField() {
                public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                    if (!pd.getName().equals("class")) {
                        Element columnElement = thisElement.addElement(pd.getName());
                        object2Xml(bw.getPropertyValue(pd.getName()), columnElement);
                         return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    public static Object xml2Object(Element ele) {
        if(ele == null ) {
            return null;
        }
        Object obj = null;
        String javaType = ele.valueOf("./@type");
        if(javaType.length() == 0) {
            javaType = "java.lang.String";
        }
        try {
            try {
                obj = Class.forName(javaType).newInstance();
            } catch (Exception e) {

            }
            if (obj instanceof String) {  //基本类型
                obj = ele.getText();
            } else if(javaType.endsWith(Integer.class.getName())) {
                obj = Integer.valueOf(ele.getText());
            } else if(javaType.endsWith(Timestamp.class.getName())) {
                obj = RmDateHelper.getTimestamp(ele.getText());
            } else if(javaType.endsWith(BigDecimal.class.getName())) {
                obj = new BigDecimal(ele.getText());
            }  else if(obj instanceof List) {
                List lObj = (List) obj;
                for (Iterator itDatas = ele.selectNodes("rmdata").iterator(); itDatas.hasNext();) {
                    Object littleObj = xml2Object((Element) itDatas.next());
                    lObj.add(littleObj);
                }  
            } else if(obj instanceof Map) {
                Map mObj = (Map) obj;
                for (Iterator itDatas = ele.selectNodes("node()").iterator(); itDatas.hasNext();) {
                    Node tmpObj = (Node)itDatas.next();
                    if (tmpObj instanceof Element) {
                        Element thisEle = (Element) tmpObj;
                        mObj.put(thisEle.getName(), xml2Object(thisEle));
                    }
                } 
            } else if(obj instanceof Serializable) {
                Map mField = new RmSequenceMap();
                for (Iterator itField = ele.selectNodes("node()").iterator(); itField.hasNext();) {
                    Node xmlField = (Node) itField.next();
                    if(xmlField.selectNodes("node()[@type]").size() > 0 || xmlField.valueOf("@type").trim().length() > 0) {
                        RmVoHelper.setVoFieldValue(obj, xmlField.getName(), xml2Object((Element)xmlField));
                    } else {
                        String fieldKey = xmlField.valueOf("name()");
                        String fieldValue = xmlField.getText();
                        mField.put(fieldKey, fieldValue);
                    }
                }
                RmPopulateHelper.populate(obj, mField);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("javaType=" + javaType);
        }

        return obj;
    }
}
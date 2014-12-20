/*
 * 系统名称:基于冉闵开发工具 --> iso9000
 * 
 * 文件名称: org.quickbundle.tools.support.accessory --> InitUploadInfo.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-9-11 10:21:39 创建1.0.0版 (wangqingmin)
 * 
 */
package org.quickbundle.third.fileupload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.path.RmPathHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class InitUploadInfo {
//    private static Document resources =  null;
    private static HashMap hAppInfo;
    private static String webappName;
    private static String warRelUrl;
    /**
     * 
     * 功能: 加载上传文件的配置文件
     *
     * @return
     */
    private synchronized static Document readProperties(){
        try {
            String uploadFileConfUrl = RmPathHelper.getWebInfDir().getPath() + RmUploadHelper.SYSTEM_FILE_SEPARATOR + "conf" + RmUploadHelper.SYSTEM_FILE_SEPARATOR + "uploadFileConf.xml";        
            Document resources = RmXmlHelper.parse(uploadFileConfUrl);
            if(resources==null){
                throw new MissingResourceException("Cannot find the uploadFileConf.xml file", "InitUploadInfo", "Resource File");
            }
            return resources; 
        }catch(MissingResourceException mre) {
            mre.printStackTrace();
            throw new Error(mre.getMessage());
        }catch (IOException ie) {
            ie.printStackTrace();
            throw new Error(ie.getMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }
	}
    
    /**
     * 
     * 功能: 分解上传配置文件的信息
     *
     *
     */
    private synchronized static void initAppInfo(){
        Document resources = readProperties();
        List lApp = resources.selectNodes("/file-config/webappWarName");
        if(lApp==null){
            webappName = "";
        }else{
            for(Iterator itLApp = lApp.iterator(); itLApp.hasNext(); ) {
                Node node = (Node) itLApp.next();
                webappName = node.valueOf("@name");
                warRelUrl = node.valueOf("@url");
            }
        }
        if(lApp!=null)lApp.clear();
        lApp = resources.selectNodes("/file-config/webapps");
        if(lApp==null){
            hAppInfo = new HashMap();
        }else{
            hAppInfo = new HashMap();
            for(Iterator itLApp = lApp.iterator(); itLApp.hasNext(); ) {
                Node node = (Node) itLApp.next();
                String warName = node.valueOf("@name");
                List lUnit = node.detach().selectNodes("unit");
                HashMap hUnit = new HashMap();
                for(Iterator itLUnit = lUnit.iterator(); itLUnit.hasNext(); ) {
                    Node UnitNode = (Node) itLUnit.next();
                    hUnit.put(UnitNode.valueOf("@name"),UnitNode.valueOf("@value"));
                }
                hAppInfo.put(warName,hUnit);
            }
        }
    }
    
    /**
     * 
     * 功能: 获得某个应用下上传文件目录列表
     *
     * @param warName
     * @return
     */
    public static HashMap getWarUnit(String warName){
        if(hAppInfo==null){
            initAppInfo();
        }
        Object obj = hAppInfo.get(warName);
        if(obj==null){
            return new HashMap();
        }else{
            return (HashMap)obj;
        }
    }
    
    /**
     * 
     * 功能: 获得上传文件应用名称
     *
     * @return
     */
    public static String getWebappName(){
        if(hAppInfo==null){
            initAppInfo();
        }
        return webappName;
    }
    
    /**
     * 
     * 功能: 
     *
     * @param warName
     * @param unitName
     * @return
     */
    public static String getFileRelUrl(String warName,String unitName){
        if(hAppInfo==null){
            initAppInfo();
        }
        Object obj = hAppInfo.get(warName);
        if(obj==null){
            return "";
        }else{
            obj = ((HashMap)obj).get(unitName);
            if(obj==null){
                return "";
            }else{
                return (warRelUrl + RmUploadHelper.SYSTEM_FILE_SEPARATOR + obj.toString());
            }
        }
    }
    
    /**
     * 
     * 功能: 获得某个应用下某个模块文件的Web路径
     *
     * @param warName
     * @param unitName
     * @return
     */
    public static String getFileWebUrl(String warName,String unitName){
        if(hAppInfo==null){
            initAppInfo();
        }
        Object obj = hAppInfo.get(warName);
        if(obj==null){
            return "";
        }else{
            obj = ((HashMap)obj).get(unitName);
            if(obj==null){
                return "";
            }else{
                return ("/" + webappName + "/" + obj.toString()+"/");
            }
        }
    }
    
    public static void main(String[] args) throws DocumentException,MissingResourceException,IOException{
        InitUploadInfo.initAppInfo();
	}
}

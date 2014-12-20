package org.quickbundle.config;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.path.RmPathHelper;

public class RmLoadConfig {
    
	public enum RmConfigEnum {
		RM_XML("/WEB-INF/config/rm/rm.xml"),
		RM_CLUSTER_XML("/WEB-INF/config/rm/rmCluster.xml");
		private String path;
		RmConfigEnum(String path) {
			this.path = path;
		}
		public String getPath() {
			return path;
		}
	}
	
    /**
     * 获得rm.xml的Document
     * @return
     */
    public static Document getRmDoc() {
        return RmLoadConfig.getSingleton().getInnerDoc(RmLoadConfig.RmConfigEnum.RM_XML.name(), RmLoadConfig.RmConfigEnum.RM_XML.getPath());
    }
    
    /**
     * 获得rmCluster.xml的Document
     * @return
     */
    public static Document getRmClusterDoc() {
        return  RmLoadConfig.getSingleton().getInnerDoc(RmLoadConfig.RmConfigEnum.RM_CLUSTER_XML.name(), getRmClusterXmlFile());
    }
 
    public static String getRmClusterXmlFile() {
    	String path = RmLoadConfig.RmConfigEnum.RM_CLUSTER_XML.getPath();
    	if(System.getProperty("nc.server.name") == null) {
    		return path;
    	}
    	String ncServerName = System.getProperty("nc.server.name");
    	File configDefault = new File(RmPathHelper.getWarDir().toString() + RmLoadConfig.RmConfigEnum.RM_CLUSTER_XML.getPath());
    	File customDir = new File(configDefault.getParent() + "/" + ncServerName);
    	if(!customDir.exists()) {
    		return path;
    	}
    	File customDirConfig = new File(customDir.toString() + "/rmCluster.xml");
    	if(customDirConfig.exists()) {
    		String warPath = RmXmlHelper.formatToFile(RmPathHelper.getWarDir().toString());
    		if(warPath.endsWith("/")) {
    			warPath = warPath.substring(0, warPath.length() - 1);
    		}
    		String customDirConfigStr = RmXmlHelper.formatToFile(customDirConfig.toString());
    		return customDirConfigStr.substring(warPath.length());
    	} else {
    		return path;
    	}
    }
    
	private static RmLoadConfig singleton = new RmLoadConfig();
	
    /**
	 * @return the singleton
	 */
	public static RmLoadConfig getSingleton() {
		return singleton;
	}
	
	private Map<String, RmLoadXmlVo> mLoadXmlVo = new HashMap<String, RmLoadXmlVo>();
	public RmLoadConfig() {
		RmConfigEnum[] ces = RmConfigEnum.values();
		for(RmConfigEnum ce : ces) {
			mLoadXmlVo.put(ce.name(), new RmLoadXmlVo());
		}
	}
    
    public Document getInnerDoc(String key, String path) {
    	return getInnerDoc(key, path, RmPathHelper.getWarDir().toString());
    }
    
    /**
     * 功能: 获得rm.xml
	 * 
     * @param warDir war目录的真实路径
     * @param filePath doc文件相对路径
     * @return
     */
    public Document getInnerDoc(String key, String path, String warDir) {
    	RmLoadXmlVo loadXmlVo = mLoadXmlVo.get(key);
    	if(loadXmlVo == null) {
    		loadXmlVo = new RmLoadXmlVo();
    		mLoadXmlVo.put(key, loadXmlVo);
    	}
        String xmlFullPath = warDir + path;
        xmlFullPath = RmXmlHelper.formatToFile(xmlFullPath);
        if(!loadXmlVo.isInit) {
        	doLoad(loadXmlVo, xmlFullPath);
        } else {
        	if(RmConfig.getSingleton().isSystemDebugMode()) {
                File docFile = new File(xmlFullPath);
                if(docFile.lastModified() != loadXmlVo.lastModifiedTime) {
                	loadXmlVo.isInit = false;
                	doLoad(loadXmlVo, xmlFullPath);
                }
        	}

        }
        return loadXmlVo.doc;
    }
    
    private void doLoad(RmLoadXmlVo loadXmlVo, String xmlFullPath) {
        if(!loadXmlVo.isInit) {
            synchronized (RmProjectHelper.class) {
            	if(!loadXmlVo.isInit) {
            		try {
            			loadXmlVo.doc = RmXmlHelper.parse(xmlFullPath);
            			loadXmlVo.isInit = true;
            		} catch (MalformedURLException e) {
            			throw new RmRuntimeException("load " + xmlFullPath +" error", e);
            		} catch (DocumentException e) {
            			throw new RmRuntimeException("load " + xmlFullPath +" error", e);
            		}
            	}
            }
        }
    }
    

	/** 
	 * get war dir in this project, called by RmPathHelper.getWarDir
	 * you can rewrite this
	 * @return
	 */
	public static File getWarDirCustom() {
		return null;
	}
}

class RmLoadXmlVo {
    Document doc = null;
    long lastModifiedTime = 0;
    volatile boolean isInit = false;

}
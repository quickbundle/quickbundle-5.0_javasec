package org.quickbundle.base.beans;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.beans.idwrapper.MaxInDbWrapper;
import org.quickbundle.base.beans.idwrapper.ShardingInCacheWrapper;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.itf.base.IRmIdGenerator;
import org.quickbundle.itf.base.IRmIdWrapper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.path.RmPathHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RmIdGenerator implements IRmIdGenerator {

	private Map<String, TableIdRuleVo> rule = null;
	
	private Map<String, IRmIdWrapper> mapWrapper = null;
	
	
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void init() {
        try {
            long startTime = System.currentTimeMillis();
            loadRule();
            initMapWrapper();
            doInitId();
            
            RmLogHelper.getLogger(this.getClass()).info("init " + mapWrapper.size() + " tables, cost " + (System.currentTimeMillis()-startTime) + " milliseconds!");
        } catch (Exception e) {
            e.printStackTrace();
            RmLogHelper.getLogger(RmIdFactory.class).error("id.xml init failed: " + e.toString());
        }
    }
    
	private void loadRule() {
		rule = new HashMap<String, TableIdRuleVo>();
        //id.xml命名空间
        Map<String, String> defaultNameSpaceMap = new HashMap<String, String>();  
        defaultNameSpaceMap.put("q", "http://www.quickbundle.org/schema");
        //读入id.xml
        Document docIdxml = null;;
		try {
			docIdxml = RmXmlHelper.parse(RmPathHelper.getWebInfDir() + "/config/rm/id.xml", defaultNameSpaceMap);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
		List<Element> lTable = docIdxml.selectNodes("/q:RmIdFactory/q:table");
		for(Element table : lTable) {
			String tableName = table.valueOf("@table_name").toUpperCase();
			TableIdRuleVo ruleVo = new TableIdRuleVo();
			ruleVo.setTableCode(table.valueOf("@table_code"));
			ruleVo.setTableName(tableName);
			ruleVo.setIdName(table.valueOf("@id_name"));
			ruleVo.setMultiDb("true".equals(table.valueOf("@multi_db")) || "1".equals(table.valueOf("@multi_db")));
			ruleVo.setWrapperClass(table.valueOf("@wrapper_class"));
			ruleVo.setWrapperClassFormat(table.valueOf("@wrapper_class_format"));
			rule.put(tableName, ruleVo);
		}
	}
	
	private void initMapWrapper() {
		mapWrapper = new HashMap<String, IRmIdWrapper>();
        for(Map.Entry<String, TableIdRuleVo> en : rule.entrySet()) {
        	String tableName = en.getKey();
        	TableIdRuleVo ruleVo = en.getValue();
        	//如果指定了wrapper_class
        	String wrapperClass = ruleVo.getWrapperClass();
        	if(wrapperClass == null || wrapperClass.length() == 0) {
        		mapWrapper.put(tableName, RmBaseConfig.getSingleton().isSystemDebugMode() ? new MaxInDbWrapper(ruleVo) : new ShardingInCacheWrapper(ruleVo));
        	} else {
        		try {
					Class clazz = this.getClass().getClassLoader().loadClass(wrapperClass);
					IRmIdWrapper customWrapper = (IRmIdWrapper)clazz.getConstructor(TableIdRuleVo.class).newInstance(ruleVo);
					mapWrapper.put(tableName, customWrapper);
				} catch (Throwable e) {
					e.printStackTrace();
					RmLogHelper.getLogger(RmIdFactory.class).error("id.xml init " + tableName + ": " + e.toString());
				}
        	}
        	
        }
	}
    
    private void doInitId() {
        for (Map.Entry<String, IRmIdWrapper> en: mapWrapper.entrySet()) {
            IRmIdWrapper wrapper = en.getValue();
            wrapper.init();
        }
    }

    public String[] requestIdInner(String tableName, int length) {
    	IRmIdWrapper wrapper = mapWrapper.get(tableName);
    	return wrapper.nextValue(length);
    }
}

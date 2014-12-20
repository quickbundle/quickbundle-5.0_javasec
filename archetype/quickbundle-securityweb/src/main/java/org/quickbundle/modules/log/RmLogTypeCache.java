package org.quickbundle.modules.log;

import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.modules.log.rmlogtype.service.IRmLogTypeService;
import org.quickbundle.modules.log.rmlogtype.util.IRmLogTypeConstants;
import org.quickbundle.modules.log.rmlogtype.vo.RmLogTypeVo;
import org.quickbundle.project.cache.RmAbstractCache;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.util.RmSequenceMap;

public class RmLogTypeCache extends RmAbstractCache {
    private Map<String, RmLogTypeVo> mLogType;
    
    private RmLogTypeVo defaultLogType =null;
    
    protected RmLogTypeCache() {
		mLogType = new RmSequenceMap<String, RmLogTypeVo>();
		defaultLogType =new RmLogTypeVo();
		defaultLogType.setId("0");
	}    

	//匹配log
    public RmLogTypeVo matchLogType(String call_code) {
        if(!isInit) {
            synchronized (this.getClass()) {
                if(!isInit) {
                    IRmLogTypeService ltService = (IRmLogTypeService)RmBeanFactory.getBean(IRmLogTypeConstants.SERVICE_KEY);
                    List<RmLogTypeVo> lLogType =  ltService.queryByCondition(null, "MATCH_PRIORITY DESC");
                    for (RmLogTypeVo vo : lLogType) {
                    	mLogType.put(vo.getPattern_value(), vo);
                    }
                    RmLogHelper.getLogger(this.getClass()).info("init ok, mPartyType.size()=" + mLogType.size());
                    isInit = true;
                }
            }
        }
        if(call_code != null) {
        	for(String pattern : mLogType.keySet()) {
        		if(call_code.matches(pattern)) {
        			return mLogType.get(pattern);
        		}
        	}
        }
        return defaultLogType;
    }
    
    
    /**
     * 全局单例
     */
    private static RmLogTypeCache singleton = new RmLogTypeCache();
    
	public static RmLogTypeCache getSingleton() {
		return singleton;
	}
}

package org.quickbundle.orgauth.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.orgauth.rmpartytype.service.IRmPartyTypeService;
import org.quickbundle.orgauth.rmpartytype.util.IRmPartyTypeConstants;
import org.quickbundle.orgauth.rmpartytype.vo.RmPartyTypeVo;
import org.quickbundle.project.cache.RmAbstractCache;
import org.quickbundle.tools.support.log.RmLogHelper;

public class RmPartyTypeCache extends RmAbstractCache {
    private Map<String, RmPartyTypeVo> mPartyType;
    private Map<String, RmPartyTypeVo> mPartyTypeId;

	protected RmPartyTypeCache getInitedInstance() {
        if(!isInit) {
            synchronized (this.getClass()) {
                if(!isInit) {
                	{
                		mPartyType = new HashMap<String, RmPartyTypeVo>();
                    	mPartyTypeId = new HashMap<String, RmPartyTypeVo>();
                		IRmPartyTypeService partyTypeService = (IRmPartyTypeService)RmBeanFactory.getBean(IRmPartyTypeConstants.SERVICE_KEY);
                		List<RmPartyTypeVo> lPartyType =  partyTypeService.queryByCondition(null, null);
                		for (RmPartyTypeVo vo : lPartyType) {
                			mPartyType.put(vo.getBs_keyword(), vo);
                        	mPartyTypeId.put(vo.getId(), vo);
                		}
                		RmLogHelper.getLogger(this.getClass()).info("init ok, mPartyType.size()=" + mPartyType.size());
                	}
                    isInit = true;
                }
            }
        }
        return this;
    }

    /**
     * 全局单例
     */
    private static RmPartyTypeCache singleton = new RmPartyTypeCache();
    
	public static RmPartyTypeCache getSingleton() {
		return singleton;
	}
	
    public static RmPartyTypeVo getPartyType(String bs_keyword) {
    	return getSingleton().getInitedInstance().mPartyType.get(bs_keyword);
    }
    
    public static RmPartyTypeVo getPartyTypeId(String id) {
    	return getSingleton().getInitedInstance().mPartyTypeId.get(id);
    }
}
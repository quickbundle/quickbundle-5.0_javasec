package org.quickbundle.orgauth.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.orgauth.rmpartyview.service.IRmPartyViewService;
import org.quickbundle.orgauth.rmpartyview.util.IRmPartyViewConstants;
import org.quickbundle.orgauth.rmpartyview.vo.RmPartyViewVo;
import org.quickbundle.project.cache.RmAbstractCache;
import org.quickbundle.tools.support.log.RmLogHelper;

public class RmPartyViewCache extends RmAbstractCache {
    private Map<String, RmPartyViewVo> mPartyView;

    protected RmPartyViewVo getPartyViewInner(String view_id) {
        if(!isInit) {
            synchronized (this.getClass()) {
                if(!isInit) {
                	mPartyView = new HashMap<String, RmPartyViewVo>();
                    IRmPartyViewService partyViewService = (IRmPartyViewService)RmBeanFactory.getBean(IRmPartyViewConstants.SERVICE_KEY);
                    List<RmPartyViewVo> lPartyView =  partyViewService.queryByCondition(null, null);
                    for (RmPartyViewVo vo : lPartyView) {
                    	mPartyView.put(vo.getId(), vo);
                    }
                    RmLogHelper.getLogger(this.getClass()).info("init ok, mPartyView.size()=" + mPartyView.size());
                    isInit = true;
                }
            }
        }
        return (RmPartyViewVo)mPartyView.get(view_id);
    }
    
    /**
     * 全局单例
     */
    private static RmPartyViewCache singleton = new RmPartyViewCache();
    
	public static RmPartyViewCache getSingleton() {
		return singleton;
	}
    
    public static RmPartyViewVo getPartyView(String view_id) {
    	return getSingleton().getPartyViewInner(view_id);
    }
}

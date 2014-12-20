package org.quickbundle.orgauth.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.orgauth.rmpartytyperelationrule.service.IRmPartyTypeRelationRuleService;
import org.quickbundle.orgauth.rmpartytyperelationrule.util.IRmPartyTypeRelationRuleConstants;
import org.quickbundle.orgauth.rmpartytyperelationrule.vo.RmPartyTypeRelationRuleVo;
import org.quickbundle.project.cache.RmAbstractCache;
import org.quickbundle.tools.support.log.RmLogHelper;

public class RmPartyTypeRelationRuleCache extends RmAbstractCache {
    private Map<ViewParentChild, RmPartyTypeRelationRuleVo> mPartyTypeRelationRule;

    public RmPartyTypeRelationRuleVo getRule(String view_id, String parent_party_type_id, String child_party_type_id) {
        if(!isInit) {
            synchronized (this.getClass()) {
                if(!isInit) {
                	mPartyTypeRelationRule = new HashMap<ViewParentChild, RmPartyTypeRelationRuleVo>();
                	IRmPartyTypeRelationRuleService ptrrService = (IRmPartyTypeRelationRuleService)RmBeanFactory.getBean(IRmPartyTypeRelationRuleConstants.SERVICE_KEY);
                    List<RmPartyTypeRelationRuleVo> lPtrr =  ptrrService.queryByCondition(null, null);
                    for (RmPartyTypeRelationRuleVo vo : lPtrr) {
                    	mPartyTypeRelationRule.put(new ViewParentChild(vo.getParty_view_id(), vo.getParent_party_type_id(), vo.getChild_party_type_id()), vo);
                    }
                    RmLogHelper.getLogger(this.getClass()).info("init ok, mPartyTypeRelationRule.size()=" + mPartyTypeRelationRule.size());
                    isInit = true;
                }
            }
        }
        return mPartyTypeRelationRule.get(new ViewParentChild(view_id, parent_party_type_id, child_party_type_id));
    }
    
    
    /**
     * 全局单例
     */
    private static RmPartyTypeRelationRuleCache singleton = new RmPartyTypeRelationRuleCache();
    
	public static RmPartyTypeRelationRuleCache getSingleton() {
		return singleton;
	}
	
	public class ViewParentChild{
		private String view_id;
		private String parent_party_type_id;
		private String child_party_type_id;
		public ViewParentChild(String view_id_, String parent_party_type_id_, String child_party_type_id_) {
			view_id = view_id_;
			parent_party_type_id = parent_party_type_id_;
			child_party_type_id = child_party_type_id_;
		}
		
		@Override
		public int hashCode() {
            int result = 0;
        	result += 29 * result + (view_id == null ? 0 : view_id.hashCode());
        	result += 29 * result + (parent_party_type_id == null ? 0 : parent_party_type_id.hashCode());
        	result += 29 * result + (child_party_type_id == null ? 0 : child_party_type_id.hashCode());
            return result;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof ViewParentChild) {
				ViewParentChild obj2 = (ViewParentChild) o;
				return isEqualsWithNull(view_id, obj2.view_id)
					&& isEqualsWithNull(parent_party_type_id, obj2.parent_party_type_id)
					&&isEqualsWithNull(child_party_type_id, obj2.child_party_type_id);
			}
			return false;
		}
		
		private boolean isEqualsWithNull(Object obj1, Object obj2) {
			if(obj1 == null) {
				if(obj2 == null) {
					return true;
				} else {
					return false;
				}
			} else {
				return obj1.equals(obj2);
			}
		}
		@Override
		public String toString() {
			return view_id + "|" + parent_party_type_id + "|" + child_party_type_id;
		}
	}
}

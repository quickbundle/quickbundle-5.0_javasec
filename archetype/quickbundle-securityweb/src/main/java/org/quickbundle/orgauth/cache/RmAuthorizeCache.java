package org.quickbundle.orgauth.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.orgauth.rmauthorize.service.IRmAuthorizeService;
import org.quickbundle.orgauth.rmauthorize.util.IRmAuthorizeConstants;
import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;
import org.quickbundle.project.cache.RmAbstractCache;
import org.quickbundle.tools.support.log.RmLogHelper;


public class RmAuthorizeCache extends RmAbstractCache {
    /**
     * 存放权限规则的Map，key是bs_keyword
     */
    private Map<String, RmAuthorizeVo> mAuthorizeByBs_keyword;
    
    /**
     * 存放权限规则的Map，key是id
     */
    private Map<String, RmAuthorizeVo> mAuthorizeById;

    /**
     * 功能: 获取权限调用规则
     *
     * @param bs_keyword
     * @return
     */
    protected RmAuthorizeVo getAuthorizeInnerByBs_keyword(String bs_keyword) {
    	init();
        return mAuthorizeByBs_keyword.get(bs_keyword);
    }
    
    protected RmAuthorizeVo getAuthorizeInnerById(String id) {
    	init();
        return mAuthorizeById.get(id);
    }
    
    private void init() {
        if(!isInit) {
            synchronized (RmAuthorizeCache.class) {
                if(!isInit) {
                	mAuthorizeByBs_keyword = new HashMap<String, RmAuthorizeVo>();
                	mAuthorizeById = new HashMap<String, RmAuthorizeVo>();
                    IRmAuthorizeService authorizeService = (IRmAuthorizeService)RmBeanFactory.getBean(IRmAuthorizeConstants.SERVICE_KEY);
                    List<RmAuthorizeVo> lAuthorize =  authorizeService.queryByCondition(null, null, -1, -1, true);
                    for (RmAuthorizeVo vo : lAuthorize) {
                    	mAuthorizeByBs_keyword.put(vo.getBs_keyword(),vo);
                    	mAuthorizeById.put(vo.getId(),vo);
                    }
                    RmLogHelper.getLogger(RmAuthorizeCache.class).info("init ok, mAuthorize.size()=" + mAuthorizeByBs_keyword.size());
                    isInit = true;
                }
            }
        }
    }
    
    /**
     * 全局单例
     */
    private static RmAuthorizeCache singleton = new RmAuthorizeCache();
    
	public static RmAuthorizeCache getSingleton() {
		return singleton;
	}
    
    /**
     * 获取权限调用规则
     *
     * @param bs_keyword
     * @return
     */
    public static RmAuthorizeVo getAuthorizeByBs_keyword(String bs_keyword) {
    	return getSingleton().getAuthorizeInnerByBs_keyword(bs_keyword);
    }
    
    /**
     * 获取权限调用规则
     * 
     * @param id
     * @return
     */
    public static RmAuthorizeVo getAuthorizeById(String id) {
    	return getSingleton().getAuthorizeInnerById(id);
    }
    
    
 /*   *//**
     * 功能: 获取访问类型字符串
     *
     * @param bs_keyword
     * @param access_type
     * @return
     *//*
    public static String[] getAccessTypeName(String bs_keyword, String access_type) {
        String[] aAccessTypeName = new String[access_type.length()];
        List lAccessTypeRule = getAuthorizeType(bs_keyword).getLAccessTypeRule();
        for (int i = 0; i < aAccessTypeName.length; i++) {
            if("1".equals(access_type.substring(i, i + 1))) {
                aAccessTypeName[i] = ((RmAccessTypeRuleVo)lAccessTypeRule.get(i)).getName();                
            }
        }
        return aAccessTypeName;
    }
    
    public static Map getAccessTypeMap(String bs_keyword) {
        Map m = new RmSequenceMap();
        List lAccessTypeRule = getAuthorizeType(bs_keyword).getLAccessTypeRule();
        int index = 0;
        for (Iterator iter = lAccessTypeRule.iterator(); iter.hasNext();) {
            RmAccessTypeRuleVo ruleVo = (RmAccessTypeRuleVo) iter.next();
            m.put(ruleVo.getAccess_position(), ruleVo.getName());
        }
        return m;
    }
    
    *//**
     * 功能: 
     *
     * @param bs_keyword
     * @param aParty_id
     * @return
     *//*
    private static List getlAuthorizeResource(String bs_keyword, String[] aParty_id) {
        List lAbstractResource = new ArrayList(); 
        IRmResourceService rmResourceService = (IRmResourceService)RmBeanFactory.getBean(IRmResourceConstants.BS_KEY);
        lAbstractResource.addAll(rmResourceService.queryByCondition("AUTHORIZE_TYPE_ID='" + RmAuthorizeCache.getAuthorizeType(bs_keyword).getId() + "' AND DEFAULT_ACCESS='1'", null));

        IRmAuthorizeService rmAuthorizeService = (IRmAuthorizeService)RmBeanFactory.getBean(IRmAuthorizeConstants.BS_KEY);
        lAbstractResource.addAll(rmAuthorizeService.queryByCondition("PARTY_ID IN(" + RmStringHelper.parseToSQLStringComma(aParty_id) + ") AND RESOURCE_ID IN(SELECT ID FROM RM_RESOURCE WHERE AUTHORIZE_TYPE_ID=" + RmAuthorizeCache.getAuthorizeType(bs_keyword).getId() + ")", null));
        
        return lAbstractResource;
    }
    
    *//**
     * 功能: 
     *
     * @param bs_keyword
     * @param aParty_id
     * @return
     *//*
    public static Map getmAuthorizeResource(String bs_keyword, String[] aParty_id) {
        Map mAuthorizeAccessType = new TreeMap();
        List lAbstractResource = getlAuthorizeResource(bs_keyword, aParty_id);
        for(Iterator itLAbstractResource = lAbstractResource.iterator(); itLAbstractResource.hasNext(); ) {
            RmAbstractResourceVo abstractResourceVo = (RmAbstractResourceVo) itLAbstractResource.next();
            if(mAuthorizeAccessType.get(abstractResourceVo.getOld_resource_id()) != null) {
                String tempAccess_type = mAuthorizeAccessType.get(abstractResourceVo.getOld_resource_id()).toString();
                abstractResourceVo.setAccess_type(RmStringHelper.getOrOperator( abstractResourceVo.getAccess_type(), tempAccess_type));
            }
            mAuthorizeAccessType.put(abstractResourceVo.getOld_resource_id(), abstractResourceVo.getAccess_type());
        }
        return mAuthorizeAccessType;
    }
    
    public static Map getmAuthorizeResourceForVo(String bs_keyword, String[] aParty_id) {
        Map mAuthorizeAccessType = new TreeMap();
        List lAbstractResource = getlAuthorizeResource(bs_keyword, aParty_id);
        for(Iterator itLAbstractResource = lAbstractResource.iterator(); itLAbstractResource.hasNext(); ) {
            RmAbstractResourceVo abstractResourceVo = (RmAbstractResourceVo) itLAbstractResource.next();
            if(mAuthorizeAccessType.get(abstractResourceVo.getOld_resource_id()) != null) {
                String tempAccess_type = mAuthorizeAccessType.get(abstractResourceVo.getOld_resource_id()).toString();
                abstractResourceVo.setAccess_type(RmStringHelper.getOrOperator( abstractResourceVo.getAccess_type(), tempAccess_type));
            }
            mAuthorizeAccessType.put(abstractResourceVo.getOld_resource_id(), abstractResourceVo);
        }
        return mAuthorizeAccessType;
    }*/
}

/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.au.tools.util --> RmGlobalReference.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-9-27 15:39:53 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.project;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.itf.cache.IRmCacheListener;
import org.quickbundle.modules.code.rmcodedata.service.IRmCodeDataService;
import org.quickbundle.modules.code.rmcodedata.util.IRmCodeDataConstants;
import org.quickbundle.modules.code.rmcodedata.vo.RmCodeDataVo;
import org.quickbundle.modules.code.rmcodetype.service.IRmCodeTypeService;
import org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants;
import org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo;
import org.quickbundle.util.RmSequenceMap;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmGlobalReference implements IRmCacheListener{
    /**
     * mGlobalReference 表示: 存放基础数据的Map
     */
    private Map mGlobalReference;
    
    /**
     * mGlobalReferenceOnlyUsable 表示: 存放基础数据的Map，但只有enable_status==1的数据
     */
    private Map mGlobalReferenceOnlyUsable;

    /**
     * 自动懒加载初始化的标志
     */
    private volatile boolean isInit = false;
    
    /**
     * 全局单例
     */
    private static RmGlobalReference singleton = new RmGlobalReference();
    
	public static RmGlobalReference getSingleton() {
		return singleton;
	}
    
    private void initOnlyOneTime() {
    	if(!isInit) {
    		synchronized (singleton) {
    			if(!isInit) {
    				initDataTotal();
    				isInit = true;
    			}
			}
    	}
    }
    
    /**
     * @return 返回 mGlobalReference。
     */
    private Map getMGlobalReference() {
    	initOnlyOneTime();
        return mGlobalReference;
    }
    
    /**
     * 功能: 返回 mGlobalReferenceOnlyUsable
     *
     * @return
     */
    private Map getMGlobalReferenceOnlyUsable() {
    	initOnlyOneTime();
        return mGlobalReferenceOnlyUsable;
    }

    
    /**
     * 功能: 初始化字典数据
     * 
     */
    public void initDataTotal() {
        mGlobalReference = new RmSequenceMap();
        mGlobalReferenceOnlyUsable = new RmSequenceMap();
        { //首先初始化RmDictionaryType
            IRmCodeTypeService codeTypeService = (IRmCodeTypeService) RmBeanFactory.getBean(IRmCodeTypeConstants.SERVICE_KEY);
            List lCodeType = codeTypeService.queryByCondition(null, null);
            for (Iterator itLCodeType = lCodeType.iterator(); itLCodeType.hasNext();) {
                RmCodeTypeVo codeTypeVo = (RmCodeTypeVo) itLCodeType.next();
                mGlobalReference.put(codeTypeVo.getType_keyword(), new RmSequenceMap());
                mGlobalReferenceOnlyUsable.put(codeTypeVo.getType_keyword(), new RmSequenceMap());
            }
        }
        { //接着初始化RmDictionary
            IRmCodeDataService codeDataService = (IRmCodeDataService) RmBeanFactory.getBean(IRmCodeDataConstants.SERVICE_KEY);
            List lCodeData = codeDataService.queryByCondition(null, "TOTAL_CODE");
            for (Iterator itLCodeData = lCodeData.iterator(); itLCodeData.hasNext();) {
                RmCodeDataVo codeDataVo = (RmCodeDataVo) itLCodeData.next();
                String type_keyword = codeDataVo.getCode_type_id_name();
                putRmCodeData(type_keyword, codeDataVo);
            }
        }
    }
    
    //TODO 局部更新调用
    /**
     * 初始化某种数据类型，暂时停止调用
     *
     * @param dictionaryType 某种数据类型关键字
     */
    @SuppressWarnings("unused")
	private void initData(String type_keyword) {
        //首先检验RmDictionaryType的合法性
        String queryCondition1 = " TYPE_KEYWORD='" + type_keyword + "'"; //本查询条件同时适用于RM_CODE_TYPE
        IRmCodeTypeService codeTypeService = (IRmCodeTypeService) RmBeanFactory.getBean(IRmCodeTypeConstants.SERVICE_KEY);
        List lCodeType = codeTypeService.queryByCondition(queryCondition1, null);
        if (lCodeType.size() == 0) { //如果没有这种数据类型，则从Map中清除之
            getMGlobalReference().remove(type_keyword);
            getMGlobalReferenceOnlyUsable().remove(type_keyword);
        } else { //接着初始化RmCodeData
            getMGlobalReference().put(type_keyword, new RmSequenceMap());
            getMGlobalReferenceOnlyUsable().put(type_keyword, new RmSequenceMap());
            String queryCondition2 = " CODE_TYPE_ID = (SELECT ID FROM RM_CODE_TYPE WHERE TYPE_KEYWORD='" + type_keyword + "')";
            IRmCodeDataService codeDataService = (IRmCodeDataService) RmBeanFactory.getBean(IRmCodeDataConstants.SERVICE_KEY);
            List lCodeData = codeDataService.queryByCondition(queryCondition2, null);
            for (Iterator itLCodeData = lCodeData.iterator(); itLCodeData.hasNext();) {
                RmCodeDataVo codeDataVo = (RmCodeDataVo) itLCodeData.next();
                putRmCodeData(type_keyword, codeDataVo);
            }
        }
    }
    
    /**
     * 内部子函数，存放编码数据对
     * 
     * @param type_keyword
     * @param codeDataVo
     */
    private void putRmCodeData(String type_keyword, RmCodeDataVo codeDataVo) {
        if (mGlobalReference.keySet().contains(type_keyword)) {
            //处理mGlobalReference
            Map mType = (Map) mGlobalReference.get(type_keyword);
            mType.put(codeDataVo.getData_key(), codeDataVo.getData_value());
        }
        if (mGlobalReferenceOnlyUsable.keySet().contains(type_keyword)) {
            if("1".equals(codeDataVo.getEnable_status())) {  
            	//处理mGlobalReferenceOnlyUsable
                Map mType = (Map) mGlobalReferenceOnlyUsable.get(type_keyword);
                mType.put(codeDataVo.getData_key(), codeDataVo.getData_value());
            }
        }
    }
    
    //对外静态类接口 begin

    /**
     * 功能: 获取参照的Map
     * 
     * @param dictionaryType 参照类型的关键字
     * @return
     */
    public static Map get(Object dictionaryType) {
        String dictionaryTypeStr = (dictionaryType == null) ? "" : String.valueOf(dictionaryType);
        return get(dictionaryTypeStr);
    }

    /**
     * 功能: 获取参照的Map
     * 
     * @param mReferenceObj 参照类型的关键字
     * @return
     */
    public static Map get(String dictionaryType) {
        return get(dictionaryType, true);
    }
    

    /**
     * 功能: 获取参照的Map
     *
     * @param dictionaryType 参照类型的关键字
     * @param allData 要取的字典数据的状态 ，true取全部数据，false只取enable_status=1的可用的数据 
     * @return
     */
    public static Map get(Object dictionaryType, boolean allData) {
        String dictionaryTypeStr = dictionaryType == null ? "" : String.valueOf(dictionaryType);
        return get(dictionaryTypeStr, allData);
    }
    
    /**
     * @param dictionaryType
     * @param allData
     * @return
     */
    public static Map get(String dictionaryType, boolean allData) {
        Object rtMap = null;
        if(allData) {
        	rtMap = getSingleton().getMGlobalReference().get(dictionaryType);
        } else {
            rtMap = getSingleton().getMGlobalReferenceOnlyUsable().get(dictionaryType);
        }
        if (rtMap == null) {
            rtMap = new RmSequenceMap();
        }
        return (Map) rtMap;
    }



    /**
     * 功能: 获取具体的参照数值
     * 
     * @param dictionaryType 参照类型的关键字
     * @param dictionaryKey 参照具体数据的关键字
     * @return
     */
    public static String get(String dictionaryType, String dictionaryKey) {
        Object rtStr = null;
        Map mRefe = get(dictionaryType, true);  //翻译要取全部字典数据
        if (mRefe != null) {
            rtStr = mRefe.get(dictionaryKey);
        }
        return (rtStr == null) ? "" : String.valueOf(rtStr);
    }

    /**
     * 功能: 获取具体的参照数值
     * 
     * @param dictionaryType 参照类型的关键字
     * @param dictionaryKey 参照具体数据的关键字
     * @return
     */
    public static String get(Object dictionaryType, Object dictionaryKey) {
        String dictionaryKeyStr = dictionaryKey == null ? "" : String.valueOf(dictionaryKey);
        String dictionaryTypeStr = dictionaryType == null ? "" : String.valueOf(dictionaryType);
        return get(dictionaryTypeStr, dictionaryKeyStr);
    }
    
	//对外静态类接口 end

	/**
	 * 刷新缓存的值，将keys对应的数据设置为已过期（或未初始化）状态
	 * 
	 * @param refreshType 缓存的刷新类型
	 * @param keys 缓存的key值，所有参数应可能使用String(如Java基本类型)
	 * @return 返回执行结果: -1表示错误, 0表示没找到删除的对象, 大于0的值表示影响的行数
	 */
	public String flushCache(String flushType, Object... keys) {
		String result = null;
		if(IRmCacheListener.RefreshType.COMMON.value().equals(flushType)) {
			isInit = false;
			result = "0";
		}
		return result;
	}

	public Map<String, String> getReverseMap(String dictionaryType) {
		Map<String, String> reverse = new HashMap<String, String>();
		Map<String, String> mOld = get(dictionaryType);
		for(Map.Entry<String, String> en : mOld.entrySet()) {
			reverse.put(en.getValue(), en.getKey());
		}
		return reverse;
	}

}
package org.quickbundle.orgauth.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.rmfunctionnode.service.IRmFunctionNodeService;
import org.quickbundle.orgauth.rmfunctionnode.util.IRmFunctionNodeConstants;
import org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo;
import org.quickbundle.project.cache.RmAbstractCache;
import org.quickbundle.tools.support.log.RmLogHelper;

public class RmFunctionNodeCache extends RmAbstractCache {

	//功能节点编码->vo对象
    private Map<String, RmFunctionNodeVo> mFunctionNode = new HashMap<String, RmFunctionNodeVo>();

    //功能节点url->Set<功能节点编码>
    private Map<String, Set<String>> mDataValue_totalCode = new HashMap<String, Set<String>>();
    
    //vo对象的列表
    private List<RmFunctionNodeVo> lFunctionNode;
    
    private void doInit() {
        if(!isInit) {
            synchronized (this.getClass()) {
                if(!isInit) {
                    IRmFunctionNodeService FunctionNodeService = (IRmFunctionNodeService)RmBeanFactory.getBean(IRmFunctionNodeConstants.SERVICE_KEY);
                    //TODO 排序 
                    lFunctionNode =  FunctionNodeService.queryByCondition(null, null, -1, -1, true);
                    for (RmFunctionNodeVo vo : lFunctionNode) {
                    	mFunctionNode.put(vo.getTotal_code(), vo);
                    	
                    	{ //以下代码，必须放在循环的结尾部分
                        	//初始化功能节点url->Set<功能节点编码>
                        	if(IOrgauthConstants.FunctionNodeType.BUTTON.value().equals(vo.getNode_type())) { //忽略按钮
                        		continue;
                         	}
                    		if(vo.getData_value() == null || vo.getData_value().trim().length() == 0) { //忽略
                    			continue;
                    		}
                    		if(mDataValue_totalCode.get(vo.getData_value()) == null) {
                    			mDataValue_totalCode.put(vo.getData_value(), new HashSet<String>());
                    		}
                    		mDataValue_totalCode.get(vo.getData_value()).add(vo.getTotal_code());
                    	}
                    }
                    RmLogHelper.getLogger(this.getClass()).info("init ok, mFunctionNode.size()=" + mFunctionNode.size());
                    isInit = true;
                }
            }
        }
    }
    
    /**
     * 全局单例
     */
    private static RmFunctionNodeCache singleton = new RmFunctionNodeCache();
    
	public static RmFunctionNodeCache getSingleton() {
		singleton.doInit();
		return singleton;
	}
    
    public static RmFunctionNodeVo getFunctionNode(String total_code) {
    	return getSingleton().mFunctionNode.get(total_code);
    }
    
	public static List<RmFunctionNodeVo> getFunctionNodeList() {
		return getSingleton().lFunctionNode;
	}
	
    /**
     * 从功能节点url得到编码
     * @param urlDataValue
     * @return
     */
    public static Set<String> getTotal_code(String urlDataValue) {
    	if(urlDataValue == null || urlDataValue.length() == 0) {
    		return null;
    	}
    	//TODO 扩展为根据此url匹配到相对的正则上，返回符合条件的编码
    	Set<String> sCode = new HashSet<String>();
    	Map<String, Set<String>> mDataCode = getSingleton().mDataValue_totalCode;
    	for(Map.Entry<String, Set<String>> en : mDataCode.entrySet()) {
    		String dataValue = en.getKey();
    		if(urlDataValue.equals(dataValue) 
    				|| urlDataValue.startsWith(dataValue + "&")
    				|| urlDataValue.startsWith(dataValue + "?")) {
    			sCode.addAll(en.getValue());
    		}
		}
    	return sCode;
//    	return getSingleton().mDataValue_totalCode.get(urlDataValue);
    }
}
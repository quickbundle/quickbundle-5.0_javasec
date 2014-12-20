package org.quickbundle.project.cache;

import java.util.Arrays;

import org.quickbundle.itf.cache.IRmCacheListener;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public abstract class RmAbstractCache implements IRmCacheListener{
    protected volatile boolean isInit = false;
	/**
	 * 分布式缓存的log入口类
	 */
	public final static Logger logCache = RmLogHelper.getLogger("rmcache");
	
    public String flushCache(String flushType, Object... keys) {
		String result = null;
		if(IRmCacheListener.RefreshType.COMMON.value().equals(flushType)) {
			isInit = false;
			result = "0";
		}
		logCache.info(this.getClass().getName() + ".flushCache(" + flushType + ", " + Arrays.deepToString(keys) + "): isInit=false");
		return result;
    }
}

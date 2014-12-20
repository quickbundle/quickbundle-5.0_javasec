package org.quickbundle.itf.cache;

import javax.jws.WebService;

@WebService
public interface IRmCacheHandler<T> {
	
	/**
	 * 集群模式下的缓存刷新实现
	 * 删除指定缓存类的某个key值(调用cacheClassName的getSingleton方法得到单例对象，再执行remove方法)
	 * 
	 * @param cacheClassName
	 * @param flushType
	 * @param keys
	 */
	public String reflectFlush(Class<T> cacheClassName, String flushType, Object... keys);

}

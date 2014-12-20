package org.quickbundle.project.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jws.WebService;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.itf.cache.IRmCacheHandler;
import org.quickbundle.itf.cache.IRmCacheListener;
import org.quickbundle.third.webservice.RmPasswordCallback;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

@WebService(endpointInterface = "org.quickbundle.itf.cache.IRmCacheHandler")
public class RmCacheHandler<T> implements IRmCacheHandler<T>{

	/**
	 * 线程池的最大容量
	 */
	public final static int MAX_THREAD_COUNT = 10; 
	
	/**
	 * 分布式缓存的log入口类
	 */
	public final static Logger logCache = RmLogHelper.getLogger("rmcache");
	
	/**
	 * 线程池
	 */
	private ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREAD_COUNT, cacheThreadFactory);
	
	private static Set<RmCacheHandler.FlushTask> sFlushTask = new CopyOnWriteArraySet<RmCacheHandler.FlushTask>();
	
	private RmCacheHandler() {
	}
	
	/**
	 * 集群模式下的缓存刷新实现，被其他节点调用
	 * 
	 * @param cacheClassName
	 * @param flushType
	 * @param keys
	 */
	public String reflectFlush(Class<T> cacheClassName, String flushType, Object... keys) {
		String result = null;
		try {
			Method methodGs = cacheClassName.getMethod("getSingleton");
			IRmCacheListener cl = (IRmCacheListener)methodGs.invoke(cacheClassName);
			result = cl.flushCache(flushType, keys);
			logCache.info(RmCacheHandler.class.getName() + ".reflectFlush(" + cacheClassName.getName() + ", " + flushType + ", " + Arrays.deepToString(keys) + "): " + result);
		} catch (Exception e) {
			logCache.error(RmCacheHandler.class.getName() + ".reflectFlush(" + cacheClassName.getName() + ", " + flushType + ", " + Arrays.deepToString(keys) + "): " + e.toString());
			result = IRmCacheListener.Result.FAIL.pattern();
		}
		return result;
	}
	
	/**
	 * 本地缓存更新后，调用此方法通知远程的兄弟节点刷新缓存
	 * 
	 * @param <T>
	 * @param cacheClassName
	 * @param flushType
	 * @param keys
	 */
	public void flushOtherNodes(final Class<T> cacheClassName, final String flushType, final Object... keys) {
		sFlushTask.add(new RmCacheHandler.FlushTask(cacheClassName, flushType, keys));
		
		final Map<String, String> mOther = RmClusterConfig.getSingleton().getOtherWsUrl();
		for (final String nodeId : mOther.keySet()) {
			String wsUrl = mOther.get(nodeId);
			final String soaUrl = wsUrl + "RmCacheHandler";
			Runnable task = new Runnable() {
				public void run() {
					try {
						RmCacheHandler.FlushTask ftThis = new RmCacheHandler.FlushTask(cacheClassName, flushType, keys);
						if(sFlushTask.contains(ftThis)) {
							sFlushTask.remove(ftThis);
							JaxWsProxyFactoryBean jw = new JaxWsProxyFactoryBean();
							jw.setServiceClass(IRmCacheHandler.class);
							jw.setAddress(soaUrl);
							appendAuth(jw, nodeId);
							IRmCacheHandler ch = (IRmCacheHandler) jw.create();
							ch.reflectFlush(cacheClassName, flushType, keys);
							logCache.info(RmCacheHandler.class.getName() + ".flushOtherNodes(" + cacheClassName.getName() + ", " + flushType + ", " + Arrays.deepToString(keys) + "): " + mOther);
						} else {
							logCache.debug("ignore " + RmCacheHandler.class.getName() + ".flushOtherNodes(" + cacheClassName.getName() + ", " + flushType + ", " + Arrays.deepToString(keys) + "): " + mOther);							
						}
					} catch (Exception e) {
						//e.printStackTrace();
						logCache.error(RmCacheHandler.class.getName() + ".flushOtherNodes(" + cacheClassName.getName() + ", " + flushType + ", " + Arrays.deepToString(keys) + "): " + e.toString());
					}
				}
			};
			threadPool.execute(task);
		}
	}
	
	/**
	 * 添加用户名和密码信息
	 * 
	 * @param jw
	 */
	public void appendAuth(JaxWsProxyFactoryBean jw, String nodeId) {
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, RmClusterConfig.getSingleton().getAuth(nodeId).keySet().toArray(new String[0])[0]);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, RmPasswordCallback.class.getName());

		Map<String, Object> mProp = new HashMap<String, Object>();
		mProp.put(FaultListener.class.getName(), new FaultListener() {
			public boolean faultOccurred(Exception exception, String description, Message message) {
				RmCacheHandler.logCache.error("fail: " + exception.toString() + " cause:" + exception.getCause());
				return false;
			}
		});
		jw.setProperties(mProp);

		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		jw.getOutInterceptors().add(wssOut);
		jw.getOutInterceptors().add(new SAAJOutInterceptor());
	}
	
	/**
	 * 销毁缓存处理器，安全关闭线程池
	 */
	public void showdown() {
		threadPool.shutdownNow();
	}
	
    /**
     * The default thread factory
     */
    static class CacheThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        CacheThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "RM-CacheHandler-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
            	t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
            	t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
    
    static CacheThreadFactory cacheThreadFactory = new CacheThreadFactory();
	
	/**
	 * 默认实例
	 */
	private static RmCacheHandler singleton = new RmCacheHandler();
	
	public static RmCacheHandler getSingleton() {
		return singleton;
	}
	
	class FlushTask {
		private Class cacheClassName;
		private String flushType;
		private Object[] keys;
		public FlushTask(Class cacheClassName, String flushType, Object... keys) {
			this.cacheClassName = cacheClassName;
			this.flushType = flushType;
			this.keys = keys;
		}
		
		@Override
		public int hashCode() {
			int result = 0;
			if(cacheClassName != null) {
				result += cacheClassName.hashCode() * 29;
			}
			if(flushType != null) {
				result += flushType.hashCode() * 29;
			}
			if(keys != null) {
				result += Arrays.deepToString(keys).hashCode() * 29;
			}
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof RmCacheHandler.FlushTask) {
				RmCacheHandler.FlushTask ft = (RmCacheHandler.FlushTask)obj;
				if(cacheClassName == null) {
					if(ft.cacheClassName != null) {
						return false;
					}
				} else {
					return cacheClassName.equals(ft.cacheClassName);
				}
				if(flushType == null) {
					if(ft.flushType != null) {
						return false;
					}
				} else {
					return flushType.equals(ft.flushType);
				}
				if(keys == null) {
					if(ft.keys != null) {
						return false;
					}
				} else {
					return keys.equals(ft.keys);
				}
			} else {
				return false;
			}
			return true;
		}
	}
}
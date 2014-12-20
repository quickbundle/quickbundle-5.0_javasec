package org.quickbundle.project.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;

import org.logicalcobwebs.proxool.ProxoolFacade;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.project.cache.RmCacheHandler;
import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.project.init.CustomSystemProperties;
import org.quickbundle.project.init.InitDatabaseHelper;
import org.quickbundle.project.init.LoadProjectConfig;
import org.quickbundle.tools.support.buffer.FlushQueueThread;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.path.RmPathHelper;
import org.slf4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 集成自Spring的监听器
 */
public class RmContextLoaderListener extends ContextLoaderListener {
	private Logger getLogger() {
		return RmLogHelper.getLogger(RmContextLoaderListener.class);
	}
	
	/*
	 * 启动服务时，初始化
	 *  (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		//系统初始化时，缓存第一个ServletContext对象
		RmHolderServlet.setDefaultServletContext(event.getServletContext());
		//更新RmConfig配置
		CustomSystemProperties.getInstance().init();
		LoadProjectConfig.initRmConfig();
		
		String initWarRootMsg = RmPathHelper.initWarRoot();
		getLogger().info(initWarRootMsg);
		synchronized (RmBeanFactory.lockInitFactory) {
			RmBeanFactory.setInitBeanStarted(true); //通知RmBeanFactory已开始启动BeanFactory
			super.contextInitialized(event);
			RmBeanFactory.setBeanFactory(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
		}
		InitDatabaseHelper.initDatabase();
		
		LoadProjectConfig.initClusterConfig();
	}

	/* 
	 * 停止服务时，关闭资源
	 * (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		//quartz begin
		try {
			org.quartz.Scheduler scheduler = org.quartz.impl.StdSchedulerFactory.getDefaultScheduler();
			scheduler.shutdown(true);
			//doWaitForClearThread(10000, "org\\.quartz\\.simpl\\.SimpleThreadPool.*");
		} catch (Exception e) {
			getLogger().error("scheduler.shutdown():" + e.toString());
		}
		//quartz end
		
		try {
			FlushQueueThread.getSingleton().shutdown();
			doWaitForClearThread(3000, "RM-FlushQueue.*");
		} catch (Exception e) {
			getLogger().error("FlushQueueThread.getSingleton().shutdown():" + e.toString());
		}
		
		try {
			RmClusterConfig.getSingleton().destroy();
			//doWaitForClearThread(3000, "NodeHeartbeat.*");
		} catch (Exception e) {
			getLogger().error("RmClusterConfig.getSingleton().destroy():" + e.toString());
		}
		
		try {
			RmCacheHandler.getSingleton().showdown();
			//doWaitForClearThread(5000, "RM-CacheHandler.*");
		} catch (Exception e) {
			getLogger().error("RmCacheHandler.getSingleton().showdown():" + e.toString());
		}
		
		try {
			ProxoolFacade.shutdown(10000);
			//doWaitForClearThread(10000, "org\\.logicalcobwebs\\.proxool\\.admin\\.StatsRoller.*");
		} catch (Exception e) {
			getLogger().error("ProxoolFacade.shutdown():" + e.toString());
		}
		
		//deregisterJdbcDriver();
		super.contextDestroyed(event);
		printThreads();
	}
	
	/**
	 * 卸载jdbc驱动
	 */
	void deregisterJdbcDriver() {
		// This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                getLogger().info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                getLogger().error(String.format("Error deregistering driver %s", driver), e);
            }

        }
	}
	
	/**
	 * 等待项目中的线程安全关闭，避免内存漏洞
	 */
	void doWaitForClearThread(long maxWaitTime, String patternThreadName) {
		if(maxWaitTime < 0) {
			maxWaitTime = 2000;
		}
		long waitTime = 0;
		try {
			while(waitTime < maxWaitTime) {
				String[] threadInfo = findMatchThread(patternThreadName);
				if(threadInfo != null) {
					if(waitTime > 0) {
						getLogger().info("wait " + threadInfo[0] + "(" + threadInfo[1] + ") for shutdown perfectly, elapsed time: " + waitTime + " ms");
					}
					Thread.sleep(1000);
					waitTime += 1000;
				} else {
					break;
				}
			}
		} catch (Exception e) {
			getLogger().error("doWaitForClearThread():", e);
		}
	}
	
	/**
	 * 检查是否存在未安全关闭的线程
	 * @return
	 */
	String[] findMatchThread(String patternThreadName) {
		Thread[] threads = getThreads();
		for(Thread thread : threads) {
			if(thread == null) {
				continue;
			}
			try {
				if(thread.getName().matches(patternThreadName) || thread.getClass().getName().matches(patternThreadName)) {
					return new String[]{thread.getName(), thread.getClass().getName()};
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void printThreads() {
		Thread[] threads = getThreads();
		System.out.println("Threads in server:");
		int count = 0;
		for(Thread t : threads) {
			if(t != null) {
				System.out.println(t.getId() + "|" + t.getClass().getName() + "|" + t.getName() + "|" + (t.getContextClassLoader() != null ? t.getContextClassLoader().getClass().getName() : ""));
				count ++;
			}
		}
		System.out.println("count:[" + count + "]");
	}
	
    /*
     * Get the set of current threads as an array.
     */
   private Thread[] getThreads() {
       // Get the current thread group 
       ThreadGroup tg = Thread.currentThread( ).getThreadGroup( );
       // Find the root thread group
       while (tg.getParent() != null) {
           tg = tg.getParent();
       }
       
       int threadCountGuess = tg.activeCount() + 50;
       Thread[] threads = new Thread[threadCountGuess];
       int threadCountActual = tg.enumerate(threads);
       // Make sure we don't miss any threads
       while (threadCountActual == threadCountGuess) {
           threadCountGuess *=2;
           threads = new Thread[threadCountGuess];
           // Note tg.enumerate(Thread[]) silently ignores any threads that
           // can't fit into the array 
           threadCountActual = tg.enumerate(threads);
       }
       
       return threads;
   }
}
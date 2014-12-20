package org.quickbundle.project.init;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmConfig;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmSqlHelper;

public class NodeHeartbeatDaemon {
	private NodeHeartbeatDaemon() {
	}
	private static NodeHeartbeatDaemon singleton = new NodeHeartbeatDaemon();
	public static NodeHeartbeatDaemon getSingleton() {
		return singleton;
	}
	
	/**
	 * 刷新节点心跳
	 */
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "NodeHeartbeat";
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
	});
	
	public void start() {
		executor.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				if(RmConfig.getSingleton().getDatabaseProductName() != null) {
					String sql = "update RM_NODE_HEARTBEAT set VERSION=VERSION+1, LAST_HEARTBEAT=" + RmSqlHelper.getFunction(RmSqlHelper.Function.SYSDATE, RmConfig.getSingleton().getDatabaseProductName()) + " where ID=?";
					RmBeanHelper.getCommonServiceInstance().doUpdate(sql, new Object[]{RmClusterConfig.getSingleton().getSelfId()});
				} else {
					String sql = "update RM_NODE_HEARTBEAT set VERSION=VERSION+1, LAST_HEARTBEAT=? where ID=?";
					RmBeanHelper.getCommonServiceInstance().doUpdate(sql, new Object[]{RmDateHelper.getSysTimestamp(), RmClusterConfig.getSingleton().getSelfId()});
				}
			}
		}, 1000, RmConfig.getSingleton().getNodeHeartbeatInterval(), TimeUnit.MILLISECONDS);
	}
	
	public void shutdown() {
		executor.shutdown();
	}
}

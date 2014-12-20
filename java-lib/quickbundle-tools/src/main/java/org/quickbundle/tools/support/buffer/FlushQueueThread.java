package org.quickbundle.tools.support.buffer;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.quickbundle.config.RmBaseConfig;

/**
 * 刷新监视器
 * 
 * @author liujia
 * @since 2010-10-14
 */
public class FlushQueueThread extends Thread {
	static enum ThreadState{
		READY,
		RUNNING,
		SLEEPING,
		SHUTTING,
		SHUTTED
	}

	private FlushQueueThread() {
		super("RM-FlushQueue");
		this.setDaemon(true);
	}
	
	/**
	 * 刷新任务执行器
	 */
	private ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory() {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "RM-FlushQueue-thread-";
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
	
	/**
	 * 检查的缓冲器集合 为保证高效迭代查询,使用CopyOnWrite结构
	 */
	private CopyOnWriteArrayList<AbstractTaskQueue> bufs = new CopyOnWriteArrayList<AbstractTaskQueue>();
	
	/**
	 * 获得缓冲器列表
	 * @return the bufs
	 */
	public CopyOnWriteArrayList<AbstractTaskQueue> getBufs() {
		return bufs;
	}

	/**
	 * 当前状态
	 */
	private ThreadState thState = ThreadState.READY;
	
	/**
	 * 控制线程开关
	 */
	private boolean stopFlag = false;
	
	/**
	 * 检查周期
	 */
	private long flush_interval = RmBaseConfig.getSingleton().getCacheCheckInterval();

	public void addTaskQueue(AbstractTaskQueue buf) {
		if (buf == null) {
			return;
		}
		bufs.add(buf);
	}
	
	/**
	 * 
	 * @param forceFlush 值true时不检查，直接提交。false时检查，不一定提交
	 */
	private void commitFlushTasks(final boolean forceFlush){
		//检查刷新全部缓冲器
		Iterator<AbstractTaskQueue> it = bufs.iterator();
		while (it.hasNext()) {
			final AbstractTaskQueue buf = it.next();
			Runnable task = new Runnable() {
				public void run() {
					if(forceFlush) {
						buf.tryFlush();
					} else {
						buf.checkFlush();
					}
				};
			};
			executor.execute(task);
		}
	}
	/**
	 * 进入"关闭ing"状态
	 */
	public void shutdown(){
		stopFlag = true;
		thState = ThreadState.SHUTTING;
		synchronized (this) {
			notifyAll();
		}
	}

	public String getThreadState(){
		return thState.name();
	}
	
	/**
	 * 完成最终的提交，然后关闭线程池
	 */
	private void doFinalComplete() {
		commitFlushTasks(true);				
		executor.shutdown();
		thState = ThreadState.SHUTTED;
	}
	
	@Override
	public void run() {
		thState = ThreadState.SLEEPING;
		while (!stopFlag) {
			try {
				thState=ThreadState.SLEEPING;
				synchronized (this) {
					wait(flush_interval);
				}
			} catch (InterruptedException e) {
				//记录已中断处理过
				Thread.interrupted();
				doFinalComplete();
				break;
			}
			thState = ThreadState.RUNNING;
			commitFlushTasks(false);
		}
		doFinalComplete();
	}
	
	
	/**
	 * 全局单例对象
	 */
	static FlushQueueThread _flushThread = new FlushQueueThread();
	
	public static FlushQueueThread getSingleton() {
		return _flushThread;
	}
	
	static { //类载入的时候，启动本线程
		if (!_flushThread.isAlive()) {
			synchronized (_flushThread) {
				if (!_flushThread.isAlive()) {
					_flushThread.start();	
				}
			}
		}
	}
}
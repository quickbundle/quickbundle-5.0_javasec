
package org.quickbundle.tools.support.buffer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

/**
 * 通用缓冲器 <h3>刷新方式:</h3>
 * <ul>
 * <li>满容刷新(默认)</li>
 * <li>过期(超过一定时间未刷新时)刷新,设置expire字段</li>
 * <li>定期(到达某一时间点)刷新,设置expire_time字段</li>
 * <li>自定义刷新,重写needFlush方法</li>
 * </ul>
 * 
 * @author liujia
 * @since 2010-10-14
 * @param <E> 缓冲对象
 */
public abstract class AbstractTaskQueue<E> {
	protected Logger log = RmLogHelper.getLogger(this.getClass());
	/**
	 * 缓冲队列
	 */
	protected ConcurrentLinkedQueue<E> buf = new ConcurrentLinkedQueue<E>();
	/**
	 * 最大容量
	 */
	protected int capcity = 1024;
	/**
	 * 上次刷新时间
	 */
	protected long last_flush = System.currentTimeMillis();
	/**
	 * 最大未刷新时间
	 */
	protected long expire_interval = RmBaseConfig.getSingleton().getCacheFlushInterval();

	/**
	 * 指定的下次刷新时刻
	 */
	protected long expire_time = Long.MAX_VALUE/2;
	/**
	 * 刷新锁，防止并发刷新
	 */
	protected Lock flushLock = new ReentrantLock();
	
	/**
	 * 构造函数
	 */
	public AbstractTaskQueue() {
		//绑定到刷新器
		FlushQueueThread.getSingleton().addTaskQueue(this);
	}
	
	/**
	 * @return 缓冲器名称
	 */
	public String getName() {
		return this.getClass().getName();
	}

	/**
	 * 增加内容,刷新时依然可插入
	 * 
	 * @param e
	 */
	public void add(E e) {
		if(e == null){
			return ;
		}
		
		if(buf.size() >= capcity * 10){
			//容量过大
			RmLogHelper.getLogger("rmexception").error("buf.clear() -> count:" + buf.size() + ", 缓冲区溢出, 请联系维护人员检查是否部署环境异常!");
			buf.clear();
		}
		//注释掉以下代码，杜绝在发起调用的业务线程中更新日志
//		if (buf.size() >= 0.1 * capcity) {
//			tryFlush();
//		}
		buf.add(e);
	}

	/**
	 * 尝试刷新
	 */
	public void tryFlush() {
		// 防止并发刷新
		if (!flushLock.tryLock()) {
			return;
		}
		try {
			long startTime = System.currentTimeMillis();
			int oldSize = buf.size();
			flush(buf);
			int thisCount = oldSize-buf.size();
			if(thisCount > 0) {
				log.info("flush " + thisCount + " records, cost " + (System.currentTimeMillis()-startTime) + " ms");
			}
		} finally {
			last_flush = System.currentTimeMillis();
			flushLock.unlock();
		}
	}

	/**
	 * 刷新缓冲区内数据，由子类提供
	 */
	protected abstract void flush(Queue<E> buf);

	/**
	 * 检查刷新条件 若满足，则刷新缓冲区
	 */
	public void checkFlush() {
		if ((last_flush + expire_interval < System.currentTimeMillis())
				|| (expire_time < System.currentTimeMillis())
				|| (buf.size() > capcity * 0.1) 
				|| (needFlush())) {
			if (expire_time < System.currentTimeMillis()) {
				expire_time = Long.MAX_VALUE / 2;
			}
			tryFlush();
		}
	}

	/**
	 * 由子类复写,提供自定义刷新条件
	 * 
	 * @return
	 */
	protected boolean needFlush() {
		return false;
	}
	
	/**
	 * 得到当前容量
	 * @return
	 */
	public int getSize() {
		return buf.size();
	}

	/**
	 * 得到最大容量
	 * @return
	 */
	public int getCapcity() {
		return capcity;
	}

	/**
	 * 设置最大容量
	 * @param capcity
	 */
	public void setCapcity(int capcity) {
		this.capcity = capcity;
	}

	/**
	 * 得到刷新间隔
	 * @return
	 */
	public long getExpire_interval() {
		return expire_interval;
	}

	/**
	 * 设置刷新间隔
	 * @param expireInterval
	 */
	public void setExpire_interval(long expireInterval) {
		expire_interval = expireInterval;
	}
	
	/**
	 * 得到刷新到期时间
	 * @return
	 */
	public long getExpire_time() {
		return expire_time;
	}
	
	/**
	 * 设置刷新到期时间
	 * @param expireInterval
	 */
	public void setExpire_time(long expireTime) {
		expire_time = expireTime;
	}

}
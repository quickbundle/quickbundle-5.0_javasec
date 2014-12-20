package org.quickbundle.third.quartz.listener;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.config.RmConfig;
import org.quickbundle.third.quartz.rmschedulerevent.service.IRmSchedulerEventService;
import org.quickbundle.third.quartz.rmschedulerevent.util.IRmSchedulerEventConstants;
import org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo;
import org.quickbundle.tools.support.buffer.AbstractTaskQueue;
import org.quickbundle.tools.support.log.RmLogHelper;

public class EventLog2DbService extends AbstractTaskQueue<RmSchedulerEventVo> {
	private String name = "调度事件缓冲器";
	public String getName() {
		return name;
	}

	EventLog2DbService() {
		super.expire_interval = RmConfig.getSingleton().getCacheFlushInterval();
	}
	
	EventLog2DbService(String name) {
		this();
		this.name = name; 
	}

	@Override
	protected void flush(Queue<RmSchedulerEventVo> buf) {
		RmSchedulerEventVo vo = null;
		List<RmSchedulerEventVo> flushVos = new LinkedList<RmSchedulerEventVo>();// 待刷新日志
		while ((vo = buf.poll()) != null) {
			flushVos.add(vo);
		}
		if (flushVos.size() > 0) {
			try {
				IRmSchedulerEventService rseService = (IRmSchedulerEventService) RmBeanFactory.getBean(IRmSchedulerEventConstants.SERVICE_KEY);
				rseService.insert(flushVos.toArray(new RmSchedulerEventVo[0]));
			} catch (Exception e) {
				RmLogHelper.getLogger("rmexception").error("flush() -> count:" + flushVos.size() + ", " + flushVos.toString() + ", " + e.toString());
			}
		}
	}
}

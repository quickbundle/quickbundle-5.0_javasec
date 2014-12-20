package org.quickbundle.third.quartz.listener;

import java.net.Inet4Address;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.quickbundle.project.listener.RmGlobalMonitor;
import org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo;
import org.quickbundle.third.quartz.util.ISchedulerConstants;
import org.quickbundle.tools.helper.RmDateHelper;

public class TriggerEventListener implements TriggerListener {
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * 
	 */
	static EventLog2DbService eventBuf = new EventLog2DbService("触发器事件");
	
	private RmSchedulerEventVo buildEventVo(Trigger trigger) {
		RmSchedulerEventVo vo = new RmSchedulerEventVo();
		vo.setCost_millis(0);
		String ip = null;
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (Exception e) {
			// ignore
		}
		vo.setCreate_ip(ip);
		vo.setCreate_time(RmDateHelper.getSysTimestamp());
		vo.setIs_archive("0");
		vo.setJob_group(trigger.getJobKey().getGroup());
		vo.setJob_name(trigger.getJobKey().getName());
		vo.setUuid(RmGlobalMonitor.uniqueUUID.get());
		vo.setTrigger_group(trigger.getKey().getGroup());
		vo.setTrigger_name(trigger.getKey().getName());
		return vo;
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {

	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	public void triggerMisfired(Trigger trigger) {
		RmSchedulerEventVo vo = buildEventVo(trigger);
		vo.setEvent_type(ISchedulerConstants.EventType.MISFIRE.name());
		eventBuf.add(vo);
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {

	}
	
}

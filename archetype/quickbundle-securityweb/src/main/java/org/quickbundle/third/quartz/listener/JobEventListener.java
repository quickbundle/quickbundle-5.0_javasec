package org.quickbundle.third.quartz.listener;

import java.net.Inet4Address;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quickbundle.project.listener.RmGlobalMonitor;
import org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo;
import org.quickbundle.third.quartz.util.ISchedulerConstants;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmUUIDHelper;

/**
 * 任务日志监听器 记录任务执行历史
 * 
 * @author liujia
 * @since 2010-10-9
 */
public class JobEventListener implements JobListener {

	/**
	 * 缓冲区
	 */
	static EventLog2DbService eventBuf = new EventLog2DbService("作业事件");

	public String getName() {
		return this.getClass().getSimpleName();
	}
	
    //本次线程中，是否在本方法创建的UUID?
    private static final ThreadLocal<Boolean> hereCreateUUID = new ThreadLocal<Boolean>();

	public void jobExecutionVetoed(JobExecutionContext context) {
		//nothing
	}
	
	private RmSchedulerEventVo buildEventVo(JobExecutionContext context) {
		JobDetail jd = context.getJobDetail();
		Trigger tg = context.getTrigger();
		RmSchedulerEventVo vo = new RmSchedulerEventVo();
		vo.setCost_millis(System.currentTimeMillis() - context.getFireTime().getTime());
		String ip = null;
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (Exception e) {
			// ignore
		}
		vo.setCreate_ip(ip);
		vo.setCreate_time(RmDateHelper.getSysTimestamp());
		vo.setFire_instance_id(context.getFireInstanceId());
		vo.setIs_archive("0");
		vo.setJob_group(jd.getKey().getGroup());
		vo.setJob_name(jd.getKey().getName());
		vo.setUuid(RmGlobalMonitor.uniqueUUID.get());
		vo.setResult(context.getResult() != null ? context.getResult().toString() : null);

		vo.setTrigger_group(tg.getKey().getGroup());
		vo.setTrigger_name(tg.getKey().getName());
		return vo;
	}

	/**
	 * 记录任务执行结束
	 */
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		RmSchedulerEventVo vo = buildEventVo(context);
		vo.setEvent_type(ISchedulerConstants.EventType.RUN.name());
		if (jobException == null) {
			vo.setResult_status(ISchedulerConstants.Result.OK.value());
		} else {
			vo.setResult_status(ISchedulerConstants.Result.ERROR.value());
			if (vo.getResult() == null) {
				vo.setResult(RmStringHelper.getStackTraceStr(jobException, 4000));
			}
		}
		eventBuf.add(vo);
		if(hereCreateUUID.get() != null && hereCreateUUID.get().booleanValue()) {
			//清除UUID
			RmGlobalMonitor.uniqueUUID.remove();
			hereCreateUUID.remove();
		}
	}

	public void jobToBeExecuted(JobExecutionContext context) {
    	if(RmGlobalMonitor.uniqueUUID.get() == null) {
    		RmGlobalMonitor.uniqueUUID.set(RmUUIDHelper.generateUUID());
    		hereCreateUUID.set(true);
    	}
	}
}
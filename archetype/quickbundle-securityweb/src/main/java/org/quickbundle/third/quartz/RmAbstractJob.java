package org.quickbundle.third.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quickbundle.project.test.RmAlarmCollector;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public abstract class RmAbstractJob implements Job {
	private static Logger log = RmLogHelper.getLogger(RmAbstractJob.class);
	
//	protected void doExecutor(final Runnable runnable) {
//		new Executor(runnable).start();
//	}
	
	public abstract Object doJob(final JobExecutionContext context) throws Exception;

	public void execute(final JobExecutionContext context) throws JobExecutionException {
		try {
			Object resultObj = doJob(context);
			String result = String.valueOf(resultObj);
			if (result != null && result.length() > 490) {
				result = result.substring(0, 490);
			}
			context.setResult("OK:" + result);
		} catch (Throwable e) {
			e.printStackTrace();
			String err = e.toString();
			try {
				err = RmStringHelper.getStackTraceStr(e, 1000);
				String[] info = RmAlarmCollector.createInfo("saveChannel_RequiresNew() error", e);
				log.error(info[0]);
				log.error(info[1]);
				if (err != null && err.length() > 490) {
					err = err.substring(0, 490);
				}
			} catch (Exception e2) {
				e.printStackTrace();
			}
			context.setResult("FAIL:" + err);
		}
	}
}

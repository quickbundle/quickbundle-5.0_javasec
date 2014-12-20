package org.quickbundle.third.quartz;

import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quickbundle.project.test.RmAlarmCollector;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmCommonJob implements Job  {
	private static Logger log = RmLogHelper.getLogger(RmCommonJob.class);
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String className = null;
		String methodName = null;
		try {
			className = context.getTrigger().getJobDataMap().getString("className");
			methodName = context.getTrigger().getJobDataMap().getString("method");
			Class clazz = ClassLoader.getSystemClassLoader().loadClass(className);
			Object obj = clazz.newInstance();
			Method method = clazz.getDeclaredMethod(methodName, new Class[]{});
	        Object result = method.invoke(obj ,new Object[] {});
			context.setResult("OK:" + result);
		} catch (Throwable e) {
			String[] info = RmAlarmCollector.createInfo(className + "." + methodName + "(): ", e);
			log.error(info[0]);
			log.error(info[1]);
			context.setResult("FAIL:" + e.getMessage());
		}
	}
	

}

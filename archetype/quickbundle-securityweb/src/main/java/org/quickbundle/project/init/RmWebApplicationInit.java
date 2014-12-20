package org.quickbundle.project.init;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.config.RmConfig;
import org.quickbundle.itf.ILoadOnStartup;
import org.quickbundle.tools.support.log.RmLogHelper;

/**
 * 初始化组件和服务
 * 
 * @author 白小勇
 * 
 */
public class RmWebApplicationInit implements ILoadOnStartup {
	
	public void destroy(HttpServlet servlet) {

	}

	public void init(HttpServlet servlet) {

		check();
		
		// quartz begin
		initQuartz();
		// quartz end
	}

	void check() {
		if (RmConfig.getSingleton().isSystemDebugMode()) {
			RmLogHelper.getLogger(this.getClass()).warn("########## System in DEBUG mode, please modify rm.xml=>\"systemDebugMode=false\" in production ##########");
			RmLogHelper.getLogger(this.getClass()).warn("########## 系统当前是开发调试模式，在生产环境中请修改rm.xml=>\"systemDebugMode=false\" ##########");
		}
		
		File warHome = new File(RmConfig.getSingleton().getWarHome());
		if (!warHome.exists()) {
			RmLogHelper.getLogger(this.getClass()).warn("warHome: " + warHome.getAbsolutePath() + " not exist");
		} else if (!warHome.canRead()) {
			RmLogHelper.getLogger(this.getClass()).warn("warHome: " + warHome.getAbsolutePath() + " can not read");
		} else if (!warHome.canWrite()) {
			RmLogHelper.getLogger(this.getClass()).warn("warHome: " + warHome.getAbsolutePath() + " can not write");
		}


	}
	
	// quartz begin
	private void initQuartz() {
		try {
			if (RmConfig.getSingleton().isSchedulerStart()) {
				long schedulerStartLazySecond = RmConfig.getSingleton().getSchedulerStartLazy();
				if (schedulerStartLazySecond <= 0) {
					org.quartz.Scheduler scheduler = (org.quartz.Scheduler) RmBeanFactory.getBean(org.quickbundle.third.quartz.util.ISchedulerConstants.QUARTZ_SHEDULER);
					scheduler.start();

				} else {
					final long finalSchedulerStartLazy = schedulerStartLazySecond;
					Thread t = new Thread(new Runnable() {
						public void run() {
							try {
								System.out.println("sleep for ejb ok begin, " + finalSchedulerStartLazy + " second");
								Thread.sleep(1000 * finalSchedulerStartLazy);
								System.out.println("sleep for ejb ok end");
								org.quartz.Scheduler scheduler = (org.quartz.Scheduler) RmBeanFactory.getBean(org.quickbundle.third.quartz.util.ISchedulerConstants.QUARTZ_SHEDULER);
								scheduler.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					t.start();
				}
			}
		} catch (Exception e) {
			RmLogHelper.getLogger(this.getClass()).error("调度器启动失败", e);
		}
	}
	// quartz end

	public void service(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// nothing
	}
}
package org.quickbundle.mda.mvm;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;

public class EclipseLog {
	private static ILog logger = null;
	static {
		logger = MvmActivator.getDefault().getLog();
	}

	private EclipseLog() {
		// nothing to do
	}

	public static void logCancel(String message, Throwable exception) {
		logger.log(new Status(Status.CANCEL, MvmActivator.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logError(String message, Throwable exception) {
		logger.log(new Status(Status.ERROR, MvmActivator.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logInfo(String message, Throwable exception) {
		logger.log(new Status(Status.INFO, MvmActivator.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logOk(String message, Throwable exception) {
		logger.log(new Status(Status.OK, MvmActivator.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logWarning(String message, Throwable exception) {
		logger.log(new Status(Status.WARNING, MvmActivator.PLUGIN_ID, Status.OK, message, exception));
	}
}

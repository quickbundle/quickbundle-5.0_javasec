package org.quickbundle.mda.gc;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;

public class EclipseLog {
	private static ILog logger = null;
	static {
		logger = QbXmlGenerateCodePlugin.getDefault().getLog();
	}

	private EclipseLog() {
		// nothing to do
	}

	public static void logCancel(String message, Throwable exception) {
		logger.log(new Status(Status.CANCEL, QbXmlGenerateCodePlugin.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logError(String message, Throwable exception) {
		logger.log(new Status(Status.ERROR, QbXmlGenerateCodePlugin.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logInfo(String message, Throwable exception) {
		logger.log(new Status(Status.INFO, QbXmlGenerateCodePlugin.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logOk(String message, Throwable exception) {
		logger.log(new Status(Status.OK, QbXmlGenerateCodePlugin.PLUGIN_ID, Status.OK, message, exception));
	}

	public static void logWarning(String message, Throwable exception) {
		logger.log(new Status(Status.WARNING, QbXmlGenerateCodePlugin.PLUGIN_ID, Status.OK, message, exception));
	}
}

package org.quickbundle.mda.gp;

import java.io.File;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class QbGenerateProjectPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static QbGenerateProjectPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;

	/**
	 * The constructor.
	 */
	public QbGenerateProjectPlugin() {
		super();
		plugin = this;
		try {
			//TODO fix bug
			resourceBundle = ResourceBundle.getBundle("org.quickbundle.gp.QbGenerateProjectPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static QbGenerateProjectPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = QbGenerateProjectPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
    /**
     * 功能: 得到插件所在目录
     * 
     * @return
     */
    public static Path getInstallLocation() {
        try {
        	//FileLocator.getBundleFile(Platform.getBundle(QbGenerateProjectPlugin.PLUGIN_ID)).getAbsolutePath()
            URL url = getDefault().getDescriptor().getInstallURL();
            String s1 = Platform.resolve(url).getFile();
            if (s1.startsWith("/"))
                s1 = s1.substring(1);
            s1 = (new Path(s1)).toOSString();
            String s;
            if (s1.endsWith(File.separator))
                s = s1;
            else
                s = s1 + File.separator;
            return new Path(s);
        } catch (Exception exception) {
            log(exception.toString());
            return null;
        }
    }

    /**
     * 功能: 写入日志
     * 
     * @param traceMessage
     */
    public static void log(String traceMessage) {
        IStatus status = new Status(Status.INFO, "org.quickbundle.mda.gp", 0, traceMessage, null);
        getDefault().getLog().log(status);
        System.out.println(traceMessage);
    }
}

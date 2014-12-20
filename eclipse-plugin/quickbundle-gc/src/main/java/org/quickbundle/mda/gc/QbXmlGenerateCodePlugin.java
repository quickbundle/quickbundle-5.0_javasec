package org.quickbundle.mda.gc;

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
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;


/**
 * The main plugin class to be used in the desktop.
 */
public class QbXmlGenerateCodePlugin extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "org.quickbundle.mda.gc"; //$NON-NLS-1$
	
    //The shared instance.
    private static QbXmlGenerateCodePlugin plugin;

    //Resource bundle.
    private ResourceBundle resourceBundle;

    //定义config的绝对路径
    public static String baseConfigPath = null;

    //本插件的工作目录
    public static String qbGenerateCodeHome = RmFileHelper.initSelfDir(RmXmlHelper.formatToUrl(System.getProperty("user.home")) + File.separator + ".quickbundle/");

    /**
     * The constructor.
     */
    public QbXmlGenerateCodePlugin() {
        super();
        plugin = this;
        resourceBundle = ResourceBundle.getBundle("resource");
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
    public static QbXmlGenerateCodePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = QbXmlGenerateCodePlugin.getDefault().getResourceBundle();
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
        } catch (Exception e) {
            log(e.toString());
            return null;
        }
    }

    /**
     * 功能: 写入日志
     * 
     * @param traceMessage
     */
    public static void log(String traceMessage) {
        IStatus status = new Status(Status.INFO, "org.quickbundle.mda.gc", 0, traceMessage, null);
        getDefault().getLog().log(status);
    }
}
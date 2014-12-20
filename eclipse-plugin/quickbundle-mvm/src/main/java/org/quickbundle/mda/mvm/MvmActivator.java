package org.quickbundle.mda.mvm;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MvmActivator extends AbstractUIPlugin{

	// The plug-in ID
	public static final String PLUGIN_ID = "org.quickbundle.mda.mvm"; //$NON-NLS-1$

	// The shared instance
	private static MvmActivator plugin;
	
	/**
	 * The constructor
	 */
	public MvmActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static MvmActivator getDefault() {
		return plugin;
	}
}

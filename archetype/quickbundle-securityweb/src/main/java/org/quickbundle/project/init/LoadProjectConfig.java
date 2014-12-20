package org.quickbundle.project.init;

import java.lang.reflect.InvocationTargetException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmConfig;
import org.quickbundle.config.RmLoadConfig;

public class LoadProjectConfig {

	/**
	 * 初始化rm.xml
	 */
	public static void initRmConfig() {
		Document rmDoc = RmLoadConfig.getRmDoc();
		PopulateRmConfig pc = new PopulateRmConfig(RmConfig.getSingleton(), rmDoc);
		pc.populate();

		Document rmClusterDoc = RmLoadConfig.getRmClusterDoc();
		PopulateRmConfig pc2 = new PopulateRmConfig(RmConfig.getSingleton(), rmClusterDoc);
		pc2.populate();
	}
	
	public static void initClusterConfig() {
		Element eleLoadCluster = (Element) RmLoadConfig.getRmClusterDoc().selectSingleNode("/rm/org.quickbundle.config.RmClusterConfig/*[1]");
		String classNameLoadCluster = eleLoadCluster.getName();
		AbstractClusterConfigLoader loadClusterConfig = null;;
		try {
			Class clazzLcc = LoadProjectConfig.class.getClassLoader().loadClass(classNameLoadCluster);
			loadClusterConfig = (AbstractClusterConfigLoader) clazzLcc.getConstructor(Element.class).newInstance(eleLoadCluster.createCopy());
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		loadClusterConfig.init();
		RmClusterConfig.setSingleton(loadClusterConfig);
		RmConfig.getSingleton().setShardingPrefix(loadClusterConfig.getSelfNode().get(RmClusterConfig.NodeKey.shardingPrefix.name()));
	}
}

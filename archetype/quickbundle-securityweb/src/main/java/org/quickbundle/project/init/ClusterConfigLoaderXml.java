package org.quickbundle.project.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Node;
import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmClusterConfig.NodeKey;
import org.quickbundle.config.RmConfig;
import org.quickbundle.tools.support.log.RmLogHelper;

public class ClusterConfigLoaderXml extends AbstractClusterConfigLoader {

	public ClusterConfigLoaderXml(Element eleClusterConfigLoader) {
		super(eleClusterConfigLoader);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		Element node = (Element) eleLoadCluster.selectSingleNode("node[@id=../@thisId]");
		List<Node> keyValues = node.selectNodes("*|@*");
		for(Node keyValue : keyValues) {
			selfNode.put(keyValue.getName(), keyValue.getText().trim());
		}
		if (RmHolderServlet.getDefaultServletContext() != null) {
			try {
				selfNode.put(RmClusterConfig.NodeKey.contentPath.name(), RmHolderServlet.getDefaultServletContext().getContextPath());
			} catch (Throwable e) {
				e.printStackTrace();
				RmLogHelper.getLogger(this.getClass()).error("JavaEE version to low: " + e.toString());
			}
		}
		//集群模式的判断
		RmConfig.getSingleton().setClusterMode(eleLoadCluster.selectNodes("node").size() > 1);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> getNodes() {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		List<Element> nodes = eleLoadCluster.selectNodes("node");
		for(Element node : nodes) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			List<Node> keyValues = node.selectNodes("*|@*");
			for(Node keyValue : keyValues) {
				nodeMap.put(keyValue.getName(), keyValue.getText().trim());
			}
			result.put(nodeMap.get(NodeKey.id.name()), nodeMap);
		}
		return result;
	}

	@Override
	public void destroy() {
		//ignore
	}
}
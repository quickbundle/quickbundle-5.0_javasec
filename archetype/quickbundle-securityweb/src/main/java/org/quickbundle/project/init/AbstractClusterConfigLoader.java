package org.quickbundle.project.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.quickbundle.config.HostInfo;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmClusterConfig.NodeKey;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.util.RmSequenceMap;

public abstract class AbstractClusterConfigLoader {
	protected Element eleLoadCluster;
	public AbstractClusterConfigLoader(Element eleLoadCluster) {
		this.eleLoadCluster = eleLoadCluster;
	}
	
	protected Map<String, String> selfNode = new HashMap<String, String>();
	
	public abstract void init();
	public abstract void destroy();
	
	public void refreshHostInfo(HostInfo hostInfo) {
		String baseUrl = buildBaseUrl(RmClusterConfig.getLocalhostInfo());
		selfNode.put(NodeKey.baseUrl.name(), baseUrl);
		selfNode.put(NodeKey.webServiceUrl.name(), baseUrl + "/services/");
		updateNodeHeartbeat(baseUrl);
	}
	
	private void updateNodeHeartbeat(String baseUrl) {
		String sql = "update RM_NODE_HEARTBEAT set VERSION=VERSION+1, BASE_URL=? where ID=?";
		RmBeanHelper.getCommonServiceInstance().doUpdate(sql, new Object[]{baseUrl, getSelfId()});
	}
	public Map<String, String> getSelfNode() {
		return selfNode;
	}
	
	/**
	 * 获得集群模式下本节点的id
	 * @return
	 */
	public String getSelfId() {
		return selfNode.get(RmClusterConfig.NodeKey.id.name());
	}
	
	public abstract Map<String, Map<String, String>> getNodes();
	
	public Map<String, String> getNode(String id) {
		return getNodes().get(id);
	}
	
	/**
	 * 获得集群模式下其他节点的webservice地址前缀
	 * 
	 * @return
	 */
	public Map<String, String> getOtherWsUrl() {
		Map<String, String> result = new RmSequenceMap<String, String>();
		String thisId = getSelfId();
		Map<String, Map<String, String>> nodes = getNodes();
		nodes.remove(thisId);
		for (Map.Entry<String, Map<String, String>> en : nodes.entrySet()) {
			result.put(en.getKey(), en.getValue().get(NodeKey.webServiceUrl.name()));
		}
		return result;
	}

	public List<String> getOtherNodeId() {
		String thisId = getSelfId();
		Map<String, Map<String, String>> nodes = getNodes();
		nodes.remove(thisId);
		return new ArrayList<String>(nodes.keySet());
	}
	
	public Map<String, String> getAuth(String id) {
		Map<String, String> nodeInfo = getNode(id);
		Map<String, String> result = new HashMap<String, String>();
		result.put(nodeInfo.get(NodeKey.user.name()), nodeInfo.get(NodeKey.password.name()));
		return result;
	}
	
	protected String buildBaseUrl(HostInfo hostInfo) {
		StringBuilder result = new StringBuilder();
		result.append(hostInfo.getScheme())
			.append("://")
			.append(hostInfo.getServerName())
			.append(":")
			.append(hostInfo.getServerPort())
			.append(hostInfo.getContextPath());
		return result.toString();
	}
}

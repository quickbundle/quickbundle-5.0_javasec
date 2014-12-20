package org.quickbundle.project.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmClusterConfig.NodeKey;
import org.quickbundle.config.RmConfig;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmUUIDHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

public class ClusterConfigLoaderDb extends AbstractClusterConfigLoader {
	
	public ClusterConfigLoaderDb(Element eleClusterConfigLoader) {
		super(eleClusterConfigLoader);
	}

	public void init() {
		if (RmHolderServlet.getDefaultServletContext() != null) {
			try {
				selfNode.put(RmClusterConfig.NodeKey.contentPath.name(), RmHolderServlet.getDefaultServletContext().getContextPath());
			} catch (Throwable e) {
				e.printStackTrace();
				RmLogHelper.getLogger(this.getClass()).error("JavaEE version to low: " + e.toString());
			}
		}
		
		//集群模式的判断
		String sql = "select count(*) from RM_NODE_HEARTBEAT where " + buildWhereActive();
		long activeNodes = RmBeanHelper.getCommonServiceInstance().doQueryForInt(sql);
		RmConfig.getSingleton().setClusterMode(activeNodes > 1);
		
		initSelfNode();
		
		NodeHeartbeatDaemon.getSingleton().start();
	}

	private void initSelfNode() {
		selfNode.put(NodeKey.id.name(), RmUUIDHelper.generateUUID());
		selfNode.put(NodeKey.user.name(), RmClusterConfig.DEFAULT_AUTH_KEY_VALUE.get(NodeKey.user.name()));
		selfNode.put(NodeKey.password.name(), RmClusterConfig.DEFAULT_AUTH_KEY_VALUE.get(NodeKey.password.name()));

		String shardingPrefix = createShardingPrefix();
		selfNode.put(NodeKey.shardingPrefix.name(), shardingPrefix);
		
		if(RmClusterConfig.getLocalhostInfo() != null) {
			refreshHostInfo(RmClusterConfig.getLocalhostInfo());
		}
	}
	
	private String buildWhereActive() {
		return RmSqlHelper.getFunction(RmSqlHelper.Function.SYSDATE, RmConfig.getSingleton().getDatabaseProductName())  + "-LAST_HEARTBEAT<" + RmConfig.getSingleton().getNodeHeartbeatInterval()/1000*1.8;
	}

	private String createShardingPrefix() {
		String sqlMax = "select max(SHARDING_PREFIX) from RM_NODE_HEARTBEAT where " + buildWhereActive();
		List<String> maxIds = RmBeanHelper.getCommonServiceInstance().queryForList(sqlMax, String.class);
		long shardingPrefix = getMaxIdOrDefault(maxIds.size() == 0 ? null : maxIds.get(0));
		String sqlInsert = "insert into RM_NODE_HEARTBEAT (ID, VERSION, SHARDING_PREFIX, LAST_HEARTBEAT) " +
				"values (?, ?, ?, ?)";
		RmBeanHelper.getCommonServiceInstance().doUpdate(sqlInsert, new Object[]{getSelfId(), 1, shardingPrefix, RmDateHelper.getSysTimestamp()});
		return String.valueOf(shardingPrefix);
	}
	
    //根据 集群+table的前缀和maxId，获取下一个可用值或默认起始值
   protected long getMaxIdOrDefault(String maxId) {
    	long result = 0L;
		if(maxId != null && maxId.length() > 0) {
			if(maxId.length() > 19) {
				result = Long.parseLong(maxId.substring(0, 19)) + 1;
			} else {
				result = Long.parseLong(maxId) + 1;
			}
		} else {
			result = Long.parseLong(firstValue());
		}
    	return result;
    }
   
	protected String firstValue() {
		String result = eleLoadCluster.valueOf("@firstShardingPrefix");
		if(result.length() == 0) {
			result = "1000";
		}
		return result;
	}
	
	public Map<String, Map<String, String>> getNodes() {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		String sql = "select ID, VERSION, SHARDING_PREFIX, BASE_URL, LAST_HEARTBEAT from RM_NODE_HEARTBEAT where " + buildWhereActive();
		List<RmCommonVo> nodes = RmBeanHelper.getCommonServiceInstance().doQuery(sql);
		for(RmCommonVo node : nodes) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			nodeMap.put(NodeKey.id.name(), node.getString("ID"));
			nodeMap.put(NodeKey.shardingPrefix.name(), node.getString("SHARDING_PREFIX"));
			nodeMap.put(NodeKey.baseUrl.name(), node.getString("BASE_URL"));
			nodeMap.put(NodeKey.webServiceUrl.name(), node.getString("BASE_URL") + "/services/");
			result.put(node.getString("ID"), nodeMap);
		}
		return result;
	}

	@Override
	public void destroy() {
		NodeHeartbeatDaemon.getSingleton().shutdown();
		String sql = "delete from RM_NODE_HEARTBEAT where ID=?";
		RmBeanHelper.getCommonServiceInstance().doUpdate(sql, new Object[]{getSelfId()});
	}
}

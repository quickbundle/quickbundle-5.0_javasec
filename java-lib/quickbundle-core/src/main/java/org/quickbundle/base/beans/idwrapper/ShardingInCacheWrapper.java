package org.quickbundle.base.beans.idwrapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.quickbundle.base.beans.AbstractDbWrapper;
import org.quickbundle.base.beans.TableIdRuleVo;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.itf.base.IRmIdWrapper;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

public class ShardingInCacheWrapper extends AbstractDbWrapper implements IRmIdWrapper {
	
	//生产模式下的ID增长Long
	private AtomicLong atomicLong = null;
	
	public ShardingInCacheWrapper(TableIdRuleVo ruleVo) {
		super(ruleVo);
	}
	
	public void init() {
    	String sql = getSqlSelectMax();
		try {
			List<String> maxIds = RmBeanHelper.getCommonServiceInstance().queryForList(sql, String.class);
			long lTableId = getMaxIdOrDefault(maxIds.size()==0 ? null : maxIds.get(0));
			atomicLong = new AtomicLong(lTableId);
		} catch (Exception e) {
			e.printStackTrace();
			RmLogHelper.getLogger(RmIdFactory.class).error("初始化" + ruleVo.getTableName() + "的最大id失败:" + e.toString());
		}
	}

	public synchronized String[] nextValue(int length) {
        //返回值
        String[] ids = new String[length];
        for (int i = 0; i < ids.length; i++) {
        	ids[i] = String.valueOf(atomicLong.getAndIncrement());
		}
        return ids;
	}

}

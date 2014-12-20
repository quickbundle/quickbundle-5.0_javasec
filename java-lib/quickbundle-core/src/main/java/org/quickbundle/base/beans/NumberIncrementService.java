package org.quickbundle.base.beans;

import java.util.List;

import org.quickbundle.tools.context.RmBeanHelper;
import org.springframework.dao.OptimisticLockingFailureException;

public class NumberIncrementService {

	public long initIdPool(TableIdRuleVo ruleVo, long blocksize) {
		long oldId;
		List<Long> oldPoolLastIds = RmBeanHelper.getCommonServiceInstance().queryForList("select LAST_ID from RM_ID_POOL where ID=?",
				new String[] { ruleVo.getTableName() }, Long.class);
		if(oldPoolLastIds.size() == 0) {
			oldId = getMaxIdFromTable(ruleVo);
		} else {
			oldId = oldPoolLastIds.get(0).longValue();
		}
		long newPoolLastId = oldId + blocksize;
		if(oldPoolLastIds.size() == 0) {
			RmBeanHelper.getCommonServiceInstance().doUpdate("insert into RM_ID_POOL (ID, VERSION, LAST_ID) values(?, ?, ?)",
					new Object[] { ruleVo.getTableName(), 1, newPoolLastId });
		} else {
			int updateCount = RmBeanHelper.getCommonServiceInstance().doUpdate("update RM_ID_POOL set LAST_ID=?, VERSION=VERSION+1 where ID=? and LAST_ID=?",
					new Object[] { newPoolLastId, ruleVo.getTableName(),  oldPoolLastIds.get(0) });
			if(updateCount == 0) {
				throw new OptimisticLockingFailureException("can not update lastId that read this time: " + ruleVo.getTableName());
			}
		}
		return oldId;
	}

	private long getMaxIdFromTable(TableIdRuleVo ruleVo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select max(");
		sql.append(ruleVo.getIdName());
		sql.append(") max_id from ");
		sql.append(ruleVo.getTableName());
		List<Long> maxIdObjs = RmBeanHelper.getCommonServiceInstance().queryForList(sql.toString(), Long.class);
		long maxId;
		if (maxIdObjs.size() == 0 || maxIdObjs.get(0) == null) {
			maxId = 0;
		} else {
			maxId = maxIdObjs.get(0).longValue();
		}
		maxId++;
		return maxId;
	}

	public long acquireId(TableIdRuleVo ruleVo, long blocksize) {
		List<Long> oldPoolLastIds = RmBeanHelper.getCommonServiceInstance().queryForList("select LAST_ID from RM_ID_POOL where ID=?",
				new String[] { ruleVo.getTableName() }, Long.class);
		long newPoolLastId = oldPoolLastIds.get(0) + blocksize;
		int updateCount = RmBeanHelper.getCommonServiceInstance().doUpdate("update RM_ID_POOL set LAST_ID=?, VERSION=VERSION+1 where ID=? and LAST_ID=?",
				new Object[] { newPoolLastId, ruleVo.getTableName(), oldPoolLastIds.get(0) });
		if (updateCount == 0) {
			throw new OptimisticLockingFailureException("can not update lastId that read this time: " + ruleVo.getTableName());
		}
		return oldPoolLastIds.get(0).longValue();
	}
}

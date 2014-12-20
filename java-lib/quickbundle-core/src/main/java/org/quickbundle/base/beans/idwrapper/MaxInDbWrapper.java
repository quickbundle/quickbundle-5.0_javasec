package org.quickbundle.base.beans.idwrapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.quickbundle.base.beans.AbstractDbWrapper;
import org.quickbundle.base.beans.TableIdRuleVo;
import org.quickbundle.itf.base.IRmIdWrapper;
import org.quickbundle.tools.context.RmBeanHelper;

public class MaxInDbWrapper extends AbstractDbWrapper implements IRmIdWrapper {

	// 开发模式下每次都查表获得最新ID的SQL
	private String sqlSelectMax = null;

	// 生产模式下的ID增长Long
	private AtomicLong atomicLong = null;

	public MaxInDbWrapper(TableIdRuleVo ruleVo) {
		super(ruleVo);
	}

	public void init() {
		sqlSelectMax = getSqlSelectMax();
	}

	public synchronized String[] nextValue(int length) {
		// generateIdFromDb模式下每次都查询的sql
		List<String> maxIds = RmBeanHelper.getCommonServiceInstance().queryForList(sqlSelectMax, String.class);
		long lTableId = getMaxIdOrDefault(maxIds.size() == 0 ? null : maxIds.get(0));
		if (atomicLong == null || atomicLong.longValue() < lTableId) {
			atomicLong = new AtomicLong(lTableId);
		}
		// 返回值
		String[] ids = new String[length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = String.valueOf(atomicLong.getAndIncrement());
		}
		return ids;
	}

}

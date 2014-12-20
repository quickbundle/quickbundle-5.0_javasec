package org.quickbundle.itf.base;

public interface IRmIdGenerator {
	public void init();
	public String[] requestIdInner(String tableName, int length);
}

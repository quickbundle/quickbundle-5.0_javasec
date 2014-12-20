package org.quickbundle.third.proxool;

import org.dom4j.Element;

public class MyProxoolDataSource extends RmProxoolDataSource {
	public MyProxoolDataSource() {
		super();
		initConnectionInfo();
	}

	public MyProxoolDataSource(String alias) {
		super(alias);
		initConnectionInfo();
	}

	private void initConnectionInfo() {
		try {
			Element dataSource = null;
			{
				// TODO
			}
			setDriver(dataSource.valueOf("driverClassName"));
			setDriverUrl(dataSource.valueOf("databaseUrl"));
			setUser(dataSource.valueOf("user"));
			String password = dataSource.valueOf("password");
			setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

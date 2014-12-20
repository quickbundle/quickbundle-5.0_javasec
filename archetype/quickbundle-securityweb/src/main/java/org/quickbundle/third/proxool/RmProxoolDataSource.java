package org.quickbundle.third.proxool;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolDataSource;

public class RmProxoolDataSource extends ProxoolDataSource {
	
    public RmProxoolDataSource() {
        super();
    }

    public RmProxoolDataSource (String alias) {
        super(alias);
    }
    
    /**
     * @see ConnectionPoolDefinitionIF#getMaximumConnectionLifetime
     */
    //qb-rm fix bug
    public void setMaximumConnectionLifetime(long maximumConnectionLifetime) {
        super.setMaximumConnectionLifetime((int)maximumConnectionLifetime);
    }
    
    /**
     * @see ConnectionPoolDefinitionIF#getHouseKeepingSleepTime
     */
    //QB-RM fix bug
    public void setHouseKeepingSleepTime(long houseKeepingSleepTime) {
        super.setHouseKeepingSleepTime((int)houseKeepingSleepTime);
    }
    
    /**
     * @see ConnectionPoolDefinitionIF#getOverloadWithoutRefusalLifetime
     */
    public void setOverloadWithoutRefusalLifetime(long overloadWithoutRefusalLifetime) {
        super.setOverloadWithoutRefusalLifetime((int)overloadWithoutRefusalLifetime);
    }
    
    /**
     * @see ConnectionPoolDefinitionIF#getRecentlyStartedThreshold
     */
    public void setRecentlyStartedThreshold(long recentlyStartedThreshold) {
        super.setRecentlyStartedThreshold((int)recentlyStartedThreshold);
    }
    
    //qb-rm
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}

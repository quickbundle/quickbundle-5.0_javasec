package org.quickbundle.third.mybatis;

import java.sql.Statement;
import java.util.Properties;
//TODO 
//import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.quickbundle.tools.helper.RmReflectHelper;

@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class ScrollableResultSetHandlerInterceptor implements Interceptor {
	public Object intercept(Invocation invocation) throws Throwable {
		return null;
		/*
		if(!(invocation.getTarget() instanceof FastResultSetHandler)) {
			return invocation.proceed();
		}
		FastResultSetHandler resultSet = (FastResultSetHandler) invocation.getTarget();

		RowBounds rowBounds = (RowBounds) RmReflectHelper.getFieldValue(resultSet, "rowBounds");

		//if (rowBounds.getLimit() > 0 && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
			RmReflectHelper.setFieldValue(resultSet, "rowBounds", new RowBounds());
		//}
		return invocation.proceed();
		*/
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}
}

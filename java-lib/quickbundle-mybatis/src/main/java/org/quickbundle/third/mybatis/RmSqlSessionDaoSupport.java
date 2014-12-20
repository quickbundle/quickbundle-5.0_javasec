package org.quickbundle.third.mybatis;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class RmSqlSessionDaoSupport extends SqlSessionDaoSupport {
	@Override
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	protected SqlSessionTemplate getSqlSessionTemplate() {
		SqlSession ss = super.getSqlSession();
		if(ss instanceof SqlSessionTemplate) {
			return (SqlSessionTemplate)ss;
		}
		return null;
	}

	/**
	 * escape转义sql输入值，防止sql注入
	 * 
	 * @param searchPara
	 * @param escapeKey
	 * @return 转义后的sql值
	 */
	protected void escapeSqlValue(Map<String, Object> searchPara, String[] escapeKeys) {
		Object orderStr = searchPara.get("orderStr");
		if(orderStr != null) {
			//
			searchPara.put("orderStr", orderStr.toString().replaceAll("[\\s]", ""));
		}
		if(escapeKeys == null) {
			return;
		}
		for(String key : escapeKeys) {
			Object original = searchPara.get(key);
			if(original != null) {
				searchPara.put(key, RmSqlHelper.escapeSqlValue(original.toString(), RmSqlHelper.TYPE_CHAR_LIKE));
			}
		}
	}
	
	protected String namespace(String id) {
		return this.getClass().getName() + "." + id;
	}
}

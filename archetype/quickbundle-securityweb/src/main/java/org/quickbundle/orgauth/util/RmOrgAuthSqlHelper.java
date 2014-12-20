package org.quickbundle.orgauth.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.tools.helper.RmStringHelper;

import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;

public class RmOrgAuthSqlHelper implements IGlobalConstants{
	/**
	 * 获得in模式的取权限sql
	 * 注意：性能较低，仅适用小数据量模式
	 * 
	 * @param party_ids
	 * @return
	 */
	public static String getSqlInAuthorize(RmAuthorizeVo authorize, String[] party_ids, String sqlAfterFrom) {
		if(authorize == null) {
			throw new RmRuntimeException("权限对象为空，请先配置好权限类别");
		}
		Document authRule = authorize.getRuleCustomCode();
		if(authRule == null) {
			throw new RmRuntimeException("权限的配置规则为空，不能构建in模式的sql");
		}
		Element eleTable = (Element)authRule.selectSingleNode("/authorize/consumer/table[@sql_after_from='" + sqlAfterFrom.toUpperCase() + "']");
		String old_resource_id_full = eleTable.valueOf("@old_resource_id_full");
		if(old_resource_id_full == null || old_resource_id_full.length() == 0) {
			old_resource_id_full = "RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct(");
		sb.append(old_resource_id_full);
		sb.append(") from RM_AUTHORIZE_RESOURCE left join RM_AUTHORIZE_RESOURCE_RECORD on RM_AUTHORIZE_RESOURCE.ID=RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_RESOURCE_ID and ");
		sb.append("RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID=");
		sb.append(authorize.getId());
		sb.append(" where RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS='1' or RM_AUTHORIZE_RESOURCE_RECORD.PARTY_ID in(");
		sb.append(RmStringHelper.parseToSQLString(party_ids));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 获得join模式的取权限sql，通过传入RmAuthorizeVo和sqlAfterFrom自动读取RM_AUTHORIZE_VO的配置
	 * 
	 * @param authorize RmAuthorizeVo缓存对象
	 * @param party_ids 查询权限的party_id列表
	 * @param sqlAfterSelect sql中select关键字后的字段部分
	 * @param sqlAfterFrom sql中from关键字后的部分，可能是多表，比如table1 join table2 on table1.column1 on table2.column2
	 * @param sqlAfterWhere sql中where关键字后的部分
	 * @return
	 */
	public static String getSqlJoinAuthorize(RmAuthorizeVo authorize, String[] party_ids, String sqlAfterSelect, String sqlAfterFrom, String sqlAfterWhere) {
		if(authorize == null) {
			throw new RmRuntimeException("权限对象为空，请先配置好权限类别");
		}
		Document authRule = authorize.getRuleCustomCode();
		if(authRule == null) {
			throw new RmRuntimeException("权限的配置规则为空，不能构建join模式的sql");
		}
		Element eleTable = (Element)authRule.selectSingleNode("/authorize/consumer/table[@sql_after_from='" + sqlAfterFrom.toUpperCase() + "']");
		if(eleTable == null) {
			throw new RmRuntimeException("权限的配置规则出错，未找到规则:" + "/authorize/consumer/table[@sql_after_from='" + sqlAfterFrom.toUpperCase() + "']");
		}
		String join_table = eleTable.valueOf("@join_table");
		String join_table_column = eleTable.valueOf("@join_table_column");
		String join_table_column_full = authRule.valueOf("@join_table_column_full");
		if(join_table_column_full == null || join_table_column_full.length() == 0) {
			join_table_column_full = join_table + "." + join_table_column;
		}
		return getSqlJoinAuthorize(party_ids, sqlAfterSelect, sqlAfterFrom, "RM_AUTHORIZE_RESOURCE", "RM_AUTHORIZE_RESOURCE_RECORD", join_table_column_full, authorize.getId(), sqlAfterWhere);
	}
	
	/**
	 * 获得join模式的取权限sql，适用于和单表join联查
	 * 
	 * @param authorizeId RM_AUTHORIZE的id 
	 * @param party_ids 查询权限的party_id列表
	 * @param sqlAfterSelect sql中select关键字后的字段部分
	 * @param joinTable 和权限表进行join关联的表名
	 * @param joinTableColumn 和权限表进行join关联的表的列名，可以是"列名", "表名.列名", 或"TO_CHAR(表名.列名)"
	 * @param sqlAfterWhere sql中where关键字后的部分
	 * @return
	 */
	public static String getSqlJoinAuthorize(String authorizeId, String[] party_ids, String sqlAfterSelect, String joinTable, String joinTableColumn, String sqlAfterWhere) {
		String joinTableColumnFull = null;
		if(joinTableColumn.trim().toUpperCase().indexOf(joinTable.trim().toUpperCase() + ".") > -1) {
			joinTableColumnFull = joinTableColumn;
		} else {
			joinTableColumnFull = joinTable + "." + joinTableColumn;
		}
		return getSqlJoinAuthorize(party_ids, sqlAfterSelect, joinTable, "RM_AUTHORIZE_RESOURCE", "RM_AUTHORIZE_RESOURCE_RECORD", joinTableColumnFull, authorizeId, sqlAfterWhere);
	}
	
	/**
	 * 获得join模式的取权限sql，支持多表join联查
	 * 此方法参数复杂，尽量减少直接调用
	 * 
	 * @param party_ids 查询权限的party_id列表
	 * @param sqlAfterSelect sql中select关键字后的字段部分
	 * @param sqlAfterFrom sql中from关键字后的部分，可能是多表，比如table1 join table2 on table1.column1 on table2.column2
	 * @param authorizeResourceTable  RM_AUTHORIZE_RESOURCE的实际表名，可在RM_AUTHORIZE定义时定义拆分新表
	 * @param authorizeResourceRecordTable RM_AUTHORIZE_RESOURCE_RECORD的实际表名，可在RM_AUTHORIZE定义时定义拆分新表
	 * @param joinTableColumnFull 和权限表进行join关联的"表名.列名"，或"TO_CHAR(表名.列名)"
	 * @param authorizeId RM_AUTHORIZE的id 
	 * @param sqlAfterWhere sql中where关键字后的部分
	 * @return 拼装后的sql语句
	 */
	static String getSqlJoinAuthorize(String[] party_ids, String sqlAfterSelect, String sqlAfterFrom, String authorizeResourceTable, String authorizeResourceRecordTable, String joinTableColumnFull, String authorizeId, String sqlAfterWhere) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append(sqlAfterSelect);
		sb.append(" from ");
		sb.append(sqlAfterFrom);
		sb.append(" join ");
		sb.append(authorizeResourceTable);
		sb.append(" on ");
		sb.append(joinTableColumnFull);
		sb.append("=");
		sb.append(authorizeResourceTable);
		sb.append(".OLD_RESOURCE_ID");
		sb.append(" where ");
		sb.append(authorizeResourceTable);
		sb.append(".");
		sb.append("AUTHORIZE_ID=");
		sb.append(authorizeId);
		sb.append(" and (");;
		sb.append(authorizeResourceTable);
		sb.append(".DEFAULT_ACCESS='1' or ");
		sb.append("exists(select ID from ");
		sb.append(authorizeResourceRecordTable);
		sb.append(" where ");
		sb.append(authorizeResourceRecordTable);
		sb.append(".AUTHORIZE_RESOURCE_ID=");
		sb.append(authorizeResourceTable);
		sb.append(".ID and ");
		sb.append(authorizeResourceRecordTable);
		sb.append(".PARTY_ID in(");
		sb.append(RmStringHelper.parseToSQLString(party_ids));
		sb.append(")))");
		if(sqlAfterWhere != null && sqlAfterWhere.trim().length() > 0) {
			if(!sqlAfterWhere.trim().toUpperCase().startsWith("AND")) {
				sb.append(" and ");
			}
			sb.append(sqlAfterWhere);
		}
		return sb.toString();
	}
	
	/**
	 * @deprecated 授权给authorizeResourceRecordTable多次后有重复记录，弃用
	 * 获得join模式的取权限sql，支持多表join联查
	 * 此方法参数复杂，尽量减少使用
	 * 
	 * @param party_ids 查询权限的party_id列表
	 * @param sqlAfterSelect sql中select关键字后的字段部分
	 * @param sqlAfterFrom sql中from关键字后的部分，可能是多表，比如table1 join table2 on table1.column1 on table2.column2
	 * @param authorizeResourceTable  RM_AUTHORIZE_RESOURCE的实际表名，可在RM_AUTHORIZE定义时定义拆分新表
	 * @param authorizeResourceRecordTable RM_AUTHORIZE_RESOURCE_RECORD的实际表名，可在RM_AUTHORIZE定义时定义拆分新表
	 * @param joinTableColumnFull 和权限表进行join关联的"表名.列名"，或"TO_CHAR(表名.列名)"
	 * @param authorizeId RM_AUTHORIZE的id 
	 * @param sqlAfterWhere sql中where关键字后的部分
	 * @return 拼装后的sql语句
	 */
	static String getSqlJoinAuthorize_join2table(String[] party_ids, String sqlAfterSelect, String sqlAfterFrom, String authorizeResourceTable, String authorizeResourceRecordTable, String joinTableColumnFull, String authorizeId, String sqlAfterWhere) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		
		sb.append(sqlAfterSelect);
		//原样输出，废弃多余的处理
/*		if(sqlAfterSelect.trim().toUpperCase().matches("COUNT\\(\\s*\\*\\s*\\)")) {
			sb.append("count("); //distinct()
			sb.append(authorizeResourceTable);
			sb.append(".OLD_RESOURCE_ID)");
		} else {
			sb.append("distinct(");
			sb.append(authorizeResourceTable);
			sb.append(".OLD_RESOURCE_ID), ");
			sb.append(sqlAfterSelect);
		}*/
		sb.append(" from ");
		sb.append(sqlAfterFrom);
		sb.append(" join ");
		sb.append(authorizeResourceTable);
		sb.append(" on ");
		sb.append(joinTableColumnFull);
		sb.append("=");
		sb.append(authorizeResourceTable);
		sb.append(".OLD_RESOURCE_ID");
		sb.append(" left join ");
		sb.append(authorizeResourceRecordTable);
		sb.append(" on ");
		sb.append(authorizeResourceTable);
		sb.append(".ID=");
		sb.append(authorizeResourceRecordTable);
		sb.append(".AUTHORIZE_RESOURCE_ID and ");
		sb.append(authorizeResourceTable);
		sb.append(".AUTHORIZE_ID=");
		sb.append(authorizeId);
		sb.append(" where ");
		sb.append(authorizeResourceTable);
		sb.append(".DEFAULT_ACCESS='1' or ");
		sb.append(authorizeResourceRecordTable);
		sb.append(".PARTY_ID in(");
		sb.append(RmStringHelper.parseToSQLString(party_ids));
		sb.append(")");
		if(sqlAfterWhere != null && sqlAfterWhere.trim().length() > 0) {
			if(!sqlAfterWhere.trim().toUpperCase().startsWith("AND")) {
				sb.append(" and ");
			}
			sb.append(sqlAfterWhere);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param dictionaryType
	 * @param dictionaryUsableStatus
	 * @param party_ids
	 * @return
	 */
	public static String getSqlRmCodeAuthorize(RmAuthorizeVo authorize, String[] party_ids, String dictionaryType, int dictionaryUsableStatus) {
		String sqlAfterSelect = "RM_CODE_DATA.DATA_KEY, RM_CODE_DATA.DATA_VALUE";
		String sqlAfterFrom = "RM_CODE_TYPE JOIN RM_CODE_DATA ON RM_CODE_TYPE.ID=RM_CODE_DATA.CODE_TYPE_ID";
		String joinTable = "RM_CODE_DATA";
		String joinTableColumn = "DATA_KEY";
		String sqlAfterWhere = "RM_CODE_TYPE.TYPE_KEYWORD='" + dictionaryType + "'";
		if(dictionaryUsableStatus == 1) {
			sqlAfterWhere = " and RM_CODE_TYPE.USABLE_STATUS='1'";
		}
		sqlAfterWhere += " order by RM_CODE_DATA.DATA_KEY";
		return getSqlJoinAuthorize(party_ids, sqlAfterSelect, sqlAfterFrom, "RM_AUTHORIZE_RESOURCE", "RM_AUTHORIZE_RESOURCE_RECORD", joinTable + "." + joinTableColumn, authorize.getId(), sqlAfterWhere);
	}
	
	public static void main(String[] args) {
		System.out.println(getSqlJoinAuthorize(IOrgauthConstants.Authorize.FUNCTION_NODE.id(), new String[]{"111"}, "count(*)", "RM_FUNCTION_NODE", "TOTAL_CODE", "ENABLE_STATUS='1'"));
	}
}
package org.quickbundle.mda.gc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class MetadataHelper {

	/**
	 * 从rule.xml中读配置,取出table_name表的元数据
	 * @param myConn
	 * @param catalog
	 * @param schemaPattern
	 * @param table_name
	 * @param docRules
	 * @param pdmParser
	 * @return
	 * @throws Exception
	 */
	public static Document getMetaDataXml(Connection myConn, String catalog, String schemaPattern, String table_name, Document docRules, PdmParser pdmParser)
			throws Exception {
		Document doc = DocumentHelper.createDocument();
		Element meta = doc.addElement("meta");
		Element project = meta.addElement("project");
		Element database = meta.addElement("database");
		Element relations = meta.addElement("relations");
		Element tables = meta.addElement("tables");

		initDatabase(database, myConn, catalog, schemaPattern, table_name, docRules, pdmParser);
		initProject(project, myConn, catalog, schemaPattern, table_name, docRules, pdmParser);

		Element table = tables.addElement("table");
		initTable(table, myConn, catalog, schemaPattern, table_name, docRules, pdmParser);

		return doc;
	}

	private static void initTable(Element table, Connection myConn, String catalog, String schemaPattern, String table_name, Document docRules,
			PdmParser pdmParser) throws SQLException {
		table.addAttribute("tableName", table_name);
		if (pdmParser != null && pdmParser.getMPdmColumn() != null && pdmParser.getMPdmColumn().containsKey(table_name)) {
			String[] aColumnInfo = (String[]) pdmParser.getMPdmColumn().get(table_name);
			table.addAttribute("tableNameDisplay", aColumnInfo[0]);
			table.addAttribute("comment", aColumnInfo[1]);
			table.addAttribute("attachedRules", aColumnInfo[2]);
		} else {
			table.addAttribute("tableNameDisplay", table_name);
			table.addAttribute("comment", "");
			table.addAttribute("attachedRules", "");
		}
		table.addAttribute("tableFilterKeyword", getFormatTableName(table_name));
		table.addAttribute("tableDirName", getFormatTableName(table_name).toLowerCase());
		table.addAttribute("tablePk", "");
		table.addAttribute("statisticColumn", "");
		table.addAttribute("keyColumn", "");
		
		String firstCatalog_schema_tableName = null; // 定义第一个遇到的表名

		// 设置table注释
		ResultSet rsTableComment = myConn.getMetaData().getTables(catalog, schemaPattern, table_name, null);
		while (rsTableComment.next()) {
			String currentCatalog_schema_tableName = rsTableComment.getString(1) + rsTableComment.getString(2) + rsTableComment.getString(3);
			if (firstCatalog_schema_tableName == null) {
				firstCatalog_schema_tableName = currentCatalog_schema_tableName;
			} else if (currentCatalog_schema_tableName.equals(firstCatalog_schema_tableName)) {

			} else { // 如果这次取到的表空间不等于上次，则弃之
				// QbXmlGenerateCodePlugin.log("在这次生成代码的会话中," + table_name +
				// "表在不同的catalog或schema中出现多次!");
				continue;
			}
			table.addAttribute("tableComment", RmStringHelper.prt(rsTableComment.getString(5)));
		}
		// 设置每列属性
		ResultSet rsColumns = myConn.getMetaData().getColumns(catalog, schemaPattern, table_name, null);
		while (rsColumns.next()) {
			String currentCatalog_schema_tableName = rsColumns.getString(1) + rsColumns.getString(2) + rsColumns.getString(3);
			if (firstCatalog_schema_tableName == null) {
				firstCatalog_schema_tableName = currentCatalog_schema_tableName;
			} else if (currentCatalog_schema_tableName.equals(firstCatalog_schema_tableName)) {
			} else { // 如果这次取到的表空间不等于上次，则弃之
				continue;
			}
			Element elementColumn = table.addElement("column");
			elementColumn.addAttribute("columnName", RmStringHelper.prt(rsColumns.getString(4)));

			String tempColumnName = rsColumns.getString(4);

			String tempColumnNameDisplay = RmStringHelper.prt(rsColumns.getString(4)).toLowerCase();
			String tempComment = RmStringHelper.prt(rsColumns.getString(12));
			String tempAttachedRules = "";
			if (pdmParser != null && pdmParser.getMPdmColumn() != null && pdmParser.getMPdmColumn().containsKey(table_name + ":" + tempColumnName)) {
				String[] aColumnInfo = (String[]) pdmParser.getMPdmColumn().get(table_name + ":" + tempColumnName);
				tempColumnNameDisplay = aColumnInfo[0];
				if (aColumnInfo[1] != null && aColumnInfo[1].trim().length() > 0) {
					tempComment = aColumnInfo[1];
				}
				tempAttachedRules = aColumnInfo[2];
			}
			elementColumn.addAttribute("columnNameDisplay", tempColumnNameDisplay); // 临时
			elementColumn.addAttribute("dataType", RmStringHelper.prt(rsColumns.getString(5)));
			elementColumn.addAttribute("isBuild", "true"); // 临时
			elementColumn.addAttribute("isBuild_list", "true"); // 临时
			elementColumn.addAttribute("filterKeyword", "");
			elementColumn.addAttribute("filterType", "default"); // 临时
			elementColumn.addAttribute("maxLength", RmStringHelper.prt(rsColumns.getString(7)));
			elementColumn.addAttribute("decimalDigits", RmStringHelper.prt(rsColumns.getString(9)));
			elementColumn.addAttribute("comment", tempComment);
			elementColumn.addAttribute("dataTypeDb", RmStringHelper.prt(rsColumns.getString(6)));
			String defaultValue = RmStringHelper.prt(rsColumns.getString(13));
			if (defaultValue != null) {
				defaultValue = defaultValue.trim();
				if (defaultValue.matches("^\\s*'.*'\\s*$")) {
					defaultValue = defaultValue.replaceAll("^\\s*'(.*)'\\s*$", "$1");
				}
			}
			elementColumn.addAttribute("defaultValue", RmStringHelper.prt(defaultValue));
			elementColumn.addAttribute("nullable", RmStringHelper.prt(rsColumns.getString(18)));

			String[] aStr = getHumanCodeTypeData(tempComment);
			elementColumn.addAttribute("humanDisplayType", aStr[0]);
			elementColumn.addAttribute("humanDisplayTypeKeyword", aStr[1]);
			elementColumn.addAttribute("humanDisplayTypeData", aStr[2]);

			elementColumn.addAttribute("attachedRules", tempAttachedRules);
		}

		{ // get pk
			ResultSet rsPk = myConn.getMetaData().getPrimaryKeys(null, null, table_name);
			String pkStr = "";

			while (rsPk.next()) {
				String currentCatalog_schema_tableName = rsPk.getString(1) + rsPk.getString(2) + rsPk.getString(3);
				if (firstCatalog_schema_tableName == null) {
					firstCatalog_schema_tableName = currentCatalog_schema_tableName;
				} else if (currentCatalog_schema_tableName.equals(firstCatalog_schema_tableName)) {
				} else { // 如果这次取到的表空间不等于上次，则弃之
					continue;
				}
				if (!"".equals(pkStr)) {
					pkStr += ",";
				}
				pkStr += rsPk.getString(4);
			}
			/*
			 * 显示到外边，小地雷 if (pkStr.indexOf(",") > 0) { throw new
			 * Exception("暂不支持生成复合主键的单表"); } if (pkStr.length() == 0) { throw
			 * new Exception("暂不支持生成没有任何主键的单表"); }
			 */
			table.addAttribute("tablePk", pkStr);
			// 主键默认不生成
			if (table.selectSingleNode("column[@columnName='" + pkStr + "']/@isBuild") != null) {
				table.selectSingleNode("column[@columnName='" + pkStr + "']/@isBuild").setText("false");
				table.selectSingleNode("column[@columnName='" + pkStr + "']/@isBuild_list").setText("false");
			}
		}
		Map<String, String> mFk_parentTablename = new HashMap<String, String>();
		{ // TODO get fk
			// String parentTableName = "";
			String statisticColumn = "";
			String firstCatalog_schema_tableName_fk = null; // 定义第一个遇到的表名
			ResultSet rsFk = myConn.getMetaData().getImportedKeys(catalog, schemaPattern, table_name);
			if (!rsFk.next()) {
				rsFk = myConn.getMetaData().getImportedKeys(catalog, schemaPattern, table_name.toLowerCase());
			} else {
				rsFk = myConn.getMetaData().getImportedKeys(catalog, schemaPattern, table_name);
			}
			while (rsFk.next()) {
				String currentCatalog_schema_tableName = rsFk.getString(5) + rsFk.getString(6) + rsFk.getString(7);
				if (firstCatalog_schema_tableName_fk == null) {
					firstCatalog_schema_tableName_fk = currentCatalog_schema_tableName;
				} else if (currentCatalog_schema_tableName.equals(firstCatalog_schema_tableName_fk)) {

				} else { // 如果这次取到的表空间不等于上次，则弃之
					continue;
				}
				if (!"".equals(statisticColumn)) {
					statisticColumn += ",";
				}
				mFk_parentTablename.put(rsFk.getString(8), rsFk.getString(3));
				statisticColumn += rsFk.getString(8);
				break; // 重要!只取第一个统计列
			}
			if ("".equals(statisticColumn)) { // 默认等于主键
				statisticColumn = table.attributeValue("tablePk");
			}
			table.addAttribute("statisticColumn", statisticColumn);
		}

		{ // 初始化关键列
			table.addAttribute("keyColumn", table.attributeValue("tablePk")); // 让关键列默认等于pk
			List<Element> lEleColumn = table.selectNodes("column");
			String keyColumnMatch = docRules.valueOf("/rules/profileConfig/keyColumnMatch/text()");
			Pattern pKeyColumn = Pattern.compile(keyColumnMatch, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			for (Element eleColumn : lEleColumn) {
				if (pKeyColumn.matcher(eleColumn.valueOf("@columnName")).find()) { // 如果有NAME或TITLE，赋值给关键列
					table.addAttribute("keyColumn", eleColumn.valueOf("@columnName"));
				}
			}

		}
		ResultSetMetaData rsColumns4JavaDataType = myConn.prepareStatement("SELECT * FROM " + table_name).executeQuery().getMetaData();
		for (int i = 1; i <= rsColumns4JavaDataType.getColumnCount(); i++) {
			String columnName = rsColumns4JavaDataType.getColumnName(i);
			Element column = (Element) table.selectSingleNode("column[@columnName='" + columnName + "']");
			column.addAttribute("dataType", rsColumns4JavaDataType.getColumnClassName(i));
			{// 针对number(19)，将其强制转化为Long
				if ((BigDecimal.class.getName().equals(column.valueOf("@dataType")) || Long.class.getName().equals(column.valueOf("@dataType")))
						&& "19".equals(column.valueOf("@maxLength")) && "0".equals(column.valueOf("@decimalDigits"))) {
					column.addAttribute("dataType", "java.lang.Long");
				}
			}
		}
		// 把外键一律定义为rm.listReference
		for (Iterator<String> itMFk_ptn = mFk_parentTablename.keySet().iterator(); itMFk_ptn.hasNext();) {
			String fk = itMFk_ptn.next().toString();
			if (table.selectSingleNode("column[@columnName='" + fk + "']") != null) {
				Element eleFk = (Element) table.selectSingleNode("column[@columnName='" + fk + "']");
				eleFk.addAttribute("humanDisplayType", "rm.listReference");
				eleFk.addAttribute("humanDisplayTypeKeyword", getFormatTableName(mFk_parentTablename.get(fk).toString()).toLowerCase());
				eleFk.addAttribute("humanDisplayTypeData", "");
			}
		}
		{ // 初始化默认勾选的组件
			Set<String> sBundleCode = new HashSet<String>();
			if (!table.valueOf("@statisticColumn").equals(table.valueOf("@tablePk"))) {
				sBundleCode.add("condition");
			}
			List<Element> lBundle = docRules.selectNodes("/rules/customBundleCode/bundle");
			for (Element bundle : lBundle) {
				if ("true".equals(bundle.valueOf("@checked"))) {
					sBundleCode.add(bundle.valueOf("@code"));
				} else if ("false".equals(bundle.valueOf("@checked"))) {
					sBundleCode.remove(bundle.valueOf("@code"));
				}
			}
			StringBuilder sbCustomBundleCode = new StringBuilder();
			for (String code : sBundleCode) {
				if (sbCustomBundleCode.length() > 0) {
					sbCustomBundleCode.append(",");
				}
				sbCustomBundleCode.append(code);
			}
			table.addAttribute("customBundleCode", sbCustomBundleCode.toString());
		}
	}

	private static void initDatabase(Element database, Connection myConn, String catalog, String schemaPattern, String table_name, Document docRules,
			PdmParser pdmParser) throws SQLException {
		database.addElement("driver").setText(docRules.valueOf("/rules/database/@driver"));
		database.addElement("url").setText(docRules.valueOf("/rules/database/@url"));
		database.addElement("userName").setText(docRules.valueOf("/rules/database/@userName"));
		database.addElement("password").setText(docRules.valueOf("/rules/database/@password"));
		database.addElement("dbProductName").setText(myConn.getMetaData().getDatabaseProductName());
	}

	private static void initProject(Element project, Connection myConn, String catalog, String schemaPattern, String table_name, Document docRules,
			PdmParser pdmParser) {
		RmXmlHelper.deepCopyElementWithClear((Element)docRules.selectSingleNode("/rules/project"), project);
	}

	/**
	 * 功能: 获得Java规范后的表名
	 * 
	 * @param table_name
	 * @return
	 */
	public static String getFormatTableName(String table_name) {
		if (table_name == null || table_name.length() == 0) {
			return table_name;
		}
		String str = "";
		String[] aName = table_name.split("_");
		for (int i = 0; i < aName.length; i++) {
			str += toUpperFirst(aName[i]);
		}
		return str;
	}

	private static String toUpperFirst(String str) {
		if (str == null || str.length() == 0) {
			return str;
		} else {
			return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
		}
	}

	public static String[] getTableNames(Connection conn) throws SQLException {
		List<String> lTableName = new ArrayList<String>();
		ResultSet rsTable = conn.getMetaData().getTables("", "", "", new String[] { "TABLE" });
		while (rsTable.next()) {
			lTableName.add(rsTable.getString(3));
		}
		return lTableName.toArray(new String[0]);
	}

	private static String[] getHumanCodeTypeData(String comment) {
		String[] aStr = new String[] { "default", "", "" };
		try {
			Pattern p = Pattern.compile("^.*\\$(\\w+?)=(.*?)\\{(.*?)\\}.*$", Pattern.DOTALL);
			Matcher matchKey = p.matcher(comment);
			if (matchKey.find()) {
				MatchResult mr = matchKey.toMatchResult();
				if ("RM_YES_NOT".equals(mr.group(1))) {
					aStr[0] = "rm.dictionary.checkbox";
				} else {
					aStr[0] = "rm.dictionary.select";
				}
				aStr[1] = mr.group(1) + "=" + mr.group(2);
				aStr[2] = mr.group(3);
			}
		} catch (Exception e) {
			QbXmlGenerateCodePlugin.log(e.toString());
		}
		return aStr;
	}
}
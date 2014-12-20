package org.quickbundle.mda.gc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

public class TableRelation {
	GenerateCodeRule gcRule = null;
	public TableRelation(GenerateCodeRule gcRule) {
		this.gcRule = gcRule;
	}
	
	public List<String> getAvailableTables() {
		return new ArrayList<String>(gcRule.getMTableDocs().keySet());
	}
	
	public List<String> getAvailableMiddleTables(String parentTable) throws SQLException {
		List<String> result = new ArrayList<String>(gcRule.getMTableDocs().keySet());
		List<String> parentTableChild = getChildTable(parentTable);
		for(String tableName : parentTableChild) {
			if(!result.contains(tableName)) {
				result.add(tableName);
			}
		}
		return result;
	}
	

	public List<String> getSmallAvaliableMiddleTables(List<String> avaliableMiddleTables, Combo comboParentTableInner, Combo middleTable) throws SQLException {
		List<String> result = new ArrayList<String>(gcRule.getMTableDocs().keySet());
		List<String> lMiddleTableInDb = new ArrayList<String>(avaliableMiddleTables);
		lMiddleTableInDb.removeAll(gcRule.getMTableDocs().keySet());
		Combo refTableOfMiddleTable = guessRefTableOfMiddleTable(middleTable);
		if(refTableOfMiddleTable != null && !refTableOfMiddleTable.isDisposed()) {
			String refTable = refTableOfMiddleTable.getText();
			Map<String,String[]> mOneToMulti = getChildTableOneToMulti(gcRule.getConnection(), gcRule.getConfig1MainRuleWizardPage().getMContainerText("catalog"), 
					gcRule.getConfig1MainRuleWizardPage().getMContainerText("schemaPattern"), 
					refTable);
			Set<String> sChild = mOneToMulti.keySet();
			for(String middleTableInDb : lMiddleTableInDb) {
				if(sChild.contains(middleTableInDb)) {
					result.add(middleTableInDb);
				}
			}
		}
		return result;
	}
	
	public List<String> getMiddleTable(String parentTable, String refTable) throws SQLException {
		List<String> result = new ArrayList<String>();
		if((parentTable == null || parentTable.length() == 0) 
				&&(refTable == null || refTable.length() == 0)) {
			return result;
		} else if(parentTable == null || parentTable.length() == 0){
			return getChildTable(refTable);
		} else if(refTable == null || refTable.length() == 0) {
			return getChildTable(parentTable);
		} else {
			List<String> parentTableChild = getChildTable(parentTable);
			List<String> refTableChild = getChildTable(refTable);
			for(String table : parentTableChild) {
				if(refTableChild.contains(table)) {
					result.add(table);
				}
			}
		}
		return result;
	}
	
	List<String> getChildTable(String parentTable) throws SQLException {
		List<String> result = new ArrayList<String>();
		Map<String,String[]> children = getChildTableOneToMulti(gcRule.getConnection(), gcRule.getConfig1MainRuleWizardPage().getMContainerText("catalog"), 
				gcRule.getConfig1MainRuleWizardPage().getMContainerText("schemaPattern"), 
				parentTable);
		result.addAll(children.keySet());
		return result;
	}
	
	
	private Combo guessRefTableOfMiddleTable(Combo middleTable) {
		Control[] aWidgetMiddleRow = middleTable.getParent().getChildren();
		boolean findMiddleTable = false;
		int index = 0;
		for(Control widget : aWidgetMiddleRow) {
			if(widget == middleTable) {
				findMiddleTable = true;
			}
			if(findMiddleTable && widget instanceof Combo) {
				index ++;
				if(index == 4) {
					return (Combo) widget;
				}
			}
		}
		
		return null;
	}
    
	
	public List<String> getColumns(String table) {
		List<String> lColumn = new ArrayList<String>();
		Document docTable = (Document)gcRule.getMTableDocs().get(table);
		if(docTable == null) {
			return lColumn;
		}
		String pkColumn = docTable.valueOf("/meta/tables/table[@tableName='" + table + "']/@tablePk");
		lColumn.add(pkColumn);
		List<Element> tableColumns = docTable.selectNodes("/meta/tables/table[@tableName='" + table + "']/column");
		for(Element column : tableColumns) {
			if(pkColumn.equals(column.valueOf("@columnName"))) {
				continue;
			}
			lColumn.add(column.valueOf("@columnName"));
		}
		
		return lColumn;
	}
	
	public String guessFkColomn(String parentTable, String childTable) {
		String fkColumn = null;
		try {
			Map<String,String[]> mOneToMulti = getChildTableOneToMulti(gcRule.getConnection(), gcRule.getConfig1MainRuleWizardPage().getMContainerText("catalog"), 
					gcRule.getConfig1MainRuleWizardPage().getMContainerText("schemaPattern"), 
					parentTable);
			if(mOneToMulti.get(childTable) != null) {
				fkColumn = mOneToMulti.get(childTable)[7];
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(fkColumn == null) {
			Document docTable = (Document)gcRule.getMTableDocs().get(childTable);
			fkColumn = docTable.valueOf("/meta/tables/table[@tableName='" + childTable + "']/@statisticColumn");
		}
		return fkColumn;
	}
	
	
	public void buildTableRelationXml(List<List<Object>> relations) {
		Map<String, Document> mTableDoc = gcRule.getMTableDocs();
		Set<String> sHasRelationTable = new HashSet<String>();
		for(List<Object> lRelationGroup : relations) {
			List<List<Widget>> lRelationRow = (List<List<Widget>>)lRelationGroup.get(0);
			if(lRelationRow.size() == 0) {
				continue;
			}
			List<Combo> tableName_refColumn = getCombos(lRelationGroup);
			if(tableName_refColumn.size() < 2) {
				continue;
			}
			String mainTableName = tableName_refColumn.get(0).getText();
			String mainRefColumn = tableName_refColumn.get(1).getText();
			Document tableDoc = mTableDoc.get(mainTableName);
			Element mainTable = (Element)tableDoc.selectSingleNode("/meta/relations/mainTable[@tableName='" + mainTableName + "']");
			if(mainTable == null) {
				Element eleRelations = (Element)tableDoc.selectSingleNode("/meta/relations");
				mainTable = eleRelations.addElement("mainTable");
				mainTable.addAttribute("tableName", mainTableName);
			}
			mainTable.addAttribute("refColumn", mainRefColumn);
			mainTable.clearContent();
			for(List<Widget> relationRow : lRelationRow) {
				if(relationRow.get(0) instanceof Canvas) { //中间表
					List<Combo> lMiddleTableRow = getCombos(relationRow);
					if(lMiddleTableRow.size() > 4) {
						Element refTable = mainTable.addElement("refTable");
						refTable.addAttribute("tableName", lMiddleTableRow.get(3).getText());
						refTable.addAttribute("refColumn", lMiddleTableRow.get(4).getText());
						Element middleTable = refTable.addElement("middleTable");
						middleTable.addAttribute("tableName", lMiddleTableRow.get(0).getText());
						middleTable.addAttribute("mainColumn", lMiddleTableRow.get(1).getText());
						middleTable.addAttribute("refColumn", lMiddleTableRow.get(2).getText());
						sHasRelationTable.add(mainTableName);
					}
				} else { //子表
					List<Combo> lChildTableRow = getCombos(relationRow);
					if(lChildTableRow.size() > 1) {
						Element refTable = mainTable.addElement("refTable");
						refTable.addAttribute("tableName", lChildTableRow.get(0).getText());
						refTable.addAttribute("refColumn", lChildTableRow.get(1).getText());
						sHasRelationTable.add(mainTableName);
					}
				}
			}
		}
		clearNotHasRelationTable(sHasRelationTable);
	}
	
	/**
	 * 清理掉没有体现关系的表
	 * 
	 * @param sHasRelationTable
	 */
	private void clearNotHasRelationTable(Set<String> sHasRelationTable) {
		Map<String, Document> mTableDoc = gcRule.getMTableDocs();
		for(Map.Entry<String, Document> en : mTableDoc.entrySet()) {
			String mainTableName = en.getKey();
			if(sHasRelationTable.contains(mainTableName)) {
				continue;
			}
			Document tableDoc = mTableDoc.get(mainTableName);
			Element eleRelations = (Element)tableDoc.selectSingleNode("/meta/relations");
			if(eleRelations != null) {
				eleRelations.clearContent();
			}
		}
	}

	private List<Combo> getCombos(List objs) {
		List<Combo> result = new ArrayList<Combo>();
		for(Object obj : objs) {
			if(obj instanceof Combo && !((Combo)obj).isDisposed() && ((Combo)obj).getText().length() > 0) {
				result.add((Combo)obj);
			}
		}
		return result;
	}
	

	@SuppressWarnings("unchecked")
	public void mergeChildTable() {
		Map<String, Document> mTableDoc = gcRule.getMTableDocs();
		for(Map.Entry<String, Document> en : mTableDoc.entrySet()) {
			String mainTable = en.getKey();
			Document tableDoc = en.getValue();
			Element eleTables = (Element)tableDoc.selectSingleNode("/meta/tables");
			Element eleMainTable = (Element)tableDoc.selectSingleNode("/meta/relations/mainTable[@tableName='" + mainTable + "']");
			if(eleMainTable == null) {
				continue;
			}
			//合并子表、中间表的目标表
			List<Element> lRefTableChild = eleMainTable.selectNodes("refTable"); //"refTable[count(middleTable)=0]"则表示只merge子表
			if(lRefTableChild.size() == 0) {
				continue;
			}
			for(Element eleRefTableChild : lRefTableChild) {
				String refTableChild = eleRefTableChild.valueOf("@tableName");
				Document docRefTableChild = mTableDoc.get(refTableChild);
				if(docRefTableChild != null){
					Element redundantRefTableInfo = (Element)tableDoc.selectSingleNode("/meta/tables/table[@tableName='" + refTableChild + "']");
					if(redundantRefTableInfo != null) { //清理冗余的缓存
						eleTables.remove(redundantRefTableInfo);
					}
					Element largeEleRefTableChild = (Element)docRefTableChild.selectSingleNode("/meta/tables/table[@tableName='" + refTableChild + "']");
					eleTables.add(largeEleRefTableChild.createCopy());
					//清理tableTos
					Element eleTableTos = (Element)gcRule.getMainRule().selectSingleNode("/rules/database/tableTos");
					Node eleTableTo = eleTableTos.selectSingleNode("tableTo[text()='" + refTableChild + "']");
					if(eleTableTo != null && eleRefTableChild.selectNodes("middleTable").size() == 0) {
						eleTableTos.remove(eleTableTo);
					}
				}
			}
		}
	}
	
	/**
	 * 找子表
	 * @param conn
	 * @param catalog
	 * @param schemaPattern
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static Map<String,String[]> getChildTableOneToMulti(Connection conn, String catalog, String schemaPattern, String tableName) throws SQLException {
    	//key是子表name，value是主表和子表的关联信息
        Map<String,String[]> mOneToMulti = new HashMap<String,String[]>();
        ResultSet rsek1 = conn.getMetaData().getExportedKeys(catalog, schemaPattern, tableName);
        if(!rsek1.next()) {
        	rsek1 = conn.getMetaData().getExportedKeys(catalog, schemaPattern, tableName.toLowerCase());
        } else {
        	rsek1 = conn.getMetaData().getExportedKeys(catalog, schemaPattern, tableName);
        }
        while(rsek1.next()) {
            String[] aRsReference = new String[14];
            for (int j = 1; j <= 14; j++) {
                aRsReference[j-1] = rsek1.getString(j);
            }
            mOneToMulti.put(aRsReference[6], aRsReference);
        }
        return mOneToMulti;
	}
	
    /**
     * 根据conn分析主子表关系
     * @param conn
     * @param catalog
     * @param schemaPattern
     * @param tableName
     * @return
     */
    public static String getParentChildtableByConn(Connection conn, String catalog, String schemaPattern, String tableName) {
        try {
        	//key是子表name，value是主表和子表的关联信息
            Map<String,String[]> mOneToMulti = getChildTableOneToMulti(conn, catalog, schemaPattern, tableName);
            //key是多对多的第3个表name，value是[主表和中间表的关联信息][第3个表和中间表的关联信息]
            Map<String,String[][]> mMultiToMulti = new HashMap<String,String[][]>();

            
            for (Iterator<String> itMOneToMulti = mOneToMulti.keySet().iterator(); itMOneToMulti.hasNext();) {
            	//子表此时作为中间表
                String childTable = itMOneToMulti.next();
                ResultSet rsik2 = conn.getMetaData().getImportedKeys(catalog, schemaPattern, childTable);
                if(!rsik2.next()) {
                	rsik2 = conn.getMetaData().getImportedKeys(catalog, schemaPattern, childTable.toLowerCase());
                } else {
                	rsik2 = conn.getMetaData().getImportedKeys(catalog, schemaPattern, childTable);
                }
                while(rsik2.next()) {
                    String[] aRsReference = new String[14];
                    for (int j = 1; j <= 14; j++) {
                        aRsReference[j-1] = rsik2.getString(j);
                    }
                    if(tableName.toUpperCase().endsWith(aRsReference[2].toUpperCase())) {
                    	continue;
                    }
                    mMultiToMulti.put(aRsReference[2], new String[][]{mOneToMulti.get(childTable), aRsReference});
                    itMOneToMulti.remove();
                    break;
                }
            }
            return buildParentChildTable(mOneToMulti, mMultiToMulti);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * @param mOneToMulti key是子表name，value是主表和子表的关联信息
     * @param mMultiToMulti key是多对多的第3个表name，value是[主表和中间表的关联信息][第3个表和中间表的关联信息]
     * @return
        CmsArticle.ID=CmsArticleVersion.ARTICLE_ID,
        CmsArticle.ID=CmsArticleColumn.ARTICLE_ID|TEMPLATE_ID=CmsTemplate.ID,
     */
    private static String buildParentChildTable(Map<String,String[]> mOneToMulti, Map<String,String[][]> mMultiToMulti) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> itMOneToMulti = mOneToMulti.keySet().iterator(); itMOneToMulti.hasNext();) {
            String childTable1 = itMOneToMulti.next();
            String[] aRsReference = mOneToMulti.get(childTable1);
            sb.append(MetadataHelper.getFormatTableName(aRsReference[2]) + "." + aRsReference[3] + "=" + MetadataHelper.getFormatTableName(aRsReference[6]) + "." + aRsReference[7] + ",\r\n");
        }
        for (Iterator<String> itMMultiToMulti = mMultiToMulti.keySet().iterator(); itMMultiToMulti.hasNext();) {
            String childTable1 = itMMultiToMulti.next();
            String[][] aaRsReference = mMultiToMulti.get(childTable1);
            sb.append(MetadataHelper.getFormatTableName(aaRsReference[0][2]) + "." + aaRsReference[0][3] + 
            		"=" + aaRsReference[0][6] + "." + aaRsReference[0][7] + "|" + aaRsReference[1][7] + 
            		"=" + MetadataHelper.getFormatTableName(aaRsReference[1][2]) + "." + aaRsReference[1][3] + 
            		"(" + aaRsReference[1][2] + "." + aaRsReference[1][3] + "),\r\n");
        }
        System.out.println(sb.toString());
        return sb.toString().replaceAll("[\\s,\\\\n\\\\r]+$", "");
    }

}

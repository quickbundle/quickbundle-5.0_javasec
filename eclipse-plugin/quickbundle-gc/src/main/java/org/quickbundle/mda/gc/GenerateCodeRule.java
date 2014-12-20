package org.quickbundle.mda.gc;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Combo;
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class GenerateCodeRule {
    
    private static String RULE_XML_FILE = "generateCode.xml";
    
    //generateCode.xml的路径
    private String mainRulePath = null;
    
    //每个表的xml文档
    private Map<String, Document> mTableDocs = new HashMap<String, Document>();

    //generateCode.xml文档
    private Document mainRule = null;
    
    private Connection connection = null;
    
    //定义的容器
    private Config1MainRuleWizardPage config1MainRuleWizardPage = null;

	public GenerateCodeRule() throws MalformedURLException, DocumentException, Exception {
		File file = initMainRuleFile();
		this.mainRulePath = file.toString();
		mainRule = RmXmlHelper.parse(file.toString());
	}
	
	public void clearTableTo() {
        Element thisTableTos = (Element) mainRule.selectSingleNode("/rules/database/tableTos");
        if (thisTableTos != null) {
            thisTableTos.clearContent();
        }	
	}
	
	/**
     * 功能: 从temp目录获得xml文件
     * 
     * @return
     * @throws Exception
     */
    private File initMainRuleFile() throws Exception {
        File fMailRule = new File(RmXmlHelper.formatToFile(QbXmlGenerateCodePlugin.qbGenerateCodeHome + File.separatorChar + RULE_XML_FILE));
        if (fMailRule.exists()) {
            Document thisTempDoc = RmXmlHelper.parse(fMailRule.getPath());
            Document thisSourceDoc = RmXmlHelper.parse(RmXmlHelper.formatToFile(QbXmlGenerateCodePlugin.baseConfigPath + RULE_XML_FILE));
            if (thisSourceDoc.valueOf("/rules/@rulesVersion").equals(thisTempDoc.valueOf("/rules/@rulesVersion"))) {
                return fMailRule;
            }
        }
        File rulePlugin = new File(RmXmlHelper.formatToFile(QbXmlGenerateCodePlugin.baseConfigPath + RULE_XML_FILE));
        if (rulePlugin.exists()) {
            try {
                RmFileHelper.copyFile(rulePlugin, fMailRule);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return fMailRule;
    }
    
    public void save() throws Exception {
        //同步rule.xml中的数据到每一个table.xml中
        {
            List<Element> lTableTo = getMainRule().selectNodes("/rules/database/tableTos/tableTo");
            for (Element thisTableTo : lTableTo) {
                Document thisTableDoc = (Document) getMTableDocs().get(thisTableTo.getText());
                thisTableDoc.selectSingleNode("/meta/database/driver").setText(getMainRule().valueOf("/rules/database/@driver"));
                thisTableDoc.selectSingleNode("/meta/database/url").setText(getMainRule().valueOf("/rules/database/@url"));
                thisTableDoc.selectSingleNode("/meta/database/userName").setText(getMainRule().valueOf("/rules/database/@userName"));
                thisTableDoc.selectSingleNode("/meta/database/password").setText(getMainRule().valueOf("/rules/database/@password"));

                RmXmlHelper.deepCopyElementWithClear((Element)getMainRule().selectSingleNode("/rules/project"), 
                		(Element)thisTableDoc.selectSingleNode("/meta/project"));

                RmXmlHelper.saveXmlToPath(thisTableDoc, RmXmlHelper.formatToFile(QbXmlGenerateCodePlugin.qbGenerateCodeHome + "/"
                        + thisTableTo.valueOf("@xmlName")));
            }
        }
        //回写rule.xml
        RmXmlHelper.saveXmlToPath(getMainRule(), mainRulePath);
        //QbXmlGenerateCodePlugin.log("save '" + mainRulePath);
    }
    
    public void initTableDoc(String currentTable, File currentTableXmlFile, PdmParser pdmParser, Config1MainRuleWizardPage config1MainRuleWizardPage) throws Exception {
        Map mTempDisplayName = new HashMap();
        Map mTempOtherConfig = new HashMap();
        { //从deployInterprets取出内置的扩展字段的表显示名称
            java.util.List lColumn = mainRule.selectNodes("//dataType/deployInterprets/deployInterpret");
            for (Iterator itLColumn = lColumn.iterator(); itLColumn.hasNext();) {
                Node thisColumn = (Node) itLColumn.next();
                String[] defaultInit = new String[5];  //0，是否构建；1，列显示名称；2，人性化方式；3，人性化方式关键字; 4,是否构建list
                defaultInit[0] = thisColumn.valueOf("@isBuild");;
                defaultInit[1] = thisColumn.valueOf("@displayName");
                defaultInit[2] = "default";
                defaultInit[3] = "";
                defaultInit[4] = defaultInit[0];
                mTempDisplayName.put((currentTable + thisColumn.getText()).toLowerCase(), defaultInit);
            }
        }
        Document oldDocTable = null;
        if (currentTableXmlFile.exists()) { //如果已经存在tableXml，取冗余
            oldDocTable = RmXmlHelper.parse(currentTableXmlFile.getPath());
            java.util.List<Element> lColumn = oldDocTable.selectNodes("//column");
            for (Element thisColumn : lColumn) {
                String[] defaultInit = new String[5];  //0，是否构建；1，列显示名称；2，人性化方式；3，人性化方式关键字; 4,是否构建list
                defaultInit[0] = thisColumn.valueOf("@isBuild");
                defaultInit[1] = thisColumn.valueOf("@columnNameDisplay");
                defaultInit[2] = thisColumn.valueOf("@humanDisplayType");
                defaultInit[3] = thisColumn.valueOf("@humanDisplayTypeKeyword");
                defaultInit[4] = thisColumn.valueOf("@isBuild_list");
                mTempDisplayName.put(thisColumn.valueOf("../@tableName") + thisColumn.valueOf("@columnName"), defaultInit);
            }
            mTempOtherConfig.put("tableFilterKeyword", oldDocTable.valueOf("/meta/tables/table[1]/@tableFilterKeyword"));
            mTempOtherConfig.put("tableDirName", oldDocTable.valueOf("/meta/tables/table[1]/@tableDirName"));
            mTempOtherConfig.put("tableNameDisplay", oldDocTable.valueOf("/meta/tables/table[1]/@tableNameDisplay"));
            mTempOtherConfig.put("statisticColumn", oldDocTable.valueOf("/meta/tables/table[1]/@statisticColumn"));
            mTempOtherConfig.put("keyColumn", oldDocTable.valueOf("/meta/tables/table[1]/@keyColumn"));
            mTempOtherConfig.put("customBundleCode", oldDocTable.valueOf("/meta/tables/table[1]/@customBundleCode"));
        }
        Document docTable = MetadataHelper.getMetaDataXml(getConnection(), 
        		((Combo) config1MainRuleWizardPage.getMContainer("catalog")).getText().length() == 0 ? null : ((Combo) config1MainRuleWizardPage.getMContainer("catalog")).getText(), 
        		((Combo) config1MainRuleWizardPage.getMContainer("schemaPattern")).getText().length() == 0 ? null : ((Combo) config1MainRuleWizardPage.getMContainer("schemaPattern")).getText(), 
        		currentTable, 
        		mainRule, 
        		pdmParser);
        { 
            //回写列显示名称(即中文名)
            java.util.List lColumn = docTable.selectNodes("//column");
            for (Iterator itLColumn = lColumn.iterator(); itLColumn.hasNext();) {
                Node thisColumn = (Node) itLColumn.next();
                String key = thisColumn.valueOf("../@tableName") + thisColumn.valueOf("@columnName");
                String keyLower = key.toLowerCase();
                if ((mTempDisplayName.get(key) != null && mTempDisplayName.get(key).toString().length() > 0)) { //如果有存储了的值,优先大小写也不差的key
                    String[] defaultInit = (String[])mTempDisplayName.get(key);
                    thisColumn.selectSingleNode("@isBuild").setText(defaultInit[0]);
                    thisColumn.selectSingleNode("@columnNameDisplay").setText(defaultInit[1]);
                    thisColumn.selectSingleNode("@humanDisplayType").setText(defaultInit[2]);
                    thisColumn.selectSingleNode("@humanDisplayTypeKeyword").setText(defaultInit[3]);
                    thisColumn.selectSingleNode("@isBuild_list").setText(defaultInit[4]);
                } else if ((mTempDisplayName.get(keyLower) != null && mTempDisplayName.get(keyLower).toString().length() > 0)) { //回写小写后的key
                    String[] defaultInit = (String[])mTempDisplayName.get(keyLower);
                    thisColumn.selectSingleNode("@isBuild").setText(defaultInit[0]);
                    thisColumn.selectSingleNode("@columnNameDisplay").setText(defaultInit[1]);
                    thisColumn.selectSingleNode("@humanDisplayType").setText(defaultInit[2]);
                    thisColumn.selectSingleNode("@humanDisplayTypeKeyword").setText(defaultInit[3]);
                    thisColumn.selectSingleNode("@isBuild_list").setText(defaultInit[4]);
                }
            }
            if(mTempOtherConfig.get("tableNameDisplay") != null) {
            	docTable.selectSingleNode("/meta/tables/table[1]/@tableNameDisplay").setText(mTempOtherConfig.get("tableNameDisplay").toString());
            }
            if(mTempOtherConfig.get("tableFilterKeyword") != null) {
                docTable.selectSingleNode("/meta/tables/table[1]/@tableFilterKeyword").setText(mTempOtherConfig.get("tableFilterKeyword").toString());
            }
            if(mTempOtherConfig.get("tableDirName") != null) {
            	docTable.selectSingleNode("/meta/tables/table[1]/@tableDirName").setText(mTempOtherConfig.get("tableDirName").toString());
            }
            if(mTempOtherConfig.get("statisticColumn") != null ) {
            	String statisticColumn = mTempOtherConfig.get("statisticColumn").toString();
            	//只有确定这个statisticColumn仍然是有效的列时
            	if(docTable.selectNodes("//column[@columnName='" + statisticColumn + "']").size() > 0) {
            		docTable.selectSingleNode("/meta/tables/table[1]/@statisticColumn").setText(mTempOtherConfig.get("statisticColumn").toString());
            	}
            }
            if(mTempOtherConfig.get("keyColumn") != null ) {
            	String keyColumn = mTempOtherConfig.get("keyColumn").toString();
            	//只有确定这个keyColumn仍然是有效的列时
            	if(docTable.selectNodes("//column[@columnName='" + keyColumn + "']").size() > 0) {
            		docTable.selectSingleNode("/meta/tables/table[1]/@keyColumn").setText(mTempOtherConfig.get("keyColumn").toString());
            	}
            }
            if(mTempOtherConfig.get("customBundleCode") != null) {
                docTable.selectSingleNode("/meta/tables/table[1]/@customBundleCode").setText(mTempOtherConfig.get("customBundleCode").toString());
            }
        }
        if(oldDocTable != null && oldDocTable.selectNodes("/meta/relations/mainTable").size() > 0){ //回写表关系
        	Element eleOldMainTable = (Element)oldDocTable.selectSingleNode("/meta/relations/mainTable");
        	Element newRelation = (Element)docTable.selectSingleNode("/meta/relations");
        	newRelation.add(eleOldMainTable.createCopy());
        }
        mTableDocs.put(currentTable, docTable);
    }
    
    public String getMainRulePath() {
		return mainRulePath;
	}
    
	/**
     * 功能: 获得保存xml配置信息的map对象
     */
    public Map getMTableDocs() {
		return mTableDocs;
	}
    
	public void setMTableDocs(Map mTableDocs) {
		this.mTableDocs = mTableDocs;
	}
	
    /**
     * 功能: 获得rule.xml文档
     */
	public Document getMainRule() {
		return mainRule;
	}
	public void setMainRule(Document mainDoc) {
		this.mainRule = mainDoc;
	}
	
    /**
     * 功能: 获得表的元数据的xml
     */
    public Document getTableDoc(String tableName) {
        return (Document) getMTableDocs().get(tableName);
    }

	public void refreshTableColumn(Map mPdmColumn, Config1MainRuleWizardPage config1MainRuleWizardPage) {
		List<String[]> lToUpdate = updateDisplayNameValue(mPdmColumn, false);
		if(lToUpdate.size() > 0) {
			StringBuilder updateInfo = new StringBuilder();
			int count = 0;
			for(String[] toUpdate : lToUpdate) {
				updateInfo.append(toUpdate[0]).append(":").append(toUpdate[1]).append("  ").append(toUpdate[2]).append("->").append(toUpdate[3]).append("\n");
				if(count ++ > 20) {
					updateInfo.append("...").append("\n").append("total: ").append(lToUpdate.size());
					break;
				}
			}
	    	MessageDialogWithToggle dialog = new MessageDialogWithToggle(config1MainRuleWizardPage.getShell(), "是否更新pdm信息到已设置的表中？", null, 
                updateInfo.toString(), MessageDialogWithToggle.CONFIRM , new String[]{"OK", "Cancel"}, 0, null, false);
	    	dialog.create();
	    	if(dialog.open() == 0) {
	    		updateDisplayNameValue(mPdmColumn, true);
	    	}
		}
	}
	
	List<String[]> updateDisplayNameValue(Map mPdmColumn, boolean realExecute) {
		List<String[]> lToUpdate = new ArrayList<String[]>();
		for(Map.Entry<String, Document> en : mTableDocs.entrySet()) {
			String tableName = en.getKey();
			Document tableDoc = en.getValue();
			
			List<Element> lTable = tableDoc.selectNodes("/meta/tables/table");
			for(Element table : lTable) {
				String docTableName = table.valueOf("@tableName");
				if(mPdmColumn.containsKey(docTableName) && !table.valueOf("@tableNameDisplay").equalsIgnoreCase(getMPdmColumnValue(mPdmColumn, docTableName))) {
					if(realExecute) {
						table.addAttribute("tableNameDisplay", getMPdmColumnValue(mPdmColumn, docTableName));
					} else {
						lToUpdate.add(new String[]{docTableName, "", table.valueOf("@tableNameDisplay"), getMPdmColumnValue(mPdmColumn, docTableName)});
					}
				}

				List<Element> lColumn = table.selectNodes("column");
				for(Element column : lColumn) {
					String docColumnName = column.valueOf("@columnName");
					if(mPdmColumn.containsKey(docTableName + ":" + docColumnName) && !column.valueOf("@columnNameDisplay").equalsIgnoreCase(getMPdmColumnValue(mPdmColumn, docTableName + ":" + docColumnName))) {
						if(realExecute) {
							column.addAttribute("columnNameDisplay", getMPdmColumnValue(mPdmColumn, docTableName + ":" + docColumnName));
						} else {
							lToUpdate.add(new String[]{docColumnName, docColumnName, column.valueOf("@columnNameDisplay"), getMPdmColumnValue(mPdmColumn, docTableName + ":" + docColumnName)});
						}
					}
				}
			}
		}
		return lToUpdate;
	}
	
	private String getMPdmColumnValue(Map mPdmColumn, String key) {
		if(mPdmColumn == null || !mPdmColumn.containsKey(key)) {
			return null;
		}
		String[] aTableInfo = (String[]) mPdmColumn.get(key);
		if(aTableInfo == null || aTableInfo.length == 0) {
			return null;
		}
		return aTableInfo[0];
	}

	public Connection getConnection() {
		boolean needConnection = false;
		try {
			if(connection != null && connection.isClosed()) {
				needConnection = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
			needConnection = true;
		}
		if(needConnection) {
			connectDatabase(config1MainRuleWizardPage.getMContainerText("driver"), config1MainRuleWizardPage.getMContainerText("url"), config1MainRuleWizardPage.getMContainerText("userName"), config1MainRuleWizardPage.getMContainerText("password"));
		}
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

    /**
     * 连接数据库
     * @param driver
     * @param url
     * @param userName
     * @param password
     * @return
     */
    public String connectDatabase(String driver, String url, String userName, String password) {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(driver); //初始化数据库连接
                connection = DriverManager.getConnection(url, userName, password);
                return null;
            } else {
                return null;
            }
        } catch (UnsupportedClassVersionError e) {
            return "当前JDK版本太低,不能载入类:" + driver + " " + e.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            return e.toString();
		}
    }
    
	public void closeConnection() {
		if(connection == null) {
			return;
		}
		try {
			if(connection.isClosed()) {
				return;
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}

	public Config1MainRuleWizardPage getConfig1MainRuleWizardPage() {
		return config1MainRuleWizardPage;
	}

	public void setConfig1MainRuleWizardPage(Config1MainRuleWizardPage config1MainRuleWizardPage) {
		this.config1MainRuleWizardPage = config1MainRuleWizardPage;
	}
}

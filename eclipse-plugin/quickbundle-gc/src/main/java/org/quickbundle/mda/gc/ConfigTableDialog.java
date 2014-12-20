/*
 * 系统名称:Quickbundle.org --> ranminXmlGenerateCode
 * 
 * 文件名称: ranminXmlGenerateCode.wizards --> ConfigTableDialog.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-12-3 1:25:18 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.mda.gc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.internal.resources.ResourceStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.quickbundle.tools.helper.RmStringHelper;

/**
 * This code was generated using CloudGarden's Jigloo SWT/Swing GUI Builder,
 * which is free for non-commercial use. If Jigloo is being used commercially
 * (ie, by a corporation, company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo. Please visit
 * www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. ************************************* A COMMERCIAL LICENSE
 * HAS NOT BEEN PURCHASED for this machine, so Jigloo or this code cannot be
 * used legally for any corporate or commercial purpose.
 * *************************************
 */
/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class ConfigTableDialog extends Dialog implements Listener {
    //父页面
    private Config1MainRuleWizardPage currentWizard;
    private GenerateCodeRule gcRule = null;

    //当前表名
    private String currentTable;

    //循环的列输入项
    private Map mColumn = new TreeMap();
    
    private Text tableNameDisplay;

    private Text tableFilterKeyword;
    
    private Text tableDirName;
    
    private Text tablePk;
    
    private Text statisticColumn;
    
    private Text keyColumn;

    private List<Button> lBCustomBundle = new ArrayList<Button>();

    /**
     * The title of the dialog.
     */
    private String title = "";

    /**
     * Ok button widget.
     */
    private Button okButton;

    /**
     * 构造函数:
     * 
     * @param parentShell
     * @param currentWizard
     * @param currentTable
     */
    public ConfigTableDialog(Shell parentShell, Config1MainRuleWizardPage currentWizard, String currentTable, GenerateCodeRule gcRule) {
        super(parentShell);
        this.currentWizard = currentWizard;
        this.currentTable = currentTable;
        this.title = "设置表: " + currentTable + "";
        this.gcRule = gcRule;
    }

    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
            ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@tableNameDisplay")).setValue(tableNameDisplay.getText());
            ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@tableFilterKeyword")).setValue(tableFilterKeyword.getText());
            ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@tableDirName")).setValue(tableDirName.getText());
            ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@tablePk")).setValue(tablePk.getText());
            ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@statisticColumn")).setValue(statisticColumn.getText());
            ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@keyColumn")).setValue(keyColumn.getText());
            {
            	//处理定制组件
                String customBundleCode = "";
                for(Button b : lBCustomBundle) {
                	if(b.getSelection()) {
                		if(customBundleCode.length() > 0) {
                			customBundleCode += ",";
                		}
                		customBundleCode += b.getData().toString();
                	}
                }
                ((Attribute) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/@customBundleCode")).setValue(customBundleCode);
            }

            for (Iterator itMColumn = mColumn.keySet().iterator(); itMColumn.hasNext();) {
                Object[] columnInfo = (Object[]) mColumn.get(itMColumn.next());
                String columnName = String.valueOf(columnInfo[0]);
                Button isBuild = (Button) columnInfo[1];
                Text columnNameDisplay = (Text) columnInfo[3];
                Combo humanDisplayType = (Combo) columnInfo[5];
                Text humanDisplayTypeKeyword = (Text) columnInfo[6];
                Button isBuild_list = (Button) columnInfo[7];
                Node node = (Node) gcRule.getTableDoc(currentTable).selectObject("/meta/tables/table[1]/column[@columnName='" + columnName + "']");
                ((Attribute) node.selectObject("@columnNameDisplay")).setValue(columnNameDisplay.getText());
                ((Attribute) node.selectObject("@isBuild")).setValue(isBuild.getSelection() ? "true" : "false");
                ((Attribute) node.selectObject("@humanDisplayType")).setValue(humanDisplayType.getText());
                ((Attribute) node.selectObject("@humanDisplayTypeKeyword")).setValue(humanDisplayTypeKeyword.getText());
                ((Attribute) node.selectObject("@isBuild_list")).setValue(isBuild_list.getSelection() ? "true" : "false");
            }
            if(!validateAll()) {
            	return;
            }
        }
        super.buttonPressed(buttonId);
    }
    
    private boolean validateAll() {
    	return 
    	validateText(tableNameDisplay, "表显示名称") && 
    	validateText(tableFilterKeyword, "规范后的表名") &&
    	validateText(tableDirName, "目录名") &&
    	validateText(tablePk, "主键") &&
    	validateText(statisticColumn, "统计列") &&
    	validateText(keyColumn, "标识列") &&
    	validateColumn(tablePk, "主键") &&
    	validateColumn(statisticColumn, "统计列") &&
    	validateColumn(keyColumn, "标识列");
    }

    private boolean validateText(Text t, String name) {
    	if(t.getText().trim().length() == 0) {
    		alert(name + "不能为空");
            t.forceFocus();
            return false;
        }
    	return true;
    }
    
    private boolean validateColumn(Text t, String name) {
    	if(gcRule.getTableDoc(currentTable).selectNodes("/meta/tables/table/column[@columnName='" + t.getText() + "']").size() == 0) {
    		alert(name + "指定的字段，不是合法的列名，请重新输入");
    		t.forceFocus();
    		t.selectAll();
    		return false;
    	}
     	return true;
    }
    
    private void alert(String str) {
        ErrorDialog msgDialog = new ErrorDialog(this.getShell(), "", str, new ResourceStatus(1, ""), 1);
        msgDialog.create();
        msgDialog.open();
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (title != null)
            shell.setText(title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
        okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    protected Control createDialogArea(Composite parent) {
        // create composite
        Composite containerParent = (Composite) super.createDialogArea(parent);
        containerParent.setLayout(new FillLayout(SWT.VERTICAL));

        GridData gd = null;
        GridLayout layout = null;
        int columns = 7; //定义列数

        ScrolledComposite scroll = new ScrolledComposite(containerParent, SWT.NONE | SWT.V_SCROLL | SWT.H_SCROLL);
        scroll.setLayout(new FillLayout(SWT.VERTICAL));
        
        Composite container = new Composite(scroll, SWT.NONE);

        gd = new GridData(GridData.FILL_VERTICAL);
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        container.setLayoutData(gd);
        layout = new GridLayout();
        layout.numColumns = columns;
        layout.verticalSpacing = 1;
        container.setLayout(layout);

        scroll.setContent(container);

        createCustomTableHead(container, columns);

        createCustomBundleArea(container, columns);
        
        createCustomColumnArea(container, columns);
        
        container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        container.layout();
        
        initialize();
        return container;
    }

    private void createCustomTableHead(Composite parent, int columns) {
    	GridData gd = null;
        //定义单独的画布
        final Canvas container = new Canvas(parent, SWT.NONE);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = columns;
        container.setLayoutData(gd);
        GridLayout layoutCanvas = new GridLayout();
        layoutCanvas.numColumns = 8;
        container.setLayout(layoutCanvas);
        
        //新的1行，表名
        new Label(container, SWT.NULL).setText("表名:");
        Text tableName = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        tableName.setLayoutData(gd);
        tableName.setText(currentTable);

        //表显示名、规范化
        String tableNameDisplayDesc = "表的显示名称，即汉化表名，比如EMPLOYEE表就是\"员工\"";
        Label label_tableNameDisplay = new Label(container, SWT.NONE);
        label_tableNameDisplay.setToolTipText(tableNameDisplayDesc);
        label_tableNameDisplay.setText("表显示名称:");
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalAlignment = GridData.END;
        label_tableNameDisplay.setLayoutData(gd);
        
        tableNameDisplay = new Text(container, SWT.BORDER | SWT.SINGLE);
        tableNameDisplay.setToolTipText(tableNameDisplayDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        tableNameDisplay.setLayoutData(gd);
        
        String tableFilterKeywordDesc = "表名规范化后的值会作为生成代码的类名/文件名，比如HR_EMPLOYEE_JOB表规范为HrEmployeeJob";
        Label label_tableFilterKeyword = new Label(container, SWT.NONE);
        label_tableFilterKeyword.setText("规范后的表名:");
        label_tableFilterKeyword.setToolTipText(tableFilterKeywordDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalAlignment = GridData.END;
        label_tableFilterKeyword.setLayoutData(gd);


        tableFilterKeyword = new Text(container, SWT.SINGLE | SWT.BORDER);
        tableFilterKeyword.setToolTipText(tableFilterKeywordDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        tableFilterKeyword.setLayoutData(gd);

        String tableDirNameDesc = "会作为生成代码的Java/Jsp文件所在的目录名，比如HR_EMPLOYEE表的目录名一般为hremployee，如果是主子表则建议是employee";
        Label label_tableDirName = new Label(container, SWT.NONE);
        label_tableDirName.setText("目录名:");
        label_tableDirName.setToolTipText(tableDirNameDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalAlignment = GridData.END;
        label_tableDirName.setLayoutData(gd);


        tableDirName = new Text(container, SWT.SINGLE | SWT.BORDER);
        tableDirName.setToolTipText(tableDirNameDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        tableDirName.setLayoutData(gd);
        
        //新的一行，主键、引用主表外键、标识列
        String tablePkDesc = "定义这个表的主键，代码生成器仅支持单主键，不支持复合主键";
        Label labelTablePk = new Label(container, SWT.NULL);
        labelTablePk.setText("主键:");
        labelTablePk.setToolTipText(tablePkDesc);
        tablePk = new Text(container, SWT.BORDER | SWT.SINGLE);
        tablePk.setToolTipText(tablePkDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        tablePk.setLayoutData(gd);
        
        String statisticColumnDesc = "生成的图标统计功能，会以统计列指定的字段分组。一般是重要的grouy by字段，比如公司ID";
        Label label_statisticColumn = new Label(container, SWT.NONE);
        label_statisticColumn.setText("统计列:");
        label_statisticColumn.setToolTipText(statisticColumnDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalAlignment = GridData.END;
        label_statisticColumn.setLayoutData(gd);

        statisticColumn = new Text(container, SWT.SINGLE | SWT.BORDER);
        statisticColumn.setToolTipText(statisticColumnDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        statisticColumn.setLayoutData(gd);
        
        String keyColumnDesc = "生成的列表界面、参照界面，会以标识列指定的字段显示而不是主键，比如新闻的标题字段，员工的姓名字段";
        Label label_keyColumn = new Label(container, SWT.NONE);
        label_keyColumn.setText("标识列:");
        label_keyColumn.setToolTipText(keyColumnDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalAlignment = GridData.END;
        label_keyColumn.setLayoutData(gd);

        keyColumn = new Text(container, SWT.SINGLE | SWT.BORDER);
        keyColumn.setToolTipText(keyColumnDesc);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 150;
        keyColumn.setLayoutData(gd);
        
        new Label(container, SWT.NULL).setText("");
        new Label(container, SWT.NULL).setText("");
	}


	private void createCustomBundleArea(Composite parent, int columns) {
    	GridData gd = null;
        //定义单独的画布
        final Canvas container = new Canvas(parent, SWT.NONE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = columns;
        container.setLayoutData(gd);
        GridLayout layoutCanvas = new GridLayout(30, false);
        container.setLayout(layoutCanvas);
        
        //新的一行，定义勾选不同组件
        Label label_customBundle = new Label(container, SWT.NULL);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = SWT.END;
        gd.verticalAlignment = SWT.CENTER;
        label_customBundle.setLayoutData(gd);
        label_customBundle.setText("生成组件:");

        List<Element> lEle = gcRule.getMainRule().selectNodes("/rules/customBundleCode/bundle");
        for(Element ele : lEle) {
            //是否构建
            Button bCustomBundle = new Button(container, SWT.CHECK);
            gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
            gd.horizontalSpan = 1;
            gd.horizontalAlignment = SWT.END;
            bCustomBundle.setLayoutData(gd);
            
            if("false".equals(ele.valueOf("@null"))) {
            	bCustomBundle.setSelection(true);
            	bCustomBundle.setEnabled(false);
            }
            bCustomBundle.setText(ele.valueOf("@name"));
            bCustomBundle.setData(ele.valueOf("@code"));
            bCustomBundle.setToolTipText(ele.valueOf("text()"));
            bCustomBundle.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					Button b = (Button)event.widget;
					String thisCode = b.getData().toString();
					if(b.getSelection()) {
						String depend = gcRule.getMainRule().valueOf("/rules/customBundleCode/bundle[@code='" + thisCode + "']/@depend");
						if(depend != null && depend.length() > 0) {
							String[] aDepend = depend.split(",");
							for(Button but : lBCustomBundle) {
								if(RmStringHelper.arrayContainString(aDepend, but.getData().toString())) {
									but.setSelection(true);
								}
							}
						}
					} else {
						List<Element> lDependThis = gcRule.getMainRule().selectNodes("/rules/customBundleCode/bundle");
						List<String> toNotSelect = new ArrayList<String>();
						for(Element eleDependThis : lDependThis) {
							if(RmStringHelper.arrayContainString(eleDependThis.valueOf("@depend").split(","), thisCode)) {
								toNotSelect.add(eleDependThis.valueOf("@code"));
							}
						}
						for(Button but : lBCustomBundle) {
							if(toNotSelect.contains(but.getData().toString())) {
								but.setSelection(false);
							}
						}
					}
				}
			});
            lBCustomBundle.add(bCustomBundle);
        }   
	}

	private void createCustomColumnArea(Composite container, int columns) {
    	GridData gd = null;
        //新的1行，增加一行分隔线
        Config1MainRuleWizardPage.createLine(container, columns);
        //全选，全不选
        final Button isBuildHead = new Button(container, SWT.CHECK);
        gd = new GridData();
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.END;
        gd.grabExcessHorizontalSpace = true;
        isBuildHead.setLayoutData(gd);
        isBuildHead.setSelection(true);
        isBuildHead.setToolTipText("定制增删改查的字段选择");
        isBuildHead.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                for (Iterator itMColumn = mColumn.keySet().iterator(); itMColumn.hasNext();) {
                    String index = (String) itMColumn.next();
                    Button tmpIsBuild = (Button)((Object[])mColumn.get(index))[1];
                    tmpIsBuild.setSelection(isBuildHead.getSelection());
                }
            }
        });
        
        Label isBuildHead_list = new Label(container, SWT.NONE);
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.END;
        gd.grabExcessHorizontalSpace = true;
        isBuildHead_list.setLayoutData(gd);
        isBuildHead_list.setText("列表");
        isBuildHead_list.setToolTipText("定制列表页和列表查询语句的字段选择");

        //描述列名
        Label label_columnName = new Label(container, SWT.NONE);
        label_columnName.setText("原始列名");
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_columnName.setLayoutData(gd);

        //描述列显示名称(即中文名)
        Label label_columnNameDisplay = new Label(container, SWT.NONE);
        label_columnNameDisplay.setText("列显示名称");
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_columnNameDisplay.setLayoutData(gd);

        //描述java标准类型
        Label label_dataType = new Label(container, SWT.NONE);
        label_dataType.setText("java标准类型");
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_dataType.setLayoutData(gd);
        
        //描述界面展示方式
        Label label_displayType = new Label(container, SWT.NONE);
        label_displayType.setText("人性化展现方式");
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_displayType.setLayoutData(gd);
        
        //描述界面展示方式的关键字
        Label label_displayTypeKeyword = new Label(container, SWT.NONE);
        label_displayTypeKeyword.setText("关键字");
        gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_displayTypeKeyword.setLayoutData(gd);

        
        Document docTable = gcRule.getTableDoc(currentTable);
        java.util.List lColumn = docTable.selectNodes("/meta/tables/table[1]/column");
        int indexColumn = 100;
        for (Iterator itLColumn = lColumn.iterator(); itLColumn.hasNext();) {
            Object[] columnInfo = new Object[8];
            Node node = (Node) itLColumn.next();
            columnInfo[0] = node.valueOf("@columnName");
            //是否构建
            Button isBuild = new Button(container, SWT.CHECK);
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.horizontalSpan = 1;
            gd.horizontalAlignment = GridData.END;
            gd.grabExcessHorizontalSpace = true;
            isBuild.setLayoutData(gd);
            if ("true".equals(node.valueOf("@isBuild"))) {
                isBuild.setSelection(true);
            } else {
                isBuild.setSelection(false);
            }
            columnInfo[1] = isBuild;
            
            //是否构建_list
            Button isBuild_list = new Button(container, SWT.CHECK);
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.horizontalSpan = 1;
            gd.horizontalAlignment = GridData.END;
            gd.grabExcessHorizontalSpace = true;
            isBuild_list.setLayoutData(gd);
            if ("true".equals(node.valueOf("@isBuild_list"))) {
                isBuild_list.setSelection(true);
            } else {
                isBuild_list.setSelection(false);
            }
            columnInfo[7] = isBuild_list;

            //列名
            Text columnName = new Text(container, SWT.BORDER | SWT.READ_ONLY);
            columnName.setText(node.valueOf("@columnName"));
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.widthHint = 150;
            gd.horizontalAlignment = GridData.BEGINNING;
            gd.grabExcessHorizontalSpace = true;
            columnName.setLayoutData(gd);

            columnInfo[2] = columnName;

            //列显示名称(即中文名)
            Text columnNameDisplay = new Text(container, SWT.BORDER);
            columnNameDisplay.setText(node.valueOf("@columnNameDisplay"));
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.widthHint = 150;
            gd.horizontalAlignment = GridData.BEGINNING;
            gd.grabExcessHorizontalSpace = true;
            columnNameDisplay.setLayoutData(gd);
            columnInfo[3] = columnNameDisplay;


            //类型
            columnNameDisplay = new Text(container, SWT.BORDER); // | SWT.READ_ONLY
            columnNameDisplay.setText(node.valueOf("@dataType"));
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.widthHint = 150;
            gd.horizontalAlignment = GridData.BEGINNING;
            gd.grabExcessHorizontalSpace = true;
            columnNameDisplay.setLayoutData(gd);
            columnInfo[4] = columnNameDisplay;

            
            //定义人性化展现方式
            Combo humanDisplayType = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.horizontalAlignment = GridData.FILL;
            humanDisplayType.setLayoutData(gd);
            String[] xmlDisplayType = null;
            {
                Node[] tempNode = (Node[])gcRule.getMainRule().selectNodes("/rules/dataType/humanDisplayTypes/humanDisplayType/text()").toArray(new Node[0]);
                xmlDisplayType = new String[tempNode.length];
                for(int j=0; j<tempNode.length; j++) {
                    xmlDisplayType[j] = tempNode[j].getText();
                }
            }
            humanDisplayType.setItems(xmlDisplayType);
            humanDisplayType.setText(node.valueOf("@humanDisplayType"));
            columnInfo[5] = humanDisplayType;
            
            //人性化展现方式关键字
            Text humanDisplayTypeKeyword = new Text(container, SWT.BORDER);
            humanDisplayTypeKeyword.setText(node.valueOf("@humanDisplayTypeKeyword"));
            gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
            gd.widthHint = 150;
            gd.horizontalAlignment = GridData.BEGINNING;
            gd.grabExcessHorizontalSpace = true;
            humanDisplayTypeKeyword.setLayoutData(gd);
            columnInfo[6] = humanDisplayTypeKeyword;
            
            mColumn.put(String.valueOf(indexColumn), columnInfo);
            indexColumn++;
        }
	}

	private void initialize() {
        tableNameDisplay.setText(gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@tableNameDisplay"));
        tableFilterKeyword.setText(gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@tableFilterKeyword"));
        tableDirName.setText(gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@tableDirName"));
        tablePk.setText(gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@tablePk"));
        statisticColumn.setText(gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@statisticColumn"));
        keyColumn.setText(gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@keyColumn"));
        //回写勾选组件
        String customBundleCode = gcRule.getTableDoc(currentTable).valueOf("/meta/tables/table[1]/@customBundleCode");
        for (Button b : lBCustomBundle) {
			b.setSelection(customBundleCode.matches("^[\\w,]*" + b.getData().toString() + "[\\w,]*$"));
		}
    }

    /**
     * 功能:实现侦听
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * @param event
     */
    public void handleEvent(Event event) {
    }
}
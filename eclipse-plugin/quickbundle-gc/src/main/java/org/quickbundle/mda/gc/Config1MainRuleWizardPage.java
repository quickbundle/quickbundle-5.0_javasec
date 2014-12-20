package org.quickbundle.mda.gc;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.internal.resources.ResourceStatus;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class Config1MainRuleWizardPage extends WizardPage implements Listener {

    private ISelection selection;

    //定义的容器
    private Map<String, Object> mContainer = null;

    private GenerateCodeRule gcRule = null;
    private PdmParser pdmParser = null;

    /**
     * Constructor for Config1MainRuleWizardPage.
     * @param pageName
     */
    public Config1MainRuleWizardPage(ISelection selection, GenerateCodeWizard currentWizard) {
        super("mainRuleWizardPage");
        setTitle("生成代码 1/3");
        this.selection = selection;
        this.gcRule = currentWizard.getGcRule();
        gcRule.setConfig1MainRuleWizardPage(this);
        mContainer = new HashMap<String, Object>();
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        int columns = 6; //定义列数
        
    	ScrolledComposite scroll = new ScrolledComposite(parent,  SWT.NULL | SWT.V_SCROLL);
    	scroll.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        //强制显示滚动条  
    	scroll.setAlwaysShowScrollBars(false);  
    	scroll.setExpandVertical(true);
    	scroll.setExpandHorizontal(true);
        //拖动滚动条里可以看到的Composite的最大高度
    	//(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()
    	scroll.setMinHeight(700);  
    	scroll.setLayout(new GridLayout(1, false));
    	
        Composite container = new Composite(scroll, SWT.NULL);
        GridLayout glContainer = new GridLayout();
        glContainer.numColumns = columns;
        //glContainer.verticalSpacing = 2;
        container.setLayout(glContainer);
        scroll.setContent(container);

        createDatabaseArea(container, columns);

        createListTableArea(container, columns);

        createBaseProjectPath(container, columns);
        
        //定义Next事件
        WizardDialog dialog = (WizardDialog) getContainer();  
        dialog.addPageChangingListener(new IPageChangingListener() {  
            public void handlePageChanging(PageChangingEvent event) {
            	if(event.getCurrentPage() instanceof Config1MainRuleWizardPage
            			&& event.getTargetPage() instanceof Config2TableRelationWizardPage) {
                    try {
                    	gcRule.save();
                        updateStatus("成功保存配置到" + RmXmlHelper.formatToFile(gcRule.getMainRulePath()));
                    } catch (Exception e1) {
                        updateStatus("保存失败:" + e1.toString());
                        e1.printStackTrace();
                        event.doit = false;
                    }
            	}
            }  
        });
        
        initialize();
        setControl(container);
    }

	private void createDatabaseArea(Composite container, int columns) {
        GridData gd = null;

        //新的1行，数据库驱动
        new Label(container, SWT.NULL).setText("数据库驱动");
        Text driver = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        gd.widthHint = 400;
        driver.setLayoutData(gd);
        addMContainer("driver", driver);
        //驱动输入帮助
        Combo dbProductName = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.FILL;
        gd.horizontalSpan = 1;
        dbProductName.setLayoutData(gd);
        dbProductName.setItems(new String[] {});
        dbProductName.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                Combo event_dbProductName = (Combo) e.getSource();
                Node thisNode = getNodeFromXmlByText("/rules/dataType/dbTypes/dbType", event_dbProductName.getText(), gcRule.getMainRule());
                setMContainerText("driver", thisNode.valueOf("@defaultDriver"));
                setMContainerText("url", thisNode.valueOf("@defaultUrl"));
            }
        });
        addMContainer("dbProductName", dbProductName);

        //新的1行，数据库地址
        new Label(container, SWT.NULL).setText("数据库地址");
        Text url = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 5;
        gd.widthHint = 500;
        url.setLayoutData(gd);
        addMContainer("url", url);

        //新的1行，用户名, 密码
        new Label(container, SWT.NONE).setText("用户名");
        Text userName = new Text(container, SWT.SINGLE | SWT.BORDER);
        userName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addMContainer("userName", userName);
        
        Label label_password = new Label(container, SWT.NONE);
        label_password.setText("密码");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.END;
        label_password.setLayoutData(gd);
        Text password = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
        gd = new GridData(GridData.BEGINNING);
        gd.horizontalAlignment = GridData.FILL;
        password.setLayoutData(gd);
        addMContainer("password", password);
        
        createButtonConnectClearArea(container, columns);
        
        //新的1行，catalog, schemaPattern
        String catalogDesc = "a catalog name; must match the catalog name as it is stored in the database; \"\" retrieves those without a catalog; null means that the catalog name should not be used to narrow the search";
        Label labelCatalog = new Label(container, SWT.NONE);
        labelCatalog.setText("catalog");
        labelCatalog.setToolTipText(catalogDesc);
        Combo catalog = new Combo(container, SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.FILL;
        catalog.setLayoutData(gd);
        catalog.setItems(new String[] {});
        catalog.setToolTipText(catalogDesc);
        addMContainer("catalog", catalog);

        String schemaPatternDesc = "a schema name pattern; must match the schema name as it is stored in the database; \"\" retrieves those without a schema; null means that the schema name should not be used to narrow the search";
        Label label_schemaPattern = new Label(container, SWT.NONE);
        label_schemaPattern.setText("schema");
        label_schemaPattern.setToolTipText(schemaPatternDesc);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.END;
        label_schemaPattern.setLayoutData(gd);

        Combo schemaPattern = new Combo(container, SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.FILL;
        schemaPattern.setLayoutData(gd);
        schemaPattern.setItems(new String[] {});
        schemaPattern.setToolTipText(schemaPatternDesc);
        addMContainer("schemaPattern", schemaPattern);

        //新的1行，tableNamePattern
        String tableNamePatternDesc = "a table name pattern; must match the table name as it is stored in the database";
        Label labelTableNamePattern = new Label(container, SWT.NONE);
        labelTableNamePattern.setText("tableName");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.END;
        labelTableNamePattern.setLayoutData(gd);
        
        Text tableNamePattern = new Text(container, SWT.SINGLE | SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.FILL;
        tableNamePattern.setLayoutData(gd);
        tableNamePattern.setToolTipText(tableNamePatternDesc);
        addMContainer("tableNamePattern", tableNamePattern);

        
        // 新的1行，pdm路径
        new Label(container, SWT.NULL).setText("pdm文件");
        Text pdmPath = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        pdmPath.setLayoutData(gd);
        addMContainer("pdmPath", pdmPath);
        
        Button button_selectPdmPath = new Button(container, SWT.PUSH);
        button_selectPdmPath.setText("浏览...");
        button_selectPdmPath.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(getShell());
                String fPdmPath = dialog.open();
                setMContainerText("pdmPath", fPdmPath);
                loadPdm();
            }
        });
		
	}
	
	private void createButtonConnectClearArea(Composite container, int columns) {
    	GridData gd = null;
        //新的1行，列出表的画布
        Canvas canvas = new Canvas(container, SWT.NONE);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = 2;
        canvas.setLayoutData(gd);
        GridLayout layoutCanvas = new GridLayout();
        layoutCanvas.numColumns = 3;
        canvas.setLayout(layoutCanvas);
        
        //连接
        Button button_connect = new Button(canvas, SWT.PUSH);
        button_connect.setText("连接并载入表");
        gd = new GridData();
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.END;
        gd.grabExcessHorizontalSpace = true;
        button_connect.setLayoutData(gd);
        button_connect.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                clear_onClick();
                connect_onClick();
            }
        });
        
        //清空按钮
        Button button_clear = new Button(canvas, SWT.PUSH);
        button_clear.setText("重置连接");
        gd = new GridData();
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.END;
        gd.grabExcessHorizontalSpace = true;
        button_clear.setLayoutData(gd);
        button_clear.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                clear_onClick();
                ((Combo) getMContainer("catalog")).setText("");
                ((Combo) getMContainer("schemaPattern")).setText("");
                ((Text) getMContainer("tableNamePattern")).setText("");
                ((Combo) getMContainer("catalog")).removeAll();
                ((Combo) getMContainer("schemaPattern")).removeAll();
            }
        });
        
        Button button_clearCache = new Button(canvas, SWT.PUSH);
        button_clearCache.setText("清缓存");
        button_clearCache.setToolTipText("清理当前url和user对应的缓存目录");
        gd = new GridData();
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.END;
        gd.grabExcessHorizontalSpace = true;
        button_clearCache.setLayoutData(gd);
        button_clearCache.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                try {
                	String longTableFolder = RmXmlHelper.formatToFile(QbXmlGenerateCodePlugin.qbGenerateCodeHome + "/" + getLongTableFolderName());
                	RmFileHelper.delDir(longTableFolder);
                    updateStatus("删除目录:" + longTableFolder);
                } catch (Exception e1) {
                    updateStatus("清缓存失败:" + e1.getMessage());
                    //e1.printStackTrace();
                }
            }
        });
	}

	private void createListTableArea(Composite container, int columns) {
    	GridData gd = null;
        //新的1行，增加一行分隔线
        //createLine(container, columns);

        //新的1行，列出表的画布
        int listTableHeight = 30;
        Canvas canvas = new Canvas(container, SWT.NONE);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = columns;
        canvas.setLayoutData(gd);
        GridLayout layoutCanvas = new GridLayout();
        layoutCanvas.numColumns = 4;
        canvas.setLayout(layoutCanvas);

        //描述数据库中的表
        Label label_tableFrom = new Label(canvas, SWT.NONE);
        label_tableFrom.setText("数据库中的表");
        gd = new GridData();
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_tableFrom.setLayoutData(gd);
        
        new Label(canvas, SWT.NONE).setText("");

        //描述您选择了的表
        Label label_tableTo = new Label(canvas, SWT.NONE);
        label_tableTo.setText("已选择的表");
        gd = new GridData();
        gd.horizontalSpan = 1;
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.grabExcessHorizontalSpace = true;
        label_tableTo.setLayoutData(gd);
        
        new Label(canvas, SWT.NONE).setText("状态");

        //待选表名列表
        final List list_tableFrom = new List(canvas, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        gd.verticalSpan = listTableHeight;
        list_tableFrom.setLayoutData(gd);
        addMContainer("list_tableFrom", list_tableFrom);
        list_tableFrom.addListener(SWT.MouseDoubleClick, this);
        
        //右移按钮
        Button button_right = new Button(canvas, SWT.PUSH);
        button_right.setText(">>");
        addMContainer("button_right", button_right);

        //已选表名列表
        List list_tableTo = new List(canvas, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        gd.verticalSpan = listTableHeight;
        list_tableTo.setLayoutData(gd);
        list_tableTo.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                refreshLTableStatus(); 
            }
            public void widgetDefaultSelected(SelectionEvent e) {
                refreshLTableStatus();
            }
        });
        addMContainer("tableTo", list_tableTo);

        //提示操作已选表名
        List list_tableStatus = new List(canvas, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        gd.verticalSpan = listTableHeight;
        list_tableStatus.setLayoutData(gd);
        addMContainer("list_tableStatus", list_tableStatus);
        
        new Label(canvas, SWT.NONE).setText("");
        
        //左移按钮
        Button button_left = new Button(canvas, SWT.PUSH);
        button_left.setText("<<");
        addMContainer("button_left", button_left);

        new Label(canvas, SWT.NONE).setText("");
        new Label(canvas, SWT.NONE).setText("");
		
	}

	private void createBaseProjectPath(Composite container, int columns) {
		GridData gd = null;
		// 新的1行，项目路径
		new Label(container, SWT.NULL).setText("项目路径");
		Text baseProjectPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 600;
		gd.horizontalSpan = columns - 2;
		baseProjectPath.setLayoutData(gd);
		addMContainer("baseProjectPath", baseProjectPath);
		Button button_baseProjectPath = new Button(container, SWT.PUSH);
		button_baseProjectPath.setText("浏览...");
		button_baseProjectPath.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseBaseProjectPath("baseProjectPath");
			}
		});
	}
    
	protected void handleBrowseBaseProjectPath(String textName) {
        ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "请选择目标文件夹");
        if (dialog.open() == ContainerSelectionDialog.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                Path resultPath = (Path) result[0];
                if ("baseProjectPath".equals(textName)) { //项目根路径
                    setMContainerText(textName, GcPluginHelper.getProjectRealPath(resultPath.toOSString()));
                }
            }
        }
	}

	//生成一行分隔线
    public static void createLine(Composite parent, int ncol) {
        Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        line.setLayoutData(gridData);
    }

    /**
     * Tests if the current workbench selection is a suitable container to use.
     * 初始化，载入相关参数
     */

    @SuppressWarnings("unchecked")
	private void initialize() {
        if (selection != null && selection.isEmpty() == false && selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection) selection;
            if (ssel.size() > 1)
                return;
            Object obj = ssel.getFirstElement();
            if (obj instanceof IResource) {
                IContainer container;
                if (obj instanceof IContainer)
                    container = (IContainer) obj;
                else
                    container = ((IResource) obj).getParent();
                //自动设置第一个项目路径和项目名
                setMContainerText("baseProjectPath", GcPluginHelper.getProjectRealPath(container.getFullPath().toString()));
            }
        }
        addCompositeFromXml((Combo) getMContainer("dbProductName"), "/rules/dataType/dbTypes/dbType", gcRule.getMainRule());
        java.util.List<Node> lNode = gcRule.getMainRule().selectNodes("//@*|node()");
        for (Node node : lNode) {
            if (node.getName() != null && node.getName().length() > 0) {
                setMContainerText(node.getName(), node.getText());
            }
        }
        addSelectTableListeners();
        loadPdm();
    }

    /**
     * 功能: 把所有的写回xml中
     */
    @SuppressWarnings("unchecked")
	private int writeValueIntoXml() {
        int count = 0;
        java.util.List<Node> lNode = gcRule.getMainRule().selectNodes("//@*|node()");
        for (Node node : lNode) {
            if (node.getName() != null && node.getName().length() > 0) {
                if (getMContainerText(node.getName()) != null && !getMContainerText(node.getName()).equals(node.getText())) {
                    node.setText(getMContainerText(node.getName()));
                    setMContainerText(node.getName(), node.getText());
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 功能: 添加选取表的侦听事件
     */
    private void addSelectTableListeners() {
        Button button_left = (Button) getMContainer("button_left");
        Button button_right = (Button) getMContainer("button_right");
        List lTableTo = ((List) getMContainer("tableTo"));
        button_left.addListener(SWT.Selection, this);
        button_right.addListener(SWT.Selection, this);
        lTableTo.addListener(SWT.MouseDoubleClick, this);
        lTableTo.addListener(SWT.MouseEnter, this);

        for (Iterator<String> itMContainer = mContainer.keySet().iterator(); itMContainer.hasNext();) {
            String key = String.valueOf(itMContainer.next());
            if (getMContainer(key) instanceof Text) {
                ((Text) getMContainer(key)).addListener(SWT.Modify, this);
            } else if (getMContainer(key) instanceof Combo) {
                ((Combo) getMContainer(key)).addListener(SWT.Modify, this);
            } else if (getMContainer(key) instanceof List) {
            	
            } else {
            	
            }
            if (getMContainer(key) instanceof Widget) {
                ((Widget) getMContainer(key)).addListener(SWT.FocusIn, this);
            }
        }
    }

    private void refreshLTableStatus() { //刷新状态聚焦
        List lTableTo = ((List) getMContainer("tableTo"));
        List lTableStatus = ((List) getMContainer("list_tableStatus"));
        if(lTableTo.getSelectionIndices().length > 0) {
            lTableStatus.deselectAll();
            lTableStatus.select(lTableTo.getSelectionIndices());
            lTableStatus.showSelection();
        }
    }
    
    /**
     * 功能:实现按钮侦听
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * @param event
     */
    public void handleEvent(Event event) {
        Button button_left = (Button) getMContainer("button_left");
        Button button_right = (Button) getMContainer("button_right");
        List lTableFrom = ((List) getMContainer("list_tableFrom"));
        List lTableTo = ((List) getMContainer("tableTo"));
        List lTableStatus = ((List) getMContainer("list_tableStatus"));
        this.setErrorMessage(null);
        if (event.type == SWT.Modify) {
        	writeValueIntoXml();
        }
        
        if ((event.widget == button_right && event.type == SWT.Selection) || (event.widget == lTableFrom && event.type == SWT.MouseDoubleClick)) {
            int[] aSelection = lTableFrom.getSelectionIndices();
            for (int i = aSelection.length - 1; i >= 0; i--) {
                lTableTo.add(lTableFrom.getItem(aSelection[i]));
                lTableStatus.add("未设置,请双击此表");
                lTableFrom.remove(aSelection[i]);
            }
        }
        if (event.widget == button_left && event.type == SWT.Selection) { 
            int[] aSelection = lTableTo.getSelectionIndices();
            for (int i = aSelection.length - 1; i >= 0; i--) {
                { //从rule.xml中删除原计划要生成的表，相当于清空
                    Node thisTableTo = getNodeFromXmlByText("/rules/database/tableTos/tableTo", lTableTo.getItem(aSelection[i]), gcRule.getMainRule());
                    if (thisTableTo != null) {
                        Element thisTableTos = thisTableTo.getParent();
                        thisTableTos.remove(thisTableTo);
                    }
                }
                lTableFrom.add(lTableTo.getItem(aSelection[i]));
                lTableTo.remove(aSelection[i]);
                lTableStatus.remove(aSelection[i]);
            }
        }
        if (event.widget == lTableTo && event.type == SWT.MouseEnter) {  //目标表区域，鼠标变手
            final Cursor cursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_HAND);
            lTableTo.setCursor(cursor);
            dialogChanged();
        }

        if (event.widget == lTableTo && event.type == SWT.MouseDoubleClick) {
            if (lTableTo.getFocusIndex() >= 0) {
            	configTableOnDoubleClick(lTableTo, lTableStatus);
            }
        }
        if (event.type == SWT.FocusIn || event.type == SWT.Modify || event.type == SWT.MouseDoubleClick) {
            dialogChanged();
        }
        
        if (event.widget == ((Text) getMContainer("pdmPath")) && event.type == SWT.Modify) {
            loadPdm();
        }

    }
    
    /**
     * 双击lTableTo，弹出配置表的对话框
     * 
     * @param lTableTo
     * @param lTableStatus
     */
    private void configTableOnDoubleClick(List lTableTo, List lTableStatus) {
        String currentTable = lTableTo.getItem(lTableTo.getFocusIndex());
        File currentTableXmlFile = new File(RmXmlHelper.formatToFile(QbXmlGenerateCodePlugin.qbGenerateCodeHome + "/" + getLongTableXmlName(currentTable)));
        if (connectDatabase()) {
            try {
                if (gcRule.getMTableDocs().get(currentTable) == null) { //如果内存中没有xml
                	gcRule.initTableDoc(currentTable, currentTableXmlFile, pdmParser, this);
                    //QbXmlGenerateCodePlugin.log("save file '" + currentTableXmlFile.getPath() + "', before dialog");
                    RmXmlHelper.saveXmlToPath((Document) gcRule.getMTableDocs().get(currentTable), currentTableXmlFile.getPath());

                }
                { //弹出对话框,并等其点OK后,把值保存回去xml
                    ConfigTableDialog dialog = new ConfigTableDialog(this.getShell(), this, currentTable, gcRule);
                    dialog.create();
                    if (dialog.open() == ContainerSelectionDialog.OK) {
                        //QbXmlGenerateCodePlugin.log("save file '" + currentTableXmlFile.getPath() + "', after OK");
                        RmXmlHelper.saveXmlToPath((Document) gcRule.getMTableDocs().get(currentTable), currentTableXmlFile.getPath());
                    }
                }
                lTableStatus.setItem(lTableTo.getFocusIndex(), "finished");
                { //删除xml中的已选状态，回写tableTos/@xmlName
                    Node thisTableTo = getNodeFromXmlByText("/rules/database/tableTos/tableTo", currentTable, gcRule.getMainRule());
                    if (thisTableTo == null) {
                        Element thisTableTos = (Element) gcRule.getMainRule().selectSingleNode("/rules/database/tableTos");
                        Element thisTableToEle = thisTableTos.addElement("tableTo");
                        thisTableToEle.setText(currentTable);
                        thisTableToEle.addAttribute("xmlName", getLongTableXmlName(currentTable));
                    } else {
                        Node thisXmlName = thisTableTo.selectSingleNode("@xmlName");
                        thisXmlName.setText(getLongTableXmlName(currentTable));
                    }
                }
            } catch (Exception e) {
            	e.printStackTrace();
                ErrorDialog msgDialog = new ErrorDialog(this.getShell(), "", "不能生成此表", new ResourceStatus(1, "\n从 " + currentTable + " 表生成xml定义文件时错误!\n\n" + e.toString()), 1);
                msgDialog.create();
                msgDialog.open();
            }
        }
    }

    /**
     * 功能: 从表名获得 table.xml的绝对路径
     */
    private String getLongTableXmlName(String currentTable) {
    	return getLongTableFolderName() + currentTable + ".xml";
    }
    
    /**
     * 得到table.xml所在的目录
     */
    private String getLongTableFolderName() {
        String formatUrl = getMContainerText("url");
        if (formatUrl.length() > 150) {
            formatUrl.substring(150);
        }
        formatUrl = formatUrl.replace(':', '_');
        formatUrl = formatUrl.replace('/', '_');
        formatUrl = formatUrl.replace('@', '_');
        formatUrl = formatUrl.replace('=', '_');
        formatUrl = formatUrl.replace('*', '_');
        formatUrl = formatUrl.replace(' ', '_');
        formatUrl = formatUrl.replace('?', '_');
        formatUrl = formatUrl.replace('&', '_');
        return formatUrl + "/" + getMContainerText("userName") + "/";
    }

    /**
     * 功能: 从xml中读取参数，初始化combo
     * 
     * @param combo
     * @param xPathStr
     * @return
     */
    @SuppressWarnings("unchecked")
	private int addCompositeFromXml(Combo combo, String xPathStr, Document doc) {
        int index = 0;
        java.util.List<Node> lNode = doc.selectNodes(xPathStr);
        for (Node node : lNode) {
            if (node.getName() != null && node.getName().length() > 0 && node.getText().length() > 0) {
            	combo.add(node.getText());
                index++;
            }
        }
        return index;
    }

    /**
     * 功能: 从xml中获取制定xPathStr的所有节点，取出与textValue相等的值
     * 
     * @param xPathStr
     * @param textValue
     * @return
     */
    @SuppressWarnings("unchecked")
	private static Node getNodeFromXmlByText(String xPathStr, String textValue, Document doc) {
        java.util.List<Node> lNodes = doc.selectNodes(xPathStr);
        Node thisNode = null;
        for (Node node : lNodes) {
            if (node.getText().equals(textValue)) {
                thisNode = node;
            }
        }
        return thisNode;
    }

    /**
     * 功能: 重新连接数据库前的清理
     */
    public void clear_onClick() {
        ((List) getMContainer("list_tableFrom")).removeAll();
        ((List) getMContainer("tableTo")).removeAll();
        ((List) getMContainer("list_tableStatus")).removeAll();
        { //从rule.xml中删除
            Element thisTableTos = (Element) gcRule.getMainRule().selectSingleNode("/rules/database/tableTos");
            if (thisTableTos != null) {
                thisTableTos.clearContent();
            }
        }
        gcRule.closeConnection();
    }

    /**
     * 功能: 连接按钮操作，连接数据库
     * 
     * @param driver
     * @param url
     * @param userName
     * @param password
     */
    public boolean connect_onClick() {
        if (!connectDatabase()) {
            return false;
        }
        ResultSet rs = null;
        ResultSet rsCatalog = null;
        ResultSet rsSchemaPattern = null;
        try {
            DatabaseMetaData dbmd = gcRule.getConnection().getMetaData();
            String catalog = "".equals(getMContainerText("catalog")) ? null : getMContainerText("catalog");
            String schemaPattern = "".equals(getMContainerText("schemaPattern")) ? null : getMContainerText("schemaPattern");
            String tableNamePattern = "".equals(getMContainerText("tableNamePattern")) ? null : getMContainerText("tableNamePattern");
            rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, new String[] { "TABLE" });
            while (rs.next()) {
                String tableName = rs.getString(3);
                ((List) getMContainer("list_tableFrom")).add(tableName);
            }
            { //get catalog
                //删掉catalog，并保留原来的值
                String currentCatalog = ((Combo) getMContainer("catalog")).getText();
                ((Combo) getMContainer("catalog")).removeAll();

                rsCatalog = dbmd.getCatalogs();
                while (rsCatalog.next()) {
                    String tempCatalog = rsCatalog.getString(1);
                    ((Combo) getMContainer("catalog")).add(tempCatalog);
                }
                if (currentCatalog.trim().length() > 0) {
                    ((Combo) getMContainer("catalog")).setText(currentCatalog);
                }
            }
            { //get schemaPattern
                //删掉schemaPattern，并保留原来的值
                String currentSchemaPattern = ((Combo) getMContainer("schemaPattern")).getText();
                ((Combo) getMContainer("schemaPattern")).removeAll();

                rsSchemaPattern = dbmd.getSchemas();
                while (rsSchemaPattern.next()) {
                    String tempSchemaPattern = rsSchemaPattern.getString(1);
                    ((Combo) getMContainer("schemaPattern")).add(tempSchemaPattern);
                }
                if (currentSchemaPattern.trim().length() > 0) {
                    ((Combo) getMContainer("schemaPattern")).setText(currentSchemaPattern);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            updateStatus(e.toString());
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                updateStatus(e1.toString());
            }
        }
    }

    /**
     * 功能: 默认连接数据库
     * 
     *  
     */
    public boolean connectDatabase() {
    	String result = gcRule.connectDatabase(getMContainerText("driver"), getMContainerText("url"), getMContainerText("userName"), getMContainerText("password"));
    	if(result != null) {
    		updateStatus(result);
    		return false;
    	} else {
    		return true;
    	}
    }
    
    private void loadPdm() {
    	if(getMContainerText("pdmPath") == null || getMContainerText("pdmPath").trim().length() == 0) {
    		return;
    	}
        updateStatus("载入pdm......");
		try {
			pdmParser = new PdmParser(getMContainerText("pdmPath"));
		} catch (MalformedURLException e) {
			updateStatus(e.toString());
			e.printStackTrace();
		} catch (DocumentException e) {
			updateStatus(e.toString());
			e.printStackTrace();
		}
        if(pdmParser == null || pdmParser.getMPdmColumn() == null) {
        	updateStatus("载入pdm失败，请重新选择！");
        	return;
        }
        updateStatus("载入pdm成功，获取了" + pdmParser.getMPdmColumn().size() + "条映射数据！");
        gcRule.refreshTableColumn(pdmParser.getMPdmColumn(), this);
    }

    /**
     * 当输入框值改变时，改变提示状态
     */
    private void dialogChanged() {
		if (getMContainerText("baseProjectPath").trim().length() == 0) {
            updateStatus("请指定项目路径");
        } else if (getMContainerText("driver").trim().length() == 0) {
            updateStatus("请指定数据库驱动");
        } else if (getMContainerText("url").trim().length() == 0) {
            updateStatus("请指定数据库地址");
        } else if (getMContainerText("userName").trim().length() == 0) {
            updateStatus("请指定用户名");
        } else if (((List) getMContainer("tableTo")).getItemCount() == 0) {
            updateStatus("请指定选择您要生成的表,双击它,并在对话框中设置其生成规则");
        } else if (((List) getMContainer("list_tableStatus")).indexOf("finished") < 0) {
            updateStatus("请至少设置成功一个表,未设置的表不会生成代码");
        } else {
            updateStatus(null);
        }
    }

    /**
     * 功能: 更新状态
     * 
     * @param message
     */
    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    /**
     * 功能: 添加容器
     * 
     * @param key
     * @param value
     */
    public void addMContainer(String key, Object value) {
        this.mContainer.put(key, value);
    }


    /**
     * 功能: 设置容器的文本内容
     * 
     * @param key
     * @param value
     */
    public void setMContainerText(String key, String value) {
        if (getMContainer(key) == null) {
            return;
        }
        if (getMContainer(key) instanceof Text) {
            if (!((Text) getMContainer(key)).getText().equals(value)) {
                ((Text) getMContainer(key)).setText(value);
            }
        } else if (getMContainer(key) instanceof Combo) {
            if (!((Combo) getMContainer(key)).getText().equals(value)) {
                ((Combo) getMContainer(key)).setText(value);
            }
        } else if (getMContainer(key) instanceof List) {
            List lTemp = (List) getMContainer(key);
            for (int i = 0; i < lTemp.getSize().y; i++) {
                if (lTemp.getItem(i).equals(value)) {
                    lTemp.select(i);
                }
            }
        } else if (getMContainer(key) instanceof String) { //hidden变量
            mContainer.put(key, value);
        }
    }

    /**
     * 功能: 获得容器对象
     * 
     * @param key
     * @return
     */
    public Object getMContainer(String key) {
        return this.mContainer.get(key);
    }
    
    /**
     * 功能: 获得容器的文本内容
     * 
     * @param key
     * @return
     */
    public String getMContainerText(String key) {
        if (getMContainer(key) == null) {
            return null;
        }
        if (getMContainer(key) instanceof Text) {
            return ((Text) getMContainer(key)).getText();
        } else if (getMContainer(key) instanceof Combo) {
            return ((Combo) getMContainer(key)).getText();
        } else if (getMContainer(key) instanceof List) {
            return "";
        } else if (getMContainer(key) instanceof String) {
            return String.valueOf(mContainer.get(key));
        } else {
            return "";
        }
    }
}
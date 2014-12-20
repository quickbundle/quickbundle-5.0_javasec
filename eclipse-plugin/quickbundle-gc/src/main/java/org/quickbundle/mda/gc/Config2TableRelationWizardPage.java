package org.quickbundle.mda.gc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class Config2TableRelationWizardPage extends WizardPage implements Listener {

	private GenerateCodeRule gcRule = null;
    final private List<List<Object>> relations = new ArrayList<List<Object>>();
    private TableRelation tableRelation = null;
    
	public Config2TableRelationWizardPage(GenerateCodeWizard currentWizard) {
        super("tableRelationWizardPage");
        setTitle("生成代码 2/3: 配置主子表和多表关系");
        this.gcRule = currentWizard.getGcRule();
        this.tableRelation = new TableRelation(currentWizard.getGcRule());
    }
	
	public void createControl(Composite parent) {
        final int columns = 7; //定义列数
    	Composite container = null;
    	//从父容器获得ScrolledComposite
    	if(parent.getChildren() != null && parent.getChildren().length > 1 && parent.getChildren()[1] instanceof ScrolledComposite) {
    		ScrolledComposite scroll = (ScrolledComposite)parent.getChildren()[1];
    		container = new Composite(scroll, SWT.NULL);
    		scroll.setContent(container);
    	} else {
    		container = new Composite(parent, SWT.NULL);
    	}
    	container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	container.setLayout(new GridLayout(columns, false));
    	final Composite containerFinal = container;
    	
    	//添加一组关系的按钮
        Button addRelationGroup = new Button(container, SWT.CENTER);
        GridData gd = new GridData(GridData.CENTER);
        gd.horizontalSpan = columns;
        addRelationGroup.setLayoutData(gd);
        addRelationGroup.setText("添加一组关系");
        addRelationGroup.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                relations.add(addRelationGroup(containerFinal, columns, null));
            }
        });
    	
        initTableRelationWidget(container, columns);
        
        //定义Next事件
        final TableRelation tableRelationFinal = tableRelation;
        WizardDialog dialog = (WizardDialog) getContainer();  
        dialog.addPageChangingListener(new IPageChangingListener() {  
            public void handlePageChanging(PageChangingEvent event) {
            	if(event.getCurrentPage() instanceof Config2TableRelationWizardPage
            			&& event.getTargetPage() instanceof Config3MvmWizardPage) {
            		try {
            			//把界面上的表关系体现到Xml
            			tableRelationFinal.buildTableRelationXml(relations);
            			//把子表合并到主表的xml中
            			tableRelationFinal.mergeChildTable();
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
        
        setControl(container);
		initialize();
		dialogChanged();
	}
	
	private void initTableRelationWidget (Composite container, int columns) {
		boolean needCreateDefault = true;
		Map<String, Document> mTableDoc = gcRule.getMTableDocs();
		cleanUnusedRefTable(mTableDoc);
		for(Map.Entry<String, Document> en : mTableDoc.entrySet()) {
			Document docMeta = en.getValue();
			List<Element> lMainTable = docMeta.selectNodes("/meta/relations/mainTable");
			for(Element mainTable : lMainTable) {
				if(mainTable.selectNodes("refTable").size() > 0) {
					needCreateDefault = false;
					relations.add(addRelationGroup(container, columns, mainTable));
				}
			}
		}
        //默认加一组关系
		if(needCreateDefault) {
			relations.add(addRelationGroup(container, columns, null));
		}
	}
	
	private void cleanUnusedRefTable(Map<String, Document> mTableDoc) {
		for(Map.Entry<String, Document> en : mTableDoc.entrySet()) {
			Document docMeta = en.getValue();
			List<Element> lMainTable = docMeta.selectNodes("/meta/relations/mainTable");
			for(Element mainTable : lMainTable) {
				if(!mTableDoc.containsKey(mainTable.valueOf("@tableName"))) {
					mainTable.getParent().remove(mainTable);
					continue;
				}
				List<Element> lRefTable = mainTable.selectNodes("refTable");
				for(Element refTable : lRefTable) {
					if(!mTableDoc.containsKey(refTable.valueOf("@tableName"))) {
						mainTable.remove(refTable);
					}
				}
			}
		}
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @param mainTable
	 * @return
	 */
	private List<Object> addRelationGroup(final Composite parent, final int columns, Element mainTable) {
        //定义单独的画布
        final Canvas canvas = new Canvas(parent, SWT.NONE);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = columns;
        canvas.setLayoutData(gd);
        GridLayout layoutCanvas = new GridLayout();
        layoutCanvas.numColumns = columns;
        canvas.setLayout(layoutCanvas);
        
        //当前组的控件容器，用于抽数据和销毁
		List<Object> lWidgetGroup = new ArrayList<Object>();
		//第一个对象是所有关系行的容器
		lWidgetGroup.add(new ArrayList<List>());
		//画布加入控件容器
		lWidgetGroup.add(canvas);
		
		createLine(lWidgetGroup, canvas, columns);
		
		//定义“删除组”的按钮
        Button removeRelationGroup = new Button(canvas, SWT.CENTER);
        removeRelationGroup.setText("删除组");
        //把当前组的控件容器绑定到“删除组”按钮
        removeRelationGroup.setData(lWidgetGroup);
        removeRelationGroup.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	List<Object> lWidgetGroupInner = (List)((Button)e.getSource()).getData();
                for(Object obj : lWidgetGroupInner) {
                	if(obj instanceof Widget) {
                		((Widget)obj).dispose();
                	} else if(obj instanceof Canvas) {
                		((Canvas)obj).dispose();
                	} else if(obj instanceof List) { //第一个对象是所有的关系行
                		for(Object lWidgetChildTableO: ((List)obj)) {
                    		List<Widget> lWidgetChildOrMiddleTable = (List)lWidgetChildTableO; //每一个关系行
                    		for(Widget widget : lWidgetChildOrMiddleTable) { //遍历关系行中的每个控件
                            	widget.dispose();
                    		}
                		}
                	}
                }
                //从顶点容器删除当前组的控件容器
                relations.remove(lWidgetGroupInner);
                parent.layout();
            }
        });
        lWidgetGroup.add(removeRelationGroup);
        
        //定义主表行
        Label labelParentTable = new Label(canvas, SWT.NULL);
        labelParentTable.setText("主表");
        lWidgetGroup.add(labelParentTable);
        
        final Combo comboParentTable = new Combo(canvas, SWT.LEFT);
        comboParentTable.setItems(tableRelation.getAvailableTables().toArray(new String[0]));
        //给comboParentTable预留一个List，放置中间表的Combo，用于分析最有可能的中间表候选
        comboParentTable.setData(new ArrayList<Combo>());
        comboParentTable.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        lWidgetGroup.add(comboParentTable);
        
        Label labelParentTablePk = new Label(canvas, SWT.NULL);
        labelParentTablePk.setText("PK");
        lWidgetGroup.add(labelParentTablePk);
        
        final Combo comboParentTablePk = new Combo(canvas, SWT.LEFT);
        comboParentTablePk.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        lWidgetGroup.add(comboParentTablePk);

        //主表表名与主表列名联动 && 并且会影响中间表的下拉范围
        comboParentTable.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				comboParentTableChange((Combo)e.getSource(), comboParentTablePk);
				Combo comboParentTableInner = (Combo)e.getSource();
				try {
					List<String> avaliableMiddleTables = tableRelation.getAvailableMiddleTables(comboParentTableInner.getText());
					List<Combo> lMiddleTable = (List)comboParentTableInner.getData();
					for(Combo middleTable : lMiddleTable) {
						if(!middleTable.isDisposed()) {
							List<String> smallAvaliableMiddleTables = null;
							try {
								smallAvaliableMiddleTables = tableRelation.getSmallAvaliableMiddleTables(avaliableMiddleTables, comboParentTableInner, middleTable);
								middleTable.setItems(smallAvaliableMiddleTables.toArray(new String[0]));
							} catch (SQLException e1) {
								e1.printStackTrace();
								setErrorMessage(e1.toString());
							}
						}
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
					setErrorMessage(e2.toString());
				}

			}
		});
        comboParentTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				comboParentTableChange((Combo)e.getSource(), comboParentTablePk);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				comboParentTableChange((Combo)e.getSource(), comboParentTablePk);
			}
		});
        //默认选中第一个表
        if(comboParentTable.getItemCount() > 0) {
        	comboParentTable.select(0);
        }
        
        //定义加主子关系的按钮
        Button addChildButton = new Button(canvas, SWT.LEFT);
        addChildButton.setText("加主子");
        //把当前组的控件容器lWidgetGroup绑定到“加主子”按钮，为了执行加主子关系动作时，能把新的关系行加入lWidgetGroup
        addChildButton.setData(lWidgetGroup);
        addChildButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	List<Object> lWidgetGroupInner = (List)((Button)e.getSource()).getData();
            	addChildTableRelation(lWidgetGroupInner, canvas, columns, comboParentTable, null);
            }
        });
        lWidgetGroup.add(addChildButton);
        
        //定义加中间表关系的按钮
        Button addMiddleTableButton = new Button(canvas, SWT.LEFT);
        addMiddleTableButton.setText("加中间表");
        //把当前组的控件容器lWidgetGroup绑定到“加中间表”按钮，为了执行加中间表关系动作时，能把新的关系行加入lWidgetGroup
        addMiddleTableButton.setData(lWidgetGroup);
        addMiddleTableButton.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		List<Object> lWidgetGroupInner = (List)((Button)e.getSource()).getData();
        		addMiddleTableRelation(lWidgetGroupInner, canvas, columns, comboParentTable, null);
        	}
        });
        lWidgetGroup.add(addMiddleTableButton);
        
        if(mainTable != null) {
        	comboParentTable.setText(mainTable.valueOf("@tableName"));
        	comboParentTablePk.setText(mainTable.valueOf("@refColumn"));
        	List<Element> lRefTable = mainTable.selectNodes("refTable");
        	for(Element refTable : lRefTable) {
        		if(refTable.selectNodes("middleTable").size() == 0) {
        			addChildTableRelation(lWidgetGroup, canvas, columns, comboParentTable, refTable);
        		} else {
        			addMiddleTableRelation(lWidgetGroup, canvas, columns, comboParentTable, refTable);
        		}
        	}
        } else {
        	//默认添加一行子表关系
        	addChildTableRelation(lWidgetGroup, canvas, columns, comboParentTable, null);
        }
        parent.layout();
        return lWidgetGroup;
	}
	
	
	private void addChildTableRelation(final List<Object> lWidgetGroup, final Composite container, int columns, Combo comboParentTable, Element refTable) {
		final List<Widget> lWidgetChildTable = new ArrayList<Widget>();
		((List)lWidgetGroup.get(0)).add(lWidgetChildTable);
        //定义主子关系行
        Label labelChildTableEqual = new Label(container, SWT.NULL);
        labelChildTableEqual.setText("=");
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.RIGHT;
        labelChildTableEqual.setLayoutData(gd);
        lWidgetChildTable.add(labelChildTableEqual);
        
        Label labelChildTable = new Label(container, SWT.NULL);
        labelChildTable.setText("子表");
        lWidgetChildTable.add(labelChildTable);
        
        Combo comboChildTable = new Combo(container, SWT.LEFT);
        comboChildTable.setItems(tableRelation.getAvailableTables().toArray(new String[0]));
        comboChildTable.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        lWidgetChildTable.add(comboChildTable);
        
        Label labelChildTableFk = new Label(container, SWT.NULL);
        labelChildTableFk.setText("FK");
        lWidgetChildTable.add(labelChildTableFk);
        
        final Combo comboChildTableFk = new Combo(container, SWT.LEFT);
        comboChildTableFk.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        //绑定主表表名Combo到子表外键Combo，以自动分析子表外键
        comboChildTableFk.setData(comboParentTable);
        lWidgetChildTable.add(comboChildTableFk);

        //子表表名与子表列名联动
        comboChildTable.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				comboParentTableChange((Combo)e.getSource(), comboChildTableFk);
				guessChildTableFk((Combo)comboChildTableFk.getData(), (Combo)e.getSource(), comboChildTableFk);
			}
		});
        comboChildTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
//				comboParentTableChange((Combo)e.getSource(), comboChildTableFk);
//				guessChildTableFk((Combo)comboChildTableFk.getData(), (Combo)e.getSource(), comboChildTableFk);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
//				comboParentTableChange((Combo)e.getSource(), comboChildTableFk);
//				guessChildTableFk((Combo)comboChildTableFk.getData(), (Combo)e.getSource(), comboChildTableFk);
			}
		});
        
        //定义"删关联"按钮
        Button removeChildButton = new Button(container, SWT.LEFT);
        removeChildButton.setText("删关联");
        removeChildButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                for(Widget widget : lWidgetChildTable) {
                	widget.dispose();
                }
                //删关联时，清理组容器的主子表关系行容器
                ((List)lWidgetGroup.get(0)).add(lWidgetChildTable);
                container.getParent().layout();
            }
        });
        lWidgetChildTable.add(removeChildButton);
        
        Label labelEmpty = new Label(container, SWT.NULL);
        lWidgetChildTable.add(labelEmpty);
        
        if(refTable != null) {
        	comboChildTable.setText(refTable.valueOf("@tableName"));
        	comboChildTableFk.setText(refTable.valueOf("@refColumn"));
        }
        
        container.getParent().layout();
	}
	private void addMiddleTableRelation(final List<Object> lWidgetGroup, final Composite container, int columns, final Combo comboParentTable, Element refTable) {
        //新的1行，列出表的画布
        final Canvas canvasMiddle = new Canvas(container, SWT.NONE);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = columns;
        canvasMiddle.setLayoutData(gd);
        GridLayout layoutCanvas = new GridLayout();
        layoutCanvas.numColumns = 11;
        canvasMiddle.setLayout(layoutCanvas);
        
		final List<Widget> lWidgetMiddleTable = new ArrayList<Widget>();
		//重要! 中间表关系行，把canvasMiddle加到lWidgetMiddleTable
		lWidgetMiddleTable.add(canvasMiddle);
		
		((List)lWidgetGroup.get(0)).add(lWidgetMiddleTable);
		
		//定义中间表行
		Label labelMiddleTableEqual1 = new Label(canvasMiddle, SWT.NULL);
		labelMiddleTableEqual1.setText("=中间表");
		GridData gdIndex = new GridData();
		gdIndex.horizontalAlignment = SWT.RIGHT;
		labelMiddleTableEqual1.setLayoutData(gdIndex);
		lWidgetMiddleTable.add(labelMiddleTableEqual1);
		
		final Combo comboMiddleTable = new Combo(canvasMiddle, SWT.BOLD);
		try {
			comboMiddleTable.setItems(tableRelation.getAvailableMiddleTables(comboParentTable.getText()).toArray(new String[0]));
		} catch (Exception e2) {
			e2.printStackTrace();
			setErrorMessage(e2.toString());
		}
		//把中间表加到comboParentTable的触发列表
		((List<Combo>)comboParentTable.getData()).add(comboMiddleTable);
		comboMiddleTable.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lWidgetMiddleTable.add(comboMiddleTable);
		
		Label labelMiddleTableDot1 = new Label(canvasMiddle, SWT.BOLD);
		labelMiddleTableDot1.setText(".");
		lWidgetMiddleTable.add(labelMiddleTableDot1);
		
		final Combo comboMiddleTableFk1 = new Combo(canvasMiddle, SWT.LEFT);
		comboMiddleTableFk1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        //绑定主表表名Combo到中间表第一个外键Combo，以自动分析中间表第一个外键
		comboMiddleTableFk1.setData(comboParentTable);
		lWidgetMiddleTable.add(comboMiddleTableFk1);
		
		Label labelMiddleTableSplit = new Label(canvasMiddle, SWT.BOLD);
		labelMiddleTableSplit.setText("|");
		lWidgetMiddleTable.add(labelMiddleTableSplit);
		
		final Combo comboMiddleTableFk2 = new Combo(canvasMiddle, SWT.LEFT);
		comboMiddleTableFk2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lWidgetMiddleTable.add(comboMiddleTableFk2);

		Label labelMiddleTableEqual2 = new Label(canvasMiddle, SWT.BOLD);
		labelMiddleTableEqual2.setText("=关联表");
		lWidgetMiddleTable.add(labelMiddleTableEqual2);

		Combo comboRefTable = new Combo(canvasMiddle, SWT.LEFT);
		comboRefTable.setItems(tableRelation.getAvailableTables().toArray(new String[0]));
		comboRefTable.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lWidgetMiddleTable.add(comboRefTable);
        //绑定关联表表名Combo到中间表第二个外键Combo，以自动分析中间表第二个外键
		comboMiddleTableFk2.setData(comboRefTable);
		
		Label labelMiddleTableDot2 = new Label(canvasMiddle, SWT.BOLD);
		labelMiddleTableDot2.setText(".");
		lWidgetMiddleTable.add(labelMiddleTableDot2);
		
		final Combo comboRefTablePk = new Combo(canvasMiddle, SWT.LEFT);
		comboRefTablePk.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lWidgetMiddleTable.add(comboRefTablePk);

        //关联表表名修改，触发关联表列名联动
		comboRefTable.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				comboParentTableChange((Combo)e.getSource(), comboRefTablePk);
				//关联表改变后，中间表的第二个外键联动
				guessChildTableFk((Combo)comboMiddleTableFk2.getData(), comboMiddleTable, comboMiddleTableFk2);
				//关联表改变后，中间表的下拉列表联动
				try {
					List<String> smallAvaliableMiddleTables = tableRelation.getMiddleTable(comboParentTable.getText(), ((Combo)e.getSource()).getText());
					List<String> lAvailableMiddleTable = tableRelation.getAvailableTables();
					for(String table0 : lAvailableMiddleTable) {
						if(!smallAvaliableMiddleTables.contains(table0)) {
							smallAvaliableMiddleTables.add(table0);
						}
					}
					comboMiddleTable.setItems(smallAvaliableMiddleTables.toArray(new String[0]));
				} catch (SQLException e1) {
					e1.printStackTrace();
					setErrorMessage(e1.toString());
				}
			}
		});
		comboRefTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
//				comboParentTableChange((Combo)e.getSource(), comboRefTablePk);
//				guessChildTableFk((Combo)comboMiddleTableFk2.getData(), comboMiddleTable, comboMiddleTableFk2);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
//				comboParentTableChange((Combo)e.getSource(), comboRefTablePk);
//				guessChildTableFk((Combo)comboMiddleTableFk2.getData(), comboMiddleTable, comboMiddleTableFk2);
			}
		});
		
		//中间表表名修改，触发2个列名联动
		comboMiddleTable.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				comboParentTableChange((Combo)e.getSource(), comboMiddleTableFk1);
				comboParentTableChange((Combo)e.getSource(), comboMiddleTableFk2);
				//中间表改变后，中间表的两个外键联动
				guessChildTableFk((Combo)comboMiddleTableFk1.getData(), (Combo)e.getSource(), comboMiddleTableFk1);
				guessChildTableFk((Combo)comboMiddleTableFk2.getData(), (Combo)e.getSource(), comboMiddleTableFk2);
			}
		});
		comboMiddleTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
//				comboParentTableChange((Combo)e.getSource(), comboMiddleTableFk1);
//				comboParentTableChange((Combo)e.getSource(), comboMiddleTableFk2);
//				guessChildTableFk((Combo)comboMiddleTableFk1.getData(), (Combo)e.getSource(), comboMiddleTableFk1);
//				guessChildTableFk((Combo)comboMiddleTableFk2.getData(), (Combo)e.getSource(), comboMiddleTableFk2);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
//				comboParentTableChange((Combo)e.getSource(), comboMiddleTableFk1);
//				comboParentTableChange((Combo)e.getSource(), comboMiddleTableFk2);
//				guessChildTableFk((Combo)comboMiddleTableFk1.getData(), (Combo)e.getSource(), comboMiddleTableFk1);
//				guessChildTableFk((Combo)comboMiddleTableFk2.getData(), (Combo)e.getSource(), comboMiddleTableFk2);
			}
		});
		
		//定义"删关联"按钮
		Button removeMiddleButton = new Button(canvasMiddle, SWT.LEFT);
		removeMiddleButton.setText("删关联");
		removeMiddleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for(Widget widget : lWidgetMiddleTable) {
					widget.dispose();
				}
                //删关联时，清理组容器的中间表关系行容器
                ((List)lWidgetGroup.get(0)).add(lWidgetMiddleTable);
				container.getParent().layout();
			}
		});
		lWidgetMiddleTable.add(removeMiddleButton);
		
        if(refTable != null) {
        	comboRefTable.setText(refTable.valueOf("@tableName"));
        	comboRefTablePk.setText(refTable.valueOf("@refColumn"));
        	comboMiddleTable.setText(refTable.valueOf("middleTable[1]/@tableName"));
        	comboMiddleTableFk1.setText(refTable.valueOf("middleTable[1]/@mainColumn"));
        	comboMiddleTableFk2.setText(refTable.valueOf("middleTable[1]/@refColumn"));
        }
        
		container.getParent().layout();
	}
	
    //生成一行分隔线
    public static void createLine(List lWidget, Composite parent, int ncol) {
        Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        line.setLayoutData(gridData);
        lWidget.add(line);
    }

	
	private void comboParentTableChange(Combo comboTable, Combo comboColumn) {
		if(comboTable.getText().length() == 0) {
			return;
		}
		comboColumn.setItems(tableRelation.getColumns(comboTable.getText()).toArray(new String[0]));
		if(comboColumn.getItemCount() > 0) {
			comboColumn.select(0);
		}
	}
	
	private void guessChildTableFk(Combo comboParentTable, Combo comboChildTable, Combo comboChildTableFk) {
		if(comboParentTable.getText().length() > 0 
				&& comboChildTable.getText().length() > 0
				&& !comboParentTable.getText().equals(comboChildTable.getText())) {
			String fkColumn = tableRelation.guessFkColomn(comboParentTable.getText(), comboChildTable.getText());
			if(fkColumn == null) {
				return;
			}
			comboChildTableFk.setText(fkColumn);
		}
	}
	
	private void initialize() {
	}
	
	private void handleBrowse() {
	}
	
	private void dialogChanged() {
		updateStatus(null);
	}

	public void updateStatus(String message) {
		setErrorMessage(message);
		//setPageComplete(message == null);
	}

	public void handleEvent(Event event) {
	}
}

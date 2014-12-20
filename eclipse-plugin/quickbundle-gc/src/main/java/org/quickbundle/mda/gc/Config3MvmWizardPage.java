package org.quickbundle.mda.gc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.quickbundle.mda.gc.project.IConfigProject;

public class Config3MvmWizardPage extends WizardPage implements Listener {

    //Wizard对象
    private GenerateCodeRule gcRule = null;
    
    private Combo comboMvm = null;
    
	public Config3MvmWizardPage(GenerateCodeWizard currentWizard) {
        super("mvmWizardPage");
        setTitle("生成代码 3/3: 配置MVM参数");
        //setDescription("请填入所有参数，然后点完成！");
        this.gcRule = currentWizard.getGcRule();
    }

	public void createControl(final Composite parent) {
        final int columns = 4; //定义列数
    	Composite container = null;
    	if(parent.getChildren() != null && parent.getChildren().length > 1 && parent.getChildren()[1] instanceof ScrolledComposite) {
    		ScrolledComposite scroll = (ScrolledComposite)parent.getChildren()[1];
    		container = new Composite(scroll, SWT.NULL);
    		scroll.setContent(container);
    	} else {
    		container = new Composite(parent, SWT.NULL);
    	}
        
    	container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	container.setLayout(new GridLayout(columns, false));
    	GridData gd = null;


    	Label labelTemplateSource = new Label(container, SWT.NULL);
    	labelTemplateSource.setText("请选择模板源:");
        gd = new GridData(GridData.CENTER);
        gd.horizontalSpan = columns;
        labelTemplateSource.setLayoutData(gd);
    	
        new Label(container, SWT.NULL).setText("模板");
        comboMvm = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        comboMvm.setItems(getMvmContextNames());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        gd.widthHint = 800;
        comboMvm.setLayoutData(gd);
    	
        new Label(container, SWT.NULL).setText("框架");
        final Text textFramework = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        textFramework.setText("jQuery-1.6 + Html + SpringMVC-3.2 + Spring-3.2 + MyBatis-3.2");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        textFramework.setLayoutData(gd);
        
        createLine(container, columns);
        
    	Label labelProjectPropHead = new Label(container, SWT.NULL);
    	labelProjectPropHead.setText("请定义项目属性:");
        gd = new GridData(GridData.CENTER);
        gd.horizontalSpan = columns;
        labelProjectPropHead.setLayoutData(gd);
        
        //选择不同的mvm
        final Canvas canvasProject = new Canvas(container, SWT.NONE);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = columns;
        canvasProject.setLayoutData(gd);
        
        comboMvm.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Combo comboMvm = (Combo)e.getSource();
				Element mvm = getMvm(comboMvm.getText());
				textFramework.setText(mvm.valueOf("framework"));
				String configProjectClass = mvm.valueOf("configProjectClass");
				if(configProjectClass == null || configProjectClass.trim().length() == 0) {
					return;
				}
				IConfigProject cp = null;
				try {
					cp = createInstance(configProjectClass.trim());
				} catch (Exception e1) {
					e1.printStackTrace();
					updateStatus(e1.toString());
				}
				comboMvm.setData(cp);
				cp.draw(canvasProject, getProjectMap());
				bindFocusIn(canvasProject);
				parent.layout();
			}
		});
        if(comboMvm.getItemCount() > 0) {
        	comboMvm.select(0);
        }

        //定义Next事件
        WizardDialog dialog = (WizardDialog) getContainer();
        dialog.addPageChangingListener(new IPageChangingListener() {  
            public void handlePageChanging(PageChangingEvent event) {
            	if(event.getCurrentPage() instanceof Config3MvmWizardPage) {

            	}
            }  
        });  
        
        setControl(container);
		try {
			doValidateSaveProject();
        } catch (Exception e1) {
            updateStatus("校验失败:" + e1.toString());
        }
	}
	
	void bindFocusIn(Canvas canvasProject) {
		Control[] controls = canvasProject.getChildren();
		for(Control ctrl : controls) {
			if(ctrl instanceof Text) {
				ctrl.addListener(SWT.Modify, this);
			}
		}
	}
	
	IConfigProject createInstance(String configProjectClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Object obj =this.getClass().getClassLoader().loadClass(configProjectClass).newInstance();
		return (IConfigProject)obj;
	}
	
	@SuppressWarnings("unchecked")
	List<Element> getMvms() {
		return gcRule.getMainRule().selectNodes("/rules/codegen/mvms/mvm");
	}
	
	String[] getMvmContextNames() {
		List<Element> lMvm = getMvms();
		List<String> result = new ArrayList<String>();
		for(Element mvm : lMvm) {
			result.add(mvm.valueOf("contextName"));
		}
		return result.toArray(new String[0]);
	}
	
	Element getMvm(String contextName) {
		return (Element)gcRule.getMainRule().selectSingleNode("/rules/codegen/mvms/mvm[contextName='" + contextName + "']");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getProjectMap() {
		Map<String, String> result = new HashMap<String, String>();
		List<Node> lProperty = gcRule.getMainRule().selectNodes("/rules/project/node()");
		for(Node property : lProperty) {
			if(property instanceof Element) {
				result.put(property.getName(), property.getText());
			}
		}
		result.put("baseProjectPath", gcRule.getMainRule().valueOf("/rules/codegen/@baseProjectPath"));
		return result;
	}
	
    //生成一行分隔线
    public static void createLine(Composite container, int ncol) {
        Label line = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        line.setLayoutData(gridData);
    }

	public void handleEvent(Event event) {
		if(event.type == SWT.Modify) {
    		try {
    			doValidateSaveProject();
            } catch (Exception e1) {
                updateStatus("校验失败:" + e1.toString());
                e1.printStackTrace();
                event.doit = false;
            }
		}
	}
	
	private void doValidateSaveProject() throws Exception {
		Element mvms = (Element)gcRule.getMainRule().selectSingleNode("/rules/codegen/mvms");
		mvms.addAttribute("contextName", comboMvm.getText());
		IConfigProject cp = (IConfigProject)comboMvm.getData();
		String errorMsg = cp.validate();
		if(errorMsg != null) {
			throw new RuntimeException(errorMsg);
		}
		Map<String, String> projectMap = cp.extractProjectMap();
		Element project = (Element)gcRule.getMainRule().selectSingleNode("/rules/project");
		for(Map.Entry<String, String> en : projectMap.entrySet()) {
			Node node = project.selectSingleNode(en.getKey());
			if(node == null) {
				node = project.addElement(en.getKey());
			}
			node.setText(en.getValue());
		}
    	gcRule.save();
    	updateStatus(null);
	}
	
	public void updateStatus(String message) {
		setErrorMessage(message);
		if(message == null) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
	}
}

package org.quickbundle.mda.gp;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogPage;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class CustomProjectWizardPage extends WizardPage {

	private GenerateProjectRule gpRule;
	
	private List<Text> texts = new ArrayList<Text>();

	public CustomProjectWizardPage(String pageName, GenerateProjectWizard generateProjectWizard) {
		super(pageName);
		setTitle("生成项目 2/2");
		setDescription("请定制生成项目的选项");
		gpRule = generateProjectWizard.getGpRule();
	}

	private Text projectPath;

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@SuppressWarnings("unchecked")
	public void createControl(Composite parent) {
		final int columns = 3; // 定义列数
		Composite container = null;
		if (parent.getChildren() != null && parent.getChildren().length > 1 && parent.getChildren()[1] instanceof ScrolledComposite) {
			ScrolledComposite scroll = (ScrolledComposite) parent.getChildren()[1];
			container = new Composite(scroll, SWT.NULL);
			scroll.setContent(container);
		} else {
			container = new Composite(parent, SWT.NULL);
		}

		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(new GridLayout(columns, false));
		GridData gd = null;

		Label label = new Label(container, SWT.NULL);
		label.setText("生成到");

		projectPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		final Text projectPathFinal = projectPath;
		gd = new GridData(GridData.FILL_HORIZONTAL);
		projectPath.setLayoutData(gd);
		projectPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				gpRule.setProjectPathValue(projectPathFinal.getText());
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				projectPath.setText(dialog.open());
			}
		});

		createKeywordReplace(container);
		
		// 开始循环模块
		List<Element> lModule = gpRule.getProjectRule().selectNodes("/rules/modules/module");
		for (Element thisModule : lModule) {
			String description = thisModule.valueOf("@description");
			// 是否构建
			Button isBuild = new Button(container, SWT.CHECK);
			gd = new GridData();
			gd.horizontalAlignment = GridData.FILL_HORIZONTAL;
			gd.grabExcessHorizontalSpace = true;
			isBuild.setLayoutData(gd);
			isBuild.setData("key", thisModule.valueOf("@key"));
			if ("true".equals(thisModule.valueOf("@isBuild"))) {
				isBuild.setSelection(true);
			} else {
				isBuild.setSelection(false);
			}
			if ("true".equals(thisModule.valueOf("@isNecessary"))) {
				isBuild.setEnabled(false);
			} else {
				isBuild.setEnabled(true);
			}
			isBuild.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					Button tempCheckbox = (Button) e.getSource();
					String tempKey = String.valueOf(tempCheckbox.getData("key"));
					Node thisNode = gpRule.getProjectRule().selectSingleNode("/rules/modules/module[@key='" + tempKey + "']/@isBuild");
					thisNode.setText(tempCheckbox.getSelection() ? "true" : "false");
				}

				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
			isBuild.setText(thisModule.valueOf("@name"));
			isBuild.setToolTipText(description);

			// 列名
			Text columnName = new Text(container, SWT.BORDER | SWT.READ_ONLY);
			columnName.setText(description);
			gd = new GridData();
			gd.widthHint = 400;
			gd.horizontalAlignment = GridData.BEGINNING;
			gd.grabExcessHorizontalSpace = true;
			columnName.setLayoutData(gd);
			columnName.setToolTipText(description);
			new Label(container, SWT.NULL).setText("");
		}

		init();
		dialogChanged();
		setControl(container);
	}
	
	@SuppressWarnings("unchecked")
	private void createKeywordReplace(Composite container) {
		List<Element> lKeywordReplace = gpRule.getProjectRule().selectNodes("/rules/keywordReplace/items/item");
		for(Element kp : lKeywordReplace) {
			String labelName = kp.valueOf("@title");
			if("true".equals(kp.valueOf("@required"))) {
				labelName = "* " + labelName;
			}
			new Label(container, SWT.NULL).setText(labelName);
			
			final Text textValue =  new Text(container, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			textValue.setLayoutData(gd);
			textValue.setData(kp);
			textValue.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					Element kp = (Element)textValue.getData();
					kp.setText(textValue.getText());
					dialogChanged();
				}
			});
			textValue.setText(kp.valueOf("@defaultValue"));
			texts.add(textValue);
			new Label(container, SWT.NULL).setText("");
		}
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void init() {
		projectPath.setText(getWorkspacePath());
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		String projectPathValue = projectPath.getText();

		if (projectPathValue.trim().length() == 0) {
			updateStatus("生成路径必须指定");
			return;
		}
		
		for(Text text : texts) {
			Element kp = (Element)text.getData();
			if("true".equals(kp.valueOf("@required")) && text.getText().trim().length() == 0) {
				updateStatus(kp.valueOf("@title") + "必须指定");
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * 功能: 获得workspace路径
	 * 
	 * @return
	 */
	public String getWorkspacePath() {
		String returnStr = RmXmlHelper.formatToUrlNoPrefix(String.valueOf(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile()));
		return returnStr;
	}
}

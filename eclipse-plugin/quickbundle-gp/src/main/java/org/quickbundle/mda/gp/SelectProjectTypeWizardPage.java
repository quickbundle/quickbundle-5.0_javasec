package org.quickbundle.mda.gp;

import java.io.File;
import java.util.List;

import org.dom4j.Element;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.quickbundle.tools.helper.io.RmFileHelper;


/**
 * The "New" wizard page allows setting the container for
 * the new file as well as the file name. The page
 * will only accept file name without the extension OR
 * with the extension that matches the expected one (mpe).
 */

public class SelectProjectTypeWizardPage extends WizardPage {

	private GenerateProjectRule gpRule;
	/**
	 * Constructor for SelectProjectTypeWizardPage.
	 * @param pageName
	 */
	public SelectProjectTypeWizardPage(String pageName, GenerateProjectWizard generateProjectWizard) {
		super(pageName);
		setTitle("生成项目 1/2");
		setDescription("请先选择项目骨架的类型");
		gpRule = generateProjectWizard.getGpRule();
	}
	@SuppressWarnings("unchecked")
	public void createControl(Composite parent) {
		int columns = 5; //定义列数
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout(new RowLayout(SWT.VERTICAL));
        //强制显示滚动条  
		scrolledComposite.setAlwaysShowScrollBars(true);  
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setMinHeight(600);  
		scrolledComposite.setLayout(new GridLayout(1, false));
    	
		Composite container = new Composite(scrolledComposite, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		layout.verticalSpacing = 9;
		container.setLayout(layout);
		scrolledComposite.setContent(container);
		
		final List<Element> lArchetype = gpRule.getMainRule().selectNodes("/archetypes/archetype");
		int index = 0;
		for(final Element archetype : lArchetype) {
			Button buttonSelect = new Button(container, SWT.RADIO);
			buttonSelect.setText(archetype.valueOf("title"));
			buttonSelect.setData(archetype);
			buttonSelect.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					radioToDocument(lArchetype, archetype);
				}
				public void widgetDefaultSelected(SelectionEvent e) {
					radioToDocument(lArchetype, archetype);
				}
			});
			if(index == 0) {
				buttonSelect.setSelection(true);
				radioToDocument(lArchetype, archetype);
			}
			
			Label labelFrame1 = new Label(container, SWT.RIGHT);
			labelFrame1.setText("框架：");
			
			Label labelFrame2 = new Label(container, SWT.LEFT);
			labelFrame2.setText(archetype.valueOf("framework"));
			
			Label labelAuthor1 = new Label(container, SWT.RIGHT);
			labelAuthor1.setText("作者：");
			
			Label labelAuthor2 = new Label(container, SWT.LEFT);
			labelAuthor2.setText(archetype.valueOf("author"));
			
			Text textDescription = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
			GridData gd = new GridData(GridData.VERTICAL_ALIGN_FILL);
	        gd.horizontalSpan = columns;
	        gd.verticalSpan = 6;
	        gd.widthHint = 800;
	        textDescription.setLayoutData(gd);
	        textDescription.setText(archetype.valueOf("description"));
			
			if(++index < lArchetype.size()) {
				createLine(container, columns);
			}
		}
		setControl(container);
	}
	
	private void radioToDocument(List<Element> lArchetype, Element archetype) {
		for(Element tempTrchetype : lArchetype) {
			if(archetype == tempTrchetype) {
				tempTrchetype.addAttribute("selected", "true");
			} else {
				tempTrchetype.addAttribute("selected", "false");
			}
		}
		try {
			gpRule.loadProjectRule();
			if(!new File(gpRule.getProjectTemplatePath()).exists()) {
				throw new RuntimeException(RmFileHelper.formatToFile(gpRule.getProjectTemplatePath()) + " not exists!");
			}
			setErrorMessage(null);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage(e.toString());
		}
		getContainer().updateButtons();
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return super.canFlipToNextPage() 
				&& gpRule.getMainRule().selectNodes("//archetype[@selected='true']").size() > 0
				&& gpRule.getProjectRule() != null
				&& getErrorMessage() == null;
	}

    //生成一行分隔线
    public static void createLine(Composite parent, int ncol) {
        Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        line.setLayoutData(gridData);
    }
    
}
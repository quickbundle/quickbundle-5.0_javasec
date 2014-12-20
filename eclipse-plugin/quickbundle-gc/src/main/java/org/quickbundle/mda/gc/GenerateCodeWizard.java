package org.quickbundle.mda.gc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import org.dom4j.DocumentException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.quickbundle.mda.mvm.CodegenEngine;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "mpe". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */

public class GenerateCodeWizard extends Wizard implements INewWizard {

	IWizardPage page1, page2, page3;
	
    private ISelection selection;
    
    private GenerateCodeRule gcRule = null;

    public GenerateCodeRule getGcRule() {
		return gcRule;
	}

	/**
     * Constructor for GenerateCodeWizard.
     * 
     * @throws DocumentException
     * @throws MalformedURLException
     */
    public GenerateCodeWizard() {
        super();
        setNeedsProgressMonitor(true);
        init();
    }
    
    private void init() {
        try {
        	//RanminXmlGenerateCodePlugin的静态变量必须由Wizard产生
            QbXmlGenerateCodePlugin.baseConfigPath = RmXmlHelper.formatToUrl(QbXmlGenerateCodePlugin.getInstallLocation().toOSString()) + "config/";
            gcRule = new GenerateCodeRule();
        } catch (Exception e) {
            e.printStackTrace();
            
        }        
    }

    /**
     * Adding the page to the wizard.
     */

    public void addPages() {
    	page1 = new Config1MainRuleWizardPage(selection, this);
        this.addPage(page1);
        page2 = new Config2TableRelationWizardPage(this);
        this.addPage(page2);
        page3 = new Config3MvmWizardPage(this);
        this.addPage(page3);
    }
    
    @Override
    public boolean canFinish() {
    	IWizardPage currentPage = getContainer().getCurrentPage();
    	if(currentPage instanceof Config3MvmWizardPage
    			&& currentPage.isPageComplete()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish() {
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                    doFinish(monitor);
                } catch (CoreException e) {
                    e.printStackTrace();
                    throw new InvocationTargetException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            getContainer().run(true, false, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            Throwable realException = e.getTargetException();
            e.printStackTrace();
            realException.printStackTrace();
            MessageDialog.openError(getShell(), "Error", realException.getMessage());
            return false;
        }
        return true;
    }

    /**
     * The worker method. It will find the container, create the file if missing
     * or just replace its contents, and open the editor on the newly created
     * file.
     */

    private void doFinish(IProgressMonitor monitor) throws CoreException {
        //create a sample file
        monitor.beginTask("正在生成代码,请稍候......", 2);
        try {
        	//保存规则文件
        	gcRule.save();
        	
            //生成代码
            CodegenEngine codegenEngine = new CodegenEngine(gcRule.getMainRulePath());
            Object[] aObj = codegenEngine.generateFiles(monitor);;
            if(aObj != null && aObj.length == 2) {
                QbXmlGenerateCodePlugin.log(String.valueOf(aObj[1]));
                QbXmlGenerateCodePlugin.log("本次UI操作跨数据库标准版，一共生成了" + String.valueOf(aObj[0]) + "个文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
            QbXmlGenerateCodePlugin.log(e.toString());
        }
    }

    /**
     * We will initialize file contents with a sample text.
     */
    InputStream openContentStream() {
        String contents = "This is the initial file contents for *.mpe file that should be word-sorted in the Preview page of the multi-page editor";
        return new ByteArrayInputStream(contents.getBytes());
    }

    void throwCoreException(String message) throws CoreException {
        IStatus status = new Status(IStatus.ERROR, "ranminXmlGenerateCode", IStatus.OK, message, null);
        throw new CoreException(status);
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize
     * from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
    }
    
    @Override
    public void createPageControls(Composite pageContainer) {
    	
    }

}
package org.quickbundle.mda.gp;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the provided container. If the container
 * resource (a folder or a project) is selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "mpe". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will be able to open it.
 */

public class GenerateProjectWizard extends Wizard implements INewWizard {
    
    private SelectProjectTypeWizardPage page1;
    private CustomProjectWizardPage page2;

    private GenerateProjectRule gpRule = null;
	
    /**
     * Constructor for GenerateProjectWizard.
     */
    public GenerateProjectWizard() {
        super();
        setNeedsProgressMonitor(true);
        gpRule = new GenerateProjectRule();
    }

    /**
     * Adding the page to the wizard.
     */
    public void addPages() {
        page1 = new SelectProjectTypeWizardPage("page1", this);
        addPage(page1);
        page2 = new CustomProjectWizardPage("page2", this);
        addPage(page2);
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We will create an operation and run it using
     * wizard as execution context.
     */
    public boolean performFinish() {
    	final CopyProjectEngine copyProjectEngine = new CopyProjectEngine(gpRule);
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                	copyProjectEngine.doFinish(monitor);
                } catch (CoreException e) {
                	e.printStackTrace();
                    throw new InvocationTargetException(e);
                } catch (Exception e) {
                	QbGenerateProjectPlugin.log(e.toString());
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
            MessageDialog.openError(getShell(), "Error", realException.getMessage());
            return false;
        }
        copyProjectEngine.openProject();
        return true;
    }
    
    @Override
    public boolean canFinish() {
    	IWizardPage currentPage = getContainer().getCurrentPage();
    	if(currentPage instanceof CustomProjectWizardPage) {
    		return currentPage.getErrorMessage() == null;
    	} else {
    		return false;
    	}
    }
    
    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        //this.selection = selection;
        return;
    }

	public GenerateProjectRule getGpRule() {
		return gpRule;
	}
	
	@Override
	public void createPageControls(Composite pageContainer) {

	}
    
}
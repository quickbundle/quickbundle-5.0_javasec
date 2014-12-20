package org.quickbundle.mda.gc.project;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class JavaEEStandardProject implements IConfigProject {

	Text javaPackageName = null;
	Text jspSourcePath = null;
	Text webAppName = null;
	Text authorName = null;
	
	public void draw(Canvas canvasProject, Map<String, String> projectMap) {
		createProjectArea(canvasProject);
		init(projectMap);
	}

	private void createProjectArea(final Composite canvasProject) {
		GridData gd = null;
		canvasProject.setLayout(new GridLayout(3, false));
		
		new Label(canvasProject, SWT.NULL).setText("java包名");
		javaPackageName = new Text(canvasProject, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		javaPackageName.setLayoutData(gd);
		Button button_javaPackageName = new Button(canvasProject, SWT.PUSH);
		button_javaPackageName.setText("浏览...");
		button_javaPackageName.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(canvasProject, "javaPackageName");
			}
		});

		new Label(canvasProject, SWT.NULL).setText("jsp目录");
		jspSourcePath = new Text(canvasProject, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		jspSourcePath.setLayoutData(gd);
		Button button_jspSourcePath = new Button(canvasProject, SWT.PUSH);
		button_jspSourcePath.setText("浏览...");
		button_jspSourcePath.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(canvasProject, "jspSourcePath");
			}
		});
		
		new Label(canvasProject, SWT.NONE).setText("web应用名");
		webAppName = new Text(canvasProject, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.FILL;
		webAppName.setLayoutData(gd);
		Button button_webAppName = new Button(canvasProject, SWT.PUSH);
		button_webAppName.setText("浏览...");
		button_webAppName.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(canvasProject, "webAppName");
			}
		});
		
		new Label(canvasProject, SWT.NONE).setText("注释作者");
		authorName = new Text(canvasProject, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.FILL;
		authorName.setLayoutData(gd);
		new Label(canvasProject, SWT.NONE).setText("");
	}
	
	private void init(Map<String, String> projectMap) {
		javaPackageName.setText(projectMap.get("javaPackageName"));
		jspSourcePath.setText(projectMap.get("jspSourcePath"));
		webAppName.setText(projectMap.get("webAppName"));
		authorName.setText(projectMap.get("authorName"));
		
		//如果webAppName不存在，自动调整
		if(projectMap.containsKey("baseProjectPath") 
				&& projectMap.get("baseProjectPath").trim().length() > 0
				&& (webAppName.getText().length() == 0 
					|| !new File(projectMap.get("baseProjectPath").trim() + File.separator + webAppName.getText()).exists())
				){
			File fProject = new File(projectMap.get("baseProjectPath").trim());
			if(fProject.exists() && fProject.isDirectory()) {
				File[] fProjectChild = fProject.listFiles(); 
				for (int i = 0; i < fProjectChild.length; i++) {
					//判断是目录并存在WEB-INF/web.xml
					if(fProjectChild[i].isDirectory() &&
							new File(fProjectChild[i].toString() + File.separator + "WEB-INF" + File.separator + "web.xml").exists()) {
						webAppName.setText(fProjectChild[i].getName());
						break;
					}
				}
			}
		}
	}
	
	public Map<String, String> extractProjectMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("javaPackageName", javaPackageName.getText());
		result.put("jspSourcePath", jspSourcePath.getText());
		result.put("webAppName", webAppName.getText());
		result.put("authorName", authorName.getText());
		//自动分析出javaPackageDir
		result.put("javaPackageDir", javaPackageName.getText().replaceAll("\\.", "/"));
		return result;
	}
	

    /**
     * 处理按钮事件，从对话框中获得值
     */
    private void handleBrowse(Composite canvasProject, String textName) {
        ContainerSelectionDialog dialog = new ContainerSelectionDialog(canvasProject.getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "请选择目标文件夹");
        if (dialog.open() == ContainerSelectionDialog.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                Path resultPath = (Path) result[0];
                String selectedFolder = resultPath.toString();
                if ("javaPackageName".equals(textName)) { //java包名
                	//得到resultPath为/projectName/src/main/java/org/quickbundle/test/rm
                	if(selectedFolder.matches("[\\w\\./]+/java/.*")) {
                		selectedFolder = selectedFolder.replaceFirst("[\\w\\./]+/java/", "");
                	} else {
                		selectedFolder = selectedFolder.replaceFirst("/\\w+/\\w+/", "");
                	}
                    selectedFolder = selectedFolder.replace('/', '.');
                    javaPackageName.setText(selectedFolder);
                } else if ("jspSourcePath".equals(textName)) { //jsp基本目录名
                	String guessWebAppName = extractSecondWord(selectedFolder);
                	if(guessWebAppName != null && guessWebAppName.length() > 0) {
                		webAppName.setText(guessWebAppName);
                	}
                	if(selectedFolder.indexOf("WEB-INF/jsp") > -1) {
                		selectedFolder = selectedFolder.substring(selectedFolder.indexOf("WEB-INF/jsp")+"WEB-INF/jsp".length());
                	} else {
                		selectedFolder = selectedFolder.replaceFirst("/\\w+/\\w+/", "");
                	}
                    jspSourcePath.setText(selectedFolder);
                } else if ("webAppName".equals(textName)) { //web应用名
                	if(selectedFolder.lastIndexOf("/") > -1) {
                		selectedFolder = selectedFolder.substring(selectedFolder.lastIndexOf("/") + 1);
                	}
                	webAppName.setText(selectedFolder);
                }
            }
        }
    }
    
    String extractSecondWord(String selectedFolder) {
    	Pattern p = Pattern.compile("/\\w+/(\\w+)/.*");
    	Matcher matcher = p.matcher(selectedFolder);
    	if(matcher.find()) {
    		return matcher.group(1);
    	}
    	return "";
    }

	public String validate() {
		String result = null;
		if (javaPackageName.getText().trim().length() == 0) {
			result = "请指定java包名";
		} else if (jspSourcePath.getText().trim().length() == 0) {
			result = "请指定jsp目录";
		} else if (webAppName.getText().trim().length() == 0) {
			result = "请指定web应用名";
		}
		return result;
	}
}

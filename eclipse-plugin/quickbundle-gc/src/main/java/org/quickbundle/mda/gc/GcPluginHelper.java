package org.quickbundle.mda.gc;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class GcPluginHelper {

    /**
     * 功能: 获得workspace路径
     * 
     * @return
     */
    public static String getWorkspacePath() {
        String returnStr = RmXmlHelper.formatToUrl(String.valueOf(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile()));
        if (returnStr.startsWith("file:///")) {
            returnStr = returnStr.substring("file:///".length());
        }
        return returnStr;
    }
    
    /**
     * 功能: 获得project路径
     * 
     * @return
     */
    public static String getProjectRealPath(String projectPath) {
        if(projectPath.startsWith("/") || projectPath.startsWith("\\")) {
            projectPath = projectPath.substring(1);            
        }
        projectPath = "/" + projectPath;
        String returnStr = "";
        IProject[] aPrj = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for(int i=0; i<aPrj.length; i++) {
            if(aPrj[i].getFullPath().toString().equals(projectPath)) {
                returnStr = aPrj[i].getLocation().toString();
                break;
            }
        }
        return returnStr;
    }

    /**
     * 功能: 获得java的包名
     * 
     * @param packagePath
     * @return
     */
    public static String getPackageName(String packagePath) {
        if (packagePath == null || packagePath.length() == 1) {
            return "";
        }
        packagePath = packagePath.replace('\\', '/');
        String[] dir = packagePath.split("/");
        if (dir.length >= 4) {
            String tempStr = "";
            for (int i = 3; i < dir.length; i++) {
                if (i == 3) {
                    tempStr += dir[i];
                } else {
                    tempStr += "." + dir[i];
                }
            }
            packagePath = tempStr;

        } else {
            packagePath = packagePath.substring(1);
            packagePath.replaceAll("/", ".");
        }
        return packagePath;
    }
}

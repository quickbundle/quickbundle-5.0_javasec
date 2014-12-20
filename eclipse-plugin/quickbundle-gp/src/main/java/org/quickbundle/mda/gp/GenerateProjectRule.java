package org.quickbundle.mda.gp;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class GenerateProjectRule {
	
    //定义source在插件下的的绝对路径
    private String templatePath = null;
    private Document mainRule = null;
    private Document projectRule = null;
    private Set<String> sNeedMkdirsFolder = new HashSet<String>();
    private String projectPathValue = null;
    
    public GenerateProjectRule() {
    	init();
	}
    
    @SuppressWarnings("unchecked")
	private void init() {
        try { //Plugin的静态变量必须由Wizard产生
        	templatePath = RmXmlHelper.formatToUrl(QbGenerateProjectPlugin.getInstallLocation().toOSString()) + "t";
        	mainRule = RmXmlHelper.parse(templatePath + "/generateProject.xml");
        	List<Element> lFolder = mainRule.selectNodes("/rules/modules/module[@isBuild='true']/folder");
            for(Element folder : lFolder) {
            	sNeedMkdirsFolder.add(folder.getText());
            }
        } catch (Exception e) {
        	QbGenerateProjectPlugin.log(e.toString());
            e.printStackTrace();
        }
    }
    
    public void loadProjectRule() throws MalformedURLException, DocumentException {
    	projectRule = null;
    	String currentSource = templatePath + "/" + mainRule.valueOf("//archetype[@selected='true']/config");
    	projectRule = RmXmlHelper.parse(currentSource);
    }
    public String getProjectTemplatePath() {
    	String currentSource = mainRule.valueOf("//archetype[@selected='true']/source");
    	if(currentSource.indexOf("{templatePath}") > -1) {
    		currentSource = currentSource.replaceFirst("\\{templatePath\\}", templatePath);
    	}
    	return RmFileHelper.formatToFile(currentSource);
    }
    
    
	public Document getMainRule() {
		return mainRule;
	}
	
	public Document getProjectRule() {
		return projectRule;
	}

	public void setProjectRule(Document projectRule) {
		this.projectRule = projectRule;
	}

	public Set<String> getSNeedMkdirsFolder() {
		return sNeedMkdirsFolder;
	}

	public String getProjectPathValue() {
		return projectPathValue;
	}

	public void setProjectPathValue(String projectPathValue) {
		this.projectPathValue = projectPathValue;
	}
	
}

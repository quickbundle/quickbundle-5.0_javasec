package org.quickbundle.third.middleware;

import java.io.File;

import org.quickbundle.tools.support.path.RmPathHelper;

public class BuildTomcatContextXml {
	public static void main(String[] args) {
		try {
			String tomcatContextFileStr = RmPathHelper.getProjectDir() + "/target/" + RmPathHelper.getWarName() + ".xml";
			File tomcatContextFile = RmPathHelper.createWarXml(tomcatContextFileStr);
			String focusLine = "#################################################################";
			System.out.println(focusLine);
			System.out.println("Build tomcat context xml (" + tomcatContextFile.getName() + ") in following folder:" );
			System.out.println("in eclipse: " + RmPathHelper.getProjectName() + "/target");
			System.out.println(tomcatContextFile.getParentFile().getAbsolutePath());
			System.out.println("please move it into $(catalina.home)/Catalina/localhost");
			System.out.println(focusLine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

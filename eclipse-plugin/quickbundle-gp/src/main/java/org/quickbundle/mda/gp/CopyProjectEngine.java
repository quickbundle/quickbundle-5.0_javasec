package org.quickbundle.mda.gp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class CopyProjectEngine {
	
	GenerateProjectRule gpRule = null;
	Map<String, String> mReplaceInFile = null;
	Map<String, String> mReplacePath = null;
	
	public CopyProjectEngine(GenerateProjectRule gpRule) {
		this.gpRule = gpRule;
		mReplaceInFile = getKeywordReplaceInFile();
		mReplacePath = getKeywordReplacePath();
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */
	public void doFinish(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("preparing...", 1);
		String strSource = gpRule.getProjectTemplatePath().toString();
		Set<String> sFileType = getFilterFileType();
		QbGenerateProjectPlugin.log("strSource:" + RmXmlHelper.formatToFile(strSource));
		globalTotalFileSum = getFileSum(RmXmlHelper.formatToFile(strSource));
		QbGenerateProjectPlugin.log("start copy files: globalTotalFileSum=" + globalTotalFileSum);
		monitor.beginTask("generation begin...", globalTotalFileSum);
		long startTime = System.currentTimeMillis();
		copyFolder(strSource, gpRule.getProjectPathValue(), sFileType, monitor);
		QbGenerateProjectPlugin.log("cost " + (System.currentTimeMillis() - startTime) + " Millisecond!");
		monitor.setTaskName("All files generated!");
		monitor.done();
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getKeywordReplaceInFile() {
		Map<String, String> result = new HashMap<String, String>();
		List<Element> items = gpRule.getProjectRule().selectNodes("/rules/keywordReplace/items/item");
		for(Element item : items) {
			result.put(item.valueOf("@keyword"), item.valueOf("text()"));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getKeywordReplacePath() {
		Map<String, String> result = new HashMap<String, String>();
		List<Element> items = gpRule.getProjectRule().selectNodes("/rules/keywordReplace/items/item[@replacePath='true']");
		for(Element item : items) {
			result.put(item.valueOf("@keyword"), item.valueOf("text()"));
		}
		return result;
	}

	/**
	 * 功能: 获得需要过滤关键字的文件类型后缀Set
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<String> getFilterFileType() {
		Set<String> sFileType = new HashSet<String>();
		List<Element> lFileAffix = gpRule.getProjectRule().selectNodes("/rules/keywordReplace/fileFilter/fileAffix");
		for (Element fileAffix : lFileAffix) {
			String tempStr = fileAffix.valueOf("@name");
			sFileType.add(tempStr);
			if ("*".equals(tempStr)) { // 如果加上*，则暴慢无比
				sFileType = new HashSet<String>();
				break;
			}
		}
		return sFileType;
	}
	

	private int globalTotalFileSum = 0;

	/**
	 * 功能: 得到文件个数
	 * 
	 * @param file
	 * @return
	 */
	public static int getFileSum(String file) {
		int sum = 0;
		File thisFile = new File(file);
		if (!thisFile.exists()) {
			return 0;
		} else {
			if (thisFile.isFile()) {
				return 1;
			} else {
				File[] childFile = thisFile.listFiles();
				for (int i = 0; i < childFile.length; i++) {
					sum += getFileSum(childFile[i].toString());
				}
				return sum;
			}
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param monitor
	 * @param oldPath
	 * @param newPath
	 * @param mToBeReplace
	 * @param sFileType
	 */
	public void copyFolder(String oldPath, String newPath, Set<String> sFileType, IProgressMonitor monitor) {
		oldPath = RmXmlHelper.formatToFile(oldPath);
		newPath = RmXmlHelper.formatToFile(newPath);
		File fOldPath = new File(oldPath);
		try {
			boolean needMkdirs = false;
			String shortOldPath = getFileEndPart(new File(oldPath));
			for (String p : gpRule.getSNeedMkdirsFolder()) {
				if (shortOldPath.matches(p)) {
					needMkdirs = true;
					break;
				}
			}

			{ // 把目标路径中的所有关键字都替换成用户指定的，项目路径和war名称
				for (String strFrom : mReplacePath.keySet()) {
					String strTo = mReplacePath.get(strFrom);
					newPath = RmStringHelper.replaceAll(newPath, strFrom, strTo);
				}
			}
			{ // 初始化本目录
				if (needMkdirs) {
					new File(newPath).mkdirs();
				}
			}
			File sourceFile = null;
			String[] fileList = fOldPath.list();
			for (int i = 0; i < fileList.length; i++) {
				String thisOldFile = fileList[i];
				String thisReplacedFile = thisOldFile;
				// 把要替换到的目标文件的文件名过滤一遍
				for (String strFrom : mReplacePath.keySet()) {
					String strTo = mReplacePath.get(strFrom);
					thisReplacedFile = RmStringHelper.replaceAll(thisOldFile, strFrom, strTo);
				}
				if (oldPath.endsWith(File.separator)) {
					sourceFile = new File(oldPath + thisOldFile);
				} else {
					sourceFile = new File(oldPath + File.separator + thisOldFile);
				}
				String targetFilePath = newPath + File.separator + thisReplacedFile;

				if (sourceFile.isFile()) {
					copySingleFile(sourceFile, targetFilePath, sFileType, monitor, thisReplacedFile);
				} else if (sourceFile.isDirectory()) {// 如果是子文件夹
					copyFolder(sourceFile.getCanonicalPath(), targetFilePath, sFileType, monitor);
				} else {
					QbGenerateProjectPlugin.log(sourceFile + "既不是文件也不是目录，忽略！");
				}
			}
		} catch (Exception e) {
			QbGenerateProjectPlugin.log("复制整个文件夹内容操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param sourceFile
	 * @param displayFilePath
	 * @param mToBeReplace
	 * @param sFileType
	 * @param monitor
	 * @param thisReplacedFile
	 */
	@SuppressWarnings("unchecked")
	private void copySingleFile(File sourceFile, String targetFile, Set<String> sFileType, IProgressMonitor monitor,
			String thisReplacedFile) {
		FileInputStream input = null;
		FileOutputStream output = null;
		{ // 显示生成过程
			String displayFilePath = RmXmlHelper.formatToFile(targetFile);
			if (displayFilePath.length() > 140) {
				displayFilePath = displayFilePath.substring(0, 12) + "..." + displayFilePath.substring(displayFilePath.length() - 125);
			}
			monitor.worked(1);
			monitor.setTaskName("copy:" + displayFilePath);
		}
		int fileNeedCopyType = fileNeedCopy(sourceFile);
		if (fileNeedCopyType > 0) {
			try {
				new File(targetFile).getParentFile().mkdirs(); // 如果文件夹不存在
																// 则建立新文件夹
				// 得到文件后缀
				int dotPos = thisReplacedFile.lastIndexOf(".");
				if (dotPos < 0) {
					dotPos = -1;
				}
				String fileType = thisReplacedFile.substring(dotPos + 1);
				if (sFileType.contains(fileType) || fileNeedCopyType == 1) {
					Map<String, String[]> mToBeReplaceKey = new HashMap<String, String[]>();
					String thisFileEncode = RmBaseConfig.getSingleton().getDefaultEncode();
					if (fileNeedCopyType == 1) {
						List<Node> lNotNeedModuleFileKey = gpRule.getProjectRule().selectNodes("/rules/modules/module[@isBuild='false']/file[count(key)>0]");
						for (Node nodeFile : lNotNeedModuleFileKey) {
							if (getFileEndPart(sourceFile).matches(nodeFile.getText().trim())) {
								{ // 读取特别指定的编码
									String tempEncode = nodeFile.valueOf("@encode");
									if (tempEncode != null && tempEncode.length() > 0) {
										thisFileEncode = tempEncode;
									}
								}
								// 读取key
								List<Node> lKey = nodeFile.selectNodes("key");
								for (Node nodeKey : lKey) {
									String[] aStr = new String[2];
									// 如果指定了替换字符串
									if (nodeKey.selectNodes("replace").size() > 0) {
										aStr[0] = nodeKey.valueOf("replace[1]/text()");
									} else {
										aStr[0] = "";
									}
									aStr[1] = nodeKey.valueOf("@p");
									mToBeReplaceKey.put(nodeKey.getText().trim(), aStr);
								}
							}
						}
						// QbGenerateProjectPlugin.log("copy file: " +
						// targetFile + ", filter key=" + mToBeReplaceKey);
					}
					// 读取源文件
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), thisFileEncode));
					String s = null;
					StringBuilder sb2 = new StringBuilder();
					boolean isFirstLine = true;
					while ((s = in.readLine()) != null) {
						if (isFirstLine) {
							isFirstLine = false;
						} else {
							sb2.append("\n");
						}
						sb2.append(s);
					}
					in.close();
					String sFinal = sb2.toString();
					// 替换对话框中指定的字符串
					if (sFileType.contains(fileType)) {
						for (String strFrom : mReplaceInFile.keySet()) {
							String strTo = mReplaceInFile.get(strFrom);
							sFinal = RmStringHelper.replaceAll(sFinal, strFrom, strTo);
						}
					}
					// 替换xml配置中定义的字符串
					if (fileNeedCopyType == 1) {
						for (String strFrom : mToBeReplaceKey.keySet()) {
							String[] strTo = mToBeReplaceKey.get(strFrom);
							if ("true".equals(strTo[1])) { // 如果是正则表达式
								sFinal = Pattern.compile(strFrom, Pattern.DOTALL).matcher(sFinal).replaceAll(strTo[0]);
							} else { // 直接替换
								sFinal = RmStringHelper.replaceAll(sFinal, strFrom, strTo[0]);
							}

						}
					}
					// 写入目标文件
					BufferedReader in4 = new BufferedReader(new StringReader(sFinal));
					BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), thisFileEncode));
					isFirstLine = true;
					while ((s = in4.readLine()) != null) {
						if (isFirstLine) {
							isFirstLine = false;
						} else {
							out1.write("\n");
						}
						out1.write(s);
					}
					out1.flush();
					out1.close();
					in4.close();
				} else { // Stream方式直接复制文件
					input = new FileInputStream(sourceFile);
					output = new FileOutputStream(targetFile);
					byte[] b = new byte[1024 * 10];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
				}
			} catch (Exception e) {
				QbGenerateProjectPlugin.log(e.toString());
				e.printStackTrace();
			} finally {
				try {
					if (input != null) {
						input.close();
					}
					if (output != null) {
						output.close();
					}
				} catch (Exception e1) {
					QbGenerateProjectPlugin.log(e1.toString());
					e1.printStackTrace();
				}
			}
		} else {
			// QbGenerateProjectPlugin.log("skip file: " + targetFile);
		}
	}

	/**
	 * 得到文件名中去掉模板路径的结尾部分字符串
	 * 
	 * @param sourceFile
	 * @return
	 */
	private String getFileEndPart(File sourceFile) {
		String uriSourceFile = null;
		try {
			uriSourceFile = sourceFile.getCanonicalPath();
		} catch (IOException e1) {
			uriSourceFile = sourceFile.getAbsolutePath();
		}
		uriSourceFile = RmXmlHelper.formatToUrl(uriSourceFile);
		uriSourceFile = uriSourceFile.substring(RmFileHelper.formatToUrl(gpRule.getProjectTemplatePath()).length());
		return uriSourceFile;
	}

	/**
	 * 功能: 判断是否需要构建这个文件
	 * 
	 * @param sourceFile
	 * @return 0 不需要, 1 需要但要过滤未选择模块的key值, 2 Stream方式直接拷贝(文件名可能经过webAppName过滤 )
	 */
	@SuppressWarnings("unchecked")
	private int fileNeedCopy(File sourceFile) {
		String uriSourceFile = getFileEndPart(sourceFile);
		RmXmlHelper.formatToUrl(sourceFile.toString());
		List<Node> lNotNeedModuleFile = gpRule.getProjectRule().selectNodes("/rules/modules/module[@isBuild='false']/file[count(key)=0]");
		for (Node nodeFile : lNotNeedModuleFile) {
			if (uriSourceFile.matches(nodeFile.getText().trim())) {
				return 0;
			}
		}
		List<Node> lNotNeedModuleFileKeyString = gpRule.getProjectRule().selectNodes("/rules/modules/module[@isBuild='false']/file[count(key)>0]");
		for (Node nodeFile : lNotNeedModuleFileKeyString) {
			if (uriSourceFile.matches(nodeFile.getText().trim())) {
				return 1;
			}
		}
		return 2;
	}

	public void openProject() {
		File projectFolder = new File(gpRule.getProjectPathValue());
		if (projectFolder.exists() && projectFolder.isDirectory()) {
			if(new File(projectFolder.toString() + File.separator + ".project").exists()) {
				boolean successOpen = doOpenProject(projectFolder.toString() + File.separator + ".project");
				if(successOpen) {
					return;
				}
			}
			File[] folders = listProjectFolder(projectFolder);
			for (File folder : folders) {
				if(new File(folder.toString() + File.separator + ".project").exists()) {
					doOpenProject(folder.toString() + File.separator + ".project");
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private File[] listProjectFolder(File projectFolder) {
		File projectTemplatePath = new File(gpRule.getProjectTemplatePath());
		final Set<String> possibleFolders = new HashSet<String>();
		projectTemplatePath.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(pathname.isDirectory()) {
					possibleFolders.add(pathname.getName());
				}
				return false;
			}
		});
		List<Element> items = gpRule.getProjectRule().selectNodes("/rules/keywordReplace/items/item");
		for(Element item : items) {
			possibleFolders.add(item.valueOf("text()"));
		}
		return projectFolder.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory() && possibleFolders.contains(pathname.getName());
			}
		});
	}

	private boolean doOpenProject(String dotProjectPath) {
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			IProjectDescription description = workspace.loadProjectDescription(new Path(dotProjectPath));
			IProject project = root.getProject(description.getName());
			if (!project.exists()) {
				project.create(description, null);
			} else {
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
			if (!project.isOpen()) {
				project.open(null);
			}
			return true;
		} catch (Exception e) {
			QbGenerateProjectPlugin.log(e.toString());
			e.printStackTrace();
			return false;
		}
	}
}

package org.quickbundle.project.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.quickbundle.config.RmLoadConfig;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

public class CustomSystemProperties {
	
	public static CustomSystemProperties getInstance() {
		Document rmDoc = RmLoadConfig.getRmDoc();
		return new CustomSystemProperties(rmDoc);
	}

	private Document doc;
	private Map<String, String> map;
	
	public CustomSystemProperties(Document doc) {
		this.doc = doc;
		map = new HashMap<String, String>();
		//init custom map
		map.put("MIDDLEWARE_WORK_DIR", System.getProperty("user.dir"));
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		List<Element> nodes = doc.selectNodes("/rm/org.quickbundle.project.init.CustomSystemProperties/*");
		for(Node node : nodes) {
			Element ele = (Element) node;
			String key = ele.getName();
			String value = ele.getText().trim();
			String newValue = parseValue(value);
			try {
				System.setProperty(key, newValue);
			} catch (Throwable e) {
				StringBuilder err = new StringBuilder();
				err.append(key).append("=>").append(value);
				if(!newValue.equals(value)) {
					err.append("(").append(newValue).append(")");
				}
				err.append(", ").append(e.toString());
				RmLogHelper.error(this.getClass(), err.toString());
			}
		}
	}
	
	String parseValue(String oldValue) {
		StringBuffer result = new StringBuffer();
		Pattern pData = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher mData = pData.matcher(oldValue);
		while(mData.find()) {
			//在循环中找出{}的表达式
			String exp = mData.toMatchResult().group(1);
			//处理表达式，添加到结果
			mData.appendReplacement(result, getProperty(exp));
		}
		mData.appendTail(result);
		return result.toString();
	}
	
	String getProperty(String key) {
		String result = map.get(key);
		if(result == null) {
			result = System.getProperty(key);
		}
		if(result != null) {
			result = RmStringHelper.replaceAll(result, "\\", "\\\\");
			result = RmStringHelper.replaceAll(result, "$", "\\$");
		}
		return result;
	}
}

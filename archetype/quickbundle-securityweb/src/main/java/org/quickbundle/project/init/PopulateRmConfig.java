package org.quickbundle.project.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.quickbundle.config.RmConfigVo;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PopulateRmConfig {
	private RmConfigVo config;
	private Document doc;
	
	private Map<String, String> map;
	
	public PopulateRmConfig(RmConfigVo config, Document doc) {
		this.config = config;
		this.doc = doc;
		map = new HashMap<String, String>();
	}
	
	@SuppressWarnings("unchecked")
	public void populate() {
        BeanWrapper bw = new BeanWrapperImpl(config);
		List<Element> nodes = doc.selectNodes("/rm/org.quickbundle.config.RmConfig/*");
		for(Element ele : nodes) {
			String key = ele.getName();
			String value = ele.getText().trim();
			String newValue = parseValue(value);
			try {
				bw.setPropertyValue(key, newValue);
				map.put(key, newValue);
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

package org.quickbundle.third.quartz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobDataMap;
import org.quickbundle.project.serializer.RmObjectMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SchedulerHelper implements ISchedulerConstants {
	/**
	 * 解析json格式的params参数
	 * @param vo 注入params的Vo
	 * @param request 获取json数据的request
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static JobDataMap parseDataMap(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {		
		String params=request.getParameter(REQ_DATA_MAP);
		if(params == null)
			return null;
		return parseJson(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static JobDataMap parseJson(String json) throws JsonParseException, JsonMappingException, IOException {
		JobDataMap dataMap = new JobDataMap();
		//转换json为map
		Map map = RmObjectMapper.getInstance().readValue(json, HashMap.class);
		List<Map<String, String>> paramsJo = (List<Map<String, String>>)map.get("params");
		for(Map keyValue : paramsJo) {
			if(!"".equals(keyValue.get("name"))) {
				dataMap.put(String.valueOf(keyValue.get("name")), String.valueOf(keyValue.get("des")));
				System.out.println(String.valueOf(keyValue.get("name")) + "\t" + String.valueOf(keyValue.get("des")));
			}
		}
		return dataMap;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(parseJson("{\"params\":[{\"name\":\"aa\", \"des\":\"1\"}, {\"name\":\"bb\", \"des\":\"22\"}]}"));
	}
}

package org.quickbundle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickbundle.project.serializer.RmObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

public class RmKeyCountList<E> {
	private RmSequenceMap<E, Long> mkc = new RmSequenceMap<E, Long>();
	public RmKeyCountList() {
		
	}
	
	public void put(E key, long count) {
		mkc.put(key, count);
	}
	
	public long getMaxCount() {
		long max = 0;
		for (Map.Entry<E, Long> en : mkc.entrySet()) {
			Long count = en.getValue();
			if(max < count.longValue()) {
				max = count.longValue();
			}
		}
		return max;
	}
	
	public long getMaxCount4Bar() {
		long max = getMaxCount();
		if(max < 2) {
			max = 2;
		} else {
			max = (int)(max * 1.2);
		}
		return max;
	}
	
	public String getJsonCount() {
		List<Long> result = new ArrayList<Long>();
		for (Map.Entry<E, Long> en : mkc.entrySet()) {
			Long count = en.getValue();
			result.add(count);
		}
		try {
			return RmObjectMapper.getInstance().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getJsonKey() {
		List<E> result = new ArrayList<E>();
		for (Map.Entry<E, Long> en : mkc.entrySet()) {
			E key = en.getKey();
			result.add(key);
		}
		try {
			return RmObjectMapper.getInstance().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getJsonKeyCount() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map.Entry<E, Long> en : mkc.entrySet()) {
			E key = en.getKey();
			long count = en.getValue();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", count);
			map.put("text", key);
			result.add(map);
		}
		try {
			return RmObjectMapper.getInstance().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}

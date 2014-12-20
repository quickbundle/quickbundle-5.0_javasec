package org.quickbundle.project.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RmObjectMapper extends ObjectMapper {
	private static ObjectMapper defaultObjectMapper = new RmObjectMapper();
	static {
		defaultObjectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
	}
	public static ObjectMapper getInstance() {
		return defaultObjectMapper;
	}
}

package org.quickbundle.third.spring.http.converter.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class RmObjectMapperSpringWeb extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	boolean writeNumbersAsStrings = false;

	public boolean isWriteNumbersAsStrings() {
		return writeNumbersAsStrings;
	}

	public void setWriteNumbersAsStrings(boolean writeNumbersAsStrings) {
		this.writeNumbersAsStrings = writeNumbersAsStrings;
		super.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, writeNumbersAsStrings);
	}

	public RmObjectMapperSpringWeb() {
		super();
		//允许单引号
		this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		//字段和值都加引号
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 数字也加引号
		this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
				jg.writeString("");
			}
		});
	}
}

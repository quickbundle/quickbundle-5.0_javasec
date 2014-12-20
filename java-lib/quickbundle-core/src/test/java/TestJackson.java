import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.quickbundle.project.serializer.RmObjectMapper;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


public class TestJackson {
	public static void main(String[] args) {
		List<Message> lvo = new ArrayList<Message>();
		lvo.add(new Message("1","a","a1"));
		lvo.add(new Message("2","b","a1bbb"));
		lvo.add(new Message("3","c","a1ccc"));
			String result = writeBackListToRowTable("a", lvo, new String[]{"age"}, null);
			System.out.println(result);
		
		try {
			System.out.println(RmObjectMapper.getInstance().readValue("{\"a\":{\"1\":1,\"2\":2},\"b\":3}", HashMap.class).get("a").getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static String writeBackListToRowTable(String namespace, List lvo, final String[] ignoreName, ObjectMapper objectMapper) {
    	if(lvo == null || lvo.size() == 0) {
    		return "";
    	}
    	//TODO ignoreName
    	if(objectMapper == null) {
    		objectMapper = RmObjectMapper.getInstance();
    	}
    	JsonEncoding encoding = JsonEncoding.UTF8;
    	objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    	
    	StringBuilder result = new StringBuilder();
    	result.append("jQuery(function(){\n");
    	result.append("writeBackListToRowTable(");
    	result.append("'");
    	result.append(namespace);
    	result.append("'");
    	result.append(", \n");
    	try {
    		// 排除
    		SimpleFilterProvider fileter = new SimpleFilterProvider();
    		fileter.addFilter("executeFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignoreName));
    		objectMapper.setFilters(fileter);

//    		// 仅包含
//    		SimpleFilterProvider fileter2 = new SimpleFilterProvider();
//    		fileter2.addFilter("includeFilter", SimpleBeanPropertyFilter.filterOutAllExcept(new String[] { "id", "quality" }));
//    		objectMapper.setFilters(fileter2);

    		// 设置日期格式化
    		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            SegmentedStringWriter sw = new SegmentedStringWriter(objectMapper.getFactory()._getBufferRecycler());
    		JsonGenerator generator = objectMapper.getFactory().createJsonGenerator(sw);
    		RmBeanSerializerFactory bidBeanFactory = RmBeanSerializerFactory.instance;
    		bidBeanFactory.setFilterId("executeFilter"); // 如果是仅包含这里填写 includeFilter
    		objectMapper.setSerializerFactory(bidBeanFactory);

    		objectMapper.writeValue(generator, lvo);

			result.append( sw.getAndClear());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	result.append("\n);");
    	result.append("\n});");
    	return result.toString();
    }
}

class RmBeanSerializerFactory extends BeanSerializerFactory {

	public final static RmBeanSerializerFactory instance = new RmBeanSerializerFactory(null);

	private Object filterId;

	protected RmBeanSerializerFactory(SerializerFactoryConfig config) {
		super(config);
	}
	
    /**
     * Method called to find filter that is configured to be used with bean
     * serializer being built, if any.
     */
    protected Object findFilterId(SerializationConfig config, BeanDescription beanDesc)
    {
    	return getFilterId();
    }

	public Object getFilterId() {
		return filterId;
	}

	public void setFilterId(Object filterId) {
		this.filterId = filterId;
	}
}

class Message {
	public Message(String id, String name, String age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	String id;
	String name;
	String age;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
}
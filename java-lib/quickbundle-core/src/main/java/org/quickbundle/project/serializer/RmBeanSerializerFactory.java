package org.quickbundle.project.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

public class RmBeanSerializerFactory extends BeanSerializerFactory {

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
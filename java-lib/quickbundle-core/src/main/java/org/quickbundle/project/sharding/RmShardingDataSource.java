package org.quickbundle.project.sharding;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class RmShardingDataSource {
	private static String dataSource_slave_key = "";
	private static String dataSource_slave_url = "";
	private static volatile boolean isInitCreateBean = false;

	public RmShardingDataSource() {
		super();
	}

	public void init(){
        if(!isInitCreateBean) {
            synchronized (RmShardingDataSource.class) {
                if(!isInitCreateBean) {
                	try {
                        if(RmBeanFactory.getBeanFactory() instanceof BeanDefinitionRegistry) {
                        	BeanDefinitionRegistry bdr = (BeanDefinitionRegistry)RmBeanFactory.getBeanFactory();
                        	String[] aChannelKey = dataSource_slave_key.split("[\\s,]+");
                        	String[] aChannelUrl = dataSource_slave_url.split("[\\s,]+");
                        	for (int i = 0; i < aChannelUrl.length; i++) {
                        		AbstractBeanDefinition bd_abstractInnerDataSource = BeanDefinitionReaderUtils.createBeanDefinition("abstractInnerDataSource", null, null);
                            	MutablePropertyValues mpv_abstractInnerDataSource = new MutablePropertyValues();
                            	mpv_abstractInnerDataSource.addPropertyValue("url", aChannelUrl[i]);
                        		bd_abstractInnerDataSource.setPropertyValues(mpv_abstractInnerDataSource);
                        		
                            	AbstractBeanDefinition bd_dataSourceChannel = BeanDefinitionReaderUtils.createBeanDefinition("abstractDataSource", null, null);  
                            	bd_dataSourceChannel.setAbstract(false);  
                            	bd_dataSourceChannel.setLazyInit(true); 
                            	MutablePropertyValues mpv_dataSourceChannel = new MutablePropertyValues();
                            	mpv_dataSourceChannel.addPropertyValue("dataSource", bd_abstractInnerDataSource);
                            	bd_dataSourceChannel.setPropertyValues(mpv_dataSourceChannel);
                            	
                            	bdr.registerBeanDefinition(aChannelKey[i], bd_dataSourceChannel);
                            	RmLogHelper.getLogger(this.getClass()).info("registerBeanDefinition:" + aChannelKey[i] + "-->" + bd_dataSourceChannel);
            				}
                        } else {
                        	RmLogHelper.getLogger(RmShardingDataSource.class).error("RmBeanFactory.getBeanFactory() instanceof BeanDefinition = false");
                        	throw new RmRuntimeException("RmBeanFactory.getBeanFactory() instanceof BeanDefinitionRegistry = false");
                        }
                        isInitCreateBean = true;
            		} catch (Exception e) {
            			e.printStackTrace();
            			if(e instanceof RuntimeException) {
            				throw (RuntimeException)e;
            			}
            		}
                }
            }
        }
	}

	public String getDataSource_slave_url() {
		return dataSource_slave_url;
	}

	public void setDataSource_slave_url(String dataSource_slave_url) {
		RmShardingDataSource.dataSource_slave_url = dataSource_slave_url;
	}
	
	public String getDataSource_slave_key() {
		return dataSource_slave_key;
	}

	public void setDataSource_slave_key(String dataSource_slave_key) {
		RmShardingDataSource.dataSource_slave_key = dataSource_slave_key;
	}
}

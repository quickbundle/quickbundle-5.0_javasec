package org.quickbundle.base.beans.factory;

import java.util.ArrayList;
import java.util.List;

import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.path.RmPathHelper;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class RmBeanFactory {
	private static Logger log = RmLogHelper.getLogger(RmBeanFactory.class);
    /**
     * 全局单例的BeanFactory
     */
    private static BeanFactory beanFactory = null;

    /**
     * 已经开始初始化过程
     */
    private static volatile boolean initBeanStarted = false;
    public static void setInitBeanStarted(boolean initBeanStarted_) {
		initBeanStarted = initBeanStarted_;
	}
    
	/**
	 * 初始化过程完毕
	 */
	private static volatile boolean isInitBean = false;
	
    /**
     * 初始化时的锁，用于将调用getBean的消费者排队等待
     */
    public static Object lockInitFactory = new Object();
    
    /**
     * 自执行初始化BeanFactory
     */
    private static void defaultInitBeanFactory() {
		try {
			long startTime = System.currentTimeMillis();
			log.info("start init bean......");
			log.info(RmPathHelper.initWarRoot());
			String springPath = "/WEB-INF/config/spring/*.xml";
			if (RmHolderServlet.getDefaultServletContext() != null) {
				springPath = RmHolderServlet.getDefaultServletContext().getInitParameter("contextConfigLocation");
			}
			if(springPath.indexOf(",") > -1) {
				List<String> lPath = new ArrayList<String>();
				String[] paths = springPath.split(",");
				for(String path : paths) {
					if(path.trim().length() == 0) {
						continue;
					}
					lPath.add(RmXmlHelper.formatToUrl(RmPathHelper.getWarDir() + path.trim()));
				}
				beanFactory = new FileSystemXmlApplicationContext(lPath.toArray(new String[0]));
			} else {
				beanFactory = new FileSystemXmlApplicationContext(RmXmlHelper.formatToUrl(RmPathHelper.getWarDir() + springPath));
			}
			log.info("end init bean, time=" + (System.currentTimeMillis() - startTime) + " milliseconds");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("defaultInitBeanFactory fail", e);
		}
    }
    
    /**
     * 返回BeanFactory
     * 
     * @return
     */
    public static BeanFactory getBeanFactory() {
        if(!isInitBean) {
    		synchronized(lockInitFactory) { //lock and init
    			if(!isInitBean) {
    		    	setInitBeanStarted(true);
    				defaultInitBeanFactory();
    				isInitBean = true;
    			}
			}
        }
        return beanFactory;
    }
    
    /**
     * 手动设置BeanFactory，一般由Web.xml配置启动的Listener调用
     * 
     * @param bf
     */
    public static void setBeanFactory(BeanFactory bf) {
    	if(bf == null) {
    		throw new RuntimeException("BeanFactory can not be null!");
    	}
    	beanFactory = bf;
    	isInitBean = true;
    }
    
    private static BeanFactory getBeanFactorySafe() {
    	if(!isInitBean && initBeanStarted) { //如果未启动完毕，但已经开始启动，则不走getBeanFactory()防止触发创建第2个BeanFactory
    		if(beanFactory != null) {
    			return beanFactory;
    		}
			synchronized(lockInitFactory) { //wait for process that initing beanFactory
				//nothing
			}
			if(!isInitBean) {
				StringBuilder warnInfo = new StringBuilder("RmBeanFactory.getBeanFactorySafe(\"");
				warnInfo.append("\") return null, StackTrace:");
				if(RmBaseConfig.getSingleton().isSystemDebugMode()) {
					warnInfo.append("\n");
					warnInfo.append(RmStringHelper.getStackTrace(10000));
				}
				log.warn(warnInfo.toString());
				return null;
			}
    	}
    	return getBeanFactory();
    }
    
	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>This method allows a Spring BeanFactory to be used as a replacement for the
	 * Singleton or Prototype design pattern. Callers may retain references to
	 * returned objects in the case of Singleton beans.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * Will ask the parent factory if the bean cannot be found in this factory instance.
	 * @param name the name of the bean to retrieve
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no bean definition
	 * with the specified name
	 * @throws BeansException if the bean could not be obtained
	 */
    public static Object getBean(String beanName) {
    	BeanFactory bf = getBeanFactorySafe();
    	if(bf != null) {
    		return bf.getBean(beanName);
    	}
    	return null;
    }

	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
	 * safety by throwing a BeanNotOfRequiredTypeException if the bean is not of the
	 * required type. This means that ClassCastException can't be thrown on casting
	 * the result correctly, as can happen with {@link #getBean(String)}.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * Will ask the parent factory if the bean cannot be found in this factory instance.
	 * @param name the name of the bean to retrieve
	 * @param requiredType type the bean must match. Can be an interface or superclass
	 * of the actual class, or {@code null} for any match. For example, if the value
	 * is {@code Object.class}, this method will succeed whatever the class of the
	 * returned instance.
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 * @throws BeanNotOfRequiredTypeException if the bean is not of the required type
	 * @throws BeansException if the bean could not be created
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    	BeanFactory bf = getBeanFactorySafe();
    	if(bf != null) {
    		return bf.getBean(name, requiredType);
    	}
    	return null;
	}

	/**
	 * Return the bean instance that uniquely matches the given object type, if any.
	 * @param requiredType type the bean must match; can be an interface or superclass.
	 * {@code null} is disallowed.
	 * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
	 * but may also be translated into a conventional by-name lookup based on the name
	 * of the given type. For more extensive retrieval operations across sets of beans,
	 * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
	 * @return an instance of the single bean matching the required type
	 * @throws NoSuchBeanDefinitionException if no bean of the given type was found
	 * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
	 * @since 3.0
	 * @see ListableBeanFactory
	 */
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
    	BeanFactory bf = getBeanFactorySafe();
    	if(bf != null) {
    		return bf.getBean(requiredType);
    	}
    	return null;
	}

	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>Allows for specifying explicit constructor arguments / factory method arguments,
	 * overriding the specified default arguments (if any) in the bean definition.
	 * @param name the name of the bean to retrieve
	 * @param args arguments to use if creating a prototype using explicit arguments to a
	 * static factory method. It is invalid to use a non-null args value in any other case.
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 * @throws BeanDefinitionStoreException if arguments have been given but
	 * the affected bean isn't a prototype
	 * @throws BeansException if the bean could not be created
	 * @since 2.5
	 */
	public static Object getBean(String name, Object... args) throws BeansException {
    	BeanFactory bf = getBeanFactorySafe();
    	if(bf != null) {
    		return bf.getBean(name, args);
    	}
    	return null;
	}	
}
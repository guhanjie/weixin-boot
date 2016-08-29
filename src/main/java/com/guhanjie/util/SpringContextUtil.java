/**
 * Project Name: owl-console-service
 * Package Name: com.pinganfu.owl.util
 * File Name: SpringContextUtil.java
 * Create Date: 2016年5月17日 下午2:41:08
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */
package com.guhanjie.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Class Name: SpringContextUtil<br/>
 * Description: [description]
 * 
 * @time 2016年5月17日 下午2:41:08
 * @author guhanjie
 * @version 1.0.0
 * @since JDK 1.6
 */
public class SpringContextUtil implements ApplicationContextAware {
	
	/**
	 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
	 */
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	
	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
        checkApplicationContext();
		return applicationContext.getBean(name);
	}
	
	/**
     * 根据bean的名称获取相应类型的对象，使用泛型，获得结果后，不需要强制转换为相应的类型
     *
     * @param clazz
     *            bean的类型，使用泛型
     * @return T类型的对象
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        checkApplicationContext();
        T bean = applicationContext.getBean(clazz);
        return bean;
    }
	
	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name
	 *            bean注册名
	 * @param requiredType
	 *            返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        checkApplicationContext();
		return applicationContext.getBean(name, requiredType);
	}
	
	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
        checkApplicationContext();
		return applicationContext.containsBean(name);
	}
	
	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        checkApplicationContext();
		return applicationContext.isSingleton(name);
	}
	
	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        checkApplicationContext();
		return applicationContext.getType(name);
	}
	
	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
	    checkApplicationContext();
		return applicationContext.getAliases(name);
	}

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("SpringContextUtil is not injected in applicationContext.xml");
        }
    }

}

package com.guhanjie.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanFactoryUtil implements BeanFactoryAware {
	
	private static BeanFactory beanFactory = null;

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}


	public static Object getBean(String beanName) {
		return getBean(beanName, Object.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> clzz) {
		if (beanFactory != null) {
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}

	public static <T> T getBean(Class<T> clzz) {
		if (beanFactory != null) {
			return beanFactory.getBean(clzz);
		}
		return null;
	}
}

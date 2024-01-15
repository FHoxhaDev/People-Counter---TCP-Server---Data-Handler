package com.example.demo.tcp.util;

import com.example.demo.tcp.handler.MsgServerOutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

	Logger logger = LoggerFactory.getLogger(MsgServerOutHandler.class);

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}

		//logger.info("ApplicationContext Configuration successful");

	}

	// get applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// Get by name Bean.
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	// Get it with class Bean.
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	// Returns the specified Bean by name and Clazz
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}

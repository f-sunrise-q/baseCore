package com.advance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationContextAware {
    private static ApplicationContext context	= null;

    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }
    public static void setContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.context = applicationContext;
    }
    public static ApplicationContext getContext(){
        return AppContext.context;
    }

    /**
     * 根据名称获取bean
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        if (context == null){
            throw new IllegalStateException("applicaitonContext un inject");
        }
        try {
            return (T)context.getBean(name);
        } catch (BeansException e) {
            logger.error("get Bean error",e);
        }
        return null;
    }

    /**
     * 根据className获取bean
     *
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T getBeanByClass(Class className){
        if (context == null){
            throw new IllegalStateException("applicaitonContext un inject");
        }
        try {
            return (T)context.getBean(className);
        } catch (BeansException e) {
            logger.error("get Bean by className error",e);
        }
        return null;
    }
}

package com.baymax.minis.spring.beans.factory.annotation;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.BeanFactory;
import com.baymax.minis.spring.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 自动注入注解处理类
 *
 * @author hujiabin wrote in 2024/1/17 22:54
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 找出bean autowired注解修饰的bean
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Autowired.class))
                .forEach(field -> {
                    String fieldName = field.getName();
                    Object autowiredObj;
                    try {
                        autowiredObj = getBeanFactory().getBean(fieldName);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}

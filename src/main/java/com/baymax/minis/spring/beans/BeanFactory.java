package com.baymax.minis.spring.beans;

/**
 * BeanFactory
 *
 * @author hujiabin wrote in 2024/1/13 21:47
 */
public interface BeanFactory {

    /**
     * 根据beanName获取bean实例
     *
     * @param beanName bean的名称
     * @return bean的实例
     */
    Object getBean(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 注册bean的定义信息
     *
     * @param beanDefinition bean的定义信息
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}

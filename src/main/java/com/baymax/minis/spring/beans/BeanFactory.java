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
    Object getBean(String beanName) throws BeansException;

    /**
     * 判断bean是否存在
     *
     * @param name bean的名称
     * @return 是否存在
     */
    boolean containsBean(String name);

    /**
     * 是否单例bean
     *
     * @param name bean的名称
     * @return 是否单例bean
     */
    boolean isSingleton(String name);

    /**
     * 是否原型bean
     *
     * @param name bean的名称
     * @return 是否原型bean
     */
    boolean isPrototype(String name);

    /**
     * 获取bean的类型
     *
     * @param name bean的名称
     * @return bean的类型
     */
    Class<?> getType(String name);

}

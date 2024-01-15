package com.baymax.minis.spring.beans;

/**
 * 管理单例 Bean的方法规范
 *
 * @author hujiabin wrote in 2024/1/14 19:12
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例bean
     *
     * @param beanName        bean名称
     * @param singletonObject bean对象
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例bean
     *
     * @param beanName bean名称
     * @return bean对象
     */
    Object getSingleton(String beanName);

    /**
     * 判断单例bean是否存在
     *
     * @param beanName bean名称
     * @return 是否存在
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例bean名称
     *
     * @return bean名称数组
     */
    String[] getSingletonNames();
}

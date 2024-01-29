package com.baymax.minis.spring.beans.factory.config;

import com.baymax.minis.spring.beans.factory.BeanFactory;

/**
 * 维护bean之间的依赖关系，和bean处理器
 *
 * @author hujiabin wrote in 2024/1/23 13:09
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加bean处理器
     *
     * @param beanPostProcessor bean 处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 获取bean处理器的个数
     *
     * @return 个数
     */
    int getBeanPostProcessorCount();

    /**
     * 注册依赖的bean
     *
     * @param beanName          bean名称
     * @param dependentBeanName 依赖的bean的名称
     */
    void registerDependentBean(String beanName, String dependentBeanName);

    /**
     * 获取依赖的bean
     *
     * @param beanName bean名称
     * @return Array
     */
    String[] getDependentBeans(String beanName);

    /**
     * 获取被bean依赖的bean
     *
     * @param beanName bean名称
     * @return Array
     */
    String[] getDependenciesForBean(String beanName);
}

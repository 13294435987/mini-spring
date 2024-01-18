package com.baymax.minis.spring.beans.factory.support;

import com.baymax.minis.spring.beans.factory.config.BeanDefinition;

/**
 * 集中存放BeanDefinition的仓库，同时提供操作的方法
 *
 * @author hujiabin wrote in 2024/1/14 21:30
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册bean的定义信息
     *
     * @param name 名称
     * @param bd   bean的定义信息
     */
    void registerBeanDefinition(String name, BeanDefinition bd);

    /**
     * 移除bean的定义信息
     *
     * @param name bean的名称
     */
    void removeBeanDefinition(String name);

    /**
     * 获取bean的定义信息
     *
     * @param name bean的名称
     * @return bean的定义信息
     */
    BeanDefinition getBeanDefinition(String name);

    /**
     * 判断bean的定义信息是否存在
     *
     * @param name bean的名称
     * @return 是否存在
     */
    boolean containsBeanDefinition(String name);
}

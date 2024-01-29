package com.baymax.minis.spring.beans.factory;

import com.baymax.minis.spring.beans.BeansException;

import java.util.Map;

/**
 * 管理bean的存储
 *
 * @author hujiabin wrote in 2024/1/23 08:15
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 是否包含bean的定义信息
     *
     * @param beanName bean的名称
     * @return 是否
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取bean定义信息的数量
     *
     * @return count
     */
    int getBeanDefinitionCount();

    /**
     * 获取bean的定义名称数组
     *
     * @return 数组
     */
    String[] getBeanDefinitionNames();

    /**
     * 根据bean的类型获取bean的名称
     *
     * @param type 类型
     * @return 名称
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 根据bean的类型获取bean对象
     *
     * @param type bean类型
     * @param <T>  对象泛型
     * @return bean对象
     * @throws BeansException 异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}

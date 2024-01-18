package com.baymax.minis.spring.beans.factory.config;

import com.baymax.minis.spring.beans.BeansException;

/**
 * bean处理器
 *
 * @author hujiabin wrote in 2024/1/17 22:45
 */
public interface BeanPostProcessor {

    /**
     * bean初始化前执行
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 处理后的bean对象
     * @throws BeansException 异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean初始化后执行
     *
     * @param bean     bean对象
     * @param beanName bean的名称
     * @return 处理后的对象
     * @throws BeansException 异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

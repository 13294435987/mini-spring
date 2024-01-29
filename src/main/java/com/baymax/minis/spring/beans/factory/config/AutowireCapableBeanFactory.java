package com.baymax.minis.spring.beans.factory.config;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.BeanFactory;

/**
 * 管理给予autowired注解的bean
 *
 * @author hujiabin wrote in 2024/1/18 20:54
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    /**
     * bean初始化之前执行 交由子类去实现
     *
     * @param existingBean bean对象
     * @param beanName     bean的名称
     * @return 处理后的bean
     * @throws BeansException 异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * bean初始化之前后执行 交由子类去实现
     *
     * @param existingBean bean对象
     * @param beanName     bean的名称
     * @return 处理后的bean
     * @throws BeansException 异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;
}

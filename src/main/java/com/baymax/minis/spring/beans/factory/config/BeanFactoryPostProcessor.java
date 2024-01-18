package com.baymax.minis.spring.beans.factory.config;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.BeanFactory;

/**
 * BeanFactoryPostProcessor
 *
 * @author hujiabin wrote in 2024/1/18 21:40
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}

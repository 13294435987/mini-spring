package com.baymax.minis.spring.context;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.ListableBeanFactory;
import com.baymax.minis.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.baymax.minis.spring.beans.factory.config.ConfigurableBeanFactory;
import com.baymax.minis.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.baymax.minis.spring.core.env.Environment;
import com.baymax.minis.spring.core.env.EnvironmentCapable;

/**
 * 应用上下文
 *
 * @author hujiabin wrote in 2024/1/29 21:50
 */
public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    @Override
    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}

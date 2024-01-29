package com.baymax.minis.spring.beans.factory.config;

import com.baymax.minis.spring.beans.factory.ListableBeanFactory;

/**
 * ConfigurableListableBeanFactory
 *
 * @author hujiabin wrote in 2024/1/29 14:14
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}

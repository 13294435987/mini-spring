package com.baymax.minis.spring.beans.factory.support;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.baymax.minis.spring.beans.factory.config.BeanDefinition;
import com.baymax.minis.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ioc引擎
 *
 * @author hujiabin wrote in 2024/1/29 15:23
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory {

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();

        for (String beanName : beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = getBeanDefinition(beanName);
            Class<?> classToMatch = mbd.getClass();
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;
            }

            if (matchFound) {
                result.add(beanName);
            }
        }
        return (String[]) result.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }
}

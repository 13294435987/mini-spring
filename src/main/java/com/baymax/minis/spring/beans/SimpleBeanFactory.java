package com.baymax.minis.spring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SimpleBeanFactory
 *
 * @author hujiabin wrote in 2024/1/13 22:23
 */
public class SimpleBeanFactory implements BeanFactory {

    /**
     * 存储所有bean的定义信息
     */
    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();

    /**
     * 存储bean的名称
     */
    private final List<String> beanNames = new ArrayList<>();

    /**
     * 存储bean的名称和对应的实例化对象
     */
    private final Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {

    }

    @Override
    public Object getBean(String beanName) throws NoSuchBeanDefinitionException {
        // 先创建获取bean的实例
        Object singleton = singletons.get(beanName);
        if (singleton == null) {
            // 不存在 则创建
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new NoSuchBeanDefinitionException();
            } else {
                // 获取bean的定义信息
                BeanDefinition beanDefinition = beanDefinitions.get(i);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                singletons.put(beanDefinition.getId(), singleton);
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.add(beanDefinition);
        beanNames.add(beanDefinition.getId());
    }
}

package com.baymax.minis.spring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理单例bean默认实现
 *
 * @author hujiabin wrote in 2024/1/14 19:24
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 容器中存放所有bean的名称的列表
     */
    protected List<String> beanNames = new ArrayList<>();

    /**
     * 容器中存放所有bean实例的map
     */
    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);


    /**
     * 存储key对应的bean依赖的bean
     */
    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /**
     * 存储被key对应的bean所有依赖的bean
     */
    protected Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.beanNames.add(beanName);
            this.singletonObjects.put(beanName, singletonObject);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }

    protected void registerDependentBean(String beanName, String dependentBeanName) {

    }

    protected boolean hasDependentBean(String beanName) {
        return false;
    }

    protected String[] getDependentBeans(String beanName) {
        return null;
    }

    protected String[] getDependenciesForBean(String beanName) {
        return null;
    }
}

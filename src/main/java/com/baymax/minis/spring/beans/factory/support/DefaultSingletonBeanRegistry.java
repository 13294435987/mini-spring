package com.baymax.minis.spring.beans.factory.support;

import com.baymax.minis.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
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
    protected final List<String> beanNames = new ArrayList<>();

    /**
     * 容器中存放所有bean实例的map
     */
    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);


    /**
     * 存储key对应的bean依赖的bean
     */
    protected final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /**
     * 存储被key对应的bean所有依赖的bean
     */
    protected final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            Object oldObject = singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.beanNames.add(beanName);
            this.singletonObjects.put(beanName, singletonObject);
            System.out.println(" bean has registered............. " + beanName);
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

    /**
     * 移除单例对象
     *
     * @param beanName bean名称
     */
    public void removeSingleton(String beanName) {
        synchronized (singletonObjects) {
            this.singletonObjects.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }

    public void registerDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeans = dependentBeanMap.get(beanName);
        // 如果已经存在了，就不需要再注册了
        if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
            return;
        }
        // No entry yet -> fully synchronized manipulation of the dependentBeans Set
        synchronized (dependentBeanMap) {
            dependentBeans = dependentBeanMap.computeIfAbsent(beanName, k -> new LinkedHashSet<>(8));
            dependentBeans.add(beanName);
        }

        synchronized (dependenciesForBeanMap) {
            Set<String> dependenciesForBean = dependenciesForBeanMap.computeIfAbsent(dependentBeanName,
                    k -> new LinkedHashSet<String>(8));
            dependenciesForBean.add(beanName);
        }
    }

    public boolean hasDependentBean(String beanName) {
        return dependentBeanMap.containsKey(beanName);
    }

    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = dependentBeanMap.get(beanName);
        if (dependentBeans == null) {
            return new String[0];
        }
        return (String[]) dependentBeans.toArray();
    }

    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (dependenciesForBean == null) {
            return new String[0];
        }
        return (String[]) dependenciesForBean.toArray();
    }
}

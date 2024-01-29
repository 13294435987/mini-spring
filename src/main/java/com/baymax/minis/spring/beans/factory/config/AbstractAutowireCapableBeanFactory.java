package com.baymax.minis.spring.beans.factory.config;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象的自动装配实现类
 *
 * @author hujiabin wrote in 2024/1/29 14:29
 */
public abstract class AbstractAutowireCapableBeanFactory
        extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 记录了所有的bean处理器
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 添加bean处理器
     *
     * @param beanPostProcessor bean处理器
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 获取bean处理器的个数
     *
     * @return counts
     */
    public int getBeanPostProcessorCount() {
        return beanPostProcessors.size();
    }

    /**
     * 获取所有bean处理器
     *
     * @return list
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

}

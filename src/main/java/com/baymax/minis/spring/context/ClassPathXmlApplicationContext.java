package com.baymax.minis.spring.context;

import com.baymax.minis.spring.beans.*;
import com.baymax.minis.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.baymax.minis.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.baymax.minis.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.baymax.minis.spring.beans.factory.support.DefaultListableBeanFactory;
import com.baymax.minis.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.baymax.minis.spring.core.ClassPathXmlResource;
import com.baymax.minis.spring.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
 *
 * @author hujiabin wrote in 2024/1/12 22:46
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        // 这里其实是用了装饰器模式，具体getBean方法和注册bean的方法都是由SimpleBeanFactory具体实现
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;

        if (isRefresh) {
            try {
                refresh();
            } catch (IllegalStateException | BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);

    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);

    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);

    }

}

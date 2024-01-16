package com.baymax.minis.spring.context;

import com.baymax.minis.spring.beans.*;
import com.baymax.minis.spring.core.ClassPathXmlResource;
import com.baymax.minis.spring.core.Resource;

/**
 * context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
 *
 * @author hujiabin wrote in 2024/1/12 22:46
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        // 这里其实是用了装饰器模式，具体getBean方法和注册bean的方法都是由SimpleBeanFactory具体实现
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            beanFactory. refresh();
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }


    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}

package com.baymax.minis.spring.context;

import com.baymax.minis.spring.beans.*;
import com.baymax.minis.spring.core.ClassPathXmlResource;
import com.baymax.minis.spring.core.Resource;

/**
 * context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
 *
 * @author hujiabin wrote in 2024/1/12 22:46
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        // 这里其实是用了装饰器模式，具体getBean方法和注册bean的方法都是由SimpleBeanFactory具体实现
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws NoSuchBeanDefinitionException {
        // context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
        return beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanDefinition);
    }
}

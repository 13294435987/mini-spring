package com.baymax.minis.spring.beans;

import com.baymax.minis.spring.core.Resource;
import org.dom4j.Element;

/**
 * XmlBeanDefinitionReader
 *
 * @author hujiabin wrote in 2024/1/13 22:17
 */
public class XmlBeanDefinitionReader {

    private final BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 加载bean定义信息
     *
     * @param resource 资源
     */
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}

package com.baymax.minis.spring;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.*;

/**
 * 解析资源目录classpath下的xml来构建应用上下文对象
 *
 * @author hujiabin wrote in 2024/1/12 22:46
 */
public class ClassPathXmlApplicationContext {

    /**
     * 存储所有bean的定义信息
     */
    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();

    /**
     * 存储bean的名称和对应的实例化对象
     */
    private final Map<String, Object> singletons = new HashMap<>();


    public ClassPathXmlApplicationContext(String fileName) {
        readXml(fileName);
        instanceBeans();
    }

    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = getClass().getClassLoader().getResource(fileName);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            // 对配置文件中的每一个<bean>，进行处理
            for (Object obj : rootElement.elements()) {
                Element element = (Element) obj;
                // 获取bean的基本信息
                String id = element.attributeValue("id");
                String className = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(id, className);
                // 将Bean的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用反射创建bean，并存储在singletons中
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 根据beanName获取bean实例
     *
     * @param beanName bean的名称
     * @return bean的实例
     */
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}

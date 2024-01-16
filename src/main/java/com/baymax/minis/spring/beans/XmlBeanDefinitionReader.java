package com.baymax.minis.spring.beans;

import com.baymax.minis.spring.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * XmlBeanDefinitionReader
 *
 * @author hujiabin wrote in 2024/1/13 22:17
 */
public class XmlBeanDefinitionReader {

    private final SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
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

            // get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            for (Element e : constructorElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                argumentValues.addArgumentValue(new ArgumentValue(pType, pName, pValue));
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);
            // end of handle constructor

            // handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !"".equals(pValue)) {
                    pV = pValue;
                } else if (pRef != null && !"".equals(pRef)) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                propertyValues.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            beanDefinition.setPropertyValues(propertyValues);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            // end of handle properties

            simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}

package com.baymax.minis.test;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.beans.factory.NoSuchBeanDefinitionException;
import com.baymax.minis.spring.context.ClassPathXmlApplicationContext;

/**
 * test main
 *
 * @author hujiabin wrote in 2024/1/13 21:24
 */
public class Test1 {
    public static void main(String[] args) throws NoSuchBeanDefinitionException, BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        BaseService bService;
        try {
            bService = (BaseService) ctx.getBean("baseservice");
            bService.sayHello();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}

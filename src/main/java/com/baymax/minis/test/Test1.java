package com.baymax.minis.test;

import com.baymax.minis.spring.ClassPathXmlApplicationContext;

/**
 * test main
 *
 * @author hujiabin wrote in 2024/1/13 21:24
 */
public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }
}

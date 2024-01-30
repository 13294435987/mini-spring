package com.baymax.minis.test;

import com.baymax.minis.spring.web.RequestMapping;

/**
 * test mvc
 *
 * @author hujiabin wrote in 2024/1/30 15:39
 */
public class HelloWorldBean {

    @RequestMapping("/test1")
    public String doTest1() {
        return "test 1, hello world!";
    }

    @RequestMapping("/test2")
    public String doTest2() {
        return "test 2, hello world!";
    }
}
package com.baymax.minis.spring.beans;

/**
 * bean定义
 *
 * @author hujiabin wrote in 2024/1/12 18:38
 */
public class BeanDefinition {

    private String id;

    private String className;


    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

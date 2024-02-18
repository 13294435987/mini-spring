package com.baymax.minis.spring.web.servlet;

import java.lang.reflect.Method;

/**
 * 封装方法映射
 *
 * @author hujiabin wrote in 2024/2/17 19:44
 */
public class HandlerMethod {

    private Object bean;

    private Class<?> beanType;

    private Method method;

    private MethodParameter[] methodParameters;

    private Class<?> returnType;

    private String description;

    private String className;

    private String methodName;

    public HandlerMethod(Method method, Object obj) {
        this.setMethod(method);
        this.setBean(obj);

    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}

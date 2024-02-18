package com.baymax.minis.spring.web.servlet;

/**
 * 绑定方法的参数
 *
 * @author hujiabin wrote in 2024/2/17 19:58
 */
public class MethodParameter {

    private volatile Class<?> parameterType;

    private volatile String parameterName;

    private volatile Object parameterValue;
}

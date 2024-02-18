package com.baymax.minis.spring.web.servlet;

import com.baymax.minis.spring.web.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 映射注册
 *
 * @author hujiabin wrote in 2024/2/17 21:22
 */
public class MappingRegistry {

    /**
     * 用于保存 @RequestMapping 注解的uri名称列表
     *
     * @see RequestMapping
     */
    private List<String> urlMappingNames = new ArrayList<>();

    /**
     * 保存uri名称与对象映射关系
     */
    private Map<String, Object> mappingObjs = new HashMap<>();

    /**
     * 保存uri名称与方法名的映射关系
     */
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjs() {
        return mappingObjs;
    }

    public void setMappingObjs(Map<String, Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }

}

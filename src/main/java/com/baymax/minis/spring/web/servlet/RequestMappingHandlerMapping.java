package com.baymax.minis.spring.web.servlet;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.web.RequestMapping;
import com.baymax.minis.spring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求url映射到方法
 *
 * @author hujiabin wrote in 2024/2/17 21:13
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;

        initMapping();
    }

    /**
     * 建立URL与调用方法和实例的映射关系，存储在mappingRegistry中
     */
    protected void initMapping() {
        Class<?> clz = null;
        Object obj = null;
        String[] controllerNames = this.wac.getBeanDefinitionNames();
        // 扫描WAC中存放的所有bean
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                obj = this.wac.getBean(controllerName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            Method[] methods = Objects.requireNonNull(clz).getDeclaredMethods();
            // 检查每一个方法声明
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    // 如果该方法带有@RequestMapping注解,则建立映射关系
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                    this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                    this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                }
            }
        }

    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String sPath = request.getServletPath();

        if (!this.mappingRegistry.getUrlMappingNames().contains(sPath)) {
            return null;
        }

        Method method = this.mappingRegistry.getMappingMethods().get(sPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);

        return new HandlerMethod(method, obj);
    }
}

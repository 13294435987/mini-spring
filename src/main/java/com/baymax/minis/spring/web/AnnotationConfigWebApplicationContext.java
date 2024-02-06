package com.baymax.minis.spring.web;

import com.baymax.minis.spring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * AnnotationConfigWebApplicationContext
 *
 * @author hujiabin wrote in 2024/2/3 10:15
 */
public class AnnotationConfigWebApplicationContext
        extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}

package com.baymax.minis.spring.web;

import com.baymax.minis.spring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * XmlWebApplicationContext
 *
 * @author hujiabin wrote in 2024/2/9 13:10
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext
        implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
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

package com.baymax.minis.spring.web;

import com.baymax.minis.spring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * WebApplicationContext
 *
 * @author hujiabin wrote in 2024/2/1 21:58
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * getServletContext
     *
     * @return ServletContext
     */
    ServletContext getServletContext();

    /**
     * setServletContext
     *
     * @param servletContext servletContext
     */
    void setServletContext(ServletContext servletContext);
}

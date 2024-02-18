package com.baymax.minis.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽象处理请求映射
 *
 * @author hujiabin wrote in 2024/2/17 19:35
 */
public interface HandlerMapping {

    /**
     * getHandler
     *
     * @param request request
     * @return handlerMethod
     * @throws Exception 异常
     */
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}

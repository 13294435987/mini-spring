package com.baymax.minis.spring.context;


/**
 * 定义事件发布接口
 *
 * @author hujiabin wrote in 2024/1/14 20:29
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);

    /**
     * 添加事件监听
     *
     * @param listener 监听器
     */
    void addApplicationListener(ApplicationListener listener);
}

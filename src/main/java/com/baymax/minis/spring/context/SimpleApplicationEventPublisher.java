package com.baymax.minis.spring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的事件发布
 *
 * @author hujiabin wrote in 2024/1/29 18:08
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        listeners.forEach(listener -> listener.onApplicationEvent(event));
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        listeners.add(listener);
    }
}

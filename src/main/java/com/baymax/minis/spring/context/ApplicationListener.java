package com.baymax.minis.spring.context;

import java.util.EventListener;

/**
 * 监听者
 *
 * @author hujiabin wrote in 2024/1/29 16:25
 */
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}

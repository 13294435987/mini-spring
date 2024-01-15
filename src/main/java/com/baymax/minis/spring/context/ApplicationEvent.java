package com.baymax.minis.spring.context;

import java.util.EventObject;

/**
 * 用于监听应用的事件
 *
 * @author hujiabin wrote in 2024/1/14 20:30
 */
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}

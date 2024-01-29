package com.baymax.minis.spring.context;

/**
 * 上下文事件刷新
 *
 * @author hujiabin wrote in 2024/1/29 18:02
 */
public class ContextRefreshEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }

    public String toString() {
        return this.msg;
    }
}

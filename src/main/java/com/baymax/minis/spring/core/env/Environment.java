package com.baymax.minis.spring.core.env;

/**
 * 给容器增加一些环境因素，使一些容器整体所需要 的属性有个地方存储访问
 *
 * @author hujiabin wrote in 2024/1/29 15:00
 */
public interface Environment extends PropertyResolver {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);

}

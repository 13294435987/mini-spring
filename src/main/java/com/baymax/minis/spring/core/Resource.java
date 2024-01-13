package com.baymax.minis.spring.core;

import java.util.Iterator;

/**
 * 把外部的配置信息都当成 Resource（资源）来进行抽象
 *
 * @author hujiabin wrote in 2024/1/13 22:03
 */
public interface Resource extends Iterator<Object> {
}

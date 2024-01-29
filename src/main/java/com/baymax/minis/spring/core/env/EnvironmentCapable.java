package com.baymax.minis.spring.core.env;

/**
 * 用于获取 Environment 实例
 *
 * @author hujiabin wrote in 2024/1/29 15:18
 */
public interface EnvironmentCapable {

    /**
     * 获取环境变量实例
     *
     * @return Environment
     */
    Environment getEnvironment();
}

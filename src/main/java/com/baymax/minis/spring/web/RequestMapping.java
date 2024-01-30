package com.baymax.minis.spring.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RequestMapping注解<p>
 * 映射请求uri到对应的方法
 *
 * @author hujiabin wrote in 2024/1/30 15:39
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 匹配的uri
     *
     * @return uri
     */
    String value() default "";
}

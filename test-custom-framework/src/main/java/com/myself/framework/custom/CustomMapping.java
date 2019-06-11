package com.myself.framework.custom;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 类名称：CustomMapping<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface CustomMapping {

    String path() default "";

    RequestMethod method();
}

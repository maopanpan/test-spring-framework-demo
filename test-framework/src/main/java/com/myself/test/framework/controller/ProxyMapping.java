package com.myself.test.framework.controller;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * description：
 *
 * @author wangjie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface ProxyMapping {


    String path() default "";

    RequestMethod method();
}

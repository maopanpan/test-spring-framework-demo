package com.myself.test.framework.controller;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * description：
 *
 * @author wangjie
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoControllerProxyRegistrar.class)
public @interface EnableAutoController {

}

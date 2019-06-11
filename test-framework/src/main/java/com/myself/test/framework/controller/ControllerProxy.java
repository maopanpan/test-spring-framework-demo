package com.myself.test.framework.controller;

import java.lang.annotation.*;

/**
 * description：
 *
 * @author wangjie
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ControllerProxy {

    String value() default "";
}

package com.myself.test.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：HelloworldController<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@RestController
public class HelloworldController {

    @GetMapping(value = "/test/sayHello")
    public String sayHello(String name) {
        return name;
    }
}

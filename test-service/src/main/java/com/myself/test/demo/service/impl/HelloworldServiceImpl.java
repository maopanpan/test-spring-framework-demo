package com.myself.test.demo.service.impl;

import com.myself.test.demo.service.HelloworldService;
import org.springframework.stereotype.Service;

/**
 * 类名称：HelloworldServiceImpl<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@Service
public class HelloworldServiceImpl implements HelloworldService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}

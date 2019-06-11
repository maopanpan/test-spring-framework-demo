package com.myself.test.demo.service;

import com.myself.framework.custom.CustomController;
import com.myself.framework.custom.CustomMapping;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类名称：HelloworldService<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@CustomController
public interface HelloworldService {

    @CustomMapping(path = "/sayHello", method = RequestMethod.GET)
    String sayHello(String name);
}

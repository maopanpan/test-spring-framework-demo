package com.myself.test.demo;

import com.myself.framework.custom.EnableAutoCustom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类名称：TestApplication<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@SpringBootApplication
@EnableAutoCustom
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}

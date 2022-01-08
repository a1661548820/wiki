package com.lzl.wiki.controller;

import com.lzl.wiki.domain.Test;
import com.lzl.wiki.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>HelloWorld</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-08 08:37
 **/
//返回的是一个字符串
//@Controller
//    返回json格式字符串
@RestController
public class TestController {
//    使用自定义变量,如果自定义配置项没有开启就默认读出Test
    @Value("${test.hello:Test}")
    private String testHello;

//    引入service
    @Resource
    private TestServiceImpl testService;
    /**
     * 常用request请求有：GET,POST,PUT,DELETE
     *常见错误类型405请求不支持，404没有这样一个接口
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld"+testHello;
    }
    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "HelloWorld !post"+name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }
}

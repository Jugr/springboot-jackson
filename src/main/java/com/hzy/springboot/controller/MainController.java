package com.hzy.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzy.springboot.entity.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private ObjectMapper jsonMapper;
//    为了区分ObjectMapper和XmlMapper，需要使用@Qualifier注解进行标记。
    public MainController(@Autowired @Qualifier("json") ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
//        this.xmlMapper = xmlMapper;
    }

    Friend friend1 = Friend.builder().nickname("张三").age(12).build();


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value = "/json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Friend json() {
        return friend1;
    }
}
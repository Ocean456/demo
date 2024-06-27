package com.example.demo.controller;

import com.example.demo.util.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Resource
    RedisUtil redisUtil;

/*    @GetMapping("/set")
    public String set() {
        redisUtil.set("key", "value");
        return "success";
    }*/

}

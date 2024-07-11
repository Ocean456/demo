package com.example.demo.controller;

import com.example.demo.util.MailUtils;
import com.example.demo.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Resource
    RedisUtils redisUtils;

    @Resource
    MailUtils mailUtils;

}

package com.example.demo.controller;

import com.example.demo.util.MailUtil;
import com.example.demo.util.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Resource
    MailUtil mailUtil;

    @GetMapping("/getValidationCode")
    public String getValidationCode(/*String email*/) {
        String email = "2282724674@qq.com";
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        mailUtil.sendMail(email, "注册验证码", code);
        return code;
    }
}

package com.example.demo.controller;

import com.example.demo.util.MailUtils;
import com.example.demo.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Resource
    RedisUtils redisUtils;

/*    @GetMapping("/set")
    public String set() {
        redisUtil.set("key", "value");
        return "success";
    }*/

    @Resource
    MailUtils mailUtils;

    @GetMapping("/getValidationCode")
    public String getValidationCode(/*String email*/) {
        String email = "2282724674@qq.com";
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        mailUtils.sendMail(email, "注册验证码", code);
        return code;
    }
}

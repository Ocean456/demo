package com.example.demo.controller;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserInfoController {

    @Resource
    UserService userService;

    @PostMapping("/info")
    public ResponseEntity<?> info(@RequestBody UserInfoDTO userInfoDTO) {
        /*if (userService.completeUserInfo(userInfoDTO,uid)) {
            return ResponseEntity.ok().body("Complete user info success");
        } else {
            return ResponseEntity.badRequest().body("Complete user info failed");
        }*/
        return null;
    }
}

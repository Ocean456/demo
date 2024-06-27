package com.example.demo.controller;

import com.example.demo.dto.LoginFormDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    RedisUtil redisUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginFormDTO loginForm) {
        if (userService.userLogin(loginForm)) {
            String token = JWTUtil.createJWT(loginForm.username);
            redisUtil.set(token, loginForm.username);
            UserDTO userDTO = new UserDTO(loginForm.username);
            return ResponseEntity.ok().header("Authorization", STR."Bearer \{token}").body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginFormDTO loginForm) {
        if (userService.userRegister(loginForm)) {
            int uid = userMapper.getUidByUsername(loginForm.username);
            if (userInfoMapper.initializeUserInfo(uid)) {
                return ResponseEntity.ok().body("Register success");
            }
            return ResponseEntity.badRequest().body("Register failed + Initialize failed");

        } else {
            return ResponseEntity.badRequest().body("Register failed");
        }
    }


}
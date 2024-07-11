package com.example.demo.controller;

import com.example.demo.dto.LoginFormDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtils;
import com.example.demo.util.MailUtils;
import com.example.demo.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    UserMapper userMapper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    MailUtils mailUtils;

    /**
     * 用户登录
     *
     * @param loginForm 登录表单数据传输对象
     * @return 如果登录成功，返回带有授权头的响应实体，否则返回未授权的响应实体
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginFormDTO loginForm) {
        if (userService.userLogin(loginForm)) {
            String token = JWTUtils.createJWT(loginForm.username);
            redisUtils.set(token, loginForm.username);
            UserDTO userDTO = new UserDTO(loginForm.username);
            return ResponseEntity.ok()
                    .header("Authorization", STR."Bearer \{token}")
                    .body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    /**
     * 用户注册
     *
     * @param registerDTO 注册表单数据传输对象
     * @return 如果注册成功，返回带有成功消息的响应实体，否则返回带有失败消息的响应实体
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(/*@RequestBody LoginFormDTO loginForm*/@RequestBody RegisterDTO registerDTO) {
        /*
         * 考虑邮箱重复注册的情况
         * 考虑用户名自动生成功能
         */

        String username = registerDTO.username;
        if (userMapper.getUserByUsername(username) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }


        if (registerDTO.code.equals(redisUtils.get(registerDTO.email))) {

            if (userService.userRegister(registerDTO)) {
                int uid = userMapper.getUidByUsername(registerDTO.username);
                if (userInfoService.initUserInfo(uid, registerDTO.email)) {
                    return ResponseEntity.ok().body("Register success");
                }
                return ResponseEntity.badRequest().body("Register failed + Initialize failed");
            } else {
                return ResponseEntity.badRequest().body("Register failed");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid code");
        }


    }

    @GetMapping("/code")
    public ResponseEntity<?> getCode(@RequestParam String email) {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        mailUtils.sendMail(email, "注册验证码", code);
        redisUtils.set(email, code);
        return ResponseEntity.ok().body("Code sent");
    }
}
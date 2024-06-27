package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dto.LoginFormDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.HashUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public boolean userLogin(LoginFormDTO loginForm) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginForm.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return HashUtil.verifyPassword(loginForm.password, user.getPassword());
        }
        return false;
    }

    public boolean userRegister(LoginFormDTO loginForm) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginForm.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            user = new User();
            user.setUsername(loginForm.getUsername());
            user.setPassword(HashUtil.hashPassword(loginForm.getPassword()));
            return userMapper.insert(user) > 0;
        }
        return false;
    }


    public boolean completeUserInfo(UserInfoDTO userInfoDTO) {
        return false;
    }
}
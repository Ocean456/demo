package com.example.demo.service;

import com.example.demo.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;

    public boolean initUserInfo(Integer uid, String email) {
        String defaultAvatar = "https://img.picui.cn/free/2024/07/03/6684726ad4414.png";
        return userInfoMapper.initUserInfo(uid, defaultAvatar, email);
    }


}

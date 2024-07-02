package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dto.LoginFormDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.HashUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 */
@Service
public class UserService {

    /**
     * 用户映射器
     */
    @Resource
    UserMapper userMapper;

    /**
     * 用户登录
     * @param loginForm 登录表单数据传输对象
     * @return 如果用户名和密码匹配，返回true，否则返回false
     */
    public boolean userLogin(LoginFormDTO loginForm) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", loginForm.getUsername()));
        return user != null && HashUtil.verifyPassword(loginForm.password, user.getPassword());
    }

    /**
     * 用户注册
     * @param loginForm 登录表单数据传输对象
     * @return 如果用户名不存在并且用户成功注册，返回true，否则返回false
     */
    public boolean userRegister(LoginFormDTO loginForm) {
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username", loginForm.getUsername())) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(loginForm.getUsername());
        user.setPassword(HashUtil.hashPassword(loginForm.getPassword()));
        return userMapper.insert(user) > 0;
    }
}
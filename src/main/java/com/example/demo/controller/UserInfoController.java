package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息控制器
 */
@RestController
@RequestMapping("/user/info")
public class UserInfoController {

    @Resource
    UserMapper userMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    /**
     * 获取用户信息
     * @param authHeader 授权头部，包含用户的JWT
     * @return 用户信息
     */
    @GetMapping("/personal")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        Integer uid = getUidFromToken(authHeader);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        String username = userMapper.selectOne(queryWrapper).getUsername();
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", uid));
        return ResponseEntity.ok(userInfo);
    }

    /**
     * 修改用户信息
     * @param authHeader 授权头部，包含用户的JWT
     * @param userInfoDTO 用户信息数据传输对象
     * @return 修改成功或失败的消息
     */
    @PostMapping("/modify")
    public ResponseEntity<?> modifyUserInfo(@RequestHeader("Authorization") String authHeader,@RequestBody UserInfoDTO userInfoDTO) {
        Integer uid = getUidFromToken(authHeader);
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(uid);
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        boolean isUpdated = userInfoMapper.update(userInfo, new QueryWrapper<UserInfo>().eq("uid", uid)) > 0;
        return isUpdated ? ResponseEntity.ok().body("Modify success") : ResponseEntity.badRequest().body("Modify failed");
    }

    /**
     * 从令牌中获取用户ID
     * @param authHeader 授权头部，包含用户的JWT
     * @return 用户ID
     */
    private Integer getUidFromToken(String authHeader) {
        String token = authHeader.substring(7);
        String username = JWTUtil.parseJWT(token);
        return userMapper.getUidByUsername(username);
    }
}
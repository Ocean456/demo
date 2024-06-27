package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/info")
public class UserInfoController {

    @Resource
    UserMapper userMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    @GetMapping("/personal")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = JWTUtil.parseJWT(token);
        Integer uid = userMapper.getUidByUsername(username);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyUserInfo(@RequestHeader("Authorization") String authHeader, UserInfoDTO userInfo) {
        String token = authHeader.substring(7);
        String username = JWTUtil.parseJWT(token);
        Integer uid = userMapper.getUidByUsername(username);
        UserInfo info = new UserInfo();
        info.setUid(uid);
        BeanUtils.copyProperties(userInfo, info);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        if (userInfoMapper.update(info, queryWrapper) > 0) {
            return ResponseEntity.ok().body("Modify success");
        } else {
            return ResponseEntity.badRequest().body("Modify failed");
        }
    }


}

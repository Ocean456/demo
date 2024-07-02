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
        Integer uid = getUidFromToken(authHeader);
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", uid));
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyUserInfo(@RequestHeader("Authorization") String authHeader, UserInfoDTO userInfoDTO) {
        Integer uid = getUidFromToken(authHeader);
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(uid);
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        boolean isUpdated = userInfoMapper.update(userInfo, new QueryWrapper<UserInfo>().eq("uid", uid)) > 0;
        return isUpdated ? ResponseEntity.ok().body("Modify success") : ResponseEntity.badRequest().body("Modify failed");
    }

    private Integer getUidFromToken(String authHeader) {
        String token = authHeader.substring(7);
        String username = JWTUtil.parseJWT(token);
        return userMapper.getUidByUsername(username);
    }
}
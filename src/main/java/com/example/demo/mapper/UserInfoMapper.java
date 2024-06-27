package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Insert("insert into userinfo (uid, phone, email, avatar, nickname, region) values (#{uid}, NULL, NULL, NULL, NULL, NULL)")
    boolean initializeUserInfo(int uid);
}

package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Insert("insert into userinfo (uid, avatar, email) values (#{uid}, #{avatar}, #{email})")
    boolean initUserInfo(Integer uid, String avatar,String email);
}

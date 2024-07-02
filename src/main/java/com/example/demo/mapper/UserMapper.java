package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select uid from user where username = #{username}")
    int getUidByUsername(String username);

    @Select("select username from user where uid = #{uid}")
    String getUsernameByUid(int uid);
}
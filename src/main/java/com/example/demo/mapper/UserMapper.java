package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select uid from user where username = #{username}")
    Integer getUidByUsername(String username);

    @Select("select username from user where uid = #{uid}")
    String getUsernameByUid(int uid);

    @Select("select * from user_view where username = #{username}")
    List<UserInfoDTO> searchUser(String username);
}
package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("userinfo")
public class UserInfo {
    @TableId(type = IdType.AUTO)
    Integer iid;
    Integer uid;
    String nickname;
    String avatar;
    String email;
    String phone;
    String region;

}

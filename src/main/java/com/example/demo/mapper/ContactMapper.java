package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.ContactDTO;
import com.example.demo.entity.Contact;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContactMapper extends BaseMapper<Contact> {
    @Select("select user.username as contactUsername, user.nickname as contactNickname, user.avatar as contactAvatar, contact.classify as contactGroup from contact, user_view as user where contact.fid = user.uid and contact.uid = #{uid}")
    List<ContactDTO> getContactByUid(int uid);

    @Delete("delete from contact where uid = #{uid} and fid = #{fid}")
    int deleteContact(int uid, int fid);

    @Select("select count(*) from contact where uid = #{uid} and fid = #{fid}")
    int getContactByUidAndFid(int uid, int fid);
}

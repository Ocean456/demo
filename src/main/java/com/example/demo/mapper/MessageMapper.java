package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.MessageDTO;
import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT u1.username AS sender, u2.username AS receiver, m.content, DATE_FORMAT(m.time, '%Y-%m-%d %H:%i:%s') AS time FROM message m JOIN user u1 ON m.sender = u1.uid JOIN user u2 ON m.receiver = u2.uid;")
    List<MessageDTO> getPersonalMessage(String username);
}

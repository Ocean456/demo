package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import com.example.demo.entity.Message;
import com.example.demo.handle.SocketHandler;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageMapper messageMapper;

    @Resource
    UserMapper userMapper;


    @GetMapping("/personal")
    public ResponseEntity<?> getMessage(@RequestHeader("Authorization") String authHeader) {
        String username = JWTUtils.parseJWT(authHeader.substring(7));
        return ResponseEntity.ok(messageMapper.getPersonalMessage(username));
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO/*,@RequestHeader("Authorization") String authHeader*/) {
        Integer sid = userMapper.getUidByUsername(messageDTO.getSender());
        Integer rid = userMapper.getUidByUsername(messageDTO.getReceiver());
        Message message = new Message(null, sid, rid, messageDTO.getContent(), null, 1);
        if (messageMapper.insert(message) > 0) {
            if (SocketHandler.isUserOnline(messageDTO.getReceiver())) {
                SocketHandler.sendMessageToUser(messageDTO);
            }
            return ResponseEntity.ok("Message sent");
        } else {
            return ResponseEntity.ok("Message failed");
        }
    }


}

package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import com.example.demo.entity.Message;
import com.example.demo.handle.SocketHandler;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

/*    @Resource
    SocketHandler socketHandler;*/

    @Resource
    MessageMapper messageMapper;

    @Resource
    UserMapper userMapper;


    @GetMapping("/personal")
    public ResponseEntity<?> getMessage(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7);
        String username = JWTUtil.parseJWT(authHeader.substring(7));
//        LoggerFactory.getLogger(this.getClass()).info(STR."User \{username} requested messages");
//        List<MessageDTO> messages = messageMapper.getPersonalMessage(username);
        return ResponseEntity.ok(messageMapper.getPersonalMessage(username));
    }

/*    @GetMapping("/per/towards")
    public ResponseEntity<?> getMessageToward(@RequestParam String username,@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String per = JWTUtil.parseJWT(token);
    }*/


    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO/*,@RequestHeader("Authorization") String authHeader*/) {
        // Integer sid = userMapper.getUidByUsername(JWTUtil.parseJWT(authHeader.substring(7)));
        Integer sid = userMapper.getUidByUsername(messageDTO.getSender());
        Integer rid = userMapper.getUidByUsername(messageDTO.getReceiver());
        Message message = new Message(null, sid, rid, messageDTO.getContent(), null, 1);
        if (messageMapper.insert(message) > 0) {
//            LoggerFactory.getLogger(this.getClass()).info(STR."Message sent from \{messageDTO.getSender()} to \{messageDTO.getReceiver()}");
            if (SocketHandler.isUserOnline(messageDTO.getReceiver())) {
                SocketHandler.sendMessageToUser(messageDTO);
            }
            return ResponseEntity.ok("Message sent");
        } else {
//            LoggerFactory.getLogger(this.getClass()).info("Message failed");
            return ResponseEntity.ok("Message failed");
        }
    }


}

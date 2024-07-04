package com.example.demo.controller;

import com.example.demo.dto.ContactDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.Contact;
import com.example.demo.mapper.ContactMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 联系人控制器
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Resource
    ContactMapper contactMapper;

    @Resource
    UserMapper userMapper;

    /**
     * 从授权头部获取用户ID
     *
     * @param authHeader 授权头部
     * @return 用户ID
     */
    private int getUidFromAuthHeader(String authHeader) {
//        String token;
//        try {
        String token = authHeader.substring(7);
//        } catch (StringIndexOutOfBoundsException e){
//            LoggerFactory.getLogger(this.getClass()).info("Invalid token");
//            return 0;
//        }
//        String self = JWTUtil.parseJWT(token);
        return userMapper.getUidByUsername(JWTUtil.parseJWT(token));
    }


    /**
     * 验证用户
     *
     * @param uid      用户ID
     * @param username 用户名
     * @return 响应实体
     */
    private ResponseEntity<?> validateUser(int uid, String username) {
        int contactUid = userMapper.getUidByUsername(username);
        if (contactUid == 0) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if (uid == contactUid) {
            return ResponseEntity.badRequest().body("Cannot add/delete self as contact");
        }
        return null;
    }

    /**
     * 获取联系人
     *
     * @param authHeader 授权头部
     * @return 联系人列表
     */
    @GetMapping("/personal")
    public ResponseEntity<List<ContactDTO>> getContact(@RequestHeader("Authorization") String authHeader) {
        int uid = getUidFromAuthHeader(authHeader);
//        List<ContactDTO> contacts = contactMapper.getContactByUid(uid);
//        LoggerFactory.getLogger(this.getClass()).info("User {} requested contacts", uid);
        return ResponseEntity.ok(contactMapper.getContactByUid(uid));
    }

    /**
     * 添加联系人
     *
     * @param username   用户名
     * @param authHeader 授权头部
     * @return 响应实体
     */
    @PostMapping("/add")
    public ResponseEntity<?> addContact(@RequestParam String username, @RequestHeader("Authorization") String authHeader) {
        int uid = getUidFromAuthHeader(authHeader);
        int fid = userMapper.getUidByUsername(username);
        if (uid == 0 /*|| uid == fid*/) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        ResponseEntity<?> response = validateUser(uid, username);
        if (response != null) return response;

        // check if contact already exists
        if (contactMapper.getContactByUidAndFid(uid, fid) == 1) {
            return ResponseEntity.badRequest().body("Contact already exists");
        }

        Contact contact = new Contact();
        contact.setUid(uid);
        contact.setClassify("朋友");
        contact.setFid(fid);
        contactMapper.insert(contact);
        return ResponseEntity.ok("Contact added");
    }

    /**
     * 删除联系人
     *
     * @param username   用户名
     * @param authHeader 授权头部
     * @return 响应实体
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteContact(@RequestParam String username, @RequestHeader("Authorization") String authHeader) {
        int uid = getUidFromAuthHeader(authHeader);
        if (uid == 0) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        ResponseEntity<?> response = validateUser(uid, username);
        if (response != null) return response;

        if (contactMapper.deleteContact(uid, userMapper.getUidByUsername(username)) == 0) {
            return ResponseEntity.badRequest().body("Contact not found");
        }

        return ResponseEntity.ok("Contact deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserInfoDTO>> searchContact(@RequestParam String username) {
        return ResponseEntity.ok(userMapper.searchUser(username));
    }
}
package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ImageUtils {

    public static final String ACCESS_TOKEN = "6f1c9a863174ca71a64891727023603e";

    public static final String OWNER = "cascade123";

    public static final String REPO = "demo";

    public static final String PATH = STR."demo/images/\{DateUtils.getYearMonth()}/";

    public static final String ADD_MESSAGE = "Add image successfully";

    public static final String DELETE_MESSAGE = "Delete image successfully";


    public static String createUploadFileUrl(String originalFileName) {
        String ext = FileUtils.getExtension(originalFileName);
        String fileName = STR."\{System.currentTimeMillis()}_\{UUID.randomUUID()}";
        String fullPath = PATH + fileName;
        return STR."https://gitee.com/api/v5/repos/\{OWNER}/\{REPO}/contents/\{fullPath}";
    }

    public static Map<String, Object> getUploadBodyMap(byte[] multipartFile) {
        HashMap<String, Object> body = new HashMap<>(3);
        Base64.Encoder encoder = Base64.getEncoder();
        body.put("access_token", ACCESS_TOKEN);
        body.put("content", encoder.encodeToString(multipartFile));
        body.put("message", ADD_MESSAGE);
        return body;
    }
}


package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class HashUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String password, String hash) {
        return encoder.matches(password, hash);
    }

}

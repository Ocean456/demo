package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashUtilTest {

    @Test
    void hashPassword() {
        String password = "123456";
        String hashedPassword= HashUtil.hashPassword(password);
        System.out.println(hashedPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void verifyPassword() {
        String password = "123456";
        String hashedPassword= HashUtil.hashPassword(password);

        assertTrue(HashUtil.verifyPassword(password, hashedPassword));
        assertFalse(HashUtil.verifyPassword("1234567", hashedPassword));
    }
}
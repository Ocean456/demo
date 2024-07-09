package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashUtilsTest {

    @Test
    void hashPassword() {
        String password = "123456";
        String hashedPassword= HashUtils.hashPassword(password);
        System.out.println(hashedPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void verifyPassword() {
        String password = "123456";
        String hashedPassword= HashUtils.hashPassword(password);

        assertTrue(HashUtils.verifyPassword(password, hashedPassword));
        assertFalse(HashUtils.verifyPassword("1234567", hashedPassword));
    }
}
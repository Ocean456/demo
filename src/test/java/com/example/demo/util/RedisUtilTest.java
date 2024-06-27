package com.example.demo.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void set() {
        assertNotNull(redisTemplate, "RedisTemplate should not be null");
        redisTemplate.opsForValue().set("key", "value");
        // 其他测试逻辑
    }
}

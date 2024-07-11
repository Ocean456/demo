package com.example.demo.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class RedisUtils {

    private final RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

}

package com.gbuxss.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbuxss.journalApp.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate redisTemplate;

    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            assert o != null;
            return objectMapper.readValue(o.toString(), entityClass);
        } catch (Exception e) {
            log.error("Something Went wrong: " + e);
            return null;
        }
    }

    public void set(String key, Object object, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jasonValue = objectMapper.writeValueAsString(object);
            redisTemplate.opsForValue().set(key, jasonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Something Went wrong: " + e);
        }
    }
}


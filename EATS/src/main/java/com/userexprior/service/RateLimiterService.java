package com.userexprior.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final Integer MAX_REQUESTS_PER_MINUTE = 10;

    public boolean allowRequest(String userId) {

        String key = "rate:" + userId;

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }

        return count <= MAX_REQUESTS_PER_MINUTE;
    }
}

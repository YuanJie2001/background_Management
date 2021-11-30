package com.vector.manager.core.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.vector.manager.core.config.ApplicationContextManager;

import java.util.concurrent.TimeUnit;

public class RedisUtil {

    private static StringRedisTemplate stringRedisTemplate = ApplicationContextManager.getBean(StringRedisTemplate.class);
    public static final int DEFAULT_TIMEOUT = 12;

    public static void put(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    public static void put(String key, Object value, Integer time) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, TimeUnit.HOURS);
    }

    public static String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}

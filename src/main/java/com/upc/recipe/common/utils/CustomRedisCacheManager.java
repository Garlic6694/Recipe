package com.upc.recipe.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Random;

@Slf4j
public class CustomRedisCacheManager extends RedisCacheManager {
    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * 针对@Cacheable设置缓存过期时间
     *
     * @param name
     * @param cacheConfig
     * @return
     */
    @NotNull
    @Override
    protected RedisCache createRedisCache(@NotNull String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        // 解析TTL
        long ttl;
        if (array.length > 1) {
            ttl = Long.parseLong(array[1]);
        } else {
            int min = 30;
            int max = 300;
            ttl = min + (long) new Random().nextInt(max - min);
        }
        log.info("过期时间[{}]", ttl);
        cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl)); // 注意单位此处用的是秒，而非毫秒
        return super.createRedisCache(name, cacheConfig);
    }
}

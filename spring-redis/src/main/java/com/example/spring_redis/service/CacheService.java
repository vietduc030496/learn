package com.example.spring_redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Cacheable(cacheNames = "cache", key = "1")
    public String getCache() {
        return "this is a cache";
    }
}

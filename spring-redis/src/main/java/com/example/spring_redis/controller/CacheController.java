package com.example.spring_redis.controller;

import com.example.spring_redis.service.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@AllArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping
    public ResponseEntity<String> getCache() {
        return ResponseEntity.ok(cacheService.getCache());
    }
}

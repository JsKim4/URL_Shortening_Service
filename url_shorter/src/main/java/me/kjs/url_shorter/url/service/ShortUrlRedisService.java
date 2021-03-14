package me.kjs.url_shorter.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ShortUrlRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public Optional<String> findFullUrlByShortResource(String shortResource) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String fullUrl = valueOperations.get(shortResource);
        return Optional.ofNullable(fullUrl);
    }

    public void addShortResourceAndFullUrl(String shortResource, String fullUrl) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(shortResource, fullUrl);
    }
}

package me.kjs.url_shorter.url.service;

import ch.qos.logback.core.boolex.EvaluationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShortUrlRedisServiceTest {

    static RedisTemplate redisTemplate = mock(RedisTemplate.class);
    static ShortUrlRedisService shortUrlRedisService;
    @BeforeAll
    static void setUp() {
        shortUrlRedisService = new ShortUrlRedisService(redisTemplate);
    }
    @Test
    @DisplayName("리소스로 레디스에서 풀 URL 조회 테스트")
    void findRedisFullUrl(){
        String resource = "ABC";
        String fullUrl = "https://github.com/JsKim4";
        ValueOperations mockValueOperation = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mockValueOperation);
        when(mockValueOperation.get(resource)).thenReturn(fullUrl);
        String s = shortUrlRedisService.findFullUrlByShortResource(resource).get();
        assertEquals(fullUrl,s);
    }




}
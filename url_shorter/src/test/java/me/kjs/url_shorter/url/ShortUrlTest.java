package me.kjs.url_shorter.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Short Url 도메인 테스트")
class ShortUrlTest {

    @Test
    @DisplayName("ShortUrl 생성 테스트")
    void createShortUrl() {
        //given
        ShortUrlCreator shortUrlCreator = mock(ShortUrlCreator.class);
        Protocol protocol = Protocol.HTTPS;
        String host = "github.com";
        int port = 0;
        String resources = "/JsKim4/URL_Shortening_Service";

        //when
        when(shortUrlCreator.getHost()).thenReturn(host);
        when(shortUrlCreator.getPort()).thenReturn(port);
        when(shortUrlCreator.getResource()).thenReturn(resources);
        when(shortUrlCreator.getProtocol()).thenReturn(protocol);
        ShortUrl shortUrl = ShortUrl.createShortUrl(shortUrlCreator);

        //then
        assertEquals("https://github.com/JsKim4/URL_Shortening_Service", shortUrl.getFullUrl());
        assertNull(shortUrl.getShortResource());
        assertEquals(0, shortUrl.getRequestCount());
    }

    @Test
    @DisplayName("ShortUrl 요청 개수 추가 테스트")
    void updateShortUrlRequestCount() {
        //given
        ShortUrlCreator shortUrlCreator = mock(ShortUrlCreator.class);
        Protocol protocol = Protocol.HTTPS;
        String host = "github.com";
        int port = 0;
        String resources = "/JsKim4/URL_Shortening_Service";
        String fullUrl = "https://github.com/JsKim4/URL_Shortening_Service";
        int requestCount = 10000;
        LocalDateTime aggregateDateTime = LocalDateTime.of(2021, 3, 11, 19, 36);
        //when
        when(shortUrlCreator.getHost()).thenReturn(host);
        when(shortUrlCreator.getPort()).thenReturn(port);
        when(shortUrlCreator.getResource()).thenReturn(resources);
        when(shortUrlCreator.getProtocol()).thenReturn(protocol);
        ShortUrl shortUrl = ShortUrl.createShortUrl(shortUrlCreator);
        shortUrl.addAggregateRequestCount(requestCount, aggregateDateTime);

        //then
        assertEquals(fullUrl, shortUrl.getFullUrl());
        assertNull(shortUrl.getShortResource());
        assertEquals(shortUrl.getRequestCount(), requestCount);
    }


}
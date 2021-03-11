package me.kjs.url_shorter.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShortUrlTest {

    @Test
    @DisplayName("ShortUrl 생성 테스트")
    void createShortUrl() {
        ShortUrlCreator shortUrlCreator = mock(ShortUrlCreator.class);
        Protocol protocol = Protocol.HTTPS;
        String host = "github.com";
        int port = 0;
        String resources = "/JsKim4/URL_Shortening_Service";

        when(shortUrlCreator.getHost()).thenReturn(host);
        when(shortUrlCreator.getPort()).thenReturn(port);
        when(shortUrlCreator.getResource()).thenReturn(resources);
        when(shortUrlCreator.getProtocol()).thenReturn(protocol);
        ShortUrl shortUrl = ShortUrl.createShortUrl(shortUrlCreator);
        assertEquals("https://github.com/JsKim4/URL_Shortening_Service", shortUrl.getUrl());
        assertNull(shortUrl.getShortResource());
        assertEquals(0, shortUrl.getRequestCount());
    }
}
package me.kjs.url_shorter.url;

import me.kjs.url_shorter.Base62Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@DisplayName("Short Url 서비스 테스트")
class ShortUrlServiceTest {


    @Autowired
    private ShortUrlRepository shortUrlRepository;
    private ShortUrlService shortUrlService;


    @BeforeEach
    void setUp(){
        shortUrlService = new ShortUrlService(shortUrlRepository,new Base62Encoder());
    }

    @Test
    @DisplayName("Short url 생성테스트")
    void createShortUrlTest() {
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
        ShortUrl shortUrl = shortUrlService.createShortUrl(shortUrlCreator);
        //then
        assertNotNull(shortUrl.getShortResource());
        assertEquals(shortUrl.getFullUrl(), fullUrl);
        assertNotNull(shortUrl.getId());
    }

    @Test
    @DisplayName("Short url 생성 중복 테스트")
    @Transactional
    void createOverlapShortUrlTest() {
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
        ShortUrl shortUrl = shortUrlService.createShortUrl(shortUrlCreator);
        ShortUrl overlapShortUrl = shortUrlService.createShortUrl(shortUrlCreator);
        //then
        assertNotNull(overlapShortUrl.getShortResource());
        assertEquals(overlapShortUrl.getFullUrl(), fullUrl);
        assertNotNull(overlapShortUrl.getId());
        assertEquals(shortUrl.getId(), overlapShortUrl.getId());
        assertEquals(shortUrl, overlapShortUrl);
        assertEquals(shortUrl.getFullUrl(), overlapShortUrl.getFullUrl());
    }
}
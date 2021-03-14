package me.kjs.url_shorter.url;

import me.kjs.url_shorter.Base62Encoder;
import me.kjs.url_shorter.url.dto.ShortUrlCreator;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
import me.kjs.url_shorter.url.service.ShortUrlEventConsumer;
import me.kjs.url_shorter.url.service.ShortUrlService;
import me.kjs.url_shorter.url.type.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@DisplayName("메세지 큐 처리 이벤트 테스트")
class ShortUrlEventConsumerTest {

    ShortUrlEventConsumer shortUrlEventConsumer;
    ShortUrlService shortUrlService;
    @Autowired
    ShortUrlRepository shortUrlRepository;
    @BeforeEach
    void setUp(){
        shortUrlEventConsumer = new ShortUrlEventConsumer(shortUrlRepository);
        shortUrlService = new ShortUrlService(shortUrlRepository,new Base62Encoder());
    }

    @Test
    @DisplayName("URL 요청수 추가 테스트")
    void addUrlRequestTest() {
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
        shortUrlEventConsumer.addUrlRequest(shortUrl.getShortResource());
        //then
        ShortUrl updatedUrl = shortUrlRepository.findByShortResource(shortUrl.getShortResource()).orElseThrow(RuntimeException::new);
        assertEquals(updatedUrl.getRequestCount(),1);

    }

}
package me.kjs.url_shorter.url;

import me.kjs.url_shorter.Base62Encoder;
import me.kjs.url_shorter.url.dto.ShortUrlCreator;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
import me.kjs.url_shorter.url.service.ShortUrlEventProducer;
import me.kjs.url_shorter.url.service.ShortUrlService;
import me.kjs.url_shorter.url.type.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@Testcontainers
@DisplayName("메세지 Event Producer 테스트")
public class ShortUrlEventProducerTest {
    private ShortUrlEventProducer shortUrlEventProducer;
    private ShortUrlService shortUrlService;
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    @MockBean
    private RabbitTemplate rabbitTemplate;
    @BeforeEach
    void setUp(){
        shortUrlService = new ShortUrlService(shortUrlRepository,new Base62Encoder());
        shortUrlEventProducer = new ShortUrlEventProducer(rabbitTemplate);
    }

    @Test
    @DisplayName("메세징 큐 데이터 추가 테스트")
    void queueAddTest() {
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
        ShortUrl shortUrl = shortUrlService.createShortUrl(shortUrlCreator);
        shortUrlEventProducer.requestUrlSend(shortUrl.getShortResource());
    }
}
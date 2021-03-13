package me.kjs.url_shorter.url;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShortUrlEventConsumer {

    private final ShortUrlRepository shortUrlRepository;

    @RabbitListener(queues = "URL_REQUEST")
    @Transactional
    public void addUrlRequest(String shortResource) {
        ShortUrl shortUrl = shortUrlRepository.findByShortResource(shortResource).orElseThrow(RuntimeException::new);
        shortUrl.addAggregateRequestCount(1, LocalDateTime.now());
    }
}

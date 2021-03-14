package me.kjs.url_shorter.url.service;

import lombok.RequiredArgsConstructor;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
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

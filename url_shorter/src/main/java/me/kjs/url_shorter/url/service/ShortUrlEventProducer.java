package me.kjs.url_shorter.url.service;

import lombok.RequiredArgsConstructor;
import me.kjs.url_shorter.url.type.MessageQueue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortUrlEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void requestUrlSend(String shortResource) {
        rabbitTemplate.convertAndSend(MessageQueue.URL_REQUEST.getQueueName(), shortResource);
    }
}


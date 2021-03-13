package me.kjs.url_shorter.url;

import lombok.RequiredArgsConstructor;
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


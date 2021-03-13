package me.kjs.url_shorter;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrlShorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShorterApplication.class, args);
    }
    @Bean
    Queue activityQueue(){
        return new Queue("URL_REQUEST");
    }

}

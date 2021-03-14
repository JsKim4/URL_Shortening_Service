package me.kjs.url_shorter.url.dto;

import lombok.*;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.type.Protocol;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ShortUrlForm {
    public static class Request {
        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Creator implements ShortUrlCreator {
            @NotEmpty
            private String host;
            @Min(0) @Max(65535)
            private Integer port;
            @NotNull
            private String resource;
            @NotNull
            private Protocol protocol;
        }
    }

    public static class Response {
        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FindOne {
            private String fullUrl;
            private String shortResource;
            private long requestCount;
            private LocalDateTime aggregateDateTime;

            public static FindOne shortUrlToFindOne(ShortUrl shortUrl) {
                return FindOne.builder()
                        .fullUrl(shortUrl.getFullUrl())
                        .shortResource(shortUrl.getShortResource())
                        .requestCount(shortUrl.getRequestCount())
                        .aggregateDateTime(shortUrl.getAggregateDateTime())
                        .build();
            }
        }
    }
}

package me.kjs.url_shorter.url;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ShortUrlForm {
    public static class Request {
        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Creator implements ShortUrlCreator {
            @NotEmpty
            private String host;
            private Integer port;
            @NotEmpty
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

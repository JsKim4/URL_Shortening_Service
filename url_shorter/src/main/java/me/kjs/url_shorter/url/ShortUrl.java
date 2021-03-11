package me.kjs.url_shorter.url;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String shortResource;
    private String host;
    private String resource;
    private Integer port;
    private Protocol protocol;
    private long requestCount;
    private LocalDateTime aggregateDateTime;

    @Builder
    public ShortUrl(String shortResource, String host, String resource, int port, Protocol protocol, long requestCount, LocalDateTime aggregateDateTime, LocalDateTime createdDate) {
        this.shortResource = shortResource;
        this.host = host;
        this.resource = resource;
        this.port = port;
        this.protocol = protocol;
        this.requestCount = requestCount;
        this.aggregateDateTime = aggregateDateTime;
    }

    public static ShortUrl createShortUrl(ShortUrlCreator shortUrlCreator) {
        return ShortUrl.builder()
                .host(shortUrlCreator.getHost())
                .port(shortUrlCreator.getPort())
                .resource(shortUrlCreator.getResource())
                .protocol(shortUrlCreator.getProtocol())
                .requestCount(0)
                .aggregateDateTime(null)
                .build();
    }

    public String getUrl() {
        return protocol.getProtocol() + host + (port == 0 ? "" : port) + resource;
    }

    public String getShortResource() {
        return shortResource;
    }

    public void aggregateRequestCount(long count, LocalDateTime aggregateDateTime) {
        requestCount = count;
        this.aggregateDateTime = aggregateDateTime;
    }

    public long getRequestCount() {
        return requestCount;
    }

}
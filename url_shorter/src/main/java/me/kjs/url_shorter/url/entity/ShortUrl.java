package me.kjs.url_shorter.url.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.kjs.url_shorter.url.dto.ShortUrlCreator;
import me.kjs.url_shorter.url.type.Protocol;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "SHORT_URL_SEQ_GENERATOR", sequenceName = "SHORT_URL_SEQ_GENERATOR", initialValue = 1, allocationSize = 1)
public class ShortUrl {
    @Id
    @Column(name = "short_url_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHORT_URL_SEQ_GENERATOR")
    private Long id;
    private String shortResource;
    private String host;
    private String resource;
    private Integer port;
    @Enumerated(EnumType.STRING)
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

    public String getFullUrl() {
        return protocol.getProtocol() + host + (port == null || port == 0 ? "" : ":" + port) + resource;
    }

    public String getShortResource() {
        return shortResource;
    }

    public void addAggregateRequestCount(long count, LocalDateTime aggregateDateTime) {
        requestCount += count;
        this.aggregateDateTime = aggregateDateTime;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void updateShortResource(String shortResource) {
        this.shortResource = shortResource;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getAggregateDateTime() {
        return aggregateDateTime;
    }
}
package me.kjs.url_shorter.url.repository;

import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.type.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByProtocolAndHostAndPortAndResource(Protocol protocol, String host, Integer port, String resource);
    Optional<ShortUrl> findByShortResource(String shortResource);
}

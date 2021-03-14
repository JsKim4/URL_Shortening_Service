package me.kjs.url_shorter.url.service;

import lombok.RequiredArgsConstructor;
import me.kjs.url_shorter.Base62Encoder;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
import me.kjs.url_shorter.url.dto.ShortUrlCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final Base62Encoder base62Encoder;

    @Transactional
    public ShortUrl createShortUrl(ShortUrlCreator creator) {
        ShortUrl shortUrl = shortUrlRepository.findByProtocolAndHostAndPortAndResource(
                creator.getProtocol(),
                creator.getHost(),
                creator.getPort(),
                creator.getResource())
                .orElse(ShortUrl.createShortUrl(creator));
        ShortUrl save = shortUrlRepository.save(shortUrl);
        save.updateShortResource(base62Encoder.encode(save.getId()));
        return shortUrl;
    }
}

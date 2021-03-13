package me.kjs.url_shorter.url;

import lombok.RequiredArgsConstructor;
import me.kjs.url_shorter.Base62Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

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

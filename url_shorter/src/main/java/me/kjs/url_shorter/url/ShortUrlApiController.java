package me.kjs.url_shorter.url;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/url")
@Slf4j
public class ShortUrlApiController {
    private final ShortUrlService shortUrlService;
    private final ShortUrlRedisService shortUrlRedisService;
    private final ShortUrlRepository shortUrlRepository;


    @PostMapping
    public ResponseEntity createShortUrl(@RequestBody ShortUrlForm.Request.Creator creator, Errors errors) throws URISyntaxException {
        validation(creator,errors);
        ShortUrl shortUrl = shortUrlService.createShortUrl(creator);
        shortUrlRedisService.addShortResourceAndFullUrl(shortUrl.getShortResource(), shortUrl.getFullUrl());
        ShortUrlForm.Response.FindOne body = ShortUrlForm.Response.FindOne.shortUrlToFindOne(shortUrl);
        URI uri = new URI("/api/url/" + shortUrl.getShortResource());
        return ResponseEntity.created(uri).body(body);
    }

    private void validation(ShortUrlForm.Request.Creator creator, Errors errors) {
        if(creator.getPort() < 0 || creator.getPort() > 65535){
            errors.reject("PortNotAvail");
        }
        if(!creator.getResource().startsWith("/")){
            errors.reject("ResourceStartNotAvail");
        }
    }

    @GetMapping("/{shortResource}")
    public ResponseEntity findByShortResource(@PathVariable String shortResource) {
        ShortUrl shortUrl = shortUrlRepository.findByShortResource(shortResource).orElseThrow(RuntimeException::new);
        ShortUrlForm.Response.FindOne body = ShortUrlForm.Response.FindOne.shortUrlToFindOne(shortUrl);
        return ResponseEntity.ok(body);
    }
}

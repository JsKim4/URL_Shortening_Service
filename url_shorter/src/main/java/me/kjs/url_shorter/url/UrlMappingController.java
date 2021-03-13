package me.kjs.url_shorter.url;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UrlMappingController {
    private final ShortUrlRedisService shortUrlRedisService;
    private final ShortUrlEventProducer shortUrlEventProducer;

    @GetMapping("/{shortResource}")
    public void mappingShortUrl(@PathVariable String shortResource, HttpServletResponse httpServletResponse) throws IOException {
        String redirectUrl = shortUrlRedisService.findFullUrlByShortResource(shortResource).orElseThrow(RuntimeException::new);
        shortUrlEventProducer.requestUrlSend(shortResource);
        httpServletResponse.sendRedirect(redirectUrl);
    }

}

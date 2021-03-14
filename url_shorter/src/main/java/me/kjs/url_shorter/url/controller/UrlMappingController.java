package me.kjs.url_shorter.url.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kjs.url_shorter.url.exception.NotFoundShortUrlException;
import me.kjs.url_shorter.url.service.ShortUrlEventProducer;
import me.kjs.url_shorter.url.service.ShortUrlRedisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        String redirectUrl = shortUrlRedisService.findFullUrlByShortResource(shortResource).orElseThrow(NotFoundShortUrlException::new);
        shortUrlEventProducer.requestUrlSend(shortResource);
        httpServletResponse.sendRedirect(redirectUrl);
    }

    @ExceptionHandler(NotFoundShortUrlException.class)
    public String notFoundExceptionHandler() {
        return "redirect:/";
    }

}

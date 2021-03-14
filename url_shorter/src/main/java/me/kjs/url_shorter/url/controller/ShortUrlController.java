package me.kjs.url_shorter.url.controller;

import lombok.RequiredArgsConstructor;
import me.kjs.url_shorter.url.dto.ShortUrlForm;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.exception.NotFoundShortUrlException;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
import me.kjs.url_shorter.url.service.ShortUrlRedisService;
import me.kjs.url_shorter.url.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import static me.kjs.url_shorter.common.ThrowUtil.hasErrorsThrow;
import static me.kjs.url_shorter.url.UrlValidator.validation;

@Controller
@RequiredArgsConstructor
public class ShortUrlController {
    private final ShortUrlService shortUrlService;
    private final ShortUrlRedisService shortUrlRedisService;
    private final ShortUrlRepository shortUrlRepository;

    @GetMapping("/")
    public String index(@RequestParam(value = "shortResource", defaultValue = "") String shortResource,
                        Model model) {
        if (!shortResource.isEmpty()) {
            ShortUrl shortUrl = shortUrlRepository.findByShortResource(shortResource).orElseThrow(NotFoundShortUrlException::new);
            ShortUrlForm.Response.FindOne findOne = ShortUrlForm.Response.FindOne.shortUrlToFindOne(shortUrl);
            model.addAttribute("shortUrl", findOne);
            model.addAttribute("hostname", findOne);
        }
        return "th/index";
    }

    @PostMapping("/")
    public String index(ShortUrlForm.Request.Creator creator,
                        Model model) {
        ShortUrl shortUrl = shortUrlService.createShortUrl(creator);
        ShortUrlForm.Response.FindOne findOne = ShortUrlForm.Response.FindOne.shortUrlToFindOne(shortUrl);
        shortUrlRedisService.addShortResourceAndFullUrl(shortUrl.getShortResource(),shortUrl.getFullUrl());
        model.addAttribute("shortUrl", findOne);
        return "th/index";
    }

    @ExceptionHandler(NotFoundShortUrlException.class)
    public String notFoundExceptionHandler() {
        return "redirect:/";
    }
}


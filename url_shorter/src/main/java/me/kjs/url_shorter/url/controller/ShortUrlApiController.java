package me.kjs.url_shorter.url.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kjs.url_shorter.common.exception.ExceptionResponseDto;
import me.kjs.url_shorter.common.exception.StatusException;
import me.kjs.url_shorter.url.entity.ShortUrl;
import me.kjs.url_shorter.url.exception.NotFoundShortUrlException;
import me.kjs.url_shorter.url.service.ShortUrlRedisService;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
import me.kjs.url_shorter.url.service.ShortUrlService;
import me.kjs.url_shorter.url.dto.ShortUrlForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static me.kjs.url_shorter.common.ThrowUtil.hasErrorsThrow;
import static me.kjs.url_shorter.url.UrlValidator.validation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/url")
@Slf4j
public class ShortUrlApiController {
    private final ShortUrlService shortUrlService;
    private final ShortUrlRedisService shortUrlRedisService;
    private final ShortUrlRepository shortUrlRepository;


    @PostMapping
    public ResponseEntity<ShortUrlForm.Response.FindOne> createShortUrl(@RequestBody @Valid ShortUrlForm.Request.Creator creator, Errors errors) throws URISyntaxException {
        hasErrorsThrow(errors);
        validation(creator, errors);
        hasErrorsThrow(errors);
        ShortUrl shortUrl = shortUrlService.createShortUrl(creator);
        shortUrlRedisService.addShortResourceAndFullUrl(shortUrl.getShortResource(), shortUrl.getFullUrl());
        ShortUrlForm.Response.FindOne body = ShortUrlForm.Response.FindOne.shortUrlToFindOne(shortUrl);
        URI uri = new URI("/api/url/" + shortUrl.getShortResource());
        return ResponseEntity.created(uri).body(body);
    }


    @GetMapping("/{shortResource}")
    public ResponseEntity<ShortUrlForm.Response.FindOne> findByShortResource(@PathVariable String shortResource) {
        ShortUrl shortUrl = shortUrlRepository.findByShortResource(shortResource).orElseThrow(NotFoundShortUrlException::new);
        ShortUrlForm.Response.FindOne body = ShortUrlForm.Response.FindOne.shortUrlToFindOne(shortUrl);
        return ResponseEntity.ok(body);
    }

    @ExceptionHandler(StatusException.class)
    public ResponseEntity<ExceptionResponseDto> statusExceptionHandler(StatusException e) {
        ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.statusExceptionToExceptionResponse(e);
        HttpStatus httpStatus = e.getHttpMethod();
        return new ResponseEntity<>(exceptionResponseDto, httpStatus);
    }
}

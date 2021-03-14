package me.kjs.url_shorter.url;

import me.kjs.url_shorter.common.exception.ValidationException;
import me.kjs.url_shorter.url.controller.ShortUrlApiController;
import me.kjs.url_shorter.url.dto.ShortUrlForm;
import me.kjs.url_shorter.url.exception.NotFoundShortUrlException;
import me.kjs.url_shorter.url.repository.ShortUrlRepository;
import me.kjs.url_shorter.url.service.ShortUrlRedisService;
import me.kjs.url_shorter.url.service.ShortUrlService;
import me.kjs.url_shorter.url.type.Protocol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("URL API Controller 테스트")
class ShortUrlApiControllerTest {

    static ShortUrlRepository shortUrlRepository;

    static ShortUrlApiController shortUrlApiController;

    @BeforeAll
    static void setUp() {
        ShortUrlService shortUrlService = mock(ShortUrlService.class);
        ShortUrlRedisService shortUrlRedisService = mock(ShortUrlRedisService.class);
        shortUrlRepository = mock(ShortUrlRepository.class);
        shortUrlApiController = new ShortUrlApiController(shortUrlService, shortUrlRedisService, shortUrlRepository);
    }

    @Test
    @DisplayName("URL SHORTING 첫번째 에러 체크시 Validate Exception Throw 테스트")
    void createShortUrlErrorThrowFirstTest() {
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(true);
        ShortUrlForm.Request.Creator creator = ShortUrlForm.Request.Creator.builder()
                .host("github.com")
                .port(-1)
                .protocol(Protocol.HTTPS)
                .resource("/JsKim4")
                .build();
        ValidationException validationException = assertThrows(ValidationException.class, () -> {
            shortUrlApiController.createShortUrl(creator, errors);
        });
    }


    @Test
    @DisplayName("URL SHORTING 두번째 에러 체크시 Validate Exception Throw 테스트")
    void createShortUrlErrorThrowSecondTest() {
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(false).thenReturn(true);
        ShortUrlForm.Request.Creator creator = ShortUrlForm.Request.Creator.builder()
                .host("github.com")
                .port(0)
                .protocol(Protocol.HTTPS)
                .resource("JsKim4")
                .build();
        ValidationException validationException = assertThrows(ValidationException.class, () -> {
            shortUrlApiController.createShortUrl(creator, errors);
        });
    }

    @Test
    @DisplayName("FIND URL NOT FOUND EXCEPTION THROW 테스트")
    void findByShortResourceThrowNotFoundTest(){
        when(shortUrlRepository.findByShortResource(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundShortUrlException.class,() -> {
            shortUrlApiController.findByShortResource("B");
        });

    }

}
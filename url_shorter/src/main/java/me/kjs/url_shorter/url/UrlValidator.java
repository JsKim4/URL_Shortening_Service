package me.kjs.url_shorter.url;

import me.kjs.url_shorter.url.dto.ShortUrlForm;
import org.springframework.validation.Errors;

public interface UrlValidator {
    static void validation(ShortUrlForm.Request.Creator creator, Errors errors) {
        if (creator.getResource() != null && !creator.getResource().startsWith("/")) {
            errors.rejectValue("resource","wrong value","리소스는 null 이거나 / 로 시작해야합니다.");
        }
    }
}

package me.kjs.url_shorter.common;

import me.kjs.url_shorter.common.exception.ValidationException;
import org.springframework.validation.Errors;

public interface ThrowUtil {
    static void hasErrorsThrow(Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
    }
}

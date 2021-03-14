package me.kjs.url_shorter.common.exception;

import org.springframework.validation.Errors;

public class ValidationException extends StatusException {
    private Errors errors;

    public ValidationException(Errors errors) {
        super(ResponseStatus.VALIDATION_ERROR);
        this.errors = errors;
    }

    @Override
    public Object getDetailMessage() {
        return errors;
    }
}

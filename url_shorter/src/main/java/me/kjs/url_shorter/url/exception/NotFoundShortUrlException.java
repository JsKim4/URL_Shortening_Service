package me.kjs.url_shorter.url.exception;

import me.kjs.url_shorter.common.exception.ResponseStatus;
import me.kjs.url_shorter.common.exception.StatusException;

public class NotFoundShortUrlException extends StatusException {
    public NotFoundShortUrlException() {
        super(ResponseStatus.NOT_FOUND_SHORT_URL);
    }
}

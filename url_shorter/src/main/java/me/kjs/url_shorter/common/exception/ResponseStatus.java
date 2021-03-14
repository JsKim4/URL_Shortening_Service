package me.kjs.url_shorter.common.exception;

import lombok.RequiredArgsConstructor;
import me.kjs.url_shorter.common.EnumType;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum ResponseStatus implements EnumType {
    NOT_FOUND_SHORT_URL(4001, "지정된 조건으로 URL을 찾을 수 없습니다.", "지정된 조건으로 URL을 찾을 수 없는 경우", NOT_FOUND),
    VALIDATION_ERROR(404, "입력값이 잘못되었습니다.", "입력값이 잘못되었을 경우", BAD_REQUEST);
    private final int status;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;

    @Override
    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpMethod() {
        return httpStatus;
    }
}

package me.kjs.url_shorter.common.exception;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponseDto<T> {
    private String error;
    private T message;
    private String description;

    public static ExceptionResponseDto statusExceptionToExceptionResponse(StatusException exception) {
        return ExceptionResponseDto.builder()
                .description(exception.getDescription())
                .error(exception.getStatus())
                .message(exception.getDetailMessage())
                .build();
    }
}

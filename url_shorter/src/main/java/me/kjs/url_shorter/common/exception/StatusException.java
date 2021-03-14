package me.kjs.url_shorter.common.exception;

import org.springframework.http.HttpStatus;

public class StatusException extends RuntimeException{
    private final ResponseStatus responseStatus;

    public StatusException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.responseStatus = exceptionStatus;
    }


    public HttpStatus getHttpMethod() {
        return responseStatus.getHttpMethod();
    }

    public String getDescription() {
        return responseStatus.getDescription();
    }

    public String getStatus() {
        return String.valueOf(responseStatus.getStatus());
    }

    public Object getDetailMessage(){
        return getMessage();
    }
}

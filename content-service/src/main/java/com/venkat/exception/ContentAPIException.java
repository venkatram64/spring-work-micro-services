package com.venkat.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ContentAPIException {

    private final String errorMsg;
    //private final Throwable throwable;
    private final HttpStatus statusCode;
    private final ZonedDateTime timestamp;

    public ContentAPIException(String errorMsg, /*Throwable throwable,*/ HttpStatus statusCode, ZonedDateTime timestamp) {
        this.errorMsg = errorMsg;
        //this.throwable = throwable;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    /*public Throwable getThrowable() {
        return throwable;
    }*/

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}

package com.venkat.exception;

public class ContentAPIRequestException extends RuntimeException{
    public ContentAPIRequestException(String message) {
        super(message);
    }

    public ContentAPIRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

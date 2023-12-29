package com.venkat.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ContentGlobalAdviceHandler {

    @ExceptionHandler(value = {ContentAPIRequestException.class})
    public ResponseEntity<Object> handleEmptyInput(ContentAPIRequestException exception){
        ContentAPIException apiException = new ContentAPIException(exception.getMessage(), exception, HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoExistingObject(NoSuchElementException exception){
        return new ResponseEntity<String>("No object found, please provide correct one", HttpStatus.NOT_FOUND);
    }

}

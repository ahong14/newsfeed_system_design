package com.newsfeed_system_design.post_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class PostResponseEntityExceptionHandler {
    // return 400, illegal arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentExceptions(Exception ex, WebRequest request) {
        PostErrorDetails errorDetails = new PostErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    };

    // return 404, elements not found
    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementExceptions(Exception ex, WebRequest request) {
        PostErrorDetails errorDetails = new PostErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    };


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        PostErrorDetails errorDetails = new PostErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    };
}

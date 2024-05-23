package com.newsfeed_system_design.newsfeed.exception;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class NewsfeedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    // return 400, illegal arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentExceptions(Exception ex, WebRequest request) {
        NewsfeedErrorDetails errorDetails = new NewsfeedErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    };

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleDeniedAccessException(Exception ex, WebRequest request) {
        NewsfeedErrorDetails errorDetails = new NewsfeedErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    };

    @ExceptionHandler(MalformedJwtException.class)
    public final ResponseEntity<Object> handleMalformedJwtException(Exception ex, WebRequest request) {
        NewsfeedErrorDetails errorDetails = new NewsfeedErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    };

    // return 404, elements not found
    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementExceptions(Exception ex, WebRequest request) {
        NewsfeedErrorDetails errorDetails = new NewsfeedErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    };

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleNoUsernameElementExceptions(Exception ex, WebRequest request) {
        NewsfeedErrorDetails errorDetails = new NewsfeedErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    };

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        NewsfeedErrorDetails errorDetails = new NewsfeedErrorDetails(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    };

}

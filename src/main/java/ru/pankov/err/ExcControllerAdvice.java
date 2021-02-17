package ru.pankov.err;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleRandomException(RandomException e) {
        AppError appError = new AppError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return new ResponseEntity<>(appError, HttpStatus.SERVICE_UNAVAILABLE);
    }
}

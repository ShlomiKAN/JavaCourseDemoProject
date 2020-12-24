package com.example.demo.controllers.advice;

import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class RESTControllerAdvice {

    @ExceptionHandler(value = { InvalidEntityException.class})
    public ResponseEntity<?> handleInvalidEntityException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto("Invalid entity",ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(value = { InvalidOperationException.class})
    public ResponseEntity<?> handleInvalidOperationException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto("Invalid entity",ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }
}

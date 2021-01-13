package com.example.redditclone.Exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.annotation.HandlesTypes;
import javax.servlet.annotation.HttpConstraint;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler {
    //Handel Validater Error
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolation(Exception e, WebRequest req) {
        ExceptionDetail detail = new ExceptionDetail(new Date(),"Validation Faild",e.getLocalizedMessage() );
        return new ResponseEntity<>(detail,HttpStatus.BAD_REQUEST);
    }
}

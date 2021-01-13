package com.example.redditclone.Exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;


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

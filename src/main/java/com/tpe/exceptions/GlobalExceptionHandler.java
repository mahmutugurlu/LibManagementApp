package com.tpe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//uygulamanın tümünde meydana gelen hataları global düzeyde ele alamak için kullanılır
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFoundException ex,
                                                                     HttpServletRequest request){


        Map<String,Object> body=new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error message",ex.getMessage());
        body.put("path",request.getRequestURI());
        body.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);//404



    }






}

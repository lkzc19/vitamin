package org.example.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<String> handleBizException(BizException e) {
        return new ResponseEntity<>("boom!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("boom!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

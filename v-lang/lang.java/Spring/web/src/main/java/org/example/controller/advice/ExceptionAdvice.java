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
    public ResponseEntity<Map<String, Object>> handleBizException(BizException e) {
        log.error("ExceptionAdvice", e);
        Map<String, Object> body = new HashMap<>();
        body.put("msg", e.getMessage());
        return new ResponseEntity<>(body, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        log.error("ExceptionAdvice", e);
        Map<String, Object> body = new HashMap<>();
        body.put("msg", "服务器错误");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

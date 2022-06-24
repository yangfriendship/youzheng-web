package me.youzheng.userservice.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, Object>> handlerUserException(UserException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(result);
    }

}
package me.youzheng.userservice.exception;

import java.util.Map;
import me.youzheng.common.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, Object>> handlerUserException(UserException exception) {
        Map<String, Object> result = exception.getErrorMap();
        return ResponseEntity.status(exception.getStatus()).body(result);
    }

}
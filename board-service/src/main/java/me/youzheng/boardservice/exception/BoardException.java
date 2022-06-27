package me.youzheng.boardservice.exception;

import me.youzheng.common.exception.AbstractException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class BoardException extends AbstractException {

    public BoardException(String message, int status) {
        super(message, status);
    }

    public BoardException(String message, HttpStatus httpStatus) {
        super(message, httpStatus.value());
    }

    public BoardException(String message) {
        super(message);
    }

    public BoardException(Errors errors) {
        super(errors);
    }

    public BoardException(Errors errors, int status) {
        super(errors, status);
    }

    public BoardException(Errors errors, HttpStatus httpStatus) {
        super(errors, httpStatus.value());
    }

}
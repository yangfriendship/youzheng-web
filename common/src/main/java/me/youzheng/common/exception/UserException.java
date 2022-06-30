package me.youzheng.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class UserException extends AbstractException {

    public UserException() {
        super("예외가 발생했습니다. 관리자에게 문의하세요.");
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, int status) {
        super(message, status);
    }

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus.value());
    }

    public UserException(Errors errors) {
        super(errors);
    }
}
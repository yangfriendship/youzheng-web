package me.youzheng.userservice.exception;

import lombok.RequiredArgsConstructor;

public class UserException extends RuntimeException{

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

}

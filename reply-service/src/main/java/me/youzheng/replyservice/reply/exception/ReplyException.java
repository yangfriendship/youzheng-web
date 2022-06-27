package me.youzheng.replyservice.reply.exception;

import me.youzheng.common.exception.AbstractException;
import org.springframework.validation.Errors;

public class ReplyException extends AbstractException {

    public ReplyException(String message, int status) {
        super(message, status);
    }

    public ReplyException(String message) {
        super(message);
    }

    public ReplyException(Errors errors) {
        super(errors);
    }

    public ReplyException(Errors errors, int status) {
        super(errors, status);
    }

}
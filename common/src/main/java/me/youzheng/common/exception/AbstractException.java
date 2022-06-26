package me.youzheng.common.exception;

public class AbstractException extends RuntimeException {

    private final int status;

    public AbstractException(String message, int status) {
        super(message);
        this.status = status;
    }

    public AbstractException(String message) {
        super(message);
        this.status = 400;
    }

    public final int getStatus() {
        return status;
    }

}
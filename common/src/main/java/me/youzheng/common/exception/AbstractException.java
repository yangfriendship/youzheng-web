package me.youzheng.common.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.youzheng.common.domain.QAbstractBaseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class AbstractException extends RuntimeException {

    private final int status;
    private Errors errors;

    public AbstractException(String message, int status) {
        super(message);
        this.status = status;
    }

    public AbstractException(String message) {
        super(message);
        this.status = 400;
    }

    public AbstractException(Errors errors) {
        this.status = 400;
        this.errors = errors;
    }

    public AbstractException(Errors errors, int status) {
        this.status = status;
        this.errors = errors;
    }

    public final int getStatus() {
        return status;
    }

    public Map<String, Object> getErrorMap() {
        Map<String, Object> result = new HashMap<>();
        if (this.errors == null) {
            result.put("message", getMessage());
        } else {
            final List<FieldError> fieldErrors = this.errors.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                result.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return result;
    }

}
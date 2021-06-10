package ru.user.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * исключение, возникающее в бизнес-логике
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 5730402622800221111L;

    @Getter
    private List<ErrorDetailItem> details;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, List<ErrorDetailItem> details) {
        super(message);
        this.details = details;
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Builder
    public BusinessException(String message, Throwable throwable, List<ErrorDetailItem> details) {
        super(message, throwable);
        this.details = details;
    }

}


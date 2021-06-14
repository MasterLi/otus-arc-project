package ru.user.exception;


public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 6063307257722352545L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

}

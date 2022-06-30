package com.company.Exceptions;

public class IncorrectOperationException extends IllegalArgumentException {
    public IncorrectOperationException() {
    }

    public IncorrectOperationException(String message) {
        super(message);
    }

    public IncorrectOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectOperationException(Throwable cause) {
        super(cause);
    }
}

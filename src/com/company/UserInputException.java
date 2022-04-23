package com.company;

public class UserInputException extends Exception {
    public UserInputException() {
    }
    public UserInputException(String message) {
        super(message);
    }

    public UserInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInputException(Throwable cause) {
        super(cause);
    }

    public UserInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

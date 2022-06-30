package com.company.Exceptions;

public class UserInputException extends IllegalArgumentException {
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



}

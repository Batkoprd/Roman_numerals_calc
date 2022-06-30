package com.company.Exceptions;

public class IncorrectRomanNumException extends IllegalArgumentException{
    public IncorrectRomanNumException() {
    }

    public IncorrectRomanNumException(String message) {
        super(message);
    }

    public IncorrectRomanNumException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectRomanNumException(Throwable cause) {
        super(cause);
    }
}

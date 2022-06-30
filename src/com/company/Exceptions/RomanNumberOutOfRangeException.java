package com.company.Exceptions;

public class RomanNumberOutOfRangeException extends IllegalArgumentException {

    public RomanNumberOutOfRangeException() {
    }

    public RomanNumberOutOfRangeException(String s) {
        super(s);
    }

    public RomanNumberOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RomanNumberOutOfRangeException(Throwable cause) {
        super(cause);
    }
}

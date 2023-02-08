package com.libraryApplication.library.customexception;

public class NullValueException extends RuntimeException {

    private String message;
    public NullValueException(String message) {
        super(message);
        this.message=message;

    }

    public NullValueException() {

    }


}

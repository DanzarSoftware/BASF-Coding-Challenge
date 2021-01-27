package com.alvarozarza.basf.exception;

public class UnzipException extends RuntimeException {
    private static final String message = "There was an error unzipping the file:  ";

    public UnzipException() {
        super(message);
    }

    public UnzipException(String file, String exceptionMessage) {
        super(message + file + " " + exceptionMessage);
    }

}
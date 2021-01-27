package com.alvarozarza.basf.exception;

public class XmlDocumentException extends RuntimeException {
    private static final String message = "There was an error processing ";

    public XmlDocumentException() {
        super(message);
    }

    public XmlDocumentException(String file, String exceptionMessage) {
        super(message + file + " " + exceptionMessage);
    }

}
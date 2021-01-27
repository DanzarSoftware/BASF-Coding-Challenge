package com.alvarozarza.basf.exception;

public class MongoDbException extends RuntimeException {
    private static final String message = "There was an error in a MongoDB operation ";

    public MongoDbException() {
        super(message);
    }

    public MongoDbException(String e) {
        super(message + e);
    }

}
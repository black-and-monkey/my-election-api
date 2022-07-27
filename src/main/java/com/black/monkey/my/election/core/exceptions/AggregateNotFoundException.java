package com.black.monkey.my.election.core.exceptions;

public class AggregateNotFoundException extends RuntimeException{

    public AggregateNotFoundException(String message) {
        super(message);
    }
}

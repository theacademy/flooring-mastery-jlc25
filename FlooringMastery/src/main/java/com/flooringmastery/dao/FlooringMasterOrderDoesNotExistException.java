package com.flooringmastery.dao;

public class FlooringMasterOrderDoesNotExistException extends RuntimeException {
    public FlooringMasterOrderDoesNotExistException(String message) {
        super(message);
    }

    public FlooringMasterOrderDoesNotExistException(String message, Throwable cause){
        super(message, cause);
    }
}

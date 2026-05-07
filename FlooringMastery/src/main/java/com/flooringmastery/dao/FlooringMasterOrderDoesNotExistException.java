package com.flooringmastery.dao;

public class FlooringMasterOrderDoesNotExistException extends RuntimeException {
    public FlooringMasterOrderDoesNotExistException(String message) {
        super(message);
    }
}

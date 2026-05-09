package com.flooringmastery.service;

public class FlooringMasterTaxNotFoundException extends RuntimeException {
    // Exception for when we can't find tax in file
    public FlooringMasterTaxNotFoundException(String message) {
        super(message);
    }
}

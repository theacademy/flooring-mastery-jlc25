package com.flooringmastery.dao;

import com.flooringmastery.dto.Tax;

import java.io.FileNotFoundException;
import java.util.List;

public interface TaxDao {
    public List<Tax> getAllTaxes() throws FileNotFoundException;
    public Tax getTaxByState(String state) throws FileNotFoundException;
}

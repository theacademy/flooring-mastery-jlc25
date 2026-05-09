package com.flooringmastery.service;

import com.flooringmastery.dao.TaxDao;
import com.flooringmastery.dao.TaxDaoImpl;
import com.flooringmastery.dto.Tax;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FlooringMasteryTaxDaoStubImpl implements TaxDao {
    public Tax onlyTax;

    public FlooringMasteryTaxDaoStubImpl(String taxFile){
        TaxDao taxDao = new TaxDaoImpl(taxFile);

        onlyTax = new Tax();
        onlyTax.setStateAbbreviation("CA");
        onlyTax.setStateName("California");
        onlyTax.setTaxRate(new BigDecimal("25.00"));
    }

    @Override
    public List<Tax> getAllTaxes() throws FileNotFoundException {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(onlyTax);
        return taxList;
    }

    @Override
    public Tax getTaxByState(String state) throws FileNotFoundException {
        if (state.equals(onlyTax.getStateAbbreviation())) {
            return onlyTax;
        } else {
            return null;
        }
    }
}

package com.flooringmastery.dao;

import com.flooringmastery.dto.Tax;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class FlooringMasteryTaxDaoTest {
    TaxDao taxDao;

    @BeforeEach
    public void setUp(){
        String testFile = "SampleFileData/Data/Taxes.txt";
        taxDao = new TaxDaoImpl(testFile);
    }


    @Test
    public void checkValidTax () throws Exception{
        // Tax parameters
        String stateAbbreviation = "WA";
        String stateName = "Washington";
        BigDecimal taxRate = new BigDecimal("9.25");

        Tax tax = new Tax();
        tax.setStateAbbreviation(stateAbbreviation);
        tax.setStateName(stateName);
        tax.setTaxRate(taxRate);

        Tax findTax = taxDao.getTaxByState(stateName);

        assertEquals(tax, findTax);
    }

    @Test
    public void checkInvalidTax() throws Exception{
        // Tax parameters
        String stateAbbreviation = "FL";
        String stateName = "Florida";
        BigDecimal taxRate = new BigDecimal("9.25");

        Tax tax = new Tax();
        tax.setStateAbbreviation(stateAbbreviation);
        tax.setStateName(stateName);
        tax.setTaxRate(taxRate);

        Tax findTax = taxDao.getTaxByState(stateName);

        // Should return null as there is no state with that name
        assertNull(findTax);
    }
}

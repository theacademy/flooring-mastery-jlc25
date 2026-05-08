package com.flooringmastery.dao;

import com.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TaxDaoImpl implements TaxDao{
    private final String TAX_FILE;
    public static final String DELIMITER = ",";
    private HashMap<String, Tax> taxes;

    public TaxDaoImpl(String taxFile){
        TAX_FILE = taxFile;
        taxes = new HashMap<>();
    }

    @Override
    public List<Tax> getAllTaxes() throws FileNotFoundException {
        loadTaxList();
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Tax getTaxByState(String stateAbbreviation) throws FileNotFoundException {
        loadTaxList();
        return taxes.get(stateAbbreviation);
    }

    private void loadTaxList() throws FileNotFoundException {

        try{
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));

            // Skip header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Only un-marshall after the header-line
            while(scanner.hasNextLine()){
                String currentLine = scanner.nextLine();

                // Process the string -> Tax & add to our taxes
                Tax tax = unmarshallTax(currentLine);
                taxes.put(tax.getStateAbbreviation(), tax);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(TAX_FILE + " was not found");
        }
    }

    private Tax unmarshallTax(String stateLine){
        // Split line and load tax with tokens
        String[] tokens = stateLine.split(DELIMITER);
        Tax tax = new Tax();

        tax.setStateAbbreviation(tokens[0]);
        tax.setStateName(tokens[1]);
        tax.setTaxRate(new BigDecimal(tokens[2]));

        return tax;
    }
}

package com.flooringmastery.dao;

import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDao{
    private final String PRODUCT_FILE;
    public static final String DELIMITER = ",";
    HashMap<String, Product> products;

    ProductDaoImpl(String productFile){
        PRODUCT_FILE = productFile;
        products = new HashMap<>();
    }

    @Override
    public List<Product> getAllProducts() throws FileNotFoundException {
        loadProductList();
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProduct(String productName) throws FileNotFoundException {
        loadProductList();
        return products.get(productName);
    }

    private void loadProductList() throws FileNotFoundException {

        try{
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));

            // Skip header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Only un-marshall after the header-line
            while(scanner.hasNextLine()){
                String currentLine = scanner.nextLine();

                // Process the string -> Tax & add to our taxes
                Product product = unmarshallProduct(currentLine);
                products.put(product.getProductType(), product);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(PRODUCT_FILE + " was not found");
        }
    }

    private Product unmarshallProduct(String productLine){
        // Split line and load tax with tokens
        Product product = new Product();
        String[] tokens = productLine.split(DELIMITER);

        product.setProductType(tokens[0]);
        product.setCostPerSquareFoot(new BigDecimal(tokens[1]));
        product.setLaborCostPerSquareFoot(new BigDecimal(tokens[2]));

        return product;
    }
}

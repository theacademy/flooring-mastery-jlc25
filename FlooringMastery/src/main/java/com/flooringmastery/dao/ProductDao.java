package com.flooringmastery.dao;

import com.flooringmastery.dto.Product;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public interface ProductDao {
    public HashMap<String, Product> getAllProducts() throws FileNotFoundException;
    public Product getProduct(String productName) throws FileNotFoundException;

}

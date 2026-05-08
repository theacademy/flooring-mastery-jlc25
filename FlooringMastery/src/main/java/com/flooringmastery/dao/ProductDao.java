package com.flooringmastery.dao;

import com.flooringmastery.dto.Product;

import java.io.FileNotFoundException;
import java.util.List;

public interface ProductDao {
    public List<Product> getAllProducts() throws FileNotFoundException;
    public Product getProduct(String productName) throws FileNotFoundException;

}

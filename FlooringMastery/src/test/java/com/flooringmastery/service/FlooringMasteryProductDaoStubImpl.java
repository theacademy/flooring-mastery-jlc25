package com.flooringmastery.service;

import com.flooringmastery.dao.FlooringMasteryProductDaoTest;
import com.flooringmastery.dao.ProductDao;
import com.flooringmastery.dao.ProductDaoImpl;
import com.flooringmastery.dto.Product;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;

public class FlooringMasteryProductDaoStubImpl implements ProductDao {
    public Product onlyProduct;


    public FlooringMasteryProductDaoStubImpl(String productFile){
        ProductDao productDao = new ProductDaoImpl(productFile);

        onlyProduct = new Product();
        onlyProduct.setProductType("Tile");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
    }

    @Override
    public HashMap<String, Product> getAllProducts() throws FileNotFoundException {
        HashMap<String, Product> products = new HashMap<>();
        products.put(onlyProduct.getProductType(), onlyProduct);
        return products;
    }

    @Override
    public Product getProduct(String productName) throws FileNotFoundException {

        if (productName.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }
}

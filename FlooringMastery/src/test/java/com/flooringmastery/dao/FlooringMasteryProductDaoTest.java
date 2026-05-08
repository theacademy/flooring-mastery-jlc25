package com.flooringmastery.dao;

import com.flooringmastery.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FlooringMasteryProductDaoTest {
    ProductDao productDao;

    @BeforeEach
    public void setUp(){
        String testFile = "SampleFileData/Data/Products.txt";
        productDao = new ProductDaoImpl(testFile);
    }


    @Test
    public void checkValidProduct () throws FileNotFoundException {
        // Product parameters
        String productType = "Carpet";
        BigDecimal costPerSquareFoot = new BigDecimal("2.25");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("2.10");

        Product product = new Product();
        product.setProductType(productType);
        product.setCostPerSquareFoot(costPerSquareFoot);
        product.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        Product findProduct = productDao.getProduct(productType);

        assertEquals(product, findProduct);
    }

    @Test
    public void checkInvalidProduct() throws FileNotFoundException{
        // Product parameters
        String productType = "Marble";
        BigDecimal costPerSquareFoot = new BigDecimal("90.20");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("30.21");

        Product product = new Product();
        product.setProductType(productType);
        product.setCostPerSquareFoot(costPerSquareFoot);
        product.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        Product findProduct = productDao.getProduct(productType);

        assertNull(findProduct);
    }

}
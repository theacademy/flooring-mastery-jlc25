package com.flooringmastery.service;

import com.flooringmastery.dto.Order;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryServiceTest {
    FlooringMasterServiceLayer service;

    public FlooringMasteryServiceTest() throws FileNotFoundException {
        FlooringMasteryOrderDaoStubImpl orderDaoStub = new FlooringMasteryOrderDaoStubImpl("SampleFileData/Orders");
        FlooringMasteryProductDaoStubImpl productDaoStub = new FlooringMasteryProductDaoStubImpl("SampleFileData/Data/Products.txt");
        FlooringMasteryTaxDaoStubImpl taxDaoStub = new FlooringMasteryTaxDaoStubImpl("SampleFileData/Data/Products.txt");


        // OrderDao orderDao = new OrderDaoImpl("SampleFileData/Orders");
        // TaxDao taxDao = new TaxDaoImpl("SampleFileData/Data/Taxes.txt");
        // ProductDao productDao = new ProductDaoImpl("SampleFileData/Data/Products.txt");

        service = new FlooringMasterServiceLayerImpl(orderDaoStub, taxDaoStub, productDaoStub);

    }

    // Add valid order
    @Test
    public void addValidOrder(){
        // ARRANGE
        LocalDate date = LocalDate.parse("03052027", DateTimeFormatter.ofPattern("MMddyyyy"));

        Order order = new Order();
        order.setOrderNumber(2);
        order.setCustomerName("Sabrina");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("515"));
        order.setLaborCost(new BigDecimal("475"));
        order.setTax(new BigDecimal("247.5"));
        order.setTotal(new BigDecimal("1237.5"));

        // ACT
        try {
            service.addOrder(date, order);
        } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown but something went wrong: " + e.getMessage());
        }
    }

    // // Add order with invalid date
    // @Test
    // public void addOrderWithInvalidDate(){
    //     // ARRANGE
    //     LocalDate date = LocalDate.parse("03052017", DateTimeFormatter.ofPattern("MMddyyyy"));
    //
    //     Order order = new Order();
    //     order.setOrderNumber(2);
    //     order.setCustomerName("Sabrina");
    //     order.setState("CA");
    //     order.setTaxRate(new BigDecimal("25.00"));
    //     order.setProductType("Wood");
    //     order.setArea(new BigDecimal("100"));
    //     order.setCostPerSquareFoot(new BigDecimal("5.15"));
    //     order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
    //     order.setMaterialCost(new BigDecimal("515"));
    //     order.setLaborCost(new BigDecimal("475"));
    //     order.setTax(new BigDecimal("247.5"));
    //     order.setTotal(new BigDecimal("1237.5"));
    //
    //     // ACT
    //     try {
    //         service.addOrder(date, order);
    //         // ASSERT
    //         fail("Test should not pass when the date is invalid.");
    //     } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
    //         return;
    //     }
    // }
    //
    // // Add order with invalid name
    // @Test
    // public void addOrderWithInvalidName(){
    //     // ARRANGE
    //     LocalDate date = LocalDate.parse("03052027", DateTimeFormatter.ofPattern("MMddyyyy"));
    //
    //     Order order = new Order();
    //     order.setOrderNumber(2);
    //     order.setCustomerName("PERSON_WITH_A_CRAZY_NAME!!!!!!");
    //     order.setState("CA");
    //     order.setTaxRate(new BigDecimal("25.00"));
    //     order.setProductType("Wood");
    //     order.setArea(new BigDecimal("100"));
    //     order.setCostPerSquareFoot(new BigDecimal("5.15"));
    //     order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
    //     order.setMaterialCost(new BigDecimal("515"));
    //     order.setLaborCost(new BigDecimal("475"));
    //     order.setTax(new BigDecimal("247.5"));
    //     order.setTotal(new BigDecimal("1237.5"));
    //     boolean success = false;
    //
    //     // ACT
    //     try {
    //         service.addOrder(date, order);
    //         // ASSERT
    //         fail("Test should not pass when the name is invalid.");
    //     } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
    //         return;
    //     }
    // }

    // Add order with invalid tax
    @Test
    public void addOrderWithInvalidTax(){
        // ARRANGE
        LocalDate date = LocalDate.parse("03052027", DateTimeFormatter.ofPattern("MMddyyyy"));

        Order order = new Order();
        order.setOrderNumber(2);
        order.setCustomerName("Shelly");
        order.setState("RANDOMSTATE");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("515"));
        order.setLaborCost(new BigDecimal("475"));
        order.setTax(new BigDecimal("247.5"));
        order.setTotal(new BigDecimal("1237.5"));
        boolean success = false;

        // ACT
        try {
            service.addOrder(date, order);
            // ASSERT
            fail("Test should not pass when the state is invalid.");
        } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
            return;
        }
    }
    // Add order with invalid product
    @Test
    public void addOrderWithInvalidProduct(){
        // ARRANGE
        LocalDate date = LocalDate.parse("03052027", DateTimeFormatter.ofPattern("MMddyyyy"));

        Order order = new Order();
        order.setOrderNumber(2);
        order.setCustomerName("Everything Inc.");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Jelly");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("515"));
        order.setLaborCost(new BigDecimal("475"));
        order.setTax(new BigDecimal("247.5"));
        order.setTotal(new BigDecimal("1237.5"));
        boolean success = false;

        // ACT
        try {
            service.addOrder(date, order);
            // ASSERT
            fail("Test should not pass when the product is invalid.");
        } catch (FileNotFoundException | FlooringMasterProductNotFoundException e) {
            return;
        }
    }

    // // Add order with invalid area
    // @Test
    // public void addOrderWithInvalidArea(){
    //     // ARRANGE
    //     LocalDate date = LocalDate.parse("03052027", DateTimeFormatter.ofPattern("MMddyyyy"));
    //
    //     Order order = new Order();
    //     order.setOrderNumber(2);
    //     order.setCustomerName("BigCorp LLC");
    //     order.setState("CA");
    //     order.setTaxRate(new BigDecimal("25.00"));
    //     order.setProductType("Wood");
    //     order.setArea(new BigDecimal("100"));
    //     order.setCostPerSquareFoot(new BigDecimal("5.15"));
    //     order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
    //     order.setMaterialCost(new BigDecimal("515"));
    //     order.setLaborCost(new BigDecimal("475"));
    //     order.setTax(new BigDecimal("247.5"));
    //     order.setTotal(new BigDecimal("1237.5"));
    //     boolean success = false;
    //
    //     // ACT
    //     try {
    //         service.addOrder(date, order);
    //         // ASSERT
    //         fail("Test should not pass when the area is invalid.");
    //     } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
    //         return;
    //     }
    // }

    // Edit valid order
    // Edit order with invalid date
    // Edit order with invalid order number

    // Remove order
    // Remove order with invalid date
    // Remove order with invalid order number

    // Export all orders

}

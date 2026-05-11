package com.flooringmastery.service;

import com.flooringmastery.dao.FlooringMasterOrderDoesNotExistException;
import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryServiceTest {
    FlooringMasterServiceLayer service;

    public FlooringMasteryServiceTest() throws FileNotFoundException {
        FlooringMasteryOrderDaoStubImpl orderDaoStub = new FlooringMasteryOrderDaoStubImpl("TestSampleFileData/Orders");
        FlooringMasteryProductDaoStubImpl productDaoStub = new FlooringMasteryProductDaoStubImpl("SampleFileData/Data/Products.txt");
        FlooringMasteryTaxDaoStubImpl taxDaoStub = new FlooringMasteryTaxDaoStubImpl("SampleFileData/Data/Taxes.txt");

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
        order.setProductType("Tile");
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
        order.setProductType("Tile");
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

    // Edit valid order date and number
    @Test
    public void editValidOrder(){
        // ARRANGE
        LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));

        // Edited order
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Sabrina");
        order.setState("TX");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));

        // ACT
        // Find original order properties and store a deep copy of the order
        Order originalOrder = service.getOrder(date, 1);
        Order originalOrderCopy = new Order();

        originalOrderCopy.setOrderNumber(originalOrder.getOrderNumber());
        originalOrderCopy.setCustomerName(originalOrder.getCustomerName());
        originalOrderCopy.setState(originalOrder.getState());
        originalOrderCopy.setTaxRate(originalOrder.getTaxRate());
        originalOrderCopy.setArea(originalOrder.getArea());
        originalOrderCopy.setCostPerSquareFoot(originalOrder.getCostPerSquareFoot());
        originalOrderCopy.setLaborCostPerSquareFoot(originalOrder.getLaborCostPerSquareFoot());
        originalOrderCopy.setMaterialCost(originalOrder.getMaterialCost());
        originalOrderCopy.setLaborCost(originalOrder.getLaborCost());
        originalOrderCopy.setTax(originalOrder.getTax());
        originalOrderCopy.setTotal(originalOrder.getTotal());


        service.editOrder(date, order);

        // Order's customer name should be different after edit
        Order newOrder = service.getOrder(date, 1);

        // ASSERT
        // The original and edited order should not be equal
        assertNotEquals(originalOrderCopy, newOrder);
    }

    @Test
    public void validateValidTaxAndValidProduct(){
        // ARRANGE
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));

        // ACT
        try {
            service.validateTax(order);
            service.validateProduct(order);
        } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown but something went wrong: " + e.getMessage());
        }
    }

    @Test
    public void validateInvalidTax(){
        // ARRANGE
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Sabrina");
        order.setState("CALI");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));

        // ACT
        try {
            service.validateTax(order);
            // ASSERT
            fail("Test should not pass when the state is invalid.");
        } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
            return;
        }
    }

    @Test
    public void validateInvalidProduct(){
        // ARRANGE
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Sabrina");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Marble");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));

        // ACT
        try {
            service.validateProduct(order);
            // ASSERT
            fail("Test should not pass when the product is invalid.");
        } catch (FileNotFoundException | FlooringMasterProductNotFoundException e) {
            return;
        }
    }

    // Edit order with invalid tax
    @Test
    public void getAllOrdersForValidDate(){
        // ARRANGE
        LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));

        // ACT
        List<Order> orderList = service.getAllOrdersForDate(date);

        // ASSERT
        // There should only be one order for the given date
        assertEquals(1, orderList.size());
    }

    // Remove order
    // Attempt to remove order
    @Test
    public void removeValidOrder(){
        // ARRANGE
        LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));
        Integer orderID = 1;

        // ACT
        Order removedOrder = service.removeOrder(date, orderID);

        // ASSERT
        // Order should exist since it's valid
        assertNotNull(removedOrder);

    }

    // Remove order with invalid date
    @Test
    public void removeInvalidOrderDate(){
        // ARRANGE
        LocalDate date = LocalDate.parse("06012023", DateTimeFormatter.ofPattern("MMddyyyy"));
        Integer orderID = 1;

        try {
            // ACT
            service.removeOrder(date, orderID);
            // ASSERT
            fail("Test should not pass when the order number is invalid.");
        } catch (FlooringMasterOrderDoesNotExistException e) {
            return;
        }
    }

    // Remove order with invalid order number
    @Test
    public void removeInvalidOrderNumber(){
        // ARRANGE
        LocalDate date = LocalDate.parse("06012023", DateTimeFormatter.ofPattern("MMddyyyy"));
        Integer orderID = 5;

        Order order = null;

        try {
            // ACT
            order = service.removeOrder(date, orderID);
            // ASSERT
            fail("Test should not pass when the order number is invalid.");
        } catch (FlooringMasterOrderDoesNotExistException e) {
            return;
        }
    }

    @Test
    public void getValidTax() throws FileNotFoundException {
        // ARRANGE
        Tax tax = new Tax();
        tax.setStateAbbreviation("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));

        // ACT
        Tax result = service.getTax(tax.getStateAbbreviation());

        // ASSERT
        assertEquals(tax, result);
    }

    @Test
    public void getInvalidTax() throws FileNotFoundException {
        // ARRANGE
        Tax tax = new Tax();
        tax.setStateAbbreviation("FL");
        tax.setStateName("Florida");
        tax.setTaxRate(new BigDecimal("5.00"));

        // ACT
        Tax result = service.getTax(tax.getStateAbbreviation());

        // ASSERT
        // There should be no Tax found
        assertNull(result);
    }

    @Test
    public void getValidProduct() throws FileNotFoundException {
        // ARRANGE
        Product product = new Product();
        product.setProductType("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        // ACT
        Product result = service.getProduct(product.getProductType());

        // ASSERT
        assertEquals(product, result);
    }

    @Test
    public void getInvalidProduct() throws FileNotFoundException {
        // ARRANGE
        Product product = new Product();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));

        // ACT
        Product result = service.getProduct(product.getProductType());

        // ASSERT
        assertNull(result);
    }
}

package com.flooringmastery.dao;

import com.flooringmastery.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlooringMasteryOrderDaoTest {
    OrderDao orderDao;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        String orderDirectory = "SampleFileData/Orders";
        orderDao = new OrderDaoImpl(orderDirectory);
    }

    @Test
    public void displayOrdersForDate(){
        LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));
        List<Order> ordersForDate = orderDao.getAllOrdersForDate(date);

        // Check that the orders for an existing file is not null
        assertNotNull(ordersForDate);

        // Check that the size of the orders list is as expected (number of entries in the file)
        assertEquals(1, ordersForDate.size());
    }

    @Test
    public void addOrder(){
        // Initialize date, order
        LocalDate date = LocalDate.parse("03052025", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order = new Order();

        order.setOrderNumber(orderDao.getMaxOrderNumber());
        order.setCustomerName("Sabrina");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("10.5"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.4"));
        order.setLaborCostPerSquareFoot(new BigDecimal("3.5"));
        order.setMaterialCost(new BigDecimal("540"));
        order.setLaborCost(new BigDecimal("350"));
        order.setTax(new BigDecimal("93.45"));
        order.setTotal(new BigDecimal("983.45"));

        // Add order to DAO
        orderDao.addOrder(date, order);

        List<Order> ordersForDate = orderDao.getAllOrdersForDate(date);

        // Check if order is present in the list
        assertTrue(ordersForDate.contains(order));
    }

    @Test
    public void removeOrder(){
        // Initialize date, order
        LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));
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

        // Add order to DAO
        orderDao.removeOrder(date, order);

        List<Order> ordersForDate = orderDao.getAllOrdersForDate(date);

        // Check if order is present in the list
        assertFalse(ordersForDate.contains(order));
    }


    @Test
    public void editOrder(){
        // Initialize date, order with information we want to replace
        LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("Aya Lovelace");
        order.setState("FL");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));

        // Add order to DAO
        orderDao.editOrder(date, order);

        List<Order> ordersForDate = orderDao.getAllOrdersForDate(date);

        // Check if edited order is present in the list
        assertTrue(ordersForDate.contains(order));
    }

}

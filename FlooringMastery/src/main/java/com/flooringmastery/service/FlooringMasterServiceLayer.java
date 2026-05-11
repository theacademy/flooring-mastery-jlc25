package com.flooringmastery.service;

import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface FlooringMasterServiceLayer {
    public void addOrder(LocalDate date, Order order) throws FileNotFoundException;
    public Order getOrder(LocalDate date, Integer orderID);
    public Order removeOrder(LocalDate date, Integer orderID);
    public Order editOrder(LocalDate date, Order order);
    public List<Tax> getAllTaxes() throws FileNotFoundException;
    public Tax getTax(String state) throws FileNotFoundException;
    public Product getProduct(String productType) throws FileNotFoundException;
    public HashMap<String, Product> getAllProducts() throws FileNotFoundException;
    public void exportAllData() throws Exception;
    public List<Order> getAllOrdersForDate(LocalDate date);

    public Order validateTax(Order order) throws FileNotFoundException , FlooringMasterTaxNotFoundException;
    public Order validateProduct(Order order) throws FileNotFoundException , FlooringMasterTaxNotFoundException;
    public Order generateOrderNumber(Order order);
    public Order calculateCosts(Order order);
}

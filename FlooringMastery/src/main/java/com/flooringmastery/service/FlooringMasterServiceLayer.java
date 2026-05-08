package com.flooringmastery.service;

import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface FlooringMasterServiceLayer {
    public void addOrder(LocalDate date, Order order);
    public Order getOrder(Integer orderID);
    public Order removeOrder(LocalDate date, Integer orderID);
    public Order editOrder(LocalDate date, Integer orderID);
    public HashMap<String, Tax> getAllTaxes();
    public Tax getTax(String state);
    public Product getProduct(String productType);
    public HashMap<String, Product> getAllProducts();
    public void exportAllData();
    public List<Order> getAllOrdersForDate(LocalDate date);
}

package com.flooringmastery.service;

import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class FlooringMasterServiceLayerImpl implements FlooringMasterServiceLayer{
    @Override
    public List<Order> getAllOrdersForDate(LocalDate date) {
        return List.of();
    }

    // TODO: IMPLEMENT METHODS

    @Override
    public void addOrder(LocalDate date, Order order) {

    }

    @Override
    public Order getOrder(Integer orderID) {
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, Integer orderID) {
        return null;
    }

    @Override
    public Order editOrder(LocalDate date, Integer orderID) {
        return null;
    }

    @Override
    public HashMap<String, Tax> getAllTaxes() {
        return null;
    }

    @Override
    public Tax getTax(String state) {
        return null;
    }

    @Override
    public Product getProduct(String productType) {
        return null;
    }

    @Override
    public HashMap<String, Product> getAllProducts() {
        return null;
    }

    @Override
    public void exportAllData() {

    }
}

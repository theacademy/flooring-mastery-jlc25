package com.flooringmastery.service;

import com.flooringmastery.dao.*;
import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class FlooringMasterServiceLayerImpl implements FlooringMasterServiceLayer{
    OrderDao orderDao;
    TaxDao taxDao;
    ProductDao productDao;

    public FlooringMasterServiceLayerImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao){
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

    @Override
    public List<Order> getAllOrdersForDate(LocalDate date) {
        return orderDao.getAllOrdersForDate(date);
    }

    @Override
    public void addOrder(LocalDate date, Order order) throws FileNotFoundException {

        // Validate
        validateTax(order);
        validateProduct(order);

        // Add order to DAO
        orderDao.addOrder(date, order);
    }

    public Order validateTax(Order order) throws FileNotFoundException , FlooringMasterTaxNotFoundException {
        // Get tax information and add it to Order
        String state = order.getState();
        Tax tax = taxDao.getTaxByState(state);

        // Check if state is in tax file
        if (tax==null){
            throw new FlooringMasterTaxNotFoundException("The state \"" + state + "\" was not found in the tax file.");
        }

        BigDecimal taxRate = tax.getTaxRate();
        order.setTaxRate(taxRate);

        return order;
    }

    public Order validateProduct(Order order) throws FileNotFoundException, FlooringMasterProductNotFoundException {
        // Get product information and add it to Order
        String productName = order.getProductType();
        Product product = productDao.getProduct(productName);

        // Check if product is in product file
        if(product==null){
            throw new FlooringMasterProductNotFoundException("The product \"" + productName + "\" was not found in the product file.");
        }

        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        order.setCostPerSquareFoot(costPerSquareFoot);

        BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
        order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        return order;
    }

    public Order generateOrderNumber(Order order){
        // Generate Order Number
        Integer orderID = orderDao.getMaxOrderNumber();
        order.setOrderNumber(orderID+1);
        return order;
    }

    public Order calculateCosts(Order order){
        // Calculate costs
        BigDecimal area = order.getArea();
        BigDecimal materialCost = order.getCostPerSquareFoot().multiply(area).setScale(2, RoundingMode.HALF_UP); // Area * Cost per square foot
        BigDecimal laborCost = order.getLaborCostPerSquareFoot().multiply(area).setScale(2, RoundingMode.HALF_UP); // Area * Labor cost per square foot
        BigDecimal taxValue = materialCost.add(laborCost).multiply(order.getTaxRate().divide(new BigDecimal("100"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP); // (Material Cost + Labor Cost) * TaxRate/100
        BigDecimal total = materialCost.add(laborCost.add(taxValue)).setScale(2, RoundingMode.HALF_UP); // Material cost + labor cost + tax value

        // Assign values
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(taxValue);
        order.setTotal(total);

        return order;
    }

    @Override
    public Order getOrder(LocalDate date, Integer orderID) throws FlooringMasterOrderDoesNotExistException{
        if (orderDao.getOrder(date, orderID) == null){
            throw new FlooringMasterOrderDoesNotExistException("Order number " + orderID + " does not exist.");
        }
        return orderDao.getOrder(date, orderID);
    }

    @Override
    public Order removeOrder(LocalDate date, Integer orderID) {
        // Check if date exists and if order number exists and return order after deleting it
        if(getOrder(date, orderID) != null){
            Order order = getOrder(date, orderID);
            orderDao.removeOrder(date, order);
            return order;
        }

        // unsuccessful search
        return null;
    }

    @Override
    public Order editOrder(LocalDate date, Order newOrder) {
        // Swap out the existing order with the new order
        orderDao.editOrder(date, newOrder);
        return newOrder;
    }

    @Override
    public List<Tax> getAllTaxes() throws FileNotFoundException {
        return taxDao.getAllTaxes();
    }

    @Override
    public Tax getTax(String state) throws FileNotFoundException {
        return taxDao.getTaxByState(state);
    }

    @Override
    public Product getProduct(String productType) throws FileNotFoundException {
        return productDao.getProduct(productType);
    }

    @Override
    public HashMap<String, Product> getAllProducts() throws FileNotFoundException {
        return productDao.getAllProducts();
    }

    @Override
    public void exportAllData() throws Exception {
        orderDao.exportAllOrders();
    }
}

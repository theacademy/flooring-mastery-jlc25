package com.flooringmastery.service;

import com.flooringmastery.dao.FlooringMasterOrderDoesNotExistException;
import com.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.flooringmastery.dao.OrderDao;
import com.flooringmastery.dao.OrderDaoImpl;
import com.flooringmastery.dto.Order;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlooringMasteryOrderDaoStubImpl implements OrderDao{

    public Order onlyOrder;
    private OrderDao orderDao;
    LocalDate onlyDate;

    public FlooringMasteryOrderDaoStubImpl(String orderDirectory) throws FileNotFoundException {
        orderDao = new OrderDaoImpl(orderDirectory);
        // initializing test date
        onlyDate = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));

        // initializing our test order
        onlyOrder = new Order();
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("Ada Lovelace");
        onlyOrder.setState("CA");
        onlyOrder.setTaxRate(new BigDecimal("25.00"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("249.00"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        onlyOrder.setMaterialCost(new BigDecimal("871.50"));
        onlyOrder.setLaborCost(new BigDecimal("1033.35"));
        onlyOrder.setTax(new BigDecimal("476.21"));
        onlyOrder.setTotal(new BigDecimal("2381.06"));
    }

    public FlooringMasteryOrderDaoStubImpl(Order testOrder){
        this.onlyOrder = testOrder;
    }

    @Override
    public List<Order> getAllOrdersForDate(LocalDate date) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {

            onlyOrder.setCustomerName(order.getCustomerName());
            onlyOrder.setState(order.getState());
            onlyOrder.setTaxRate(order.getTaxRate());
            onlyOrder.setProductType(order.getProductType());
            onlyOrder.setArea(order.getArea());
            onlyOrder.setCostPerSquareFoot(order.getCostPerSquareFoot());
            onlyOrder.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
            onlyOrder.setMaterialCost(order.getMaterialCost());
            onlyOrder.setLaborCost(order.getLaborCost());
            onlyOrder.setTax(order.getTax());
            onlyOrder.setTotal(order.getTotal());
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate date, Order order) {
        if (date.equals(onlyDate) && order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void exportAllOrders() throws Exception {

    }

    @Override
    public Integer getMaxOrderNumber() {
        return orderDao.getMaxOrderNumber();
    }

    @Override
    public Order getOrder(LocalDate date, Integer orderID)
            throws FlooringMasterOrderDoesNotExistException {
        if (date.equals(onlyDate) && orderID.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }


}

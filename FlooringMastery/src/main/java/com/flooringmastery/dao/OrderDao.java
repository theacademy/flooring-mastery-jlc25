package com.flooringmastery.dao;

import com.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    public List<Order> getAllOrdersForDate(LocalDate date);
    public Order addOrder(LocalDate date, Order order);
    public Order editOrder(LocalDate date, Order order);
    Order getOrder(LocalDate date, Integer orderID);
    public Order removeOrder(LocalDate date, Order order);
    public void exportAllOrders() throws Exception;
    Integer getMaxOrderNumber();
}

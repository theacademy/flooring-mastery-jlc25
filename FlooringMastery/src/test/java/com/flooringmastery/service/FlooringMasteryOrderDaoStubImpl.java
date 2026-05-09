package com.flooringmastery.service;

import com.flooringmastery.dao.FlooringMasterOrderDoesNotExistException;
import com.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.flooringmastery.dao.OrderDao;
import com.flooringmastery.dao.OrderDaoImpl;
import com.flooringmastery.dto.Order;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlooringMasteryOrderDaoStubImpl implements OrderDao{

    public Order onlyOrder;
    private OrderDao orderDao;

    public FlooringMasteryOrderDaoStubImpl(String orderDirectory) throws FileNotFoundException {
        // initializing our test order
        // String orderDirectory = "SampleFileData/Orders";
        orderDao = new OrderDaoImpl(orderDirectory);

        onlyOrder = new Order();
        onlyOrder.setOrderNumber(orderDao.getMaxOrderNumber());
        onlyOrder.setCustomerName("Josephine");
        onlyOrder.setState("TX");
        onlyOrder.setTaxRate(new BigDecimal("4.45"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("50"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        onlyOrder.setMaterialCost(new BigDecimal("175"));
        onlyOrder.setLaborCost(new BigDecimal("225"));
        onlyOrder.setTax(new BigDecimal("17.8"));
        onlyOrder.setTotal(new BigDecimal("417.8"));
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
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate date, Order order) {
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
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
        if (orderID.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }


}

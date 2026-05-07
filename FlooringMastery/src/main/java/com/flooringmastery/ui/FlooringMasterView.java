package com.flooringmastery.ui;

import com.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class FlooringMasterView {

    private UserIO io;

    public FlooringMasterView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection(){
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public Order getNewOrderInfo(){
        String orderDate = io.readString("Please enter Order date (ddmmyyyy)");


        String customerName = io.readString("Please enter Customer Name");
        String state = io.readString("Please enter State");
        String productType = io.readString("Please enter Product Type");
        BigDecimal area = io.readBigDecimal("Please enter Area", new BigDecimal("100")); // area needs to be min 100sqft

        Order newOrder = new Order();
        newOrder.setCustomerName(customerName);
        newOrder.setProductType(productType);
        newOrder.setArea(area);

        return newOrder;
    }

    public LocalDate getDateInput(){
        String dateInput = io.readString("Please enter Order date (ddmmyyyy)");

        // TODO: fix with input date
        return LocalDate.now();
    }

    public void displayOrder(Order order){
        if (order != null) {
            io.print((order.getOrderNumber().toString()));
            io.print(order.getCustomerName());
            io.print(order.getState());
            io.print(order.getTaxRate().toString());
            io.print(order.getProductType());
            io.print(order.getArea().toString());
            io.print(order.getCostPerSquareFoot().toString());
            io.print(order.getLaborCostPerSquareFoot().toString());
            io.print(order.getMaterialCost().toString());
            io.print(order.getLaborCost().toString());
            io.print(order.getTax().toString());
            io.print(order.getTotal().toString());
            io.print("");
        } else {
            io.print("No such order.");
        }
        io.readString("Please hit enter to continue.");    }

    public void displayAllOrders(List<Order> orders){
        for (Order order: orders){
            displayOrder(order);
        }
    }

    public void displayAllOrdersBanner(){
        io.print("===== Display All Orders =====");
    }

    public void displayErrorMessage(String message){
        io.print(message);
    }

    public void displayUnknownCommand(){
        io.print("");
    }

    public void addOrderBanner(){
        io.print("===== Add Order =====");
    }

    public void editOrderBanner(){
        io.print("===== Edit Order =====");
    }

    public void removeOrderBanner(){
        io.print("===== Remove Order =====");
    }

    public void listAllOrdersBanner(){
        io.print("===== List All Orders =====");
    }

    public void exportAllDataBanner(){
        io.print("===== Export All Data =====");
    }

    public void displayExitBanner(){
        io.print("Exiting program... GOODBYE!!");
    }

    private boolean validateOrderDate(String date){
        return false;
    }

    private boolean validateCustomerName(String name){
        return false;
    }

    private boolean validateState(){
        return false;
    }

    private boolean validateProductType(){
        return false;
    }
}

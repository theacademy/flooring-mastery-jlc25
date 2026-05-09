package com.flooringmastery.ui;

import com.flooringmastery.dto.Order;
import com.flooringmastery.service.FlooringMasterTaxNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public Order getNewOrderCustomer(){
        Order newOrder = new Order();

        // Get customer name and check if it's alphanumerics, periods, commas and NOT blank
        boolean validName = false;
        String customerName;

        while (!validName){

            customerName = getNameInput();
            if(!validateStringInputNotBlank(customerName)){
                io.print("Input cannot be blank.");
            }
            else {
                newOrder.setCustomerName(customerName);
                validName = true;
            }
        }
        return newOrder;
    }

    public void getNewTaxInfo(Order order){
        String state = io.readString("Please enter State");
        order.setState(state);
    }

    public void getNewProductInfo(Order order){
        String productType = io.readString("Please enter Product Type");
        order.setProductType(productType);
    }

    public void getArea(Order order){
        BigDecimal area = io.readBigDecimal("Please enter Area", new BigDecimal("100")); // area needs to be min 100sqft
        order.setArea(area);
    }

    public LocalDate getDateInput(){
        String dateInput = io.readString("Please enter Order date (ddmmyyyy)");
        return LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    public LocalDate getFutureDate(){
        String dateInput;
        LocalDate date = null;

        // Prompt user until they provide a valid date
        boolean isValid = false;
        
        while (!isValid){
            dateInput = io.readString("Please enter Order date (ddmmyyyy)");

            // TODO: Validate format
            date  = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("ddMMyyyy"));

            // Validate that date is in the future
            if (!validateOrderFutureDate(date)){
                io.print("Order date should be in the future.");
            }
            else {
                isValid = true;
            }
        }
        return date;
    }


    public String getNameInput(){
        boolean isValid = false;
        String customerName = null;
        while (!isValid){
             customerName = io.readString("Please enter Customer Name");

            // Checks if the name is composed of only alphanumerics, periods, commas
            if (!validateCustomerName(customerName)){
                io.print("Customer name should only contain alphanumerics[a-z][0-9], periods and commas.");
            }
            else {
                isValid = true;
            }
        }

        return customerName;
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

    public boolean displayValidationMessage(String prompt){
        String answer = io.readString(prompt + "? (Y/N)");

        if (answer.toUpperCase().equals("Y")){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validateOrderDateFormat(LocalDate date){
        return false;
    }

    private boolean validateOrderFutureDate(LocalDate date){
        // Check if the date is in the future
        return date.isAfter(LocalDate.now());
    }

    private boolean validateCustomerName(String name) {
        // Check if alphanumerical, period, commas
        return name.matches("^[a-zA-Z0-9.,]*$");
    }

    private boolean validateStringInputNotBlank(String input) {
        return !input.isBlank();
    }

    public Integer getExistingOrderNumber() {
        return io.readInt("Enter the number of an existing number");
    }
}

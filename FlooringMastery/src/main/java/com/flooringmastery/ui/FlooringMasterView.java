package com.flooringmastery.ui;

import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringMasterView {

    private UserIO io;

    public FlooringMasterView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection(){
        io.print("  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("  * <<Flooring Program>>");
        io.print("  * 1. Display Orders");
        io.print("  * 2. Add an Order");
        io.print("  * 3. Edit an Order");
        io.print("  * 4. Remove an Order");
        io.print("  * 5. Export All Data");
        io.print("  * 6. Quit");
        io.print("  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt(">> Please select from the above choices.", 1, 6);
    }

    public Integer getExistingOrderNumber() {
        return io.readInt(">> Enter the number of an existing order: ");
    }

    public Order getNewOrderCustomer(){
        Order newOrder = new Order();

        // Get customer name and check if it's alphanumerics, periods, commas and NOT blank
        boolean validName = false;
        String customerName;

        while (!validName){

            customerName = getNameInput(">> Please enter Customer Name: "); // getNameInput validates name
            if(customerName.isBlank()){
                io.print("INVALID INPUT: Customer name cannot be blank.");
            }
            else {
                newOrder.setCustomerName(customerName);
                validName = true;
            }
        }
        return newOrder;
    }

    public void getNewTaxInfo(Order order){
        String state = io.readString(">> Please enter State: ").toUpperCase();
        order.setState(state);
    }

    public void getNewProductInfo(Order order){
        String productType = io.readString(">> Please enter Product Type: ");
        order.setProductType(productType);
    }

    public void getArea(Order order){
        boolean isValid = false;
        BigDecimal area = null;
        while(!isValid){
            try{
                area = io.readBigDecimal(">> Please enter Area (Area must be at least 100sqft): ", new BigDecimal("100")); // area needs to be min 100sqft
                isValid = true;
            }catch(NumberFormatException e){
                io.print("INVALID INPUT: " + e.getMessage());
            }
        }
        order.setArea(area);
    }

    public LocalDate getDateInput(){
        boolean isValid = false;
        LocalDate date = null;
        
        while (!isValid){
            try{
                String dateInput = io.readString(">> Please enter Order date (dd-mm-yyyy)");
                date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                isValid = true;
            }
            catch(DateTimeException e){
                io.print("INVALID INPUT: Date needs to follow dd-mm-yyyy input");
            }
        }
        return date;
    }

    public LocalDate getFutureDate(){
        LocalDate date = null;

        // Prompt user until they provide a valid date
        boolean isValid = false;
        
        while (!isValid){
            date = getDateInput();

            // Validate that date is in the future
            if (!date.isAfter(LocalDate.now())){
                io.print("INVALID INPUT: Order date should be in the future.");
            }
            else {
                isValid = true;
            }
        }
        return date;
    }

    public String getNameInput(String prompt){
        boolean isValid = false;
        String customerName = null;
        while (!isValid){
             customerName = io.readString(prompt);

            // Checks if the name is composed of only alphanumerics, periods, commas
            if (!customerName.matches("^[a-zA-Z0-9., ]*$")){
                io.print("INVALID INPUT: Customer name should only contain alphanumerics[a-z][0-9], periods and commas.");
            }
            else {
                isValid = true;
            }
        }

        return customerName;
    }

    public String editCustomerName(String originalName){
        String input = getNameInput(">> Please enter Customer name (" + originalName + "): ");
        // If input is blank, return original value
        if(input.isBlank()){
            return originalName;
        }
        else{
            return input;
        }
    }

    public String editState(String originalState){
        String input = io.readString(">> Please enter state abbreviation (" + originalState + "): ");

        // If input is blank, return original value
        if(input.isBlank()){
            return originalState;
        }
        else{
            return input;
        }
    }

    public String editProduct(String originalProduct){
        String input = io.readString(">> Please enter product name (" + originalProduct + "): ");

        // If input is blank, return original value
        if(input.isBlank()){
            return originalProduct;
        }
        else{
            return input;
        }
    }

    public BigDecimal editArea(BigDecimal originalArea){
        boolean isValid = false;
        String inputString = "";

        while (!isValid){
            inputString = io.readString(">> Please enter an area size of at least 100 sqft (" + originalArea + "): ");

            // Blank, return original
            if (inputString.isBlank()){
                return originalArea;
            }

            try{
                // Return area if it's valid (at least 100)
                if (new BigDecimal(inputString).compareTo(new BigDecimal("100")) >= 0){
                    isValid = true;
                }
            }catch(NumberFormatException e){
                io.print("INVALID INPUT: " + e.getMessage());
            }
        }
        return new BigDecimal(inputString);
    }

    public void displayOrder(Order order){
        if (order != null) {
            io.print("  / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / /");
            io.print("  * Order number: \t\t\t\t\t\t\t\t\t" + (order.getOrderNumber().toString()));
            io.print("  * Customer name: \t\t\t\t\t\t\t\t\t" + order.getCustomerName());
            io.print("  * State Abbreviation: \t\t\t\t\t\t\t" + order.getState());
            io.print("  * Tax rate: \t\t\t\t\t\t\t\t\t\t" + order.getTaxRate().toString());
            io.print("  * Product Type: \t\t\t\t\t\t\t\t\t" + order.getProductType());
            io.print("  * Area: \t\t\t\t\t\t\t\t\t\t\t" +  order.getArea().toString());
            io.print("  * Cost per Square Foot: \t\t\t\t\t\t\t" +  order.getCostPerSquareFoot().toString());
            io.print("  * Labor cost per Square Foot: \t\t\t\t\t" + order.getLaborCostPerSquareFoot().toString());
            io.print("  * Material Cost: \t\t\t\t\t\t\t\t\t" + order.getMaterialCost().toString());
            io.print("  * Labor Cost: \t\t\t\t\t\t\t\t\t" + order.getLaborCost().toString());
            io.print("  * Tax: \t\t\t\t\t\t\t\t\t\t\t" + order.getTax().toString());
            io.print("  * Total: \t\t\t\t\t\t\t\t\t\t\t" + order.getTotal().toString());
            io.print("  / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / /");
        } else {
            io.print("INVALID INPUT: No such order.");
        }
    }

    public void displayAllOrders(List<Order> orders){
        // No orders
        if (orders == null){
            io.print("No orders found.");
            return;
        }

        for (Order order: orders){
            displayOrder(order);
        }
    }

    public void displayAllProducts(List<Product> products){
        if (products != null) {
            io.print("* * * * * * * * * * * * * * * * Displaying All Products * * * * * * * * * * * * * * * *");
            io.print("  / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / /");

            for (Product product:products){
                io.print("  * Product Type: \t\t\t\t\t\t\t\t\t" + product.getProductType());
                io.print("  * Cost per Square Foot: \t\t\t\t\t\t\t" +  product.getCostPerSquareFoot().toString());
                io.print("  * Labor cost per Square Foot: \t\t\t\t\t" + product.getLaborCostPerSquareFoot().toString());
                io.print("  / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / / /");
            }

        }
    }

    public void displayAllOrdersBanner(){
        io.print("* * * * * * * * * * * * * * * * Displaying All Orders * * * * * * * * * * * * * * * *");
    }

    public void displayErrorMessage(String message){
        io.print(message);
    }

    public void displayUnknownCommand(){
        io.print("");
    }

    public void addOrderBanner(){
        io.print("* * * * * * * * * * * * * * * * Add Order * * * * * * * * * * * * * * * *");
    }

    public void editOrderBanner(){
        io.print("* * * * * * * * * * * * * * * * Edit Order * * * * * * * * * * * * * * * *");
    }

    public void removeOrderBanner(){
        io.print("* * * * * * * * * * * * * * * * Remove Order * * * * * * * * * * * * * * * *");
    }

    public void exportAllDataBanner(){
        io.print("* * * * * * * * * * * * * * * * Export All Data * * * * * * * * * * * * * * * *");
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
}

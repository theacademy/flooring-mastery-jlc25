package com.flooringmastery.controller;

import com.flooringmastery.dao.FlooringMasterOrderDoesNotExistException;
import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.service.FlooringMasterProductNotFoundException;
import com.flooringmastery.service.FlooringMasterTaxNotFoundException;
import com.flooringmastery.service.FlooringMasterServiceLayer;
import com.flooringmastery.ui.FlooringMasterView;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlooringMasterController {
    FlooringMasterView view;
    FlooringMasterServiceLayer service;

    public FlooringMasterController(FlooringMasterView view, FlooringMasterServiceLayer service){
        this.view = view;
        this.service = service;
    }

    public void run(){
        boolean keepGoing = true;
        int selection;

        try {
            while (keepGoing) {

                selection = view.printMenuAndGetSelection();

                switch (selection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportAllData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }


            }
            exitMessage();
        } catch (Exception e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void displayOrders(){
        view.displayAllOrdersBanner();

        // Get orders for given date
        LocalDate date = view.getDateInput();
        List<Order> orders = service.getAllOrdersForDate(date);

        view.displayAllOrders(orders);
    }

    public void addOrder() throws FileNotFoundException {
        view.addOrderBanner();

        // Get new order date and check that it's in the future
        LocalDate date = view.getFutureDate();

        // Get input for order
        // Get customer name
        Order order = view.getNewOrderCustomer();

        // Get user input for tax state abbreviation until it's valid
        boolean validTaxState = false;
        while (!validTaxState){
            try{
                // Get tax info from user and validate with service
                view.getNewTaxInfo(order);
                order = service.validateTax(order);

                validTaxState = true;
            } catch (FlooringMasterTaxNotFoundException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        // Get product name
        boolean validProduct = false;

        // Get all products to display them
        List<Product> products = new ArrayList<>(service.getAllProducts().values());

        // Get user input for product until it's valid
        while (!validProduct){
            try{
                view.displayAllProducts(products);
                // Get product info from user and validate with service
                view.getNewProductInfo(order);
                order = service.validateProduct(order);
                validProduct = true;
            } catch (FlooringMasterProductNotFoundException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        // Get Area
        view.getArea(order);

        // Generate order number and calculate costs
        service.generateOrderNumber(order);
        service.calculateCosts(order);

        // Summary of the fields
        view.displayOrder(order);

        // Ask user if they want to save
        boolean validate = view.displayValidationMessage("Do you wish to place the order?");

        if (validate){
            // Try to add the order
            service.addOrder(date, order);
        }
    }

    public void editOrder() throws FlooringMasterOrderDoesNotExistException{
        view.editOrderBanner();

        // Get order date and number
        LocalDate date = view.getDateInput();

        // Keep asking user for date until it's valid (orders exist for the date)
        while (service.getAllOrdersForDate(date)==null){
            date = view.getDateInput();
        }

        // Get user input for order number
        Integer orderID = view.getExistingOrderNumber();

        // Try to get original order if it exists
        Order originalOrder;
        try{
            originalOrder = service.getOrder(date, orderID);
        }catch(FlooringMasterOrderDoesNotExistException e){
            view.displayErrorMessage(e.getMessage());
            // Return to menu if the order does not exist (null)
            return;
        }

        // Make a new order for the edit (in case user changes their mind at the end)
        Order newOrder = new Order();

        // Copy original values to new order in case user doesn't want to change certain fields
        newOrder.setOrderNumber(originalOrder.getOrderNumber());
        newOrder.setCustomerName(originalOrder.getCustomerName());
        newOrder.setState(originalOrder.getState());
        newOrder.setProductType(originalOrder.getProductType());
        newOrder.setArea(originalOrder.getArea());

        // Input for Customer name
        String newName = view.editCustomerName(originalOrder.getCustomerName());
        newOrder.setCustomerName(newName);

        // Get user input for tax state abbreviation until it's valid
        boolean validTaxState = false;
        String newState = "";

        while (!validTaxState){
            try{
                // Get tax info from user and validate with service
                newState = view.editState(originalOrder.getState());
                newOrder.setState(newState);
                newOrder = service.validateTax(newOrder);

                validTaxState = true;
            } catch (FileNotFoundException | FlooringMasterTaxNotFoundException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        // Get user input for product name until it's valid
        boolean validProduct = false;
        String newProduct="";

        while (!validProduct){
            try{
                // Get product info from user and validate with service
                newProduct = view.editProduct(originalOrder.getProductType());
                newOrder.setProductType(newProduct);
                newOrder = service.validateProduct(newOrder);
                validProduct = true;
            } catch (FileNotFoundException | FlooringMasterProductNotFoundException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        // Area
        BigDecimal newArea = view.editArea(originalOrder.getArea());
        newOrder.setArea(newArea);

        // Re-calculate order if one of the fields changed
        if (!newName.equals(originalOrder.getCustomerName()) || !newState.equals(originalOrder.getState()) || !newProduct.equals(originalOrder.getProductType()) || !newArea.equals(originalOrder.getArea())){
            service.calculateCosts(newOrder);
            // Display new order info
            view.displayOrder(newOrder);
        }
        else{
            // Show unchanged order
            view.displayOrder(originalOrder);
        }

        // Ask user if they want to save
        boolean validation = view.displayValidationMessage("Do you want to save? ");
        // Edit order
        if (validation){
            // Save if yes
            service.editOrder(date, newOrder);
        }
    }

    public void removeOrder() throws FlooringMasterOrderDoesNotExistException{
        view.removeOrderBanner();

        // Get order date and number
        LocalDate date = view.getDateInput();

        // Get user input for date until it's a valid date
        while (service.getAllOrdersForDate(date)==null){
            date = view.getDateInput();
        }

        // Get order number
        Integer orderID = view.getExistingOrderNumber();

        // Validate order number with service
        try{
            Order order = service.getOrder(date, orderID);

            // Display order
            view.displayOrder(order);

            // Prompt
            boolean choseYes = view.displayValidationMessage("Are you sure you want to remove this order?");
            if (choseYes){
                service.removeOrder(date, orderID);
            }
        }catch (FlooringMasterOrderDoesNotExistException e){
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void exportAllData() throws Exception {
        view.exportAllDataBanner();
        try{
            service.exportAllData();
        }
        catch (Exception e){
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void exitMessage(){
        view.displayExitBanner();
    }

    public void unknownCommand(){
        view.displayUnknownCommand();
    }
}

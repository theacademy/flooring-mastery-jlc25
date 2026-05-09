package com.flooringmastery.controller;

import com.flooringmastery.dao.FlooringMasterOrderDoesNotExistException;
import com.flooringmastery.dto.Order;
import com.flooringmastery.service.FlooringMasterProductNotFoundException;
import com.flooringmastery.service.FlooringMasterTaxNotFoundException;
import com.flooringmastery.service.FlooringMasterServiceLayer;
import com.flooringmastery.ui.FlooringMasterView;

import java.io.FileNotFoundException;
import java.time.LocalDate;
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

        // Get tax state abbreviation
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
        while (!validProduct){
            try{
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
        service.generateOrderNumberAndCalculateCosts(order);

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

        // Validate order number with service

        // Prompt user with existing information
        // Allow blank input (they will be the same as the original order)

        // Re-calculate order if needed

        // Ask user if they want to save

        // Save if yes

    }

    public void removeOrder() throws FlooringMasterOrderDoesNotExistException{
        view.removeOrderBanner();

        // Get order date and number
        LocalDate date = view.getDateInput();
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

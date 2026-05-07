package com.flooringmastery.controller;

import com.flooringmastery.dao.FlooringMasterOrderDoesNotExistException;
import com.flooringmastery.dto.Order;
import com.flooringmastery.service.FlooringMasterServiceLayer;
import com.flooringmastery.service.FlooringMasterServiceLayerImpl;
import com.flooringmastery.ui.FlooringMasterView;

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
        int selection = 0;

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
        } catch (FlooringMasterOrderDoesNotExistException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    public void displayOrders(){
        view.displayAllOrdersBanner();
        LocalDate date = view.getDateInput();
        // List<Order> orders = service.getAllOrdersForDate(LocalDate date);
        // view.displayAllOrders(orders);
    }

    public void addOrder(){
        view.addOrderBanner();
    }

    public void editOrder() throws FlooringMasterOrderDoesNotExistException{
        view.editOrderBanner();
    }

    public void removeOrder() throws FlooringMasterOrderDoesNotExistException{
        view.removeOrderBanner();
    }

    public void exportAllData(){
        view.exportAllDataBanner();
    }

    public void exitMessage(){
        view.displayExitBanner();
    }

    public void unknownCommand(){
        view.displayUnknownCommand();
    }


}

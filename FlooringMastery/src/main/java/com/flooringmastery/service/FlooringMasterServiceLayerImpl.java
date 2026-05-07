package com.flooringmastery.service;

import com.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public class FlooringMasterServiceLayerImpl implements FlooringMasterServiceLayer{
    @Override
    public List<Order> getAllOrdersForDate(LocalDate date) {
        return List.of();
    }
}

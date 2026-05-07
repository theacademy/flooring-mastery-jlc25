package com.flooringmastery.service;

import com.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasterServiceLayer {
    public List<Order> getAllOrdersForDate(LocalDate date);
}

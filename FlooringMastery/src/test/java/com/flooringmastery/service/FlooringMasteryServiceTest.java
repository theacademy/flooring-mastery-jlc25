package com.flooringmastery.service;

import com.flooringmastery.dao.*;

public class FlooringMasteryServiceTest {
    FlooringMasterServiceLayer service;

    FlooringMasteryServiceTest(){
        // OrderDao orderDao = new OrderDaoImpl();
        // TaxDao taxDao = new TaxDaoImpl();
        // ProductDao productDao = new ProductDaoImpl();

        service = new FlooringMasterServiceLayerImpl();

    }

}

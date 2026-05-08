package com.flooringmastery.dao;

import com.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDaoImpl implements OrderDao{
    private final String ORDERS_DIRECTORY;
    private final String DELIMITER = ",";
    private final String FILENAME_DELIMITER = "_";
    HashMap<LocalDate, List<Order>> orders;
    Set<LocalDate> newOrderDates;
    private int maxOrderNumber = 0;

    OrderDaoImpl(String ordersDirectory) throws FileNotFoundException {
        ORDERS_DIRECTORY = ordersDirectory;
        orders = new HashMap<>();
        newOrderDates = new HashSet<>();
        loadOrderList();
    }

    @Override
    public List<Order> getAllOrdersForDate(LocalDate date) {
        return orders.get(date);
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        // If the date doesn't already exist, make a new list
        // Add the order to the list
        orders.computeIfAbsent(date, k -> new ArrayList<Order>()).add(order);
        return order;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        // Find orders for given date
        List<Order> ordersForDate= orders.get(date);

        // Find an order that has a matching orderID and replace it by our new order
        ordersForDate.stream().forEach((ord) -> {
            if(ord.getOrderNumber().equals(order.getOrderNumber())){
                ord.setCustomerName(order.getCustomerName());
                ord.setState(order.getState());
                ord.setTaxRate(order.getTaxRate());
                ord.setProductType(order.getProductType());
                ord.setArea(order.getArea());
                ord.setCostPerSquareFoot(order.getCostPerSquareFoot());
                ord.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
                ord.setMaterialCost(order.getMaterialCost());
                ord.setLaborCost(order.getLaborCost());
                ord.setTax(order.getTax());
                ord.setTotal(order.getTotal());
            }
        });

        return order;
    }

    @Override
    public Order removeOrder(LocalDate date, Order order) {
        orders.get(date).remove(order);
        return order;
    }

    // TODO: implement
    @Override
    public void exportAllOrders() {

    }

    private void loadOrderList() throws FileNotFoundException{
        // For each file in the directory
        File directory = new File(ORDERS_DIRECTORY);
        File[] allOrderFiles = directory.listFiles();

        for (File file: allOrderFiles){
            List<Order> orderPerDate = new ArrayList<>();

            // Get the name of the file only without the path
            String filename = file.getName();

            // Remove the file extension
            String[] filenameWithoutExtension = filename.split("\\.");
            filename = filenameWithoutExtension[0];

            // Parse date
            String[] filenameTokens= filename.split(FILENAME_DELIMITER);
            String dateString = filenameTokens[1];

            // Generate Local Date based on filename
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));

            try{
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(file.toString())));

                // Skip header line
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                // Only un-marshall after the header-line
                while(scanner.hasNextLine()){
                    String currentLine = scanner.nextLine();

                    // Process the order string -> add order to list
                    Order order = unmarshallOrder(currentLine);
                    orderPerDate.add(order);

                    // Check if the order number is the max
                    if (order.getOrderNumber() > maxOrderNumber){
                        maxOrderNumber = order.getOrderNumber();
                    }

                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(file.toString() + " was not found");
            }

            // Add list of orders for the given date
            orders.put(date, orderPerDate);
        }
    }

    private Order unmarshallOrder(String orderLine){
        Order order = new Order();
        String[] tokens = orderLine.split(DELIMITER);

        order.setOrderNumber(Integer.parseInt(tokens[0]));
        order.setCustomerName(tokens[1]);
        order.setState(tokens[2]);
        order.setTaxRate(new BigDecimal(tokens[3]));
        order.setProductType(tokens[4]);
        order.setArea(new BigDecimal(tokens[5]));
        order.setCostPerSquareFoot(new BigDecimal(tokens[6]));
        order.setLaborCostPerSquareFoot(new BigDecimal(tokens[7]));
        order.setMaterialCost(new BigDecimal(tokens[8]));
        order.setLaborCost(new BigDecimal(tokens[9]));
        order.setTax(new BigDecimal(tokens[10]));
        order.setTotal(new BigDecimal(tokens[11]));

        return order;
    }

    private String marshallOrder(Order order){
        // Concatenate string
        String orderAsText = order.getOrderNumber() + DELIMITER;
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTax() + DELIMITER;
        orderAsText += order.getTotal();

        return orderAsText;
    }

    // TODO : finish implementing
    private void writeOrdersForDate(LocalDate date){
        // Get filename for date

        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        List<Order> ordersList = orders.get(date);

        for (Order order: ordersList){

        }

    }

    public Integer getMaxOrderNumber(){
        return maxOrderNumber;
    }


    // TODO: stretch goal
    // private void writeBackup(){
    //
    // }
}

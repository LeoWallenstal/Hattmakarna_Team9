/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import static hattmakarna.data.Hattmakarna.idb;
import java.util.*;
import java.util.function.Function;
import oru.inf.InfException;

/**
 *
 * @author Gastinlogg
 */
public class OrderRegister {

    private ArrayList<Order> allOrders;

    public OrderRegister() {
        allOrders = new ArrayList<>();
        initAllOrders();
    }

    private void initAllOrders() {
        String sqlQuery = "SELECT order_id FROM sales_order";

        try {
            ArrayList<String> orders = idb.fetchColumn(sqlQuery);
            for (String id : orders) {
                allOrders.add(new Order(id));
            }
        } catch (InfException ex) {
            System.out.println(" in initAllOrders()");
        }

    }
    
    /**
     * A generalized function to sort a list of customers.
     * @param fieldExtractor The function which gets the field to compare; e.g. Customer::getFirstName, Customer::getTelephoneNumber.
     * @param ascending toggles ascending or descending.
     * @return Returns the sorted list.
     */
    public ArrayList<Order> sortBy(Function<Order, String> fieldExtractor, boolean ascending) {
        ArrayList<Order> sorted = new ArrayList<>(allOrders);
        Comparator<Order> comparator = Comparator.comparing(fieldExtractor, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        sorted.sort(comparator);
        return sorted;
    }
    
    public ArrayList<Order> getOrders(){
        return allOrders;
    }
}

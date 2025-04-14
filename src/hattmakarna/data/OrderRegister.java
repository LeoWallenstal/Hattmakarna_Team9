/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import static hattmakarna.data.Hattmakarna.idb;
import java.util.*;
import oru.inf.InfException;

/**
 *
 * @author Gastinlogg
 */
public class OrderRegister {

    private ArrayList<Order> allOrders;

    public OrderRegister() {
        //allOrders = initAllOrders();
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
}

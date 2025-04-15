/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import oru.inf.InfException;

/**
 *
 * @author leonb
 */
public class OrderTest {

    public OrderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        try {
            Hattmakarna.connectToDB();
        } catch (InfException ex) {
            Logger.getLogger(OrderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createOrder method, of class Order.
     */
    @Test
    public void testCreateOrder() {
        System.out.println("createOrder");
        int customer_id = 1;
        ArrayList<String> hattar = null;
        double totalPris = 999.99;
        boolean isFastProduction = false;
        String result = Order.createOrder(customer_id, hattar, totalPris, isFastProduction);
        Order order = new Order(result);

        assertEquals(order.getCustomer_id(), customer_id);
        assertEquals(order.getStatus(), Status.PLACED);

    }

    /**
     * Test of getTabelName method, of class Order.
     */
    @Test
    public void testGetTabelName() {
        System.out.println("getTabelName");
        Order instance = new Order("1");
        String expResult = "";
        String result = instance.getTabelName();
        assertEquals("sales_order", result);

    }

    /**
     * Test of getIdAttributeName method, of class Order.
     */
    @Test
    public void testGetIdAttributeName() {
        System.out.println("getIdAttributeName");
        Order instance = new Order("1");
        String expResult = "order_id";
        String result = instance.getIdAttributeName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getOrder_id method, of class Order.
     */
    @Test
    public void testGetOrder_id() {
        System.out.println("getOrder_id");
        Order instance = new Order("1");
        int expResult = 1;
        int result = instance.getOrder_id();
        assertEquals(expResult, result);

    }

    /**
     * Test of setOrder_id method, of class Order.
     */
    @Test
    public void testSetOrder_id() {
        System.out.println("setOrder_id");
        int order_id = 0;
        Order instance = new Order("1");
        instance.setOrder_id(order_id);

    }

    /**
     * Test of getCustomer_id method, of class Order.
     */
    @Test
    public void testGetCustomer_id() {
        System.out.println("getCustomer_id");
        Order instance = new Order("1");
        int expResult = 1;
        int result = instance.getCustomer_id();
        assertEquals(expResult, result);

    }

    /**
     * Test of setCustomer_id method, of class Order.
     */
    @Test
    public void testSetCustomer_id() {
        System.out.println("setCustomer_id");
        int customer_id = 0;
        Order instance = new Order("1");
        instance.setCustomer_id(customer_id);

    }


    /**
     * Test of getTotalPris method, of class Order.
     */
    @Test
    public void testGetTotalPris() {
        System.out.println("getTotalPris");
        Order instance = new Order("1");
        int expResult = 0;
        int result = instance.getTotalPris();
        assertEquals(expResult, result);

    }

    /**
     * Test of setTotalPris method, of class Order.
     */
    @Test
    public void testSetTotalPris() {
        System.out.println("setTotalPris");
        int totalPris = 0;
        Order instance = new Order("1");
        instance.setTotalPris(totalPris);

    }

    /**
     * Test of getStatus method, of class Order.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Order instance = new Order("1");
        Status expResult = null;
        Status result = instance.getStatus();
        assertEquals(expResult, result);

    }

    /**
     * Test of setStatus method, of class Order.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        Status status = null;
        Order instance = new Order("1");
        instance.setStatus(status);

    }

    /**
     * Test of setRecived_data method, of class Order.
     */
    @Test
    public void testSetRecived_data() {
        System.out.println("setRecived_data");
        Date recived_data = null;
        Order instance = new Order("1");
        instance.setRecived_data(recived_data);

    }

    /**
     * Test of isFastProduction method, of class Order.
     */
    @Test
    public void testIsFastProduction() {
        System.out.println("isFastProduction");
        Order instance = new Order("1");
        boolean expResult = false;
        boolean result = instance.isFastProduction();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFastProduction method, of class Order.
     */
    @Test
    public void testSetFastProduction() {
        System.out.println("setFastProduction");
        boolean isFastProduction = false;
        Order instance = new Order("1");
        instance.setFastProduction(isFastProduction);

    }

    /**
     * Test of getIdString method, of class Order.
     */
    @Test
    public void testGetIdString() {
        System.out.println("getIdString");
        Order instance = new Order("1");
        String expResult = "1";
        String result = instance.getIdString();
        assertEquals(expResult, result);

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hattmakarna.data;

import hattmakarna.Hattmakarna;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import oru.inf.InfException;

/**
 *
 * @author leonb
 */
public class OrderTest {
    
    public OrderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        try {
            Hattmakarna.connectToDB();
        } catch (InfException ex) {
            Logger.getLogger(OrderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createOrder method, of class Order.
     */
    @Test
    @DisplayName("awdawljk alwdalk")
    public void testCreateOrder() {
      String id = Order.createOrder(0, null, 100000, true);
      
      System.out.println(id);
    }

    /**
     * Test of getTabelName method, of class Order.
     */
    @Test
    public void testGetTabelName() {
        System.out.println("getTabelName");
        Order instance = null;
        String expResult = "";
        String result = instance.getTabelName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdAttributeName method, of class Order.
     */
    @Test
    public void testGetIdAttributeName() {
        System.out.println("getIdAttributeName");
        Order instance = null;
        String expResult = "";
        String result = instance.getIdAttributeName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrder_id method, of class Order.
     */
    @Test
    public void testGetOrder_id() {
        System.out.println("getOrder_id");
        Order instance = null;
        String expResult = "";
        String result = instance.getOrder_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOrder_id method, of class Order.
     */
    @Test
    public void testSetOrder_id() {
        System.out.println("setOrder_id");
        String order_id = "";
        Order instance = null;
        instance.setOrder_id(order_id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomer_id method, of class Order.
     */
    @Test
    public void testGetCustomer_id() {
        System.out.println("getCustomer_id");
        Order instance = null;
        String expResult = "";
        String result = instance.getCustomer_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCustomer_id method, of class Order.
     */
    @Test
    public void testSetCustomer_id() {
        System.out.println("setCustomer_id");
        String customer_id = "";
        Order instance = null;
        instance.setCustomer_id(customer_id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHattar method, of class Order.
     */
    @Test
    public void testGetHattar() {
        System.out.println("getHattar");
        Order instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getHattar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHattar method, of class Order.
     */
    @Test
    public void testSetHattar() {
        System.out.println("setHattar");
        ArrayList<String> hattar = null;
        Order instance = null;
        instance.setHattar(hattar);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalPris method, of class Order.
     */
    @Test
    public void testGetTotalPris() {
        System.out.println("getTotalPris");
        Order instance = null;
        int expResult = 0;
        int result = instance.getTotalPris();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTotalPris method, of class Order.
     */
    @Test
    public void testSetTotalPris() {
        System.out.println("setTotalPris");
        int totalPris = 0;
        Order instance = null;
        instance.setTotalPris(totalPris);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class Order.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Order instance = null;
        Status expResult = null;
        Status result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStatus method, of class Order.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        Status status = null;
        Order instance = null;
        instance.setStatus(status);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecived_data method, of class Order.
     */
    @Test
    public void testGetRecived_data() {
        System.out.println("getRecived_data");
        Order instance = null;
        Date expResult = null;
        Date result = instance.getRecived_data();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRecived_data method, of class Order.
     */
    @Test
    public void testSetRecived_data() {
        System.out.println("setRecived_data");
        Date recived_data = null;
        Order instance = null;
        instance.setRecived_data(recived_data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFastProduction method, of class Order.
     */
    @Test
    public void testIsFastProduction() {
        System.out.println("isFastProduction");
        Order instance = null;
        boolean expResult = false;
        boolean result = instance.isFastProduction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFastProduction method, of class Order.
     */
    @Test
    public void testSetFastProduction() {
        System.out.println("setFastProduction");
        boolean isFastProduction = false;
        Order instance = null;
        instance.setFastProduction(isFastProduction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

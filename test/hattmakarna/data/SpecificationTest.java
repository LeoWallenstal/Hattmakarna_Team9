/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hattmakarna.data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
public class SpecificationTest {

    public SpecificationTest() {
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
            Logger.getLogger(SpecificationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSpecID method, of class Specification.
     */
    @Test
    public void testGetSpecID() {
        System.out.println("getSpecID");
        Specification instance = new Specification();
        int expResult = 0;
        int result = instance.getSpecID();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDescription method, of class Specification.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Specification instance = new Specification();
        instance.setDesciption("Skibidi");
        String expResult = "Skibidi";
        instance.save();
        int id = instance.getSpecID();
        instance = new Specification(Integer.toString(id));
        String result = instance.getDescription();
        System.out.println(result);

        assertEquals(expResult, result);

    }

    /**
     * Test of getHatID method, of class Specification.
     */
    @Test
    public void testGetHatID() {
        System.out.println("getHatID");
        Specification instance = new Specification();
        int expResult = 1;

        int result = instance.getHatID();
        assertEquals(expResult, result);

    }

    /**
     * Test of getImagePath method, of class Specification.
     */
    @Test
    public void testGetImagePath() {
        try {
            System.out.println("getImagePath");
            Specification instance = new Specification();
            instance.setDesciption("test");
            instance.setSkiss(ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/en/4/4d/Shrek_%28character%29.png")));
            assertEquals(instance.save(),true);

            instance = new Specification(String.valueOf(instance.getSpecID()));            
            String expResult = "images\\specBild-hat-1.png";
            String result = instance.getImagePath();
            
            System.out.println(instance.getImagePath());

            assertEquals(expResult, result);
        } catch (MalformedURLException ex) {
            fail("Bild sparning misslyckades");
        } catch (IOException ex) {
            Logger.getLogger(SpecificationTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Test of setDesciption method, of class Specification.
     */
    @Test
    public void testSetDesciption() {
        System.out.println("setDesciption");
        String newDescription = "";
        Specification instance = new Specification();
        instance.setDesciption(newDescription);
    }

    /**
     * Test of setSkiss method, of class Specification.
     */
    @Test
    public void testSetSkiss() {
        System.out.println("setSkiss");
        BufferedImage image = null;
        Specification instance = new Specification();
        instance.setSkiss(image);
    }

    /**
     * Test of setHatId method, of class Specification.
     */
    @Test
    public void testSetHatId() {
        System.out.println("setHatId");
        int id = 0;
        Specification instance = new Specification();
        instance.setHatId(id);
    }

    /**
     * Test of setFileFromUser method, of class Specification.
     */
    @Test
    public void testSetFileFromUser() {
        System.out.println("setFileFromUser");
        Specification instance = new Specification();
        instance.setFileFromUser();
    }

    /**
     * Test of getTabelName method, of class Specification.
     */
    @Test
    public void testGetTabelName() {
        System.out.println("getTabelName");
        Specification instance = new Specification();
        String expResult = "hat_spec";
        String result = instance.getTabelName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIdAttributeName method, of class Specification.
     */
    @Test
    public void testGetIdAttributeName() {
        System.out.println("getIdAttributeName");
        Specification instance = new Specification();
        String expResult = "spec_id";
        String result = instance.getIdAttributeName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIdString method, of class Specification.
     */
    @Test
    public void testGetIdString() {
        System.out.println("getIdString");
        Specification instance = new Specification();
        String expResult = null;
        String result = instance.getIdString();
        assertEquals(expResult, result);
    }

    /**
     * Test of save method, of class Specification.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Specification instance = new Specification();
        boolean expResult = true;
        boolean result = instance.save();
        assertEquals(expResult, result);
        System.out.println(instance.getSpecID());
    }

}

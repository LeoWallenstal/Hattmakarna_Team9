/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hattmakarna.data;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import hattmakarna.UI.RegisterCustomerWindow;
import hattmakarna.UI.LogInWindow;
import hattmakarna.data.Order;
import hattmakarna.data.Specification;
import hattmakarna.data.Status;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JOptionPane;
import hattmakarna.UI.EditCustomer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import hattmakarna.UI.OrderWindow;

import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author walle
 */
public class Hattmakarna {

    public static InfDB idb;
    private static final String DB_PASSWORD = "dbHattAdminPW";
    private static final String DB_USER = "dbHattAdmin";
    private static final String DB_NAME = "hattmakaren";
    private static final String DB_PORT = "3306";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            IntelliJTheme.setup(new FileInputStream("themes/theme1.properties"));
        // FlatLaf.setup(new FlatMacLightLaf());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hattmakarna.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Koppla till databas
        try {
            connectToDB();

            new LogInWindow(idb).setVisible(true);
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "Koppling till databasen misslyckades! :/");
            e.printStackTrace();
        }

    }

    public static void connectToDB() throws InfException {
        idb = new InfDB(DB_NAME, DB_PORT, DB_USER, DB_PASSWORD);
    }
}

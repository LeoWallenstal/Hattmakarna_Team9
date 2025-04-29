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
import hattmakarna.util.PrintDebugger;
import java.awt.AWTEvent;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
        
        //Loading icons
        List<Image> icons = List.of(
            Toolkit.getDefaultToolkit().getImage(Hattmakarna.class.getResource("/resources/icons/appIcon16.png")),
            Toolkit.getDefaultToolkit().getImage(Hattmakarna.class.getResource("/resources/icons/appIcon32.png")),
            Toolkit.getDefaultToolkit().getImage(Hattmakarna.class.getResource("/resources/icons/appIcon64.png")),
            Toolkit.getDefaultToolkit().getImage(Hattmakarna.class.getResource("/resources/icons/appIcon256.png"))
        );

        //För att toggla printdebuggern av/på
        PrintDebugger.enabled = false;
      
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof WindowEvent we && we.getID() == WindowEvent.WINDOW_OPENED) {
                Window w = we.getWindow();
                if (w instanceof JFrame jf) {
                    // instead of jf.setIconImage(icon);
                    jf.setIconImages(icons);
                    
                    //Sätter defaulttitel till "Hattmakarna - " + det som redan finns i de andra fönstrena
                    String existingTitle = jf.getTitle();
                    String title = existingTitle.isBlank() ? "Hattmakarna - " : "Hattmakarna - " + existingTitle;
                    jf.setTitle(title);
                }
                if(w instanceof JDialog jd){
                    jd.setIconImages(icons);
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK);

        
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

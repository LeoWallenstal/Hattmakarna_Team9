/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hattmakarna.data;

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

		// Koppla till databas
		try {
			connectToDB();
                        
			//new LogInWindow(idb).setVisible(true);
                        User u1 = new User("1");
                        new RegisterCustomerWindow(u1, new OrderWindow(u1)).setVisible(true);
		} catch (InfException e) {
			JOptionPane.showMessageDialog(null, "Koppling till databasen misslyckades! :/");
			e.printStackTrace();
		}



	}
	
	public static void connectToDB() throws InfException{
		idb = new InfDB(DB_NAME,DB_PORT,DB_USER,DB_PASSWORD);
	}
}

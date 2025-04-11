/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hattmakarna;

import javax.swing.JOptionPane;

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
			idb = new InfDB(DB_NAME, DB_PORT, DB_USER, DB_PASSWORD);
  new LogInWindow(idb).setVisible(true);
		} catch (InfException e) {
			JOptionPane.showMessageDialog(null, "Koppling till databasen misslyckades! :/");
			e.printStackTrace();
		}
                
            User u1 = new User(); //JAMES
            User u2 = new User("1"); //OTTO, ADMIN
            u1.setFirstName("James");
            u1.setLastName("Ellison");
            u1.setEmail("Jamesellison95@gmail.com");
            u1.setPWCandidate("hejhejhej");
            u1.setAdmin(u2, true);
            u1.create();
            
            User u1Copy = new User(u1);
            
            u1.setAdmin(u2, false);
            
            u1.save(u1Copy);
            
            
            
           // u1.sav
            
       
	}
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hattmakarna;

import java.util.ArrayList;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author walle
 */
public class Hattmakarna {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InfDB idb = null;
        
        try{
            idb = new InfDB("hattmakaren", "3306", "dbHattAdmin", "dbHattAdminPW");
        }catch(InfException ex){
            System.out.println(ex.getMessage() + "i main");
        }
        
        Customer c1 = new Customer("1", idb);
        
        c1.addTelephoneNumber("0722410155");
        c1.addTelephoneNumber("1234567890");
        
        c1.addEmailAdress("enMailAdress@hej.se");
        c1.addEmailAdress("enAnnanMailAdress@hej.se");
        
        c1.save();
        c1.delete();
        
        /*Customer c2 = new Customer(idb);
        c2.setFirstName("James");
        c2.setLastName("Ellison");
        c2.setAdress("Rudbecksgatan 38A");
        c2.setPostalCode("70223");
        c2.setCountry("Sverige");
        
        ArrayList<String> telephoneNumbers = new ArrayList<>();
        ArrayList<String> emailAdresses = new ArrayList<>();
        
        telephoneNumbers.add("0722410155");
        telephoneNumbers.add("1234567890");
        
        emailAdresses.add("Jamesellison95@gmail.com");
        emailAdresses.add("EnAnnanMail@hej.se");
        
        c2.setTelephoneNumbers(telephoneNumbers);
        c2.setEmailAdresses(emailAdresses);
        
        c2.insert(); */
        
        
    }
    
}

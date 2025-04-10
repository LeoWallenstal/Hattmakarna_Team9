/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hattmakarna;

import java.util.*;
import oru.inf.InfDB;
import oru.inf.InfException;


/**
 *
 * @author james
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String customerID;
    private String adress;
    private String postalCode;
    private String country;
    private ArrayList<String>telephoneNumbers;
    private ArrayList<String>emailAdresses;
    
    /*private ArrayList<String>nuvarandeOrder; kanske? Där ett (eller flera) orderID 
    sparas, och kan instansieras vid behov, via ID på liknande sätt som denna klassen. */
    
    private final InfDB idb;
    
    public Customer(HashMap<String, String> customerMap, InfDB idb){
        this.idb = idb;
        
        this.customerID = customerMap.get("customer_id");
        this.firstName = customerMap.get("first_name");
        this.lastName = customerMap.get("last_name");
        this.adress = customerMap.get("address");
        this.postalCode = customerMap.get("postalcode");
        this.country = customerMap.get("country");
        this.telephoneNumbers = initTelephoneNumbers();
        this.emailAdresses = initEmailAdresses();
    }
    
    public Customer(String customerID, InfDB idb){
        this.idb = idb;
        
        HashMap<String, String> aCustomer = new HashMap<>();
        String sqlQuery = "SELECT * FROM Customer WHERE ID = " + customerID;
        
        try{
            aCustomer = idb.fetchRow(customerID);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + ", in Customer(), Customer.java");
        }
        
        this.telephoneNumbers = initTelephoneNumbers();
        this.emailAdresses = initEmailAdresses();
    }
    
    //Initialisering
    
    private ArrayList<String> initTelephoneNumbers(){
        
        String sqlQuery = "SELECT * FROM phone WHERE customer_id = " 
            + customerID;
        
        ArrayList<HashMap<String, String>> telephoneNumberMap = new ArrayList<>();
        ArrayList<String> telephoneNumbers = new ArrayList<>();
        
        
        try{
            telephoneNumberMap = idb.fetchRows(sqlQuery);
            
        }catch(InfException ex){
            System.out.println(ex.getMessage() + " in fetchTelephoneNumbers(), Customer.java");
            System.out.println(sqlQuery);
        }
        
        for(HashMap<String, String> aTelephoneNumber : telephoneNumberMap){
            telephoneNumbers.add(aTelephoneNumber.get("phone_number"));
        }
        return telephoneNumbers;
    }
    
    private ArrayList<String> initEmailAdresses(){
        
        String sqlQuery = "SELECT * FROM mail WHERE customer_id = " 
            + customerID;
        
        ArrayList<HashMap<String, String>> EmailAdressesMap = new ArrayList<>();
        ArrayList<String> EmailAdresses = new ArrayList<>();
        
        
        try{
            EmailAdressesMap = idb.fetchRows(sqlQuery);
            
        }catch(InfException ex){
            System.out.println(ex.getMessage() + " in fetchEmailAdresses(), Customer.java");
        }
        
        for(HashMap<String, String> anEmailAdress : EmailAdressesMap){
            EmailAdresses.add(anEmailAdress.get("mail"));
        }
        return EmailAdresses;
    }
    
    //Getters
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getCustomerID(){
        return customerID;
    }
    
    public ArrayList<String> getEmailAdresses(){
        return emailAdresses;
    }
    
    public ArrayList<String> getTelephoneNumbers(){
        return telephoneNumbers;
    }
    
    public String getAdress(){
        return adress;
    }
    
    //Setters
    
    public void setFirstName(String newFirstName){
        if(Validerare.validateName(newFirstName)){
            this.firstName = newFirstName; 
        }
       
    }
    
    public void setLastName(String newLastName){
        if(Validerare.validateName(newLastName)){
            this.firstName = newLastName;
        }
       
    }
    
    public void addTelephoneNumber(String telephoneNumber){
        if(Validerare.validatePhoneNumber(telephoneNumber)){
            telephoneNumbers.add(telephoneNumber);
        }        
        
    }
    
    public void removeTelephoneNumber(int indexPos){
        if(indexPos >= 0 && indexPos < telephoneNumbers.size()){
            telephoneNumbers.remove(indexPos);
        }
    }
    
    public void addEmailAdress(String emailAdress){ 
        if(Validerare.validateEmail(emailAdress)){
            emailAdresses.add(emailAdress);
        }        

    }
    
    public void removeEmailAdress(int indexPos){
        if(indexPos >= 0 && indexPos < emailAdresses.size()){
            emailAdresses.remove(indexPos);
        }
    }
    
    
    //För att kolla ett objekts 'state' vid debug. 
    public String toString(){
        String output = "[ID]: " + customerID + "\n[Name]: " + firstName + " "
            + lastName + "\n[Adress]: " + adress + ", " + postalCode + ", " 
            + country + "\n[Telephone Numbers]: ";
        
        Iterator it = telephoneNumbers.iterator();
        while(it.hasNext()){
            output += it.next();
            if(it.hasNext()){
                output += ", ";
            }
        }
        
        output += "\n[Email Adresses]: ";
        
        it = emailAdresses.iterator();
        while(it.hasNext()){
            output += it.next();
            if(it.hasNext()){
                output += ", ";
            }
        }
        return output;
    }
    
    
    //DB
    
    public void save(){
        
    }
    
}

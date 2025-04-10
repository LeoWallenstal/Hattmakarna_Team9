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
    
    
    public Customer(InfDB idb){
        this.idb = idb;
    }
    
    public Customer(HashMap<String, String> customerMap, InfDB idb){
        this.idb = idb;
        
        this.customerID = customerMap.get("customer_id");
        this.firstName = customerMap.get("first_name");
        this.lastName = customerMap.get("last_name");
        this.adress = customerMap.get("address");
        this.postalCode = customerMap.get("postalcode");
        this.country = customerMap.get("country");
        this.telephoneNumbers = fetchTelephoneNumbers();
        this.emailAdresses = fetchEmailAdresses();
    }
    
    public Customer(String customerID, InfDB idb){
        this.idb = idb;
        
        HashMap<String, String> customerMap = new HashMap<>();
        String sqlQuery = "SELECT * FROM customer WHERE customer_id = " + customerID;
        
        try{
            customerMap = idb.fetchRow(sqlQuery);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + ", in Customer(), Customer.java");
            System.out.println(sqlQuery);
        }
        
        this.customerID = customerMap.get("customer_id");
        this.firstName = customerMap.get("first_name");
        this.lastName = customerMap.get("last_name");
        this.adress = customerMap.get("address");
        this.postalCode = customerMap.get("postalcode");
        this.country = customerMap.get("country");
        
        this.telephoneNumbers = fetchTelephoneNumbers();
        this.emailAdresses = fetchEmailAdresses();
    }
    
    //Initialisering
    
    private ArrayList<String> fetchTelephoneNumbers(){
        
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
    
    private ArrayList<String> fetchEmailAdresses(){
        
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
        // TODO: Validering
        this.firstName = newFirstName;  
    }
    
    public void setLastName(String newLastName){
        // TODO: Validering
        this.lastName = newLastName;
    }
    
    public void setAdress(String newAdress){
        // TODO: Validering
        this.adress = newAdress;
    }
    
    public void setPostalCode(String newPostalCode){
        // TODO: Validering
        this.postalCode = newPostalCode;
    }
    
    public void setCountry(String newCountry){
        // TODO: Validering
        this.country = newCountry;
    }
    
    public void addTelephoneNumber(String telephoneNumber){
        // TODO: Validering
        telephoneNumbers.add(telephoneNumber);
    }
    
    public void setTelephoneNumbers(ArrayList<String> telephoneNumbers){
        // TODO -- Validering
        this.telephoneNumbers = telephoneNumbers;
    }
    
    public void removeTelephoneNumber(int indexPos){
        if(indexPos >= 0 && indexPos < telephoneNumbers.size()){
            telephoneNumbers.remove(indexPos);
        }
    }
    
    public void addEmailAdress(String emailAdress){ 
        // TODO -- Validering
        emailAdresses.add(emailAdress);
    }
    
    public void setEmailAdresses(ArrayList<String> emailAdresses){
        // TODO -- Validering
        this.emailAdresses = emailAdresses;
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
        String sqlQuery = "UPDATE customer SET " 
            + "first_name = '" + firstName + "', "
            + "last_name = '" + lastName + "', "
            + "address = '" + adress + "', "
            + "postalcode = '" + postalCode + "', "
            + "country = '" + country + "' " 
            + "WHERE customer_id = " + customerID; 
        
        try{
            idb.update(sqlQuery);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + "1st query in save(), Customer.java");
            System.out.println(sqlQuery);
        }
        
        //--------Telefonnummer--------
        ArrayList<String> telephoneNumbersToAdd = fetchTelephoneNumbersToAdd();
        
        Iterator it = telephoneNumbersToAdd.iterator();
        while(it.hasNext()){
            sqlQuery = "INSERT INTO phone (customer_id, phone_number) "
                    + "VALUES ('" + customerID + "', '" + it.next() + "');";
            
            try{
                idb.insert(sqlQuery);
                System.out.println("[SqlQuery]: " + sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + " 1st sqlQuery, in save(), Customer.java");
            }
        }
   
        
        //--------Email--------
        ArrayList<String> emailAdressesToAdd = fetchEmailAdressesToAdd();   
        it = emailAdressesToAdd.iterator();
        while(it.hasNext()){
            sqlQuery = "INSERT INTO mail (customer_id, mail) "
                    + "VALUES ('" + customerID + "', '" + it.next() + "');";
            
            try{
                idb.insert(sqlQuery);
                System.out.println("[SqlQuery]: " + sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + " 3rd sqlQuery, in save(), Customer.java");
            }
        }
        
    }
    
    
    //Funkar inte just nu pga constraints.
    public void delete(){
        try{
            idb.delete("DELETE FROM phone WHERE customer_id = " + customerID);
            idb.delete("DELETE FROM mail WHERE customer_id = " + customerID);
            idb.update("UPDATE sales_order SET customer_ID = NULL WHERE customer_id = " + customerID);
            idb.delete("DELETE FROM customer WHERE customer_id = " + customerID); 

        } catch (InfException ex) {
            System.out.println(ex.getMessage() + "in delete(), Customer.java");
        }
    }
    
    public void insert(){ 
        int newID = 0;
        
        try{
            newID = Integer.parseInt(idb.getAutoIncrement("customer", "customer_id"));
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        
        String sqlQuery = "INSERT INTO customer (customer_id, first_name, last_name, "
                + "address, postalcode, country) "
                + "VALUES (" + newID + ", '" + firstName + "', '" + lastName
                + "', '" + adress + "', '" + postalCode + "', '" + country + "');";
        
        try{
            idb.insert(sqlQuery);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + "1st sqlQuery, in insert(), Customer.java");
        }
        
        //--------Telefonnummer--------
        Iterator it = telephoneNumbers.iterator();
        while(it.hasNext()){
            sqlQuery = "INSERT INTO phone (customer_id, phone_number) "
                    + "VALUES ('" + newID + "', '" + it.next() + "');";
            
            try{
                idb.insert(sqlQuery);
                System.out.println("[SqlQuery]: " + sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + " 2nd sqlQuery, in insert(), Customer.java");
            }
        }
        
        //--------Email-------- 
        it = emailAdresses.iterator();
        while(it.hasNext()){
            sqlQuery = "INSERT INTO mail (customer_id, mail) "
                    + "VALUES ('" + newID + "', '" + it.next() + "');";
            
            try{
                idb.insert(sqlQuery);
                System.out.println("[SqlQuery]: " + sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + " 3rd sqlQuery, in insert(), Customer.java");
            }
        }
        
        
    }
    
    
    
    //helpers till DB-funktionen save()
    private ArrayList<String> fetchTelephoneNumbersToAdd(){
        ArrayList<String> telephoneNumbersDB = fetchTelephoneNumbers();
        ArrayList<String> thisTelephoneNumbers = new ArrayList<String>(telephoneNumbers);
        ArrayList<String> telephoneNumbersToAdd = new ArrayList<String>();
        
        for(String telephoneNumber : thisTelephoneNumbers){
            if(!telephoneNumbersDB.contains(telephoneNumber)){
                telephoneNumbersToAdd.add(telephoneNumber);
            }
        }
        return telephoneNumbersToAdd;
    }
    
    private ArrayList<String> fetchEmailAdressesToAdd(){
        ArrayList<String> emailAdressesDB = fetchEmailAdresses();
        ArrayList<String> thisEmailAdresses = new ArrayList<String>(emailAdresses);
        ArrayList<String> emailAdressesToAdd = new ArrayList<String>();
        
        for(String emailAdress : thisEmailAdresses){
            if(!emailAdressesDB.contains(emailAdress)){
                emailAdressesToAdd.add(emailAdress);
            }
        }
        
        return emailAdressesToAdd;
    }
}

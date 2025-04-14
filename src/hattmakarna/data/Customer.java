/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hattmakarna.data;

import java.util.*;
import oru.inf.InfException;
import hattmakarna.util.Util;
import static hattmakarna.data.Hattmakarna.idb;
import static hattmakarna.util.Validerare.*;

/**
 * @author james
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String customerID;
    private String adress;
    private String postalCode;
    private String country;
    private ArrayList<String> telephoneNumbers;
    private ArrayList<String> emailAdresses;
       
    //---------- Constructors ----------
    
    //För testning, skapa en blank kund.
    public Customer(){
    }
    
    public Customer(HashMap<String, String> customerMap){
        this.customerID = customerMap.get("customer_id");
        this.firstName = customerMap.get("first_name");
        this.lastName = customerMap.get("last_name");
        this.adress = customerMap.get("address");
        this.postalCode = customerMap.get("postalcode");
        this.country = customerMap.get("country");
        this.telephoneNumbers = fetchTelephoneNumbers();
        this.emailAdresses = fetchEmailAdresses();
    }
    
    
    /**
     * Skapar ett 'Customer'-objekt via ett ID.
     * @param customerID The customerID with which to create a 'Customer'-object.
     */
    public Customer(String customerID){
        
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
    
    // -------------------- COPY CONSTRUCTOR --------------------
    public Customer(Customer that){
        this.customerID = that.getCustomerID();
        this.firstName = that.getFirstName();
        this.lastName = that.getLastName();
        this.adress = that.getAdress();
        this.postalCode = that.getPostalCode();
        this.country = that.getCountry();
       
        this.telephoneNumbers = new ArrayList<>(that.getTelephoneNumbers());
        this.emailAdresses = new ArrayList<>(that.getEmailAdresses());
    }
    
    // -------------------- Initialisering --------------------
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
    
    // -------------------- Getters --------------------
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
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
    
    public String getPostalCode(){
        return postalCode;
    }
    
    public String getCountry(){
        return country;
    }
    
    //-------------------- Setters --------------------
    public void setFirstName(String newFirstName){
        if(validateName(newFirstName)){
            this.firstName = newFirstName;  
        }
    }
    
    public void setLastName(String newLastName){
        if(validateName(newLastName)){
            this.lastName = newLastName;  
        }
    }
    
    public void setAdress(String newAdress){
        if(validateAdress(newAdress)){
            this.adress = newAdress;
        }
    }
    
    public void setPostalCode(String newPostalCode){
        if(validatePostalCode(newPostalCode)){
            this.postalCode = newPostalCode;
        }
    }
    
    public void setCountry(String newCountry){
        if(validateCountry(newCountry)){
            this.country = newCountry;
        }
    }
    
    public void addTelephoneNumber(String telephoneNumber){
        if(validatePhoneNumber(telephoneNumber)){
            telephoneNumbers.add(telephoneNumber);
        }
    }
    
    public void setTelephoneNumbers(ArrayList<String> telephoneNumbers){
        this.telephoneNumbers = telephoneNumbers;
    }
    
    public void removeTelephoneNumber(int indexPos){
        if(indexPos >= 0 && indexPos < telephoneNumbers.size()){
            telephoneNumbers.remove(indexPos);
        }
    }
    
    public void addEmailAdress(String emailAdress){ 
        if(validateEmail(emailAdress)){
            emailAdresses.add(emailAdress);
        }
    }
    
    public void setEmailAdresses(ArrayList<String> emailAdresses){
        this.emailAdresses = emailAdresses;
    }
    
    public void removeEmailAdress(int indexPos){
        if(indexPos >= 0 && indexPos < emailAdresses.size()){
            emailAdresses.remove(indexPos);
        }
    }
    
    
    //För att kolla ett objekts 'state' vid debug. 
    @Override
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
    
    //-------------------- DB --------------------
    
    /*Här skickas en oförändrad kopia av kunden in, för att jämföra
    vart eventuell modifiering har skett, och updaterar sedan databasen
    med endast de förändringarna!*/
    public void save(Customer unmodified){
        ArrayList<String> updates = fetchUpdates(unmodified);
        
        if(!updates.isEmpty()){
            String sqlQuery = "UPDATE customer SET " 
            + String.join(",", updates) + " WHERE customer_id  = " 
            + customerID + ";";
        
            try{
                idb.update(sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + "1st query in save(), Customer.java");
                System.out.println(sqlQuery);
            }
        }
        
        
        //DEBUG
        System.out.print("UPDATES: ");
        for(String anUpdate : updates){
            System.out.println(anUpdate);
        }
        
        updateTelephoneNumbers(unmodified);
        
        updateEmailAdresses(unmodified);
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
        
    //----------Helpers till DB-funktionen save()----------
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
    
    private ArrayList<String> fetchTelephoneNumbersToRemove(){
        ArrayList<String> telephoneNumbersDB = fetchTelephoneNumbers();
        ArrayList<String> thisTelephoneNumbers = new ArrayList<String>(telephoneNumbers);
        ArrayList<String> telephoneNumbersToRemove = new ArrayList<String>();
        
        for(String telephoneNumber : telephoneNumbersDB){
            if(!thisTelephoneNumbers.contains(telephoneNumber)){
                telephoneNumbersToRemove.add(telephoneNumber);
            }
        }
        return telephoneNumbersToRemove;
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
    
    private ArrayList<String> fetchEmailAdressesToRemove(){
        ArrayList<String> emailAdressesDB = fetchEmailAdresses();
        ArrayList<String> thisEmailAdresses = new ArrayList<String>(emailAdresses);
        ArrayList<String> emailAdressesToRemove = new ArrayList<String>();
        
        for(String emailAdress : emailAdressesDB){
            if(!thisEmailAdresses.contains(emailAdress)){
                emailAdressesToRemove.add(emailAdress);
            }
        }
        return emailAdressesToRemove;
    }
    
    
    
    
    private ArrayList<String> fetchUpdates(Customer unmodified){
        ArrayList<String> updates = new ArrayList<>();
        
        if(!this.getFirstName().equals(unmodified.getFirstName())){
            updates.add("first_name = '" + this.getFirstName() + "'");
        }
        if(!this.getLastName().equals(unmodified.getLastName())){
            updates.add("last_name = '" + this.getLastName() + "'");
        }
        if(!this.getAdress().equals(unmodified.getAdress())){
            updates.add("address = '" + this.getAdress() + "'");
        }
        if(!this.getPostalCode().equals(unmodified.getPostalCode())){
            updates.add("postalcode = '" + this.getPostalCode() + "'");
        }
        if(!this.getCountry().equals(unmodified.getCountry())){
            updates.add("country = '" + this.getCountry() + "'");
        }
        if(updates.isEmpty()){
            System.out.println("fetchUpdates() returned empty, no updates!");
        }
        return updates;
    }
    
    private void updateTelephoneNumbers(Customer unmodified){
        if(telephoneNumbers.size() > unmodified.getTelephoneNumbers().size()){
            System.out.println("TN IF!!!!!!!!!");
            String sqlQuery = "";
            ArrayList<String> telephoneNumbersToAdd = fetchTelephoneNumbersToAdd();

            Iterator it = telephoneNumbersToAdd.iterator();
            while(it.hasNext()){
                sqlQuery = "INSERT INTO phone (customer_id, phone_number) "
                        + "VALUES ('" + customerID + "', '" + it.next() + "');";

                try{
                    idb.insert(sqlQuery);
                    System.out.println("[SqlQuery]: " + sqlQuery);
                }catch(InfException ex){
                    System.out.println(ex.getMessage() + "if() sqlQuery, in updateTelephoneNumbers(), Customer.java");
                }
            }
        }
        else if(telephoneNumbers.size() < unmodified.getTelephoneNumbers().size()){
            System.out.println("TN ELSE IF!!!!!!!!!");
            String sqlQuery = "";
            ArrayList<String> telephoneNumbersToRemove = fetchTelephoneNumbersToRemove();

            Iterator it = telephoneNumbersToRemove.iterator();
            while(it.hasNext()){
                sqlQuery = "DELETE FROM phone WHERE phone_number = '" + it.next() + "';";

                try{
                    idb.delete(sqlQuery);
                    System.out.println("[SqlQuery]: " + sqlQuery);
                }catch(InfException ex){
                    System.out.println(ex.getMessage() + "else if() sqlQuery, in updateTelephoneNumbers(), Customer.java");
                }
            }
        }
    }
    
    private void updateEmailAdresses(Customer unmodified){
        if(emailAdresses.size() > unmodified.getEmailAdresses().size()){
            String sqlQuery = "";
            ArrayList<String> emailAdressesToAdd = fetchEmailAdressesToAdd();   
            Iterator it = emailAdressesToAdd.iterator();
            while(it.hasNext()){
                sqlQuery = "INSERT INTO mail (customer_id, mail) "
                        + "VALUES ('" + customerID + "', '" + it.next() + "');";

                try{
                    idb.insert(sqlQuery);
                    System.out.println("[SqlQuery]: " + sqlQuery);
                }catch(InfException ex){
                    System.out.println(ex.getMessage() + " if() sqlQuery, in updateEmailAdresses(), Customer.java");
                }
            }
        }
        else if(emailAdresses.size() < unmodified.getEmailAdresses().size()){
            System.out.println("TN ELSE IF!!!!!!!!!");
            String sqlQuery = "";
            ArrayList<String> emailAdressesToRemove = fetchEmailAdressesToRemove();

            Iterator it = emailAdressesToRemove.iterator();
            while(it.hasNext()){
                sqlQuery = "DELETE FROM mail WHERE mail = '" + it.next() + "';";

                try{
                    idb.delete(sqlQuery);
                    System.out.println("[SqlQuery]: " + sqlQuery);
                }catch(InfException ex){
                    System.out.println(ex.getMessage() + "else if() sqlQuery, in updateEmailAdresses(), Customer.java");
                }
            }
        }
    }
}

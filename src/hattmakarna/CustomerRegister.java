/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author james
 */
public class CustomerRegister {
    private final InfDB idb;
    private final ArrayList<Customer> allCustomers;
    
    public CustomerRegister(InfDB idb){
        this.idb = idb;
        allCustomers = initAllCustomers();
    }
    
    private ArrayList<Customer> initAllCustomers(){

        
        String sqlQuery = "SELECT * FROM customer";
        
        ArrayList<HashMap<String, String>> customerMaps = new ArrayList<>();
        ArrayList<Customer> customerList = new ArrayList<>();
        
        try{
            customerMaps = idb.fetchRows(sqlQuery);
        }catch(InfException ex){
            System.out.println(" in initAllCustomers(), CustomerRegistry.java");
        }
        
        for(HashMap<String, String> aCustomerMap : customerMaps){
            customerList.add(new Customer(aCustomerMap, idb));
        }
        
        return customerList;
    }
    
    public ArrayList<Customer> searchByEmail(String emailSearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){
            for(String anEmailAdress : aCustomer.getEmailAdresses()){
                if(anEmailAdress.startsWith(emailSearch)){
                    searchResult.add(aCustomer);
                }
            }
        }
        return searchResult;
    }

    public ArrayList<Customer> searchByName(String nameSearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){
            String fullName = aCustomer.getFirstName() + " " 
                + aCustomer.getLastName();
            if(fullName.startsWith(nameSearch)){
                searchResult.add(aCustomer);
            }
        }
        return searchResult;
    }
    
    public ArrayList<Customer> searchByTelephone(String telephoneSearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){
            for(String aTelephoneNumber : aCustomer.getTelephoneNumbers()){
                if(aTelephoneNumber.startsWith(telephoneSearch)){
                    searchResult.add(aCustomer);
                }
            }
        }
        return searchResult;
    }
    
    public Customer getCustomer(String customerID){
        for(Customer aCustomer : allCustomers){
            if(customerID.equals(aCustomer.getCustomerID())){
                return aCustomer;
            }
        }
        System.out.println("getCustomer(), in CustomerRegister.java returned NULL!");
        return null;
    }
            
    @Override
    public String toString(){
        String output = "";
        Iterator it = allCustomers.iterator();
        
        while(it.hasNext()){
            output += it.next().toString();
            if(it.hasNext()){
                output += "\n";
            }
        }
        return output;
    }
    
}

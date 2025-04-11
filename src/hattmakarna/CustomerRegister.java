/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import oru.inf.InfException;
import static hattmakarna.Hattmakarna.idb;

/**
 *
 * @author james
 */
public class CustomerRegister {
    private final ArrayList<Customer> allCustomers;
    
    public CustomerRegister(){
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
            customerList.add(new Customer(aCustomerMap));
        }
        
        return customerList;
    }
    
    public ArrayList<Customer> searchByEmail(String emailSearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){
            for(String anEmailAdress : aCustomer.getEmailAdresses()){
                if(anEmailAdress.toLowerCase().startsWith(emailSearch.toLowerCase())){
                    searchResult.add(aCustomer);
                }
            }
        }
        return searchResult;
    }

    public ArrayList<Customer> searchByName(String nameSearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){

            String firstName = aCustomer.getFirstName().toLowerCase();
            String lastName = aCustomer.getLastName().toLowerCase();
            if(firstName.startsWith(nameSearch.toLowerCase()) || lastName.startsWith(nameSearch.toLowerCase())){

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
        System.out.println("getCustomer(), in CustomerRegister.java returned NULL! :(");
        return null;
    }
    
    public Customer getCustomer(int indexPos){
        if(indexPos >= 0 && indexPos < allCustomers.size()){
            return allCustomers.get(indexPos);
        }
        System.out.println("getCustomer(int), in CustomerRegister.java returned NULL! :(");
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

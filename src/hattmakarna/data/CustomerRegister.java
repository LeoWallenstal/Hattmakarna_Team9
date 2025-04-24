/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import oru.inf.InfException;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.util.PrintDebugger;
import java.util.Comparator;
import java.util.function.Function;

/**
 *
 * @author james
 */
public class CustomerRegister {
    private final ArrayList<Customer> allCustomers;
    
    /**
     * Fetches from DB and initializes an ArrayList of customers.
     */
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
        
        nameSearch = nameSearch.toLowerCase();
        
        for(Customer aCustomer : allCustomers){
            String fullName = aCustomer.getFullName().toLowerCase();
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
    
    public ArrayList<Customer> searchByCountry(String countrySearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){
            if(aCustomer.getCountry().startsWith(countrySearch)){
                searchResult.add(aCustomer);
            }
        }
        return searchResult;
    }
    
    public ArrayList<Customer> searchByPostalCode(String postalCodeSearch){
        ArrayList<Customer> searchResult = new ArrayList<>();
        
        for(Customer aCustomer : allCustomers){
            if(aCustomer.getPostalCode().startsWith(postalCodeSearch)){
                searchResult.add(aCustomer);
            }
        }
        return searchResult;
    }
    
    
    /**
     * A generalized function to sort a list of customers.
     * @param fieldExtractor The function which gets the field to compare; e.g. Customer::getFirstName, Customer::getTelephoneNumber.
     * @param ascending toggles ascending or descending.
     * @return Returns the sorted list.
     */
    public ArrayList<Customer> sortBy(Function<Customer, String> fieldExtractor, boolean ascending) {
        ArrayList<Customer> sorted = new ArrayList<>(allCustomers);
        Comparator<Customer> comparator = Comparator.comparing(fieldExtractor, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        sorted.sort(comparator);
        return sorted;
    }
    
    public Customer getCustomer(String customerID){
        for(Customer aCustomer : allCustomers){
            if(customerID.equals(aCustomer.getCustomerID())){
                return aCustomer;
            }
        }
        PrintDebugger.error(("This function with parameter (customerID): " + customerID + " returned null!"));
        return null;
    }
    
    public Customer getCustomer(int indexPos){
        if(indexPos >= 0 && indexPos < allCustomers.size()){
            return allCustomers.get(indexPos);
        }
        PrintDebugger.error(("This function with parameter (indexPos): " + indexPos + " returned null!"));
        return null;
    }
    
    public void remove(int indexPos){
        if(indexPos > 0 && indexPos < allCustomers.size()){
            allCustomers.remove(indexPos);
        }
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
    
    public ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }
    
    public void add(Customer aCustomer){
        if(!customerExists(aCustomer)){
            allCustomers.add(aCustomer);
        }
    }
    
    public boolean customerExists(Customer aCustomer){
        for(Customer registerCustomer : allCustomers){
            if(registerCustomer.equals(aCustomer)){
                return true;
            }
        }
        return false;
    }
    
    public Customer getCustomerByEmail(String email) {
      for (Customer aCustomer : allCustomers) {
          for (String e : aCustomer.getEmailAdresses()) {
              if (e.equalsIgnoreCase(email)) {
                  return aCustomer;
              }
          }
      }
      return null;
    }
   
}

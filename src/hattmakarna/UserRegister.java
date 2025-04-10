/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import oru.inf.InfDB;
import java.util.*;
import oru.inf.InfException;
/**
 *
 * @author walle
 */
public class UserRegister {
    
    private final InfDB idb;
    private final ArrayList<User> allUsers;
    public UserRegister(InfDB idb){
        this.idb = idb;
        allUsers = initAllUsers();
    }
    
    public ArrayList<User> getAllUsers(){
        return allUsers;
    }
    
    public User getUser(String userId){
        for(User aUser : allUsers){
            if(aUser.getId().equals(userId)){
                return aUser;
            }
        }
        System.out.println("getUser(), in UserRegister.java returned NULL!");
        return null;
    }
    
    
    private ArrayList<User> initAllUsers(){
        String sqlQuery = "SELECT * FROM user";
        ArrayList<User> userList = new ArrayList<>();
        try{
           ArrayList<HashMap<String, String>> userMaps = idb.fetchRows(sqlQuery);  
           for(HashMap<String, String> userMap : userMaps ) {
               userList.add(new User(userMap, idb));
           }            
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return userList;
    }
}

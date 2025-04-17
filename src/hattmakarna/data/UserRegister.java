/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import oru.inf.InfDB;
import java.util.*;
import oru.inf.InfException;
import static hattmakarna.data.Hattmakarna.idb;

/**
 *
 * @author walle
 */
public class UserRegister {
    
    private final ArrayList<User> allUsers;
    
    public UserRegister(){
        allUsers = initAllUsers();
    }
    
    public ArrayList<User> getAllUsers(){
        return allUsers;
    }
    
    public User getUser(String userId){
        for(User aUser : allUsers){
            if(aUser.getID().equals(userId)){
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
               userList.add(new User(userMap));
           }            
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return userList;
    }
}

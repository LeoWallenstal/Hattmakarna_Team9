/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author walle
 */
public class User {
    
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    
    public User (HashMap<String, String> userMap, InfDB idb){
        userId = userMap.get("user_id");
        firstName = userMap.get("first_name");
        lastName = userMap.get("last_name");
        email = userMap.get("email");
    }
    
    public User(String aUserId, InfDB idb) {
        this(fetchUserMap(aUserId, idb), idb);
    }

    private static HashMap<String, String> fetchUserMap(String userId, InfDB idb) {
        try {
            String sqlQuery = "SELECT * FROM user WHERE user_id = '" + userId + "'";
            return idb.fetchRow(sqlQuery);
        } catch (InfException ex) {
            System.out.println("Error fetching user: " + ex.getMessage());
            return new HashMap<>();
        }
    }
    
    public String getId(){
        return userId;
    }
    
    public String getFullName(){
        return firstName + " " + lastName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
}

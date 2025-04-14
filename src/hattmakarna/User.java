/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import static hattmakarna.Hattmakarna.idb;
import java.util.HashMap;
import oru.inf.InfException;
import static hattmakarna.Validerare.*;
import java.util.ArrayList;

/**
 *
 * @author walle
 */
public class User {
    
    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String pwCandidate;
    private boolean isAdmin;
    
    //---------- Constructors ----------
    
    /* Tom för att kunna testa lite, eventuellt vid skapande av ny användare
    i databasen? */
    public User(){
        
    }
    
    public User (HashMap<String, String> userMap){
        userID = userMap.get("user_id");
        firstName = userMap.get("first_name");
        lastName = userMap.get("last_name");
        email = userMap.get("email");
        pwCandidate = "";
        isAdmin = isAdmin();
    }
    
    public User(String aUserId) {
        this(fetchUserMap(aUserId));
    }

    private static HashMap<String, String> fetchUserMap(String userId) {
        try {
            String sqlQuery = "SELECT * FROM user WHERE user_id = '" + userId + "'";
            return idb.fetchRow(sqlQuery);
        } catch (InfException ex) {
            System.out.println("Error fetching user: " + ex.getMessage());
            return new HashMap<>();
        }
    }
    
    // ---------- COPY CONSTRUCTOR ----------
    
    public User(User that){
        this.userID = that.getID();
        this.firstName = that.getFirstName();
        this.lastName = that.getLastName();
        this.email = that.getEmail();
        this.isAdmin = that.isAdmin();
    }
    
    // ---------- Getters ----------
    
    public String getID(){
        return userID;
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
    
    public String getPwCandidate(){
        return pwCandidate;
    }
    
    public boolean isAdmin(){
        String sqlQuery = "SELECT user_id FROM ADMIN WHERE user_id = "
            + userID + ";";
        String adminID = "";
        try{
            adminID = idb.fetchSingle(sqlQuery);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + " in isAdmin(), User.java");
        }
        return userID.equals(adminID);
    }
    
    //För att kolla ett objekts state vid debug.
    @Override
    public String toString(){
        String output = "[UserID]: " + userID + "\n[First name]: " + firstName
            + "\n[Last name]: " + lastName + "\n[Email]: " + email;
        
        return output;
    }
    
    // ---------- Setters ----------
    
    public void setFirstName(String newName){
        if(validateName(newName)){
            this.firstName = newName;
        }
    }
    
    public void setLastName(String newName){
        if(validateName(newName)){
            this.lastName = newName;
        }
    }
    
    public void setEmail(String newEmail){
        if(validateEmail(newEmail)){
            this.email = newEmail;
        }
    }
    
    public void setAdmin(User userLoggedIn, boolean value){
        if(userLoggedIn.isAdmin()){
            this.isAdmin = value;
        }
    }
    
    public void setPWCandidate(String pw){
        this.pwCandidate = pw;
    }
    
    public void setID(String ID){
        if(userID == null || userID.isEmpty()){
            userID = ID;
        }
    }
    
    // ---------- DB ----------
    
    public void save(User unmodified){
        ArrayList<String> updates = fetchUpdates(unmodified);
        
        String sqlQuery = "";
        
        //Om listan med updates är tom, görs ingen sqlQuery.
        if(!updates.isEmpty()){
            sqlQuery = "UPDATE user SET "
            + String.join(",", updates) + " WHERE user_id = "
            + userID + ";";
        
            try{
                idb.update(sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + "\n1st query in save(), User.java");
                System.out.println(sqlQuery);
            }
        }
        
        //---------- ADMIN ----------
        
        if(!updateAdmin(unmodified)){
            
            if(isAdmin){
                sqlQuery = "INSERT INTO admin (user_id) " +
                "VALUES ('" + userID + "');";
                try{
                    idb.insert(sqlQuery);
                }catch(InfException ex){
                    System.out.println(ex.getMessage() + "\n2nd query in save(), User.java");
                    System.out.println(sqlQuery);
                }
                //ADMIN DEBUG
                System.out.println("");
                System.out.println("ADMIN GRANTED TO " + this.getFullName());
            }
            else{
                sqlQuery = "DELETE FROM admin  " +
                "WHERE user_id = " + userID + ";";
                try{
                    idb.delete(sqlQuery);
                }catch(InfException ex){
                    System.out.println(ex.getMessage() + "\n3rd query in save(), User.java");
                    System.out.println(sqlQuery);
                }
                //ADMIN DEBUG
                System.out.println("ADMIN TAKEN AWAY FROM " + this.getFullName());
            }  
        }
        //save() DEBUG
        System.out.print("UPDATES: ");
        for(String anUpdate : updates){
            System.out.println(anUpdate);
        }
    }
    
    public void create(){
        int newID = 0;
        
        try{
            newID = Integer.parseInt(idb.getAutoIncrement("user", "user_id"));
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        this.setID(String.valueOf(newID));
        
        String sqlQuery = "INSERT INTO user (user_id, first_name, last_name, "
                + "email, password) "
                + "VALUES (" + newID + ", '" + firstName + "', '" + lastName
                + "', '" + email + "', '" + pwCandidate + "');";
        
        try{
            idb.insert(sqlQuery);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + "1st sqlQuery, in insert(), User.java");
        }
        
        // ---------- ADMIN ----------
        if(isAdmin){
            sqlQuery = "INSERT INTO admin (user_id) " +
                "VALUES (" + newID + ");";
            try{
            idb.insert(sqlQuery);
            }catch(InfException ex){
                System.out.println(ex.getMessage() + "2nd query in save(), User.java");
                System.out.println(sqlQuery);
            }
            //DEBUG
            System.out.println("ADMIN GRANTED TO " + this.getFullName());
        }
        
        
    }
    
    public void delete(){
        try{
            idb.delete("DELETE FROM task WHERE user_id = " + userID);
            idb.delete("DELETE FROM user WHERE user_id = " + userID);
            if(isAdmin){
                idb.delete("DELETE from admin WHERE user_id = " + userID);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage() + "in delete(), Customer.java");
        }
    }
    
    // ---------- Helpers till DB ----------
    
    private ArrayList<String>fetchUpdates(User unmodified){
        ArrayList<String> updates = new ArrayList<>();
        
        if(!this.getFirstName().equals(unmodified.getFirstName())){
            updates.add("first_name = '" + this.getFirstName() + "'");
        }
        if(!this.getLastName().equals(unmodified.getLastName())){
            updates.add("last_name = '" + this.getFirstName() + "'");
        }
        if(!this.getEmail().equals(unmodified.getEmail())){
            updates.add("email = '" + this.getEmail() + "'"); 
        }
        if(pwCandidate.isEmpty() || !pwMatchesDB(pwCandidate)){
            updates.add("password = '" + this.getPwCandidate() + "'");
            pwCandidate = "";
        }
        if(updates.isEmpty()){
            System.out.println("fetchUpdates() returned empty, no updates!");
        }
        return updates;
    }
    
    private boolean updateAdmin(User unmodified){
        return this.isAdmin == unmodified.isAdmin();
    }
    
    private boolean pwMatchesDB(String pwCandidate){
        String currentPW = "";
        try{
            currentPW = idb.fetchSingle("SELECT password FROM user"
                + " WHERE user_id = " + userID + ";"); 
            
        }catch(InfException ex){
            System.out.println(ex.getMessage() + " in pwMatchesDB(), User.java");
        }
        
        if(currentPW == null){
            return false;
        }
        
        return currentPW.equals(pwCandidate);
    }
      
}

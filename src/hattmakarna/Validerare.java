/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

/**
 *
 * @author Gastinlogg
 */
public class Validerare {
    
    
    
    public static boolean validateEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    
    public static boolean validatePhoneNumber(String number){
        return number.matches("^\\+?[0-9\\-]+$");
    }
    
    public static boolean validateName(String name){
        return name.matches("^[A-Za-zÅÄÖåäö\\-]+$");
    }
    
    
}

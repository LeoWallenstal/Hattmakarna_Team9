/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.util;

/**
 *
 * @author Gastinlogg
 */
public class Validerare {
    
    
    //tom String inte tillåtet.
    public static boolean validateEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    
    //'-' inte stillåtet, mellanslag inte tillåtet, inte heller tom String.
    public static boolean validatePhoneNumber(String number){
        return number.matches("^\\d{10}$");
    }
    
    //'-' tillåtet, mellanslag inte tillåtet, inte heller tom String.
    public static boolean validateName(String name){
        return name.matches("^[A-Za-zÅÄÖåäö\\-]+$");
    }
    
    /*Endast fem siffor tillåtet, whitespace mellan tredje och fjärde siffran
    tillåtet. 55534 och 555 34 godkänt. */
    public static boolean validatePostalCode(String postalCode) {
        return postalCode.matches("^[0-9]{3}\\s?[0-9]{2}$");
    }
    
    public static boolean validateCountry(String country){
        return country.matches("^[a-zA-Z\\s\\-]+$");
    }
    
    public static boolean validateAdress(String adress){
        return adress.matches("^[a-zA-ZåäöÅÄÖ0-9](?!.*[ ,\\-]{2})[a-zA-ZåäöÅÄÖ0-9 ,\\-]*[a-zA-ZåäöÅÄÖ0-9]$");
    }
    
    
    
}

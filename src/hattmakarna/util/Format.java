/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.util;

/**
 *
 * @author james
 */
public class Format {
     public static String postalCode(String postalCode){
        if(postalCode.matches("^\\\\d{5}$")){
            return postalCode;
        }
        else if(postalCode.matches("^\\d{3} \\d{2}$")){
            String firstPart = postalCode.substring(0,3);
            String secondPart = postalCode.substring(4,6);
            return firstPart + secondPart;
        }
        return postalCode;
    }
}

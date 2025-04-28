/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;
import static hattmakarna.data.Hattmakarna.idb;
import hattmakarna.data.User;
import hattmakarna.util.PrintDebugger;
import java.util.ArrayList;
import oru.inf.InfException;

/**
 * @author james
 */
public class EmailGenerator {
    private ArrayList<String> allUserEmails;
    
    private static final String EMAIL_DOMAIN = "@hatt.se";
    
    public EmailGenerator(){
        String sqlQuery = "SELECT email FROM user;";
        
        try{
            allUserEmails = new ArrayList<>(idb.fetchColumn(sqlQuery));
        }catch(InfException ex){
            PrintDebugger.error(ex, sqlQuery, "AllUserEmails couldn't initialize properly!");
        }
    }
    
    /**
     * 
     * @param prefixToCompare The prefix to compare.
     * @return true/false whether or not the prefix matches a prefix in 
     * allUserEmails
     */
    public boolean contains(String prefixToCompare){
        for(String anEmail : allUserEmails){
            String prefix = anEmail.split("@")[0];
            if(prefix.equals(prefixToCompare)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param aUser The user to whom the email is generated.
     * @return a fully generated email formatted as "firstname.lastname@hatt.se".
     */
    public String generateEmail(User aUser){
        String emailPrefix = formatPrefix(aUser.getFirstName(), aUser.getLastName());
        if(contains(emailPrefix)){
            emailPrefix += aUser.getID();
        }
        emailPrefix += EMAIL_DOMAIN;
        return emailPrefix;
    }
    
    /**
     * 
     * @param firstName of the user to whom the email is generated.
     * @param lastName of the user to whom the email is generated.
     * @return a formatted prefix of the email, as "firstname.lastname".
     */
    private String formatPrefix(String firstName, String lastName){
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();
        String emailPrefix = firstName + "." + lastName;
        emailPrefix = normalizePrefix(emailPrefix);
        return emailPrefix;
    }
    
    /**
     * 
     * @param prefix the email prefix to be normalized
     * @return a normalized email-prefix.
     */
    private String normalizePrefix(String prefix){
       char[] prefixArr = prefix.toCharArray();

       char[] normalized = new char[prefixArr.length];

       for(int i = 0; i < prefixArr.length; i++){
           normalized[i] = getCharNormalization(prefixArr[i]);
       }
       return new String(normalized);
    }
    
    /**
     * This function for now only normalizes swedish characters, [åäö].
     * @param c the character to normalize, if 'c' is not å, ä or ö, 'c'
     * is returned without modification.
     * @return a normalized character [a-z].
     */
    private char getCharNormalization(char c){
        if(c == 'å' || c == 'ä'){
            return 'a';
        }
        else if(c == 'ö'){
            return 'o';
        }
        return c;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import java.util.*;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Användaren
 */
public class Specification {

    private InfDB idb;
    private String s_ID;
    private String description;
    private String size;
    private String hat_ID;
    private String skiss;

    public Specification(HashMap<String, String> hat_SpecMap, InfDB idb) {
        this.idb = idb;
        
        this.s_ID = hat_SpecMap.get("spec_id");
        this.description = hat_SpecMap.get("beskrivning");
        this.size = hat_SpecMap.get("size");
        this.hat_ID = hat_SpecMap.get("hat_id");
        this.skiss = hat_SpecMap.get("skiss");
    }
    
    public Specification(String specificationID, InfDB idb){
        this.idb = idb;
        
        HashMap<String, String> aSpecification = new HashMap<>();
        String sqlQuery = "SELECT * FROM hat_spec WHERE ID = " + specificationID;
        
        try{
            aSpecification = idb.fetchRow(sqlQuery);
        }catch(InfException ex){
            System.out.println(ex.getMessage() + ", in Specification(), Specification.java");
            
        
            
        }
    }
    
    public String getSpecID(){
    return s_ID;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getHatID() {
        return hat_ID;
    }

    public String getSkiss() {
        return skiss;
    }

    public void setDesciption(String newDescription) {
//    if (Validerare.){}
        this.description = newDescription;
    }

    public void setSize(String newSize) {
//    if (Validerare.){}
        this.size = newSize;
    }

    public void setSkiss(String newSkiss) {
        // if (Validerare.){}
        this.skiss = newSkiss;
    }
    
    
    
}

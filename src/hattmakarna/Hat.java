/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.*;

/**
 *
 * @author Gastinlogg
 */
public class Hat {
    
    private InfDB idb;
    private String hatId;
    private ArrayList<String> materialList;
    private ArrayList<MaterialOrder> materialBehov;
    private String modelId;
    private String orderId;
    
    public Hat(InfDB idb){
        
    this.idb = idb;
    
    
    }
    
    public Hat(InfDB idb, String hatId){
        
        this.idb = idb;
        
        HashMap<String, String> aHat = new HashMap<>();
        String sqlQuery = "SELECT * FROM hat WHERE hat_id = " + hatId;
        
        try {
            aHat = idb.fetchRow(sqlQuery);
        }
        catch(InfException ex){
            System.out.println(ex.getMessage() + ", in Hat(), Hat.java");
        }
        
        ArrayList<String> materials = new ArrayList<>();
        sqlQuery = "SELECT material_id FROM hat_material WHERE hat_id = " + hatId;
        
        try{
            materials = idb.fetchColumn(sqlQuery);
        }
        catch(InfException ex){
            System.out.println(ex.getMessage() + ", in Hat(), Hat.java");
        }
        
        this.hatId = hatId;
        this.modelId = aHat.get("model_id");
        this.orderId = aHat.get("order_id");
        this.materialList = materials;
        
    }
    
    public String gethatId(){
        return hatId;
    }
    
    public String getModelId(){
        return modelId;
    }
    
    public String getOrderId(){
        return orderId;
    }
}

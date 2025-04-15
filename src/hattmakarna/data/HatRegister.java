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
 * @author sebas
 */
public class HatRegister {
    private final ArrayList<Hat> allHats;
    
    
    public HatRegister(InfDB idb){
        allHats = initAllHats();
    }
    
    public ArrayList<Hat> getAllHats(){
        return allHats;
    }
    
    public Hat getHat(String hatId){
        for(Hat aHat : allHats){
            if(aHat.gethatId().equals(hatId)){
                return aHat;
            }
        }
        System.out.println("getHat(), in HatRegister.java returned NULL!");
        return null;
    }
    
    
    private ArrayList<Hat> initAllHats(){
        String sqlQuery = "SELECT hat_id,model_id, order_id  FROM hat";
        ArrayList<Hat> hatList = new ArrayList<>();
        try{
           ArrayList<HashMap<String, String>> rows = idb.fetchRows(sqlQuery);  
           for(HashMap<String, String> row : rows) {
               String hatId = row.get("hat_id");
               String modelId = row.get("model_id");
               String orderId = row.get("order_id");
             
                hatList.add(new Hat(idb, hatId, modelId, orderId));
           }            
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return hatList;
    }
    public ArrayList<HashMap<String,String>>getDetailedHatRows(){
        String sql = "SELECT name,price,size,beskrivning ,hat.hat_id,hat_model.model_id,spec_id FROM  hat_model join hat join hat h on hat_model.model_id = h.model_id join hat_spec hs on hat.hat_id = hs.hat_id";
                        
                        try {
                            return idb.fetchRows(sql);
                        } catch (InfException e) {
                            System.out.println("Fel: " + e.getMessage());
                            return new ArrayList<>();
                        }
    }
      
                                                                  
    
}

    


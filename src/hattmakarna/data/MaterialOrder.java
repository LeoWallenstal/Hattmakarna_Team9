/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.*;
import static hattmakarna.data.Hattmakarna.idb;

/**
 *
 * @author Gastinlogg
 */
public class MaterialOrder {
    
    private String hatID;
    private ArrayList<HashMap<String, String>> materialList;
    
    public MaterialOrder(String hatID){
        this.hatID = hatID;
        materialList = new ArrayList<>();
       
        try{
            String sqlQuery = "SELECT material_id, amount, color FROM hat_material WHERE hat_id = " + hatID 
                    + " AND hat_id IN ( SELECT hat_id FROM hat WHERE order_id IN (SELECT order_id FROM sales_order WHERE material_ordered = 0))";
            ArrayList<HashMap<String, String>> result = idb.fetchRows(sqlQuery);
            
            materialList.addAll(result);
            
        }
        catch(InfException ex){
            System.out.println(ex.getMessage() + ", in MaterialOrder(), MaterialOrder.java");
        }
        
    }    
        
    
    public ArrayList<HashMap<String,String>> getMaterialList(){
        return materialList;
    }
    
    public void printMaterial(){
        for(HashMap<String, String> row : materialList){
            System.out.println("Material: " + row.get("material_id") +
                    ", färg: " + row.get("color") + ", " + row.get("amount"));
            
        }
    }
    
    public HashMap<String, Double> getTotalMaterialAmount() {
        HashMap<String, Double> total = new HashMap<>();
        
        for(HashMap<String, String> row: materialList) {
            String materialId = row.get("material_id");
            String amountRow = row.get("amount");
            double amount = 0.0;
            
            if(amountRow != null){
                amount = Double.parseDouble(amountRow);
            }
            
            total.put(materialId, total.getOrDefault(materialId, 0.0) + amount);
        }
        
        return total;
    }
    
}

package hattmakarna;

import oru.inf.InfDB;
import oru.inf.InfException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author linahanssons
 */
public class Model {
    
    private String name;
    private double price;
    private String modelId;
    private InfDB idb;
    
    public Model (String modelId, InfDB idb) {
        this.idb = idb;
        this.modelId = modelId;
        this.name = fetchSingle(modelId, "name");
        try {
    this.price = Double.parseDouble(fetchSingle(modelId, "price"));
} catch (NumberFormatException e) {
    System.out.println("Fel vid konvertering av pris: " + e.getMessage());
    this.price = 0.0; // eller throw vidare om det är kritiskt
}
    }    
     private String fetchSingle(String id, String column) {
        String result = "";
        String query = "SELECT " + column + " FROM hat_model WHERE model_id = " + id;
        try {
            result = idb.fetchSingle(query);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
     
         public void addMaterial(String name, double price) {
        String sqlAddQuery = "INSERT INTO hat_model VALUES ('" + name + "', " + price + ")";

        try {
            idb.insert(sqlAddQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
           public void removeMaterial(String modelId) {
        String sqlRemoveQuery = "DELETE FROM hat_model WHERE model_id = '" + modelId + "'";

        try {
            idb.delete(sqlRemoveQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    
    public void updatePrice(double newPrice) {
    String sql = "UPDATE hat_model SET price = " + newPrice + " WHERE model_id = '" + modelId + "'";

    try {
        idb.update(sql);
        this.price = newPrice; // uppdatera också objektets interna värde
    } catch (InfException ex) {
        System.out.println("Fel vid uppdatering av pris: " + ex.getMessage());
    }
}
    
        public void updateName(String newName) {
    String sql = "UPDATE hat_model SET name = '" + newName + "' WHERE model_id = '" + modelId + "'";

    try {
        idb.update(sql);
        this.name = newName; // uppdatera också objektets interna värde
    } catch (InfException ex) {
        System.out.println("Fel vid uppdatering av namn: " + ex.getMessage());
    }
}

}
           
     

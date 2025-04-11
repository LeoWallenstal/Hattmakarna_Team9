package hattmakarna;

import oru.inf.InfException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import static hattmakarna.Hattmakarna.idb;
import java.util.HashMap;
import oru.inf.InfDB;
import java.util.*;
import oru.inf.InfException;

/**
 *
 * @author linahanssons
 */
public class Model {

    private String name;
    private String price;
    private String modelId;
    private InfDB idb;

    public Model(HashMap<String, String> modelMap) {
        modelId = modelMap.get("model_id");
        name = modelMap.get("name");
        price = modelMap.get("price");
    }
    
    public Model(HashMap<String, String> modelMap, InfDB idb) {
    this.idb = idb;
    this.modelId = modelMap.get("model_id");
    this.name = modelMap.get("name");
    
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

    public String getPrice() {
        return price;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public void updatePrice(String newPrice) {
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

package hattmakarna.data;

import oru.inf.InfException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import static hattmakarna.data.Hattmakarna.idb;
import java.util.HashMap;
import oru.inf.InfDB;
import java.util.*;
import oru.inf.InfException;

/**
 *
 * @author linahanssons
 */
public class Model extends DatabaseObject {

    private String name;
    private double price;
    private int model_id;

    public Model(String id) {
        super(id);
    }

    public Model(HashMap<String, String> modelMap) {
        model_id = Integer.parseInt(modelMap.get("model_id"));
        name = modelMap.get("name");
        System.out.println(modelMap.get("price"));
        price = Double.parseDouble(modelMap.get("price"));
    }

    public void addMaterial(String name, double price) {
        String sqlAddQuery = "INSERT INTO hat_model VALUES ('" + name + "', " + price + ")";

        try {
            idb.insert(sqlAddQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeMaterial(String modelID) {
        String sqlRemoveQuery = "DELETE FROM hat_model WHERE model_id = '" + modelID + "'";

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

    public String getModelID() {
        return String.valueOf(model_id);
    }

    public void setModelId(String modelID) {
        this.model_id = Integer.parseInt(modelID);
    }

    public void updatePrice(String modelID, double newPrice) {
        String sql = "UPDATE hat_model SET price = " + newPrice + " WHERE model_id = '" + modelID + "'";

        try {
            idb.update(sql);
            this.price = newPrice; // uppdatera också objektets interna värde
        } catch (InfException ex) {
            System.out.println("Fel vid uppdatering av pris: " + ex.getMessage());
        }
    }

    public void updateName(String newName) {
        String sql = "UPDATE hat_model SET name = '" + newName + "' WHERE model_id = '" + model_id + "'";

        try {
            idb.update(sql);
            this.name = newName; // uppdatera också objektets interna värde
        } catch (InfException ex) {
            System.out.println("Fel vid uppdatering av namn: " + ex.getMessage());
        }
    }

    @Override
    protected String getTabelName() {
        return "hat_model";
    }

    @Override
    protected String getIdAttributeName() {
        return "model_id";
    }

    @Override
    protected String getIdString() {
        return String.valueOf(model_id);
    }

    @Override
    protected void setIdString(String id) {
        this.model_id = Integer.parseInt(id);
    }

}

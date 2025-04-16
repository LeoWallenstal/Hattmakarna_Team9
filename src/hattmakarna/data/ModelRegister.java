/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;
import java.util.*;
import oru.inf.InfException;
import static hattmakarna.data.Hattmakarna.idb;
import oru.inf.InfDB;


/**
 *
 * @author sebas
 */
//Klass för modellregister
public class ModelRegister {
    
    private final ArrayList<Model> allModels;
   
 public ModelRegister(InfDB idb) {
    this.allModels = initAllModels(idb);
}
 /**
 * Används i klasser där vi inte vill skicka in idb manuellt.
 * Hämtar idb från Hattmakarna.idb
 */
public ModelRegister() {
        this.allModels = initAllModels(Hattmakarna.idb); // använder redan er globala idb
    }
  
    
    public ArrayList<Model> getAllHats(){
        return initAllModels(Hattmakarna.idb);
        
    }

    public Model getModel(String modelID){
        for(Model aModel : allModels){
            if(aModel.getModelID().equals(modelID)){
                return aModel;
            }
        }
        System.out.println("getModel(), in ModelRegister.java returned NULL!");
        return null;
    }


    private ArrayList<Model> initAllModels(InfDB idb) {
        String sqlQuery = "SELECT model_id, price, name FROM hat_model";
        ArrayList<Model> modelList = new ArrayList<>();
        try {
            ArrayList<HashMap<String, String>> rows = idb.fetchRows(sqlQuery);
            for (HashMap<String, String> row : rows) {
                modelList.add(new Model(row)); // 💡 Här använder vi rätt konstruktor
            }
        } catch (InfException ex) {
            System.out.println("Fel vid hämtning av modeller: " + ex.getMessage());
        }
        return modelList;
    }
    
}
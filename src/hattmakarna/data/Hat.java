/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.*;
import static hattmakarna.data.Hattmakarna.idb;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gastinlogg
 */
public class Hat extends DatabaseObject {

    private final String id = UUID.randomUUID().toString();

    /**
     * ----- DATABAS FÄLT ------
     */
    private int hat_id;
    private int model_id;
    private int order_id;
    private double price;
    private String size;

    @Deprecated
    private ArrayList<MaterialOrder> materialBehov;

    private List<MaterialHat> materials;

    private boolean isSpecial;
    private Specification specification;

    public Hat() {
        specification = new Specification();
        materials = new ArrayList<>();
    }

    public Hat(String hatId) {
        super(hatId);

        try {
            String specId = Hattmakarna.idb.fetchSingle("select spec_id from hat_spec where hat_id = " + hatId);

            if (specId != null && specId.isEmpty()) {
                specification = new Specification(specId);
            }

            materials = new ArrayList<>();
            // Hämta material
            Hattmakarna.idb.fetchColumn("select material_hat_id from hat_material where hat_id = " + hatId).forEach(e -> {
                materials.add(new MaterialHat(e));
            });

        } catch (InfException ex) {
            Logger.getLogger(Hat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MaterialHat> getMaterials() {
        return materials;
    }

    public String getId() {
        return id;
    }

    public Specification getSpecification() {
        return specification;
    }

    @Deprecated
    public Hat(InfDB idb, String hatId, String modelId, String orderId) {

        this.hat_id = Integer.parseInt(hatId);
        this.model_id = Integer.parseInt(modelId);
        this.order_id = Integer.parseInt(orderId);
        //this.materialList = materials;
        isSpecial = false;
        setSpecial();
        this.price = 0;
    }

    private void setSpecial() {
        String query = "SELECT model_id FROM hat_model WHERE name = 'Special'";
        try {
            String specialId = idb.fetchSingle(query);
            if (String.valueOf(model_id).equals(specialId)) {
                isSpecial = true;
            }
        } catch (InfException ex) {
            Logger.getLogger(Hat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String gethatId() {

        return String.valueOf(hat_id);
    }

    public Model getModel() {
        return new Model(String.valueOf(model_id));
    }

    public String getModelId() {
        return String.valueOf(model_id);
    }

    public String getOrderId() {
        return String.valueOf(order_id);
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object e) {

        if (e.getClass() != this.getClass()) {
            return false;
        }

        Hat h = (Hat) e;

        return h.getHatId() == this.getHatId();

    }

    public String getHatId() {
        return String.valueOf(hat_id);
    }

    public void setModelId(String modelId) {
        this.model_id = Integer.parseInt(modelId);
    }

    public ArrayList<MaterialOrder> getMaterialBehov() {
        return materialBehov;
    }

    public void setMaterialBehov(ArrayList<MaterialOrder> materialBehov) {
        this.materialBehov = materialBehov;
    }

    public boolean isIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    @Override
    public int hashCode() {
        if (!isSpecial) {
            return Objects.hash(hat_id, model_id, 0);
        } else {
            return Objects.hash(hat_id, model_id, id);
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    protected String getTabelName() {
        return "hat";
    }

    @Override
    protected String getIdAttributeName() {
        return "hat_id";
    }

    @Override
    protected String getIdString() {
        return String.valueOf(hat_id);
    }

    @Override
    protected void setIdString(String id) {
        this.hat_id = Integer.parseInt(id);
    }
}

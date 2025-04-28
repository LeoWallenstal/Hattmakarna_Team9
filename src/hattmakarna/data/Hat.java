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

    public int getHat_id() {
        return hat_id;
    }

    public void setHat_id(int hat_id) {
        this.hat_id = hat_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    private double price;
    private String size;
    private boolean isExpress;

    public boolean isIsExpress() {
        return isExpress;
    }

    public void setIsExpress(boolean isExpress) {
        this.isExpress = isExpress;
    }

    @Deprecated
    private ArrayList<MaterialOrder> materialBehov;

    private List<MaterialHat> materials;

    private boolean isSpecial;
    private Specification specification;

    public Hat() {
        materials = new ArrayList<>();
    }

    public Hat(String hatId) {
        super(hatId);

        try {

            setSpecial();

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
        if (specification == null) {
            specification = new Specification();
        }

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
            } else {
                isSpecial = false;
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

        if (!isSpecial) {
            return true;
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
            return Objects.hash(hat_id, model_id, 0, isExpress);
        } else {
            return Objects.hash(hat_id, model_id, id, isExpress);
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

    @Override
    public boolean save() {

        System.err.println("Sparar order");
        if (!super.save()) {
            System.err.println("Order ej sparad");
            return false;
        }

        System.err.println("Order sparad");

        if (specification != null) {
            specification.setHatId(hat_id);
            specification.save();
        }
        System.err.println("Matierla lista check");

        if (materials != null) {
            materials.forEach(e -> {
                
                // Nånstans läggs de till dupleter vettefan hur men nu sparas dem ej ifall de redan har ett id
                if (e.getIdString() == null || e.getIdString().isEmpty()) {
                    e.setHat_id(hat_id);

                    System.err.println("Sparar hat material");
                    e.save();
                }

            });
        }
        if (materials != null) {
            materials.forEach(e -> {
                e.setHat_id(hat_id);
                e.setIdString(String.valueOf(hat_id));

                e.save();
            });
        }

        return true;
    }

    @Override
    public Hat clone() {
        Hat clone = new Hat();
        clone.setHat_id(this.hat_id); // depending on your logic, you might want a new hat_id
        clone.setModel_id(this.model_id);
        clone.setOrder_id(this.order_id);
        clone.setPrice(this.price);
        clone.setSize(this.size);
        clone.setIsExpress(this.isExpress);
        clone.setIsSpecial(this.isSpecial);

        // Clone specification if it exists
        if (this.specification != null) {
            clone.specification = (Specification) this.specification.clone();
        }

        // Clone material list
        if (this.materials != null) {
            List<MaterialHat> clonedMaterials = new ArrayList<>();
            for (MaterialHat mat : this.materials) {
                clonedMaterials.add((MaterialHat) mat.clone());
            }
            clone.materials = clonedMaterials;
        }

        // Clone deprecated materialBehov
        if (this.materialBehov != null) {
            ArrayList<MaterialOrder> clonedMaterialBehov = new ArrayList<>();
            for (MaterialOrder order : this.materialBehov) {
                clonedMaterialBehov.add(order.clone());
            }
            clone.materialBehov = clonedMaterialBehov;
        }

        return clone;
    }

}

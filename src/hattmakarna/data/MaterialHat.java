/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.awt.Color;

/**
 *
 * @author leonb
 */
public class MaterialHat extends DatabaseObject {

    private int material_hat_id;
    private int material_id;
    private int hat_id;
    private int amount;
    private String Color;

    public MaterialHat(String id) {
        super(id);
    }
    public MaterialHat() {
        super();
    }

    public int getMaterial_hat_id() {
        return material_hat_id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getHat_id() {
        return hat_id;
    }

    public void setHat_id(int hat_id) {
        this.hat_id = hat_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public Material getMaterial() {
        return new Material(String.valueOf(material_id));
    }

    @Override
    protected String getTabelName() {
        return "hat_material";
    }

    @Override
    protected String getIdAttributeName() {
        return "material_hat_id";
    }

    @Override
    protected String getIdString() {
        return String.valueOf(material_hat_id);
    }

    @Override
    protected void setIdString(String id) {
        material_hat_id = Integer.parseInt(id);
    }
    @Override
public MaterialHat clone() {
    MaterialHat copy = new MaterialHat();
    copy.material_hat_id = this.material_hat_id; // Optional: set to 0 if it's a new DB entry
    copy.material_id = this.material_id;
    copy.hat_id = this.hat_id;
    copy.amount = this.amount;
    copy.Color = this.Color;
    return copy;
}


}

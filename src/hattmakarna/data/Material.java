package hattmakarna.data;

import hattmakarna.util.Validerare;
import oru.inf.InfDB;
import oru.inf.InfException;
import static hattmakarna.data.Hattmakarna.idb;

public class Material {

    private final String materialId;
    private String name;
    private String unit;

    public Material(String materialID) {
        this.materialId = materialID;
        this.name = fetchSingle(materialID, "name");
        this.unit = fetchSingle(materialID, "unit");
    }

    private String fetchSingle(String id, String column) {
        String result = "";
        String query = "SELECT " + column + " FROM Material WHERE material_id = '" + id + "'";
        try {
            result = idb.fetchSingle(query);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public void addMaterial(String name, String unit) {
        String sqlAddQuery = "INSERT INTO Material VALUES ('" + name + "', '" + unit + "')";

        try {
            idb.insert(sqlAddQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeMaterial(String materialId) {
        String sqlRemoveQuery = "DELETE FROM Material WHERE material_id = '" + materialId + "'";

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
        if (Validerare.validateName(name)) {
            this.name = name;
        }
    }

    public String getMaterialID() {
        return materialId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String newUnit) {
        if (!newUnit.isBlank() || !newUnit.isEmpty()) {
            unit = newUnit;
        } else {
            System.out.println("Error: Field is empty or blank");
        }
    }
}

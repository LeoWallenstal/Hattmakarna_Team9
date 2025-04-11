package hattmakarna;

import oru.inf.InfDB;
import oru.inf.InfException;

public class Material {

    private final String materialId;
    private String name;
    private String unit;
    private final InfDB idb;

    public Material(String materialId, InfDB idb) {
        this.idb = idb;
        this.materialId = materialId;
        this.name = fetchSingle(materialId, "name");
        this.unit = fetchSingle(materialId, "unit");
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

    public String getMaterialId() {
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

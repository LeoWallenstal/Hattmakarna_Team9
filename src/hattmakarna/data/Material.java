package hattmakarna.data;

import hattmakarna.util.Validerare;
import oru.inf.InfDB;
import oru.inf.InfException;
import static hattmakarna.data.Hattmakarna.idb;

public class Material extends DatabaseObject {

    private int material_id;
    private String name;
    private String unit;

    public Material() {
        super();
    }

    public Material(String materialID) {
        super(materialID);
    }

    @Deprecated
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

    @Deprecated
    public void addMaterial(String name, String unit) {
        String sqlAddQuery = "INSERT INTO Material VALUES ('" + name + "', '" + unit + "')";

        try {
            idb.insert(sqlAddQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Deprecated
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
        return String.valueOf(material_id);
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

    @Override
    protected String getTabelName() {
        return "material";
    }

    @Override
    protected String getIdAttributeName() {
        return "material_id";
    }

    @Override
    protected String getIdString() {
        return String.valueOf(material_id);
    }

    @Override
    protected void setIdString(String id) {
        this.material_id = Integer.parseInt(id);
    }
    
    @Override
public Material clone() {
    Material copy = new Material();
    copy.material_id = this.material_id; // Optional: reset if creating a new DB entry
    copy.name = this.name;
    copy.unit = this.unit;
    return copy;
}

}

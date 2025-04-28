package hattmakarna.data;

import hattmakarna.util.Validerare;
import oru.inf.InfDB;
import oru.inf.InfException;
import static hattmakarna.data.Hattmakarna.idb;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representerar ett material i databasen, inklusive namn, enhet och saldo.
 */
public class Material extends DatabaseObject {

    /**
     * Materialets unika ID.
     */
    private int material_id;

    /**
     * Namnet på materialet.
     */
    private String name;

    /**
     * Enheten som materialet mäts i (t.ex. meter, styck).
     */
    private String unit;

    /**
     * Aktuellt saldo (mängd) av materialet i lager.
     */
    private int amount;

    /**
     * Standardkonstruktor.
     */

    public Material() {
        super();
    }

    /**
     * Skapar ett Material-objekt baserat på ett givet material-ID.
     * @param materialID materialets ID som sträng
     */
    public Material(String materialID) {
        super(materialID);
    }

    /**
     * Hämtar ett specifikt kolumnvärde från material med angivet ID.
     * Endast för intern/temporär användning.
     * @param id materialets ID
     * @param column kolumnnamnet som ska hämtas
     * @return kolumnens värde som sträng
     */
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

    /**
     * Lägger till ett nytt material i databasen.
     * OBS! Föråldrad metod – använd inte i ny kod.
     * 
     * @param name materialets namn
     * @param unit enhet för materialet
     */
    @Deprecated
    public void addMaterial(String name, String unit) {
        String sqlAddQuery = "INSERT INTO Material VALUES ('" + name + "', '" + unit + "')";

        try {
            idb.insert(sqlAddQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Tar bort ett material från databasen.
     * OBS! Föråldrad metod – använd inte i ny kod.
     * 
     * @param materialId ID för materialet som ska tas bort
     */
    @Deprecated
    public void removeMaterial(String materialId) {
        String sqlRemoveQuery = "DELETE FROM Material WHERE material_id = '" + materialId + "'";

        try {
            idb.delete(sqlRemoveQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @return Namnet på materialet
     */
    public String getName() {
        return name;
    }

    /**
     * Sätter materialets namn om det är giltigt enligt valideringsregler.
     * @param name det nya namnet
     */
    public void setName(String name) {
        if (Validerare.validateName(name)) {
            this.name = name;
        }
    }

    /**
     * @return Materialets ID som sträng
     */
    public String getMaterialID() {
        return String.valueOf(material_id);
    }

    /**
     * @return Enheten för materialet
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sätter enheten för materialet, om den inte är tom.
     * @param newUnit den nya enheten
     */
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

    /**
     * Skapar en kopia av detta Material-objekt.
     * @return en klonad instans av Material
     */
    @Override
    public Material clone() {
        Material copy = new Material();
        copy.material_id = this.material_id; // OBS: kopieras direkt, kan återställas om det ska bli nytt objekt
        copy.name = this.name;
        copy.unit = this.unit;
        return copy;
    }

    /**
     * @return Aktuellt saldo (mängd) av materialet
     */
    public int getSaldo() {
        return amount;
    }
}

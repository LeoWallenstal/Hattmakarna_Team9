package hattmakarna.data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import hattmakarna.util.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import oru.inf.InfException;

/**
 * <p>
 * Abstrakt basklass för alla objekt som ska representeras från databasen. Genom
 * att ärva denna klass får objektet automatisk mappning av fält baserat på
 * attribut i en databasrad.
 * </p>
 *
 * <p>
 * Funktioner i denna klass:
 * <ul>
 * <li><b>Laddar objekt från databas</b> baserat på ID (via konstruktor)</li>
 * <li><b>Mappning av databasrader</b> till fält med samma namn</li>
 * <li><b>Konverterar strängvärden</b> till korrekt datatyp för fält</li>
 * <li><b>Stöd för ArrayList&lt;T&gt;</b> med grundläggande datatyper</li>
 * </ul>
 * </p>
 *
 * <p>
 * För att använda denna klass:
 * <ul>
 * <li>Skapa en klass som ärver <code>DatabaseObject</code></li>
 * <li>Implementera <code>getTabelName()</code> och
 * <code>getIdAttributeName()</code></li>
 * <li>Säkerställ att fältnamn matchar kolumnnamn i databasen exakt</li>
 * </ul>
 * </p>
 */
public abstract class DatabaseObject {

    protected boolean hasId = false;

    public DatabaseObject() {
    }

    /**
     * Konstruktor som laddar objektet från databasen baserat på dess ID.
     *
     * @param id Identifieraren för objektet.
     */
    public DatabaseObject(String id) {

        try {
            // Mappa objektet
            DatabaseObjectMapper(this, Hattmakarna.idb
                    .fetchRow("select * from " + getTabelName() + " where " + getIdAttributeName() + " = " + id));

            hasId = true;
        } catch (InfException e) {
            e.printStackTrace();
        }
    }

    // Returnerar tabellnamnet i databasen för objektet
    protected abstract String getTabelName();

    // Returnerar namnet på ID-attributet för objektet
    protected abstract String getIdAttributeName();

    protected abstract String getIdString();

    /**
     * Mapper fälten av det angivna object från hashmapen. Notera att fältens
     * namn måste matcha exakt resultatens attribute namn. Attribut som ej
     * matchar ett fält ignoreras.
     *
     * @param ref Objektet som ska mappas.
     * @param response Hashmapen med nycklar och värden som matchar objektet.
     */
    private static void DatabaseObjectMapper(Object ref, HashMap<String, String> response) {

        // Maps columns with the matching field by name
        for (Field f : ref.getClass().getDeclaredFields()) {

            try {
                boolean access = f.canAccess(ref);
                // Set to accessible temporary
                f.setAccessible(true);

                // If the field name doesn't exist in the class, continue;
                if (response.get(f.getName()) == null) {
                    continue;
                }

                // Set fields value to responses value
                f.set(ref, stringToFieldType(response.get(f.getName()), f));

                // Set back to previous accessibility
                f.setAccessible(access);

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Konverterar en string i mysql format av ett värde till ett fälts
     * datatype. Vid misslyckande ges felmeddelande och returerar NULL.
     *
     * @param value Mysql string värdet.
     * @param f fält objektet vars type ska konveteras till.
     * @return value konverterat till Field f's datatyp. Vid fel null.
     */
    @SuppressWarnings("deprecation")
    private static Object stringToFieldType(String value, Field f) {

        Class<?> type = f.getType();

        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == boolean.class || type == Boolean.class) {
            if(value.equals("1") || value.equals("true"))
                return true;
            else 
                return false;
        } else if (type == short.class || type == Short.class) {
            return Short.parseShort(value);
        } else if (type == byte.class || type == Byte.class) {
            return Byte.parseByte(value);
        } else if (type.isEnum()) {
            // Enum-typ: matchar värdet mot enum-konstanten
            return Enum.valueOf((Class<Enum>) type, value);
        } else if (type == Date.class) {
            try {
                // Adjust this format to match your MySQL date string
                // Use "yyyy-MM-dd" if it's only date
                // or "yyyy-MM-dd HH:mm:ss" if it includes time
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                return formatter.parse(value);
            } catch (ParseException e) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "Kunde inte parsa datum: " + value, e);
                return null;
            }
        } else if (type == char.class || type == Character.class) {
            return value.charAt(0); // Assumes it's a single character string
        } else if (type == ArrayList.class) {
            // Get the generic type of the list
            Type genericType = f.getGenericType();

            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                Type elementType = pt.getActualTypeArguments()[0];

                if (elementType instanceof Class) {
                    Class<?> elementClass = (Class<?>) elementType;
                    return parseStringToList(value, elementClass);
                }
            }

        }

        throw new IllegalArgumentException("Datatypen av fältet stöds ej än. Datatyp: " + type.getName()
                + ". Du kan ingorera detta fel om fältet inte behövs mappas :)");
    }

    /**
     * Konverterar en String som är en Mysql array till en ArrayList vars
     * element typ blir elemenType.
     *
     * @param value stringen som ska konverteras. Måste vara i mysl format.
     * @param elementType typen vars elementen är av
     * @return
     */
    private static ArrayList<Object> parseStringToList(String value, Class<?> elementType) {
        String[] parts = value.split(",");
        // Mysql array har '[' i början och ']' i slut, dem ska bort!
        if (parts.length == 0) {
            return new ArrayList<Object>();
        }
        parts[0].substring(1);
        parts[parts.length - 1].substring(0, parts[parts.length - 1].length() - 1);

        ArrayList<Object> result = new ArrayList<>();

        for (String part : parts) {
            part = part.trim();
            if (elementType == String.class) {
                result.add(part);
            } else if (elementType == Integer.class || elementType == int.class) {
                result.add(Integer.parseInt(part));
            } else if (elementType == Double.class || elementType == double.class) {
                result.add(Double.parseDouble(part));
            } else if (elementType == Boolean.class || elementType == boolean.class) {
                result.add(Boolean.parseBoolean(part));
            } else if (elementType == Long.class || elementType == long.class) {
                result.add(Long.parseLong(part));
            } else if (elementType == Float.class || elementType == float.class) {
                result.add(Float.parseFloat(part));
            } else if (elementType == Character.class || elementType == char.class) {
                result.add(part.charAt(0));
            } else {
                throw new IllegalArgumentException("Unsupported list element type: " + elementType.getName());
            }
        }

        return result;
    }

    /**
     * Sparar objektets attribut till motsvarande databas-tabell.
     *
     * Metoden matchar fälten i den aktuella klassens objekt med kolumnerna i
     * databastabellen (baserat på namn). Den bygger upp och kör en SQL
     * INSERT-sats där alla matchande attribut och deras värden sparas.
     *
     * Datatyper hanteras enligt följande:
     * <ul>
     * <li>Strängar och datum omges av enkla citationstecken och enkelcitat i
     * strängar ersätts med dubbla enkelcitat (SQL-säkerhet).</li>
     * <li>Boolean-värden omvandlas till 1 (true) eller 0 (false).</li>
     * <li>Numeriska värden (int, float, double osv) inkluderas direkt.</li>
     * <li>Null-värden hanteras och sparas som SQL NULL.</li>
     * </ul>
     *
     * @return true om sparningen lyckades, false annars
     */
    public boolean save() {
        try {
            // Hämta alla kolumnnamn från databasen
            ArrayList<String> dbColumns = hattmakarna.data.Hattmakarna.idb.fetchColumn(
                    "SELECT column_name FROM information_schema.columns WHERE table_name = '" + getTabelName() + "'"
            );

            // Här samlar vi matchande attribut
            List<String> attributeNames = new ArrayList<>();
            List<String> attributeValues = new ArrayList<>();

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Field f : this.getClass().getDeclaredFields()) {
                if (f.getName() == getIdAttributeName()) {
                    continue;
                }

                if (dbColumns.contains(f.getName())) {
                    boolean access = f.canAccess(this);
                    f.setAccessible(true);

                    Object value = f.get(this);
                    String sqlValue;

                    // Konvertera till SQL-kompatibelt värde
                    if (value == null) {
                        sqlValue = "NULL";
                    } else if (value instanceof String || value instanceof java.sql.Date) {
                        sqlValue = "'" + value.toString().replace("'", "''") + "'";
                    } else if (value instanceof java.util.Date) {
                        sqlValue = "'" + dateFormatter.format((java.util.Date) value) + "'";
                    } else if (value instanceof Boolean) {
                        sqlValue = (Boolean) value ? "1" : "0";
                    } else if (value instanceof Number) {
                        sqlValue = value.toString();
                    } else {
                        sqlValue = "'" + value.toString().replace("'", "''") + "'";
                    }

                    attributeNames.add(f.getName());
                    attributeValues.add(sqlValue);

                    f.setAccessible(access);
                }
            }

            String sql;

            // Ny post → INSERT
            if (getIdString() == null || getIdString().isEmpty()) {
                sql = "INSERT INTO " + getTabelName()
                        + " (" + String.join(", ", attributeNames) + ") VALUES ("
                        + String.join(", ", attributeValues) + ");";

                System.out.println("Genererad SQL: " + sql);
                hattmakarna.data.Hattmakarna.idb.insert(sql);
            } // Befintlig post → UPDATE
            else {
                List<String> setClauses = new ArrayList<>();
                for (int i = 0; i < attributeNames.size(); i++) {
                    setClauses.add(attributeNames.get(i) + " = " + attributeValues.get(i));
                }

                sql = "UPDATE " + getTabelName()
                        + " SET " + String.join(", ", setClauses)
                        + " WHERE " + getIdAttributeName() + " = " + getIdString() + ";";

                System.out.println("Genererad SQL: " + sql);
                hattmakarna.data.Hattmakarna.idb.update(sql);
            }

            return true;

        } catch (InfException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Tar bort entiteten från databas.
     *
     * @return True om delete lyckades. False vid fel.
     */
    public boolean delete() {

        if (getIdString() == null || getTabelName() == null || getIdAttributeName() == null) {
            return false;
        }

        try {
            Hattmakarna.idb.delete("delete from " + getTabelName() + " where " + getIdAttributeName() + " = " + getIdString());
            return true;
        } catch (InfException ex) {
            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

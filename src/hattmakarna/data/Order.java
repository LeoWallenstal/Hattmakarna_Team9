package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import oru.inf.InfException;

/**
 * Representerar en beställning i systemet och hanterar kopplingar mellan
 * kund, datum, hattar och beställningsstatus.
 */
public class Order extends DatabaseObject {

    // Fält motsvarande kolumner i tabellen sales_order
    private int order_id;
    private int customer_id;
    private int totalPris;
    private Status status;
    private Date recived_date;
    private boolean  material_ordered;
    private boolean isFastProduction;
    
    /**
     * Tom standardkonstruktor.
     */
    public Order() {
    }

    /**
     * Konstruktor som laddar en order utifrån dess ID, och hämtar relaterade hattar.
     *
     * @param orderId Orderns ID som sträng
     */
    public Order(String orderId) {
        super(orderId);

    }

    /**
     * Skapar en ny order i databasen med kopplade hattar och returnerar det nya order-ID:t.
     *
     * @param customer_id        ID för kunden som gör beställningen
     * @param hattar             Lista med hatt-ID:n som ska kopplas till ordern
     * @param totalPris          Totala priset för ordern
     * @param isFastProduction   Om produktionen ska gå snabbare än normalt
     * @return Det genererade order-ID:t som en sträng, eller null vid fel
     */
    @Deprecated
    public static String createOrder(int customer_id, ArrayList<String> hattar,
                                     double totalPris, boolean isFastProduction) {
        try {
            // Skapa dagens datum i SQL-format (yyyy-MM-dd)
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(new java.util.Date());

            // Skapa INSERT-sats för att lägga in en ny order i sales_order-tabellen
            String sql = String.format(Locale.US,
                    "INSERT INTO sales_order (price, customer_id, status, recived_date) " +
                    "VALUES (%.2f, %d, '%s', '%s');",
                    totalPris, customer_id, Status.PLACED, date);

            System.out.println(sql);

            // Kör INSERT
            hattmakarna.data.Hattmakarna.idb.insert(sql);

            // Hämta det senast skapade order-ID:t för den kunden
            String getOrderIdQuery = String.format(
                    "SELECT order_id FROM sales_order " +
                    "WHERE customer_id = %s ORDER BY order_id DESC LIMIT 1;",
                    customer_id);

            String id = Hattmakarna.idb.fetchSingle(getOrderIdQuery);

            return id;

        } catch (InfException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return Namnet på tabellen i databasen som motsvarar detta objekt.
     */
    @Override
    protected String getTabelName() {
        return "sales_order";
    }

    /**
     * @return Namnet på ID-kolumnen i databasen.
     */
    @Override
    protected String getIdAttributeName() {
        return "order_id";
    }

    // Getters och setters för alla fält

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public ArrayList<String> getHattar() {
        
        ArrayList<String> hattIds = new ArrayList<>();
           try {
            // Hämta alla hatt-id:n kopplade till denna order
            hattIds = Hattmakarna.idb.fetchColumn(
                "SELECT hat_id FROM hat WHERE order_id = " + order_id);
        } catch (InfException e) {
            e.printStackTrace();
        }
           
           return hattIds;
    }

    public int getTotalPris() {
        return totalPris;
    }

    public void setTotalPris(int totalPris) {
        this.totalPris = totalPris;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getRecived_data() {
        return recived_date;
    }

    public void setRecived_data(Date recived_data) {
        this.recived_date = recived_data;
    }

    public boolean isFastProduction() {
        return isFastProduction;
    }

    public void setFastProduction(boolean isFastProduction) {
        this.isFastProduction = isFastProduction;
    }

    public void setMaterialOrdered(boolean status){
        this.material_ordered = status;
    }
    
    public boolean getMaterialOrdered(){
        return material_ordered;
    }
    /**
     * @return ID:t som sträng, används av basklassen för att avgöra om INSERT eller UPDATE ska göras.
     */
    @Override
    protected String getIdString() {
        return Integer.toString(order_id);
    }
}

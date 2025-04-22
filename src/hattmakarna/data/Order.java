package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import oru.inf.InfException;

/**
 * Representerar en beställning i systemet och hanterar kopplingar mellan kund,
 * datum, hattar och beställningsstatus.
 */
public class Order extends DatabaseObject {

    // Fält motsvarande kolumner i tabellen sales_order
    private int order_id;
    private int customer_id;
    private int totalPris;
    private Status status;
    private Date recived_date;
    private boolean material_ordered;
    private boolean isFastProduction;

    private List<Hat> hats;

    /**
     * Tom standardkonstruktor.
     */
    public Order() {
    }

    /**
     * Konstruktor som laddar en order utifrån dess ID, och hämtar relaterade
     * hattar.
     *
     * @param orderId Orderns ID som sträng
     */
    public Order(String orderId) {
        super(orderId);

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

    public ArrayList<hattmakarna.data.Hat> getHattarObjects() {

        ArrayList<Hat> hattar = new ArrayList<>();

        try {
            // Hämta alla hatt-id:n kopplade till denna order
            Hattmakarna.idb.fetchColumn(
                    "SELECT hat_id FROM hat WHERE order_id = " + order_id).forEach(h -> {
                        hattar.add(new Hat(h));
                    });

        } catch (InfException e) {
            e.printStackTrace();
        }
        return hattar;
    }

    public ArrayList<String> getHattar() {

        try {
            return Hattmakarna.idb.fetchColumn(
                    "SELECT hat_id FROM hat WHERE order_id = " + order_id);
        } catch (InfException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public void setHats(List<Hat> hats) {
        this.hats = hats;
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

    public void setMaterialOrdered(boolean status) {
        this.material_ordered = status;
    }

    public boolean getMaterialOrdered() {
        return material_ordered;
    }

    public Customer getCustomer() {
        return new Customer(String.valueOf(customer_id));
    }

    /**
     * @return ID:t som sträng, används av basklassen för att avgöra om INSERT
     * eller UPDATE ska göras.
     */
    @Override
    protected String getIdString() {
        return Integer.toString(order_id);
    }

    @Override
    protected void setIdString(String id) {
        this.order_id = Integer.parseInt(id);
    }

    @Override
    public boolean save() {
        // Spara order objektet
        super.save();

        if (hats != null) {
            hats.forEach(e -> {
                e.setOrder_id(order_id);
                e.save();
            });
        }

        return true;
    }
}

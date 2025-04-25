package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import oru.inf.InfException;

/**
 * Representerar en beställning i systemet och hanterar kopplingar
 * mellan kund, hattar, beställningsstatus, datum och materialbeställning.
 */
public class Order extends DatabaseObject {

    /** Unikt ID för beställningen. */
    private int order_id;

    /** ID för den kund som gjort beställningen. */
    private int customer_id;

    /** Totalt pris för beställningen. */
    private double price;

    /** Aktuell status för beställningen. */
    private Status status;

    /** Datum då beställningen mottogs. */
    private Date recived_date;

    /** Om materialet har beställts för denna order. */
    private boolean material_ordered;

    /** Om beställningen ska gå i snabb produktion. */
    private boolean isFastProduction;

    /** Lista med hattar som ingår i beställningen. */
    private List<Hat> hats;

    /** Standardkonstruktor. */
    public Order() {}

    /**
     * Skapar en ny Order med givet ID.
     *
     * @param orderId Orderns ID som sträng
     */
    public Order(String orderId) {
        super(orderId);
    }

    @Override
    protected String getTabelName() {
        return "sales_order";
    }

    @Override
    protected String getIdAttributeName() {
        return "order_id";
    }

    // --- Getters och setters ---

    /** @return Orderns ID */
    public int getOrderId() {
        return order_id;
    }

    /**
     * Sätter orderns ID.
     * @param orderId nytt order-ID
     */
    public void setOrderId(int orderId) {
        this.order_id = orderId;
    }

    /** @return Kundens ID */
    public int getCustomerId() {
        return customer_id;
    }

    /**
     * Sätter kundens ID.
     * @param customerId nytt kund-ID
     */
    public void setCustomerId(int customerId) {
        this.customer_id = customerId;
    }

    /** @return Beställningens totalpris */
    public double getTotalPrice() {
        return price;
    }

    /**
     * Sätter totalpriset för beställningen.
     * @param totalPrice nytt totalpris
     */
    public void setTotalPrice(double totalPrice) {
        this.price = totalPrice;
    }

    /** @return Status för beställningen */
    public Status getStatus() {
        return status;
    }

    /**
     * Sätter status för beställningen.
     * @param status ny status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /** @return Datum då beställningen togs emot */
    public Date getReceivedDate() {
        return recived_date;
    }

    /**
     * Sätter mottagningsdatum för beställningen.
     * @param receivedDate datum då ordern mottogs
     */
    public void setReceivedDate(Date receivedDate) {
        this.recived_date = receivedDate;
    }

    /** @return true om ordern är för snabb produktion */
    public boolean isFastProduction() {
        return isFastProduction;
    }

    /**
     * Sätter om ordern är för snabb produktion.
     * @param fastProduction true om snabb produktion krävs
     */
    public void setFastProduction(boolean fastProduction) {
        this.isFastProduction = fastProduction;
    }

    /** @return true om material har beställts */
    public boolean isMaterialOrdered() {
        return material_ordered;
    }

    /**
     * Sätter om material har beställts för ordern.
     * @param materialOrdered true om materialet är beställt
     */
    public void setMaterialOrdered(boolean materialOrdered) {
        this.material_ordered = materialOrdered;
    }

    /** @return Lista med hattar i beställningen */
    public List<Hat> getHats() {
        return hats;
    }

    /**
     * Sätter listan med hattar för ordern.
     * @param hats lista med hattobjekt
     */
    public void setHats(List<Hat> hats) {
        this.hats = hats;
    }

    /**
     * Hämtar alla hattobjekt kopplade till denna order.
     * @return lista med Hat-objekt
     */
    public List<Hat> fetchHatObjects() {
        List<Hat> fetchedHats = new ArrayList<>();
        try {
            List<String> hatIds = Hattmakarna.idb.fetchColumn("SELECT hat_id FROM hat WHERE order_id = " + order_id
            );
            for (String id : hatIds) {
                fetchedHats.add(new Hat(id));
            }
        } catch (InfException e) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, "Kunde inte hämta hattar för order", e);
        }
        return fetchedHats;
    }

    /**
     * Hämtar alla hatt-ID:n kopplade till denna order.
     * @return lista med strängar av hatt-ID:n
     */
    public List<String> fetchHatIds() {
        try {
            return Hattmakarna.idb.fetchColumn("SELECT hat_id FROM hat WHERE order_id = " + order_id
            );
        } catch (InfException e) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, "Kunde inte hämta hatt-ID:n", e);
        }
        return new ArrayList<>();
    }

    /**
     * @return Customer-objekt kopplat till denna order
     */
    public Customer getCustomer() {
        return new Customer(String.valueOf(customer_id));
    }

    @Override
    protected String getIdString() {
        return Integer.toString(order_id);
    }

    @Override
    protected void setIdString(String id) {
        this.order_id = Integer.parseInt(id);
    }

    /**
     * Sparar ordern till databasen, inklusive kopplade hattar.
     * @return true om sparningen lyckades
     */
    @Override
    public boolean save() {
        super.save();

        if (hats != null) {
            for (Hat hat : hats) {
                hat.setOrder_id(order_id);
                hat.save();
            }
        }
        return true;
    }
}

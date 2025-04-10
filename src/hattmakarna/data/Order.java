package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import hattmakarna.Hattmakarna;
import hattmakarna.util.Util;
import oru.inf.InfException;

enum Status {
	PLACED, MAKING, PACKAGED, SHIPPED,
}

/**
 * Representerar en kundorder i Hattmakarnas system.
 * 
 * Denna klass innehåller all information relaterad till en enskild order,
 * inklusive kund-ID, pris, status, mottagningsdatum, tillval för
 * snabbproduktion och en lista med beställda hattar. Klassen kan skapa nya
 * ordrar, hämta existerande ordrar från databasen och uppdatera ordrar om de
 * redan existerar (s.k. upsert).
 * 
 * Funktioner inkluderar:
 * <ul>
 * <li>Hämtning av orderdata från databasen baserat på order-ID</li>
 * <li>Insättning av nya ordrar i databasen</li>
 * <li>Uppdatering av existerande ordrar om någon information ändrats</li>
 * <li>Hantering av hattar kopplade till en order</li>
 * </ul>
 * 
 * Används främst av affärslogiken för att hantera orderflödet från beställning
 * till leverans i databasen <code>sales_order</code>.
 */
public class Order {

	private String order_id;
	private String customer_id;
	private ArrayList<String> hattar;
	private int totalPris;
	private Status status;
	private Date recived_data;
	private boolean isFastProduction;

	@Test
	public void testCreateOrder() {
		ArrayList<String> hattar = new ArrayList<>();
		// String id = Order.createOrder(1, hattar, 1000, false);
	}

	/**
	 * Konstruktur för att skappa ett tom order objekt.
	 */
	public Order() {
	}

	/**
	 * Skappar ett order objekt från databasen.
	 * 
	 * @param orderId id av ordern
	 */
	public Order(String orderId) {
		try {
			// Mappa objektet
			Util.DatabaseObjectMapper(this,
					Hattmakarna.idb.fetchRow("select * from orders where orderId = " + orderId));

			// Mappa alla hatt id's
			this.hattar = Hattmakarna.idb.fetchColumn("select hat_id from hat where order_id =" + orderId);

		} catch (InfException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Spara order till databasen. Om ordern är ej skappad än i databasen skappas
	 * den nu, annars uppdateras.
	 * 
	 * @return True om sparningen lyckades, False om den misslyckades.
	 */
	public boolean save() {
		try {
			// Om order_id null skappa en helt ny order och sätt nya id
			if (order_id == null) {
				this.order_id = createNewOrder();
			} else {
				upsertOrder();
			}
		} catch (InfException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * Försöker updatera en order baserat på id. Om id inte matchar någon order
	 * skappas en ny med objektets order_id.
	 * 
	 * @throws InfException
	 */
	private void upsertOrder() throws InfException {
		String sql = String.format(Locale.US,
				"INSERT INTO sales_order (order_id, price, customer_id, status, recived_date) "
						+ "VALUES (%d, %.2f, %d, '%s', '%s') " + "ON DUPLICATE KEY UPDATE "
						+ "price = IF(price <> VALUES(price), VALUES(price), price), "
						+ "customer_id = IF(customer_id <> VALUES(customer_id), VALUES(customer_id), customer_id), "
						+ "status = IF(status <> VALUES(status), VALUES(status), status), "
						+ "recived_date = IF(recived_date <> VALUES(recived_date), VALUES(recived_date), recived_date);",
				order_id, (double) totalPris, customer_id, status.toString(), getRecived_data_str());

		Hattmakarna.idb.insert(sql);
	}

	/**
	 * Skappar en ny order.
	 * 
	 * @return id av nya ordern
	 * @throws InfException
	 */
	private String createNewOrder() throws InfException {
		// Sql query för att skapa en ny order i sales_order tabellen
		String sql = String.format(Locale.US,
				"INSERT INTO sales_order ( price, customer_id, status, recived_date) "
						+ "VALUES ( %.2f, %d, '%s', '%s');",
				totalPris, customer_id, // must be a valid customer_id integer string
				Status.PLACED, getRecived_data_str());

		// Skappa ordern
		Hattmakarna.idb.insert(sql);

		// Hämta id av nya ordern
		String getOrderIdQuery = String.format(
				"SELECT order_id FROM sales_order WHERE customer_id = %s ORDER BY order_id DESC LIMIT 1;", customer_id);

		String id = Hattmakarna.idb.fetchSingle(getOrderIdQuery);

		if (order_id == null)
			throw new InfException("Ny skappad order fick ej ett id :/");

		return id;
	}

	/*
	 * _________________BARA MASSA MASSA GETTERS & SETTERS_______________________
	 */
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public ArrayList<String> getHattar() {
		return hattar;
	}

	public void setHattar(ArrayList<String> hattar) {
		this.hattar = hattar;
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
		return recived_data;
	}

	public String getRecived_data_str() {
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(recived_data);
	}

	public void setRecived_data(Date recived_data) {
		this.recived_data = recived_data;
	}

	public boolean isFastProduction() {
		return isFastProduction;
	}

	public void setFastProduction(boolean isFastProduction) {
		this.isFastProduction = isFastProduction;
	}

}

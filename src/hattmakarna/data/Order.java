package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import hattmakarna.Hattmakarna;
import hattmakarna.util.Util;
import oru.inf.InfException;

enum Status {
	PLACED, MAKING, PACKAGED, SHIPPED,
}

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
		String id = Order.createOrder(1, hattar, 1000, false);
	}

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

	public static String createOrder(int customer_id, ArrayList<String> hattar, double totalPris,
			boolean isFastProduction) {

		try {

			// Ta dagens datum, konvertera till sql format
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

			// Sql query för att skapa en ny order i sales_order tabellen
			String sql = String.format(
					"INSERT INTO sales_order (order_id,price, customer_id, status, recived_date) "
							+ "VALUES (%d, %.2f, %d, '%s', '%s');",
					1, totalPris, customer_id, // must be a valid customer_id integer string
					Status.PLACED, date);

			System.out.println(sql);
			// Skappa ordern
			hattmakarna.Hattmakarna.idb.insert(sql);

			String getOrderIdQuery = String.format(
					"SELECT order_id FROM sales_order WHERE customer_id = %s ORDER BY order_id DESC LIMIT 1;",
					customer_id);

			String id = Hattmakarna.idb.fetchSingle(getOrderIdQuery);

			return id;

		} catch (InfException e) {
			e.printStackTrace();
			return null;
		}
	}
}

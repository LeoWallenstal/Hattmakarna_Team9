package hattmakarna.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hattmakarna.Hattmakarna;
import oru.inf.InfException;

enum Status {
	PLACED, MAKING, PACKAGED, SHIPPED,
}

public class Order extends DatabaseObject {

	private String order_id;
	private String customer_id;
	private ArrayList<String> hattar;
	private int totalPris;
	private Status status;
	private Date recived_data;
	private boolean isFastProduction;

	public Order(String orderId) {
		super(orderId);

		try {
			// Mappa alla hatt id's
			this.hattar = Hattmakarna.idb.fetchColumn("select hat_id from hat where order_id = " + orderId);

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
			String sql = String.format(Locale.US,
					"INSERT INTO sales_order (price, customer_id, status, recived_date) "
							+ "VALUES (%.2f, %d, '%s', '%s');",
					totalPris, customer_id, // must be a valid customer_id integer string
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

	@Override
	protected String getTabelName() {
		return "sales_order";
	}

	@Override
	protected String getIdAttributeName() {
		return "order_id";
	}

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

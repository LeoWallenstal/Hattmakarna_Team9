package tests;

import java.util.ArrayList;

import org.junit.gen5.api.Test;

import hattmakarna.Hattmakarna;
import hattmakarna.data.Order;

class TestOrder {

	@Test
	void testOrder() {

		try {
			Hattmakarna.connectToDB();

			ArrayList<String> hattar = new ArrayList<>();
			String id = Order.createOrder(1, hattar, 1000, false);

			if (id == null)
				throw new Exception("Order creation failed");

			Order order = new Order(id);

			System.out.println(order.getOrder_id());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

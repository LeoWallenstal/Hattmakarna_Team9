/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.*;
import static hattmakarna.data.Hattmakarna.idb;
import java.text.SimpleDateFormat;

/**
 *
 * @author Gastinlogg
 */
public class MaterialOrder {

    private String hatID;
    private ArrayList<HashMap<String, String>> materialList;

    public MaterialOrder(String hatID) {
        this.hatID = hatID;
        materialList = new ArrayList<>();

        try {
            String sqlQuery = "SELECT material_id, amount, color FROM hat_material WHERE hat_id = " + hatID
                    + " AND hat_id IN ( SELECT hat_id FROM hat WHERE order_id IN (SELECT order_id FROM sales_order WHERE material_ordered = 0))";
            ArrayList<HashMap<String, String>> result = idb.fetchRows(sqlQuery);

            materialList.addAll(result);

        } catch (InfException ex) {
            System.out.println(ex.getMessage() + ", in MaterialOrder(), MaterialOrder.java");
        }

    }

    public ArrayList<HashMap<String, String>> getMaterialList() {
        return materialList;
    }

    public void printMaterial() {
        for (HashMap<String, String> row : materialList) {
            System.out.println("Material: " + row.get("material_id")
                    + ", färg: " + row.get("color") + ", " + row.get("amount"));

        }
    }

    public HashMap<String, Double> getTotalMaterialAmount() {
        HashMap<String, Double> total = new HashMap<>();

        for (HashMap<String, String> row : materialList) {
            String materialId = row.get("material_id");
            String amountRow = row.get("amount");
            double amount = 0.0;

            if (amountRow != null) {
                amount = Double.parseDouble(amountRow);
            }

            total.put(materialId, total.getOrDefault(materialId, 0.0) + amount);
        }

        return total;
    }

    public static ArrayList<HashMap<String, String>> getOrdersWithMaterials(Date fromDate, Date toDate) {
        ArrayList<HashMap<String, String>> orders = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT sales_order.order_id, count(hat.hat_id) as hats "
                    + "FROM sales_order JOIN hat ON sales_order.order_id = hat.order_id "
                    + "WHERE sales_order.material_ordered = 0 "
            );

            if (fromDate != null) {
                sqlQuery.append("AND sales_order.recived_date >= '").append(sdf.format(fromDate)).append("' ");
            }
            if (toDate != null) {
                sqlQuery.append("AND sales_order.recived_date <= '").append(sdf.format(toDate)).append("' ");
            }

            sqlQuery.append("GROUP BY sales_order.order_id");

            orders = idb.fetchRows(sqlQuery.toString());

            if (orders == null) {
                orders = new ArrayList<>();
            }

        } catch (InfException e) {
            System.out.println("Fel i getOrdersWithMaterial: " + e.getMessage());
        }

        return orders;
    }


}

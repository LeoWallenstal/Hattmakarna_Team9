/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import static hattmakarna.data.Hattmakarna.idb;
import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfException;

/**
 *
 * @author walle
 */
public class TaskRegister {

    private ArrayList<Task> allTasks;
    private ArrayList<Task> ongoingTasks;

    public TaskRegister() {
        allTasks = new ArrayList<>();
        ongoingTasks = new ArrayList<>();
        initAllTasks();
    }

    private void initAllTasks() {
        String sqlQuery = """
            SELECT 
            t.*, 
            m.name,
            h.order_id
            FROM task t
            JOIN hat h ON t.hat_id = h.hat_id
            JOIN hat_model m ON h.model_id = m.model_id
        """;

        try {
            ArrayList<HashMap<String, String>> tasks = idb.fetchRows(sqlQuery);
            for (HashMap<String, String> taskMap : tasks) {
                allTasks.add(new Task(taskMap));
            }
        } catch (InfException ex) {
            System.out.println(" in initAllTasks()");
        }
    }

    private void initUsersOngoingTasks(String userId) {
        String sqlQuery = """
    SELECT 
        t.*, 
        m.name,
        h.order_id,
        o.isFastProduction
    FROM task t       
    JOIN hat h ON t.hat_id = h.hat_id
    JOIN hat_model m ON h.model_id = m.model_id
    JOIN sales_order o ON h.order_id = o.order_id
    WHERE t.status = 'PÅGÅENDE' AND t.user_id = """ + userId;

        try {
            ArrayList<HashMap<String, String>> tasks = idb.fetchRows(sqlQuery);
            for (HashMap<String, String> taskMap : tasks) {
                ongoingTasks.add(new Task(taskMap));
            }
        } catch (InfException ex) {
            System.out.println(" in initAllTasks()");
        }

    }

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public ArrayList<Task> getUsersOngoingTasks(String userId) {
        initUsersOngoingTasks(userId);
        return ongoingTasks;
    }

    public void refreshUsersOngoingTasks(String UserId) {
        ongoingTasks.clear();
        initUsersOngoingTasks(UserId);
    }
}

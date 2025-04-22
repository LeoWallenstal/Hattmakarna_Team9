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

    public TaskRegister() {
        allTasks = new ArrayList<>();
        initAllTasks();
    }

    private void initAllTasks() {
        String sqlQuery = """
            SELECT 
            t.*, 
            m.name AS model_name
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

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public void refreshTasks() {
        allTasks.clear();
        initAllTasks();
    }
}

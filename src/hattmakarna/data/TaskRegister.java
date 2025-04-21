/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import static hattmakarna.data.Hattmakarna.idb;
import java.util.ArrayList;
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
        String sqlQuery = "SELECT task_id FROM task";

        try {
            ArrayList<String> tasks = idb.fetchColumn(sqlQuery);
            for (String id : tasks) {
                allTasks.add(new Task(id));
            }
        } catch (InfException ex) {
            System.out.println(" in initAllTasks()");
        }

    }
    
    public ArrayList<Task> getTasks(){
        return allTasks;
    }
    
    public void refreshTasks(){
        allTasks.clear();
        initAllTasks();
    }
}

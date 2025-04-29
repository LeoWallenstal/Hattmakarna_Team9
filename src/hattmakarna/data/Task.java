/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author walle
 */
public class Task extends DatabaseObject {

    private int task_id;
    private Date start_date;
    private TaskStatus status;
    private int user_id;
    private int hat_id;
    private String name;
    private int order_id;
    private boolean isFastProduction;

    public Task() {

    }

    public Task(String taskId) {
        super(taskId);
    }

    public Task(HashMap<String, String> taskMap) {
        task_id = Integer.parseInt(taskMap.get("task_id"));
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String rawDate = taskMap.get("start_date");
            start_date = formatter.parse(rawDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        status = TaskStatus.valueOf(taskMap.get("status"));
        user_id = Integer.parseInt(taskMap.get("user_id"));
        hat_id = Integer.parseInt(taskMap.get("hat_id"));
        name = taskMap.get("name");
        order_id = Integer.parseInt(taskMap.get("order_id"));
        isFastProduction = "1".equals(taskMap.get("isFastProduction"));

    }

    @Override
    protected String getTabelName() {
        return "task";
    }

    @Override
    protected String getIdAttributeName() {
        return "task_id";
    }

    @Override
    protected String getIdString() {
        return String.valueOf(task_id);
    }

    @Override
    protected void setIdString(String id) {
        task_id = Integer.parseInt(id);
    }

    public int getTaskId() {
        return task_id;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getUserId() {
        return user_id;
    }

    public int getHatId() {
        return hat_id;
    }

    public String getModelName() {
        return name;
    }
    
    public int getOrderId(){
        return order_id;
    }
    
    public void setStatus(TaskStatus status){
        this.status = status;
    }
    
    public boolean isFastProduction(){
        return isFastProduction;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hattmakarna.data;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author walle
 */

public class Task extends DatabaseObject{
    private int task_id;
    private Date start_date;
    private TaskStatus status;
    private int user_id;
    private int hat_id;
    
    public Task(){
        
    }
    
    public Task(String taskId){
        super(taskId);
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
    
    
    public int getTaskId(){
        return task_id;
    }
    
    public Date getStartDate(){
        return start_date;
    }
    
    public void setStartDate(Date start_date){
        this.start_date = start_date;
    }
    
    public TaskStatus getStatus(){
        return status;
    }
    
    public int getUserId(){
        return user_id;
    }
    
    public int getHatId(){
        return hat_id;
    }
    
}

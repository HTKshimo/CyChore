package com.example.CyCHORE.Task;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "description")
    String description;

    @Column(name = "assigned_to")
    Integer assigned_to;

    @Column(name = "completed")
    Boolean completed;

    @Column(name = "deadline")
    String deadline;

    @Column(name = "time_completed")
    String time_completed;

    @Column(name = "in_pool")
    Boolean in_pool;

    @Column(name = "completed_by")
    Integer completed_by;

    @Column(name = "group_id")
    Integer group_id;

    @Column(name = "title")
    String title;

    public Task(int id, String description, int assigned_to, boolean completed, String deadline, String time_completed, boolean in_pool, int completed_by, int group_id, String title){
        this.group_id = group_id;
        this.description = description;
        this.deadline = deadline;
        this.in_pool = in_pool;
        this.assigned_to = assigned_to;
        this.time_completed = time_completed;
        this.completed = completed;
        this.id = id;
        this.completed_by = completed_by;
        this.title = title;
    }

    public Task(){}

    public Integer getId() { return id; }

    public Boolean is_completed() { return completed; }

    public Integer is_assigned_to() { return assigned_to; }

    public String toString() { return description; }

    public String getDdl() { return deadline; }

    public String getTimeCompleted() { return time_completed; }

    public String getTitle() { return title; }

    public Boolean getIn_pool() {return in_pool; }

    public Integer getGroup_id() { return group_id; }

    public void setGroup_id(Integer g_id) { this.group_id = g_id; }

    public void changeCompleteStatus(Integer user_id, Boolean status) {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ts);
        this.completed = status;
        this.time_completed = s;
        this.completed_by = (status) ? user_id : null;
    }

    public String assignTaskToUser(Integer u_id){

        this.assigned_to = u_id;
        return "assigned task to " + u_id.toString();
    }

}

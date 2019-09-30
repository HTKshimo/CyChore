package com.example.CyCHORE.Task;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "assigned_to")
    Integer assigned_to;

    @Column(name = "completed")
    Boolean completed;

    @Column(name = "description")
    String description;

    @Column(name = "deadline")
    String deadline;

    @Column(name = "time_completed")
    String time_completed;

    @Column(name = "completed_by")
    Integer completed_by;

    public Integer getId() { return id; }

    public Boolean is_completed() { return completed; }

    public Integer is_assigned_to() { return assigned_to; }

    public String toString() { return description; }

    public String getDdl() { return deadline; }

    public String getTimeCompleted() { return time_completed; }

    public void changeCompleteStatus(Integer user_id, Boolean status) {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ts);
        this.completed = status;
        this.time_completed = s;
        this.completed_by = (status) ? user_id : null;
    }

}
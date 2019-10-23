package com.example.CyCHORE.Complaint;

import javax.persistence.*;

@Entity
@Table(name="complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "task_id")
    Integer task_id;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    Integer status;

    public Integer getId() { return id; }

    public Integer getTask_id() { return task_id; }

    public String getDescription() { return description; }

    public Integer getStatus() { return status; }

}
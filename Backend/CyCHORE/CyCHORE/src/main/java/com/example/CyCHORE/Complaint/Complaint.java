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

    @Column(name = "filer_id")
    Integer filer_id;


    public Integer getId() { return id; }

    public Integer getTask_id() { return task_id; }

    public String getDescription() { return description; }

    public Integer getStatus() { return status; }
    public Integer getFiler_id() { return filer_id; }

    //public Integer is_assigned_to() { return assigned_to; }

}
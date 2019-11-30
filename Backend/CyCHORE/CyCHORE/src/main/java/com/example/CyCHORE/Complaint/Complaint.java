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

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    /**
     * 0: new(no admin assigned)
     * 1: currently processing(assigned admin)
     * 2: approved
     * 3: rejected
     */
    @Column(name = "status")
    Integer status;

    @Column(name = "filer_id")
    Integer filer_id;

    @Column(name = "admin_id")
    Integer admin_id;


    public Integer getId() { return id; }
    public Integer getTask_id() { return task_id; }
    public Integer getAdmin_id() { return admin_id; }
    public String getTitle() { return title;}
    public String getDescription() { return description; }
    public Integer getStatus() { return status; }
    public Integer getFiler_id() { return filer_id; }

    public void setAdmin_id(Integer admin_id) { this.admin_id = admin_id; }
    public void setStatus(Integer status) { this.status = status; }
}
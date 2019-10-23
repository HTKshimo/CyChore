package com.example.CyCHORE.User;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String email;

    @Column
    String first_name;

    @Column
    Integer group_id;

    public User() {
    }

    public User(String email, String first_name, int group_id) {
        this.email = email;
        this.first_name = first_name;
        this.group_id = group_id;
    }

    public User(String email, String first_name) {
        this.email = email;
        this.first_name = first_name;
    }

    public Integer getId() { return id; }

    public String getUser() { return email; }

    public String getFirst_name() {return first_name;}

    public Integer getGroupId() { return group_id; }

    public void setGroup_id(Integer g_id) {
        this.group_id = g_id;
    }

    public String toString() {
        return getUser();
    }

}
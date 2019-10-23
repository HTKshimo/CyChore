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

    @Column
    Integer tier;

    public User() {
    }

    public User(String email, String first_name, Integer group_id, Integer tier) {
        this.email = email;
        this.first_name = first_name;
        this.group_id = group_id;
        this.tier = tier;
    }

    public User(String email, String first_name, Integer tier) {
        this.email = email;
        this.first_name = first_name;
        this.tier = tier;
    }

    public Integer getId() { return id; }

    public String getUser() { return email; }

    public String getFirst_name() {return first_name;}

    public Integer getGroupId() { return group_id; }
    public  String getEmail() { return email;}
    //public String getPassword() { return password;}

    public Integer getTier() { return tier; }

    public void setGroup_id(Integer g_id) {
        this.group_id = g_id;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public String toString() {
        return getUser();
    }

}

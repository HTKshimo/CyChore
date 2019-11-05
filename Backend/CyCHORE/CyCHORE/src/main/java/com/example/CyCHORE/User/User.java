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
    String username;

    @Column
    String password;

    @Column
    Integer group_id;

    @Column
    Integer tier;

    public User() {
        this.email = "empty";
        this.first_name = "empty";
        this.username = "empty";
        this.password = "empty";
        this.group_id = null;
        this.tier = null;

    }

    public User(String email, String first_name, String username, String password, Integer group_id, Integer tier) {
        this.email = email;
        this.first_name = first_name;
        this.username = username;
        this.password = password;
        this.group_id = group_id;
        this.tier = tier;
    }

//    public User(String email, String first_name, String user_name, String password, Integer tier) {
//        this.email = email;
//        this.first_name = first_name;
//        this.user_name = user_name;
//        this.password = password;
//        this.tier = tier;
//    }

    public Integer getId() { return id; }

    public String getUser() { return email; }

    public String getFirst_name() {return first_name;}

    public String getUser_name() {return username;}

    public String getPassword() {return password;}

    public Integer getGroupId() { return group_id; }
    public  String getEmail() { return email;}

    public Integer getTier() { return tier; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

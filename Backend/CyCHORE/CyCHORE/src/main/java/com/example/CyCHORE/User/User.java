package com.example.CyCHORE.User;

import javax.persistence.*;

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String email;

    public Integer getId() { return id; }

    public String getUser() { return email; }

//    public String toString() {
//        return getUser();
//    }


}
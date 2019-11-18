package com.example.CyCHORE.Listings;

import javax.persistence.*;

@Entity
@Table(name="listings")
public class Listings {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer list_id;

    @Column(name = "address")
    String address;

    @Column(name = "group_id")
    Integer group_id;

    @Column(name = "user_id")
    Integer user_id;

    @Column(name = "price")
    Float price;

    @Column(name = "description")
    String description;

    public  String getAddress() {return address;}
    public Integer getList_id() { return list_id; }
    public Integer getGroup_id() { return group_id; }
    public Integer getUser_id() { return user_id; }
    public String getDescription() { return description; }
    public Float getPrice() { return price; }



}






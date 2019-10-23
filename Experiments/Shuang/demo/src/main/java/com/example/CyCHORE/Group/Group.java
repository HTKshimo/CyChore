package com.example.CyCHORE.Group;

import antlr.LexerSharedInputState;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "address")
    String address;

    //unsure about whether need to keep this property, but will leave it for now
    @Column(name = "num_of_tenants")
    Integer num_of_tenants;

    public Integer getId() { return id; }

    public String getAddress() { return address; }

    public Integer getNum_of_tenants() { return num_of_tenants; }

}

package com.example.CyCHORE.Group;

import javax.persistence.*;

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

    public Group(String addr, int num_of_tenants){
        this.address = addr;
        this.num_of_tenants = num_of_tenants;
    }

    public Integer getId() { return id; }

    public String getAddress() { return address; }

    public Integer getNum_of_tenants() { return num_of_tenants; }

}

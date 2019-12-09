package com.example.CyCHORE.Group;

import javax.persistence.*;

@Entity
@Table(name="tenant_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "address")
    String address;

    //unsure about whether need to keep this property, but will leave it for now
    @Column(name = "num_of_tenants")
    Integer num_of_tenants;

    public Group() {
        this.id = null;
        this.address = "empty";
        this.num_of_tenants = null;
    }

    public Group(Integer id, String address, Integer num_of_tenants) {
        this.id = id;
        this.address = address;
        this.num_of_tenants = num_of_tenants;
    }

    public Integer getId() { return id; }

    public String getAddress() { return address; }

    public Integer getNum_of_tenants() { return num_of_tenants; }

    public void setId(Integer id_num) { this.id = id_num; }

    public void setAddress(String add) {
        this.address = add;
    }

    public void setNum_of_tenants(Integer num_ten){this.num_of_tenants = num_ten; }


}

package com.example.CyCHORE.Chatroom;

import javax.persistence.*;

@Entity
@Table(name = "chatroom")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String group_name;

    @Column
    Long lastUpdatedTimestamp;

    public Integer getId() {
        return id;
    }

    public String getGroup_name(){
        return group_name;
    }

    public Long getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(Long lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}

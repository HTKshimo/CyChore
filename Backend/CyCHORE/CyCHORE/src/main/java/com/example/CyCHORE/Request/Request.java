package com.example.CyCHORE.Request;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "requesterId")
    Integer requesterId;

    @Column(name = "requesteeId")
    Integer requesteeId;

    @Column(name = "response")
    String response; //TODO: explore image response/storage format

    /**
     * 0: view Sublease listing
     * 1: cleaning service advertising
     */
    @Column(name = "type")
    Integer type;

    /**
     * 0: new(no admin assigned)
     * 1: currently processing(assigned admin)
     * 2: approved
     * 3: rejected
     */
    @Column(name = "status")
    Integer status;

    @Column(name = "lastUpdated")
    Long lastUpdated;

    public Request(){ }

    public Request(int requesterId, int type){
        this.requesterId = requesterId;
        this.type = type;
        this.status = 0;
        Date date= new Date();
        this.lastUpdated = date.getTime();
    }

    public Integer getType() {
        return type;
    }

    public Integer getId() { return id; }

    public Integer getStatus() {
        return status;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public Integer getRequesteeId() {
        return requesteeId;
    }

    public String getResponse() {
        return response;
    }

    public void setRequesteeId(int requesteeId) {
        this.requesteeId = requesteeId;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setLastUpdated() {
        Date date= new Date();
        this.lastUpdated = date.getTime();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

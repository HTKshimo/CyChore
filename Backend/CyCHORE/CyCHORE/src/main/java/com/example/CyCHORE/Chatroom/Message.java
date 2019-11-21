package com.example.CyCHORE.Chatroom;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    Integer room_id;

    @Column
    String message;

    @Column
    Integer sender_id;

    @Column
    Long timestamp;

    public Message(){}

    public Message(String msg, int room_id, int sender_id){
        this.message = msg;
        this.room_id = room_id;
        this.sender_id = sender_id;
        Date date= new Date();
        this.timestamp = date.getTime();
    }

    public Integer getId() {
        return id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}

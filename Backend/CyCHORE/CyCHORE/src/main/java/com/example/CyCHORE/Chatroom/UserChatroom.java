package com.example.CyCHORE.Chatroom;

import javax.persistence.*;

@Entity
@Table(name = "user_chatroom")
public class UserChatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "chatroom_id")
    Integer chatroom_id;

    @Column(name = "user_id")
    Integer user_id;

    public void setChatroom_id(Integer chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getChatroom_id(){
        return chatroom_id;
    }

    public Integer getUser_id(){
        return user_id;
    }

}


package com.example.CyCHORE.Chatroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

import static com.example.CyCHORE.Chatroom.UserChatroomController.setLastUpdatedTimestamp;
import static com.example.CyCHORE.User.UserController.getUsername;

@RestController
public class MessageController {

    @Autowired
    static MessageRepository mr;

    public MessageController(MessageRepository mr) {
        this.mr = mr;
    }

    public static void addMessage(String message, int chatroom_id, int sender_id) {
        Message m = new Message(message, chatroom_id, sender_id);
        mr.save(m);
        setLastUpdatedTimestamp(chatroom_id);
        System.out.println(m.getMessage());
    }

    /**
     * @param chatroom_id
     * @return Array of Strings in format of sender[timestamp]: message in order latest -> oldest for chatroom with given id
     */
    public static Collection<String> getChatroomMessages(int chatroom_id) {
        List<Message> allMessages;
        HashMap<Long, String> temp = new HashMap<>();
        allMessages = mr.findAll();
        String formattedMsg;
        Timestamp t;
        for (Message m : allMessages) {
            if ((m.getRoom_id()) == chatroom_id) {
                t = new Timestamp(m.getTimestamp());
                formattedMsg = getUsername(m.getSender_id()) + "[" + t.toString() + "]: " + m.getMessage();
                temp.put(m.getTimestamp(), formattedMsg);
            }
        }
        Map<Long, String> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(temp);
        return treeMap.values();
    }

}


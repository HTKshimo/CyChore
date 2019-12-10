package com.example.CyCHORE.Chatroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
public class ChatroomController {

    @Autowired
    static ChatroomRepository chr;

    public static String getChatroomName(int chatroom_id) {
        List<Chatroom> allChatrooms;
        allChatrooms = chr.findAll();
        for (Chatroom temp : allChatrooms) {
            if (temp.getId() == chatroom_id) {
                return temp.getGroup_name();
            }
        }
        return null;
    }

    /**
     *
     * @param chatroom_id: id of chatroom for which timestamp is updated
     * @return
     * 0: chatroom does not exist
     * 1: success
     */
    public static int setLastUpdatedTimestamp(int chatroom_id){
        Optional<Chatroom> chatroom;
        chatroom = chr.findById(chatroom_id);
        if (chatroom.isPresent()) {
            Chatroom c =  chatroom.get();
            Date date= new Date();
            c.setLastUpdatedTimestamp(date.getTime());
            chr.save(c);
            return 1;
        } else {
            return 0;
        }
    }
}
package com.example.CyCHORE.Chatroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.example.CyCHORE.Chatroom.UserChatroomController.getUserChatrooms;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MessageController {

    @Autowired
    static MessageRepository mr;

    public MessageController(MessageRepository mr) {
        this.mr = mr;
    }

    @RequestMapping(value = "/addMessage/{message}/{chatroom_id}/{sender_id}", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public static void addMessage(@PathVariable String message, @PathVariable int chatroom_id, @PathVariable int sender_id){
        Message m = new Message(message, chatroom_id, sender_id);
        mr.save(m);
        System.out.println(m.getMessage());
    }

    //get all messages for user from all their chatrooms
    public static HashMap<Integer, ArrayList<Message>> getMessages(int user_id){
        HashSet<Integer> cr_ids = getUserChatrooms(user_id);
        HashMap<Integer, ArrayList<Message>> mList = new HashMap<>();
        List<Message> allMessages;
        allMessages = mr.findAll();
        for (Message m : allMessages){
            if (cr_ids.contains(m.getRoom_id())){
                if(!mList.containsKey(m.getRoom_id())){
                    mList.put(m.getRoom_id(), new ArrayList<>());
                }
                mList.get(m.getRoom_id()).add(m);
            }
        }
        return mList;
    }
}


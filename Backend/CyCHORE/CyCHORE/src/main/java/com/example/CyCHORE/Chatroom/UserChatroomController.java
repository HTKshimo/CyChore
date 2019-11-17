package com.example.CyCHORE.Chatroom;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UserChatroomController {

    @Autowired
    static UserChatroomRepository ucr;
    @Autowired
    static ChatroomRepository chr;

    public UserChatroomController(UserChatroomRepository ucr, ChatroomRepository chr) {
        this.chr = chr;
        this.ucr = ucr;
    }

    public void addUserToChatroom(int user_id, int chatroom_id) {
        UserChatroom uch = new UserChatroom();
        uch.chatroom_id = chatroom_id;
        uch.user_id = user_id;
        ucr.save(uch);
    }

    @RequestMapping(value = "/addUserToGroup", method = POST, produces = "application/json;charset=UTF-8")
    public int createChatroomWithUsers(HttpServletRequest request) throws JSONException, IOException { //change this to HTTP method, addUserToChatroom should not be HTTP method
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer[] user_ids = (Integer[]) jsonObj.get("user_ids");
        Chatroom cr = new Chatroom();
        chr.save(cr);
        int cr_id = cr.getId();
        for (int u_id : user_ids){
            addUserToChatroom(u_id, cr_id);
        }
        return cr_id;
    }

    public static List<Integer> getChatroomUsers(int chatroom_id) {
        List<Integer> user_ids = new ArrayList<>();
        List<UserChatroom> allUserChatroom;
        allUserChatroom = ucr.findAll();
        for (UserChatroom temp : allUserChatroom) {
            if (temp.getChatroom_id() == chatroom_id) {
                user_ids.add(temp.getUser_id());
            }
        }
        return user_ids;
    }

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

}


package com.example.CyCHORE.Chatroom;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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

    @RequestMapping(value = "/addUserToChatroom/{user_id}/{chatroom_id}", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public static void addUserToChatroom(@PathVariable int user_id, @PathVariable int chatroom_id) {
        UserChatroom uch = new UserChatroom();
        uch.chatroom_id = chatroom_id;
        uch.user_id = user_id;
        ucr.save(uch);
    }

    @RequestMapping(value = "/createChatroomWithUsers", method = POST, produces = "application/json;charset=UTF-8")
    public static int createChatroomWithUsers(HttpServletRequest request) throws JSONException, IOException { //change this to HTTP method, addUserToChatroom should not be HTTP method
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

    public static ArrayList<Integer> getChatroomUsers(int chatroom_id) {
        ArrayList<Integer> user_ids = new ArrayList<>();
        List<UserChatroom> allUserChatroom;
        allUserChatroom = ucr.findAll();
        for (UserChatroom temp : allUserChatroom) {
            if (temp.getChatroom_id() == chatroom_id) {
                user_ids.add(temp.getUser_id());
            }
        }
        return user_ids;
    }

    public static HashSet<Integer> getUserChatrooms(int user_id) {
        HashSet<Integer> cr_ids = new HashSet<>();
        List<UserChatroom> allUserChatroom;
        allUserChatroom = ucr.findAll();
        for (UserChatroom temp : allUserChatroom) {
            if (temp.getUser_id() == user_id) {
                cr_ids.add(temp.getChatroom_id());
            }
        }
        return cr_ids;
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


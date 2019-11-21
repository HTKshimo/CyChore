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
import java.util.*;
import java.util.stream.Collectors;

import static com.example.CyCHORE.Chatroom.MessageController.getChatroomMessages;
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

    public static void addUserToChatroom(int user_id, int chatroom_id) {
        UserChatroom uch = new UserChatroom();
        uch.chatroom_id = chatroom_id;
        uch.user_id = user_id;
        ucr.save(uch);
    }

    @RequestMapping(value = "/createChatroomWithUsers", method = POST, produces = "application/json;charset=UTF-8")
    public String createChatroomWithUsers(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer[] user_ids = (Integer[]) jsonObj.get("user_ids");
        Chatroom cr = new Chatroom();
        chr.save(cr);
        int cr_id = cr.getId();
        for (int u_id : user_ids){
            addUserToChatroom(u_id, cr_id);
        }
        JSONObject toReturn = new JSONObject();
        toReturn.put("status", 0);
        toReturn.put("cr_id", cr_id);
        return toReturn.toString();
    }

    public static ArrayList<Integer> getChatroomUsers(int chatroom_id) {
        ArrayList<Integer> user_ids = new ArrayList<>();
        List<UserChatroom> allUserChatroom;
        allUserChatroom = ucr.findAll();
        for (UserChatroom temp : allUserChatroom) {
            System.out.println(temp.getChatroom_id() + " " + temp.getUser_id() + temp.getId());
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

    @RequestMapping(value = "/getUserChatHistory", method = POST, produces = "application/json;charset=UTF-8")
    public static String getUserChatHistory(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int user_id = (int) jsonObj.get("user_id");
        int cr_id;
        TreeMap<Long, Integer> cr_ids = new TreeMap<>(Collections.reverseOrder()); //sorted chatroom_ids based on timestamp
        List<UserChatroom> allUserChatroom;
        allUserChatroom = ucr.findAll();
        for (UserChatroom temp : allUserChatroom) {
            if (temp.getUser_id() == user_id) {
                cr_id = temp.getChatroom_id();
                cr_ids.put(chr.findById(cr_id).get().getLastUpdatedTimestamp(), cr_id);
            }
        }
        Collection<Integer> sortedCrIDs = cr_ids.values();
        JSONObject toReturn = new JSONObject();
        toReturn.put("status", 0);
        toReturn.put("cr_ids", sortedCrIDs); //list of chatroom_ids belong to this user in order latest -> oldest
        for (int cr : sortedCrIDs){
            JSONObject crJson = new JSONObject();
            crJson.put("Members", getChatroomUsers(cr));
            crJson.put("chatlog", getChatroomMessages(cr));
            toReturn.put(String.valueOf(cr), crJson);
        }
        return toReturn.toString();
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

    /**
     *
     * @param chatroom_id: id of chatroom for which timestamp is updated
     * @return
     * 0: chatroom does not exist
     * 1: success
     */
    public static int setLastUpdatedTimestamp(int chatroom_id){
        Optional<Chatroom> chatroom = chr.findById(chatroom_id);
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


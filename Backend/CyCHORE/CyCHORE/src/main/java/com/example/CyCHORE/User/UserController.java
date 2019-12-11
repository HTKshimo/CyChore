package com.example.CyCHORE.User;


import com.example.CyCHORE.Chatroom.Message;
import com.example.CyCHORE.Task.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    static UserRepository ur;


    public UserController(UserRepository ur) {
        this.ur = ur;
    }
    public UserController() {

    }


    /**
     *This method will register a new user to the application by taking in values such as; username, email and the type of user that is being registered (for example; current tenant, potential tenant, admin).
     *It returns a status of ‘0’ if successful (and ‘1’ if the user is not already a registered user- by verifying the information in the database).
     */
    @RequestMapping(value = "/registerUser", method = POST, produces = "application/json;charset=UTF-8")
    public String registerUser(HttpServletRequest request) throws JSONException, IOException {
        //@PathVariable String first_name, @PathVariable String email, @PathVariable Integer tier
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        String username = (String) jsonObj.get("username");
        String email = (String) jsonObj.get("email");
        Integer tier = (Integer) jsonObj.get("tier");
        User u = new User();
        JSONObject toReturn = new JSONObject();
        List<User> users = ur.findAll();
        for (int i = 0; i < users.size(); i++) {
            User p = users.get(i);
            if (p.getEmail().equals(email)) {
                toReturn.put("status", "1");
                return toReturn.toString();
            }
        }
        u.email = email;
        u.username = username;
        u.tier = tier;
        ur.save(u);
        toReturn.put("status", "0");
        return toReturn.toString();
    }

    // Below functions are for DEMO 4 USE CASES (For the MyGroup Page; JoinGroup() and GroupInfo())
    @RequestMapping(value = "/JoinGroup", method = POST, produces = "application/json;charset=UTF-8")
    public String JoinGroup(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer u_id = (Integer) jsonObj.get("uid");
        Integer g_id = (Integer) jsonObj.get("groupid");
        Boolean success = ur.existsById(u_id);
        JSONObject toReturn = new JSONObject();
        if (success) {
            Optional<User> test = ur.findById((u_id));
            User u = test.get();
            u.setGroup_id(g_id);
            ur.save(u);
        }
        if (success) {
            toReturn.put("status", "0");
            toReturn.put("uid", u_id);
            toReturn.put("groupid", g_id);
        } else {
            toReturn.put("status", "1");
        }
        return toReturn.toString();
    }

    @RequestMapping(value = "/GroupInfo", method = POST, produces = "application/json;charset=UTF-8")
    public String GroupInfo (HttpServletRequest request) throws JSONException, IOException {

        //Boolean success = ur.existsById(u_id);
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer u_id = (Integer) jsonObj.get("uid");
        JSONObject toReturn = new JSONObject();
        List<User> allUsers;
        allUsers = ur.findAll();
        int count =0;
        JSONArray jsonArray = new JSONArray();

        for (User temp : allUsers) {
            if(temp.group_id != null) {
                if (temp.group_id == ur.getUserById(u_id).group_id) {
                    JSONObject curUser = new JSONObject();
                    curUser.put("User Name", temp.username);
                    jsonArray.put(curUser);
                    count++;
                }
            }
            }
        if(count >=1){
            toReturn.put("status","0");
            toReturn.put("Names of users in the group", jsonArray);

        }
       else{
            toReturn.put("status","1");
      }
        return toReturn.toString();
    }
    @RequestMapping(value = "/ChangeUsername", method = POST, produces = "application/json;charset=UTF-8")
    public String ChangeUsername (HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer u_id = (Integer) jsonObj.get("uid");
        String username = (String) jsonObj.get("username");

        Boolean success = ur.existsById(u_id);
        JSONObject toReturn = new JSONObject();
        if (success) {
            Optional<User> test = ur.findById((u_id));
            User u = test.get();
            u.setUsername(username);
            ur.save(u);
        }
        if(success){
            toReturn.put("status","0");
            toReturn.put("uid", u_id);
            toReturn.put("username", username);
        }
        else{
            toReturn.put("status","1");
        }
        return toReturn.toString();
    }
    //ChangePassword()
    @RequestMapping(value = "/ChangePassword", method = POST, produces = "application/json;charset=UTF-8")
    public String ChangePassword (HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer u_id = (Integer) jsonObj.get("uid");
        String password = (String) jsonObj.get("password");


        Boolean success = ur.existsById(u_id);
        JSONObject toReturn = new JSONObject();
        if (success) {
            Optional<User> test = ur.findById((u_id));
            User u = test.get();
            u.setPassword(password);
            ur.save(u);
        }
        if(success){
            toReturn.put("status","0");
            toReturn.put("uid", u_id);
            toReturn.put("password", password);
        } else {
            toReturn.put("status", "1");
        }
        return toReturn.toString();
    }

    public static String getUsername(int user_id) {
        List<Integer> user_ids = new ArrayList<>();
        Optional<User> u = ur.findById(user_id);
        if (u.isPresent()) {
            return u.get().toString();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/login", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validateLogin(HttpServletRequest request) throws JSONException, IOException {

        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        String email = (String) jsonObj.get("email");
        String password = (String) jsonObj.get("password");

        int userID = -1;
        int tier = -1;
        int group_id = -1;
        List<User> allUsers = ur.findAll();
        int isValid = 0;
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(password)) {
                    isValid = 1;
                    userID = user.getId();
                    tier = user.getTier();
                    group_id = user.getGroupId();
                }
            }
        }

        JSONObject o = new JSONObject();
        o.put("status", isValid);
        o.put("uid", userID);
        o.put("tier", tier);
        o.put("groupid", group_id);

        return o.toString();
    }

    @RequestMapping(value = "/delete", method = DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteUser(HttpServletRequest request) throws JSONException, IOException{
        int isValid = -1;
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int id = (int) jsonObj.get("user_id");
        String password = (String) jsonObj.get("password");
        Optional<User> u = ur.findById(id);
        if (u.isPresent()) {
            // value is present inside Optional
            isValid = 1;
            ur.deleteById(id);
        } else {
            return null;
        }

        JSONObject o = new JSONObject();
        o.put("status", isValid);
        o.put("uid", id);
        return o.toString();
    }
    public List<User>getAllUsers(){
        List<User> allUsers;
        allUsers = ur.findAll();
        return allUsers;
    }

}

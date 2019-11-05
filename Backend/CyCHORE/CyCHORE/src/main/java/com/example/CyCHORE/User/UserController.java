package com.example.CyCHORE.User;

//import com.example.CyCHORE.Person;
import com.example.CyCHORE.Task.Task;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository ur;


    public UserController(UserRepository ur) {
        this.ur = ur;
    }

    /**
    	This method will register a new user to the application by taking in values such as; username, email and the type of user that is being registered (for example; current tenant, potential tenant, admin).
    	It returns a status of ‘0’ if successful (and ‘1’ if the user is not already a registered user- by verifying the information in the database).

     */

    @RequestMapping(value = "/registerUser/{first_name}/{email}/{tier}", method = POST, produces = "application/json;charset=UTF-8")
    public String registerUser(@PathVariable String first_name, @PathVariable String email, @PathVariable Integer tier) throws JSONException {
        User u = new User();
        JSONObject toReturn = new JSONObject();
        List<User> users= ur.findAll();
        for(int i =0;i<users.size();i++) {
            User p = users.get(i);
            if (p.getEmail().equals(email)) {
                    toReturn.put("status", "1");
                    return toReturn.toString();
            }
        }
        u.email = email;
        u.first_name = first_name;
        u.tier = tier;
        ur.save(u);
        toReturn.put("status", "0");
        return toReturn.toString();
        }


    @RequestMapping(value = "/addUserToGroup/{u_id}/{g_id}", method = POST, produces = "application/json;charset=UTF-8")
    public String addUserToGroup(@PathVariable Integer u_id, @PathVariable Integer g_id) throws JSONException {
        Boolean success = ur.existsById(u_id);
        JSONObject toReturn = new JSONObject();
        if (success) {
            Optional<User> test = ur.findById((u_id));
            User u = test.get();
            u.setGroup_id(g_id);
            ur.save(u);
        }
        if(success){
            toReturn.put("status","0");
        }
        else{
            toReturn.put("status","1");
        }
        return toReturn.toString();
    }

    // DEMO 4 USE CASES CONTINUED (For the MyGroup Page; JoinGroup() and GroupInfo()
    @RequestMapping(value = "/JoinGroup/{request}/{u_id}/{g_id}", method = POST, produces = "application/json;charset=UTF-8")
    public String JoinGroup (@PathVariable Integer u_id, @PathVariable Integer g_id) throws JSONException {
        Boolean success = ur.existsById(u_id);
        JSONObject toReturn = new JSONObject();
        if (success) {
            Optional<User> test = ur.findById((u_id));
            User u = test.get();
            u.setGroup_id(g_id);
            ur.save(u);
        }
        if(success){
            toReturn.put("status","0");
            toReturn.put("uid", u_id);
            toReturn.put("groupid", g_id);
        }
        else{
            toReturn.put("status","1");
        }
        return toReturn.toString();
    }

    //GroupInfo()
    //UPDATE!!!!!!!!
    @RequestMapping(value = "/GroupInfo/{request}/{u_id}", method = POST, produces = "application/json;charset=UTF-8")
    public String GroupInfo (@PathVariable Integer u_id) throws JSONException {
        //Boolean success = ur.existsById(u_id);
        JSONObject toReturn = new JSONObject();
        List<User> allUsers;
        allUsers = ur.findAll();
        int count =0;
        JSONArray jsonArray = new JSONArray();

        Optional<User> test = ur.findById((u_id));
//        User u = test.get();
//        ur.save(u);
        for (User temp : allUsers) {
            if(temp.group_id != null) {
                if (temp.group_id == ur.getUserById(u_id).group_id) {
                    JSONObject curUser = new JSONObject();
                    curUser.put("User Name", temp.username);
                    jsonArray.put(curUser);
                    count++;
                    //Users_inGroup.put(temp.toString(), curUser);
                }
            }
            }
        if(count >=1){
            toReturn.put("status","0");
            toReturn.put("Names of users in the group", jsonArray.toString());

        }
       else{
            toReturn.put("status","1");
      }
        return toReturn.toString();
    }


    //ChangeUserName()
    @RequestMapping(value = "/ChangeUsername/{request}/{u_id}/{username}", method = POST, produces = "application/json;charset=UTF-8")
    public String ChangeUsername (@PathVariable Integer u_id, @PathVariable String username) throws JSONException {
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
    @RequestMapping(value = "/ChangePassword/{request}/{u_id}/{password}", method = POST, produces = "application/json;charset=UTF-8")
    public String ChangePassword (@PathVariable Integer u_id, @PathVariable String password) throws JSONException {
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
            toReturn.put("username", password);
        }
        else{
            toReturn.put("status","1");
        }
        return toReturn.toString();
    }



}
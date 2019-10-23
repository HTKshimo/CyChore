package com.example.CyCHORE.User;

//import com.example.CyCHORE.Person;
import com.fasterxml.jackson.databind.util.JSONPObject;
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

    @RequestMapping(value = "/registerUser/{first_name}/{email}/{tier}", method = POST, produces = "application/json;charset=UTF-8")
    String registerUser(@PathVariable String first_name, @PathVariable String email, @PathVariable Integer tier) throws JSONException {
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
    String addUserToGroup(@PathVariable Integer u_id, @PathVariable Integer g_id) throws JSONException {
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
}
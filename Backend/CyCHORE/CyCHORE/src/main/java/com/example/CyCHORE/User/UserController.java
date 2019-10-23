package com.example.CyCHORE.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository ur;

    public UserController(UserRepository ur) {
        this.ur = ur;
    }

    @PostMapping("/registerUser/{first_name}/{email}/{tier}")
    public String registerUser(@PathVariable String first_name, @PathVariable String email, @PathVariable Integer tier){
        User u = new User();
        u.email = email;
        u.first_name = first_name;
        u.tier = tier;
        ur.save(u);
        return "0";
    }

    @PutMapping("/addUserToGroup/{u_id}/{g_id}")
    Boolean addUserToGroup(@PathVariable Integer u_id, @PathVariable Integer g_id){
        Boolean success = ur.existsById(u_id);
        if (success) {
            Optional<User> test = ur.findById((u_id));
            User u = test.get();
            u.setGroup_id(g_id);
            ur.save(u);
        }
        return success;
    }
}

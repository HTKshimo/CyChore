package com.example.CyCHORE.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository ur;

    @PostMapping("/registerUser/{first_name}/{email}")
    User registerUser(@PathVariable String first_name, @PathVariable String email){
        User u = new User();
        u.email = email;
        u.first_name = first_name;
        ur.save(u);
        return u;
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

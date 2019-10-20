package com.example.CyCHORE.Group;

import com.example.CyCHORE.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    GroupRepository gr;
    UserRepository ur;

    //@RequestMapping("getUsersInGroup/{g_id}")

}

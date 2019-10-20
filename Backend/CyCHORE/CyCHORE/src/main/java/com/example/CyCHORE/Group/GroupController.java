package com.example.CyCHORE.Group;

import com.example.CyCHORE.Task.*;
import com.example.CyCHORE.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    GroupRepository gr;
    @Autowired
    TaskRepository tr;

    @RequestMapping("getFinishedTasksInGroup/{g_id}")
    public List<Task> getFinishedTasksInGroup(@PathVariable Integer g_id) {
        List<Task> allTasks;
        allTasks = tr.findAll();
        List<Task> allFinishedTasksInGroup = new ArrayList<Task>();
        for (Task t : allTasks){
            if (t.getGroup_id() == g_id && t.is_completed()){
                allFinishedTasksInGroup.add(t);
            }
        }
        return allFinishedTasksInGroup;
    }

    @RequestMapping("getGroupTaskPool/{g_id}")
    public List<Task> getGroupTaskPool(@PathVariable Integer g_id) {
        List<Task> allTasks;
        allTasks = tr.findAll();
        List<Task> allTaskInPool = new ArrayList<Task>();
        for (Task t : allTasks){
            if (t.getGroup_id() == g_id && t.getIn_pool()){
                allTaskInPool.add(t);
            }
        }
        return allTaskInPool;
    }

}

package com.example.CyCHORE.Group;

import com.example.CyCHORE.Task.*;
import com.example.CyCHORE.User.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
            System.out.println(t.getGroup_id() + " " + t.is_completed());
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

    @RequestMapping(value = "/createGroup", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String createGroup(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        String address = String.valueOf(jsonObj.get("address"));
        Integer numOfTenants = Integer.valueOf((Integer) jsonObj.get("numOfTenants"));
        JSONObject toSend = new JSONObject();
        Group g = new Group(address, numOfTenants);
        gr.save(g);
        toSend.put("status", "0");
        return toSend.toString();
    }

}

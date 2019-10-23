package com.example.CyCHORE.Task;

import com.example.CyCHORE.Group.*;
import com.example.CyCHORE.User.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.CyCHORE.Group.GroupController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TaskController {

    @Autowired
    TaskRepository tr;
    @Autowired
    GroupRepository gr;
    @Autowired
    UserRepository ur;

    @RequestMapping(value = "/getTaskList/{uid}", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    private String getTaskList(@PathVariable Integer uid) throws JSONException {
        List<Task> allTaskList;
        allTaskList = tr.findAll();
	List<String> taskList = new ArrayList<String>();
        JSONObject toSend = new JSONObject();
        int todoCount = 0;
        int finishCount = 0;
        JSONObject toDo = new JSONObject();
        JSONObject finished = new JSONObject();

        for (Task temp : allTaskList) {
            if (temp.is_assigned_to() == uid){

                JSONObject curTask = new JSONObject();
                //JSONObject curTask = new JSONArray();
                curTask.put("title",temp.toString());
                curTask.put("tid",temp.getId());
                curTask.put("ddl",temp.getDdl());
                curTask.put("complete",temp.is_completed());
                if(temp.is_completed()){
                    finishCount++;
                    finished.put(temp.toString(), curTask);
                }
                else{
                    todoCount++;
                    toDo.put(temp.toString(), curTask);
                }
           }
        }
        toSend.put("status", "0");
        
        toSend.put("todo_count",todoCount);
        toSend.put("finish_count",finishCount);
        toSend.put("todo_json",toDo.toString());
        toSend.put("finish_json",finished.toString());
        return toSend.toString();
    }

    @RequestMapping(value = "/getTaskListHistory/{request}/{uid}/{gid}", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    private String getTaskListHistory(@PathVariable String request, @PathVariable Integer uid,@PathVariable Integer gid) throws JSONException {
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        JSONObject toSend = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Task temp : allTaskList) {
            if(temp.completed == true) {
                if (temp.is_assigned_to() == uid || temp.group_id == gid) {
                    JSONObject curTask = new JSONObject();
                    curTask.put("title", temp.toString());
                    curTask.put("tid", temp.getId());
                    curTask.put("ddl", temp.getDdl());
                    curTask.put("complete", temp.is_completed());
                    jsonArray.put(curTask);
                }
            }
        }
        toSend.put("status", "0");
        toSend.put("history",jsonArray.toString());
        return toSend.toString();
    }

    @RequestMapping(value = "/createTask/{title}/{description}/{g_id}/{ddl}", method = POST, produces = "application/json;charset=UTF-8")
    String createTask(@PathVariable String title, @PathVariable String description, @PathVariable Integer g_id, @PathVariable long ddl) throws JSONException {
        JSONObject toReturn = new JSONObject();
        Task t = new Task();
        Timestamp timestamp = new Timestamp(ddl);
        t.deadline = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
        t.group_id = g_id;
        t.description = description;
        t.title = title;
        tr.save(t);
        toReturn.put("status","0");
        toReturn.put("title",t.title);
        toReturn.put("tid",t.id);
        toReturn.put("description",t.description);
        toReturn.put("ddl",t.deadline);
        toReturn.put("complete","1");

        return toReturn.toString();
    }
    @DeleteMapping("/delete/{t_id}")
    Boolean deleteTask(@PathVariable Integer t_id){
        Boolean success = tr.existsById(t_id);
        if (success) {
            Optional<Task> test = tr.findById((t_id));
            Task t = test.get();
            tr.delete(t);
        }
        return success;
    }

    @PutMapping("/markAsCompleted/{u_id}/{t_id}")
    String markAsCompleted(@PathVariable Integer u_id, @PathVariable Integer t_id){
        Optional<Task> test = tr.findById(t_id);
        Task t = test.get();
        t.changeCompleteStatus(u_id, true);
        tr.save(t);
        return t.getTimeCompleted();
    }

    @PostMapping("/ChangeTaskStatus/{request}/{uid}/{tid}/{changeTo}")
    String ChangeTaskStatus(@PathVariable String request, @PathVariable Integer uid, @PathVariable Integer tid, @PathVariable String changeTo) throws JSONException {

        List<Task> allTaskList;
        allTaskList = tr.findAll();
        JSONObject o = new JSONObject();
        Task toUpdate=null;
        for (Task temp : allTaskList) {
            if (temp.id == tid) {
                toUpdate=temp;
                if(changeTo.equals("0")){
                    toUpdate.completed = true;
                }
                else if(changeTo.equals("1")){
                    toUpdate.completed = false;
                }
                else if(changeTo.equals("2")){
                    toUpdate.in_pool = true;
                }
                else if(changeTo.equals("3")){
                    System.out.println("3");
                }
                else if(changeTo.equals("5")){
                    toUpdate.assigned_to = uid;
                    toUpdate.id = tid;
                }
                tr.save(toUpdate);
                o.put("status", "0");
                return o.toString();
            }
        }
        o.put("status", "1");
        return o.toString();
    }

    @PostMapping("/pickup/{request}/{changeTo}/{u_id}/{t_id}")
    String assignPickUpTaskToUser(@PathVariable Integer u_id, @PathVariable Integer t_id) throws JSONException {
        Boolean success = tr.existsById(t_id);
        JSONObject o = new JSONObject();
        if (success) {
            Optional<Task> test = tr.findById((t_id));
            Task t = test.get();
            success = success && t.getIn_pool();
            if (success) {
                t.assignTaskToUser(u_id);
                tr.save(t);
            }
        }
        if(success){
            o.put("status","0");
            return o.toString();
        }
        o.put("status","1");
        return o.toString();
    }

    public List<Integer> getUsersInGroup(Integer g_id) {
        List<User> allUsers;
        allUsers = ur.findAll();
        List<Integer> allUserIDs = new ArrayList<Integer>();
        for (User u : allUsers){
            if (u.getGroupId() == g_id){
                allUserIDs.add(u.getId());
            }
        }
        return allUserIDs;
    }

    @PostMapping("randomlyAssign/{t_id}")
    //get task's group id, find all users in group, randomly assign
    String randomlyAssignTaskToUser(@PathVariable Integer t_id) throws JSONException {
        Boolean success = tr.existsById(t_id);
        JSONObject o = new JSONObject();
        if (success) {
            Optional<Task> test = tr.findById((t_id));
            Task t = test.get();
            Integer g_id = t.getGroup_id();
            List<Integer> usersInGroup = getUsersInGroup(g_id);
            Random rand = new Random();
            Integer selected = usersInGroup.get(rand.nextInt(usersInGroup.size()));
            t.assignTaskToUser(selected);
            tr.save(t);
        }
        if(success){
            o.put("status","0");
            return o.toString();
        }
        o.put("status","1");
        return o.toString();
    }

    @RequestMapping(value = "/getTaskPool/{request}/{uid}/{gid}", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    private String getTaskPool(@PathVariable String request, @PathVariable Integer uid,@PathVariable Integer gid) throws JSONException {
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        JSONObject toSend = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Task temp : allTaskList) {
            JSONObject curTask = new JSONObject();
            if(temp.in_pool == true) {
                if (temp.is_assigned_to() == uid || temp.group_id == gid) {
                    curTask.put("title", temp.toString());
                    curTask.put("tid", temp.getId());
                    curTask.put("ddl", temp.getDdl());
                    curTask.put("complete", temp.is_completed());
                    jsonArray.put(curTask);
                }
            }
        }
        toSend.put("status", "0");
        toSend.put("pool_list",jsonArray.toString());
        return toSend.toString();
    }

}

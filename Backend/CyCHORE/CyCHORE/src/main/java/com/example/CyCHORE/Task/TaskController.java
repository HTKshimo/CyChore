package com.example.CyCHORE.Task;

import com.example.CyCHORE.Group.*;
import com.example.CyCHORE.User.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/getTaskList/{id}", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    private String getTaskList(@PathVariable Integer id) throws JSONException {
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        List<String> taskList = new ArrayList<String>();
        JSONObject toSend = new JSONObject();
        int todoCount = 0;
        int finishCount = 0;
        JSONObject toDo = new JSONObject();
        JSONObject finished = new JSONObject();

        for (Task temp : allTaskList) {
            if (temp.is_assigned_to() == id){

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

//<<<<<<< HEAD
//
//    @PutMapping("/markAsCompleted/{user_id}/{task_id}")
//    String markAsCompleted(@PathVariable Integer user_id, @PathVariable Integer task_id){
//        Optional<Task> test = tr.findById(task_id);
//=======
    @PostMapping("/createTask/{title}/{description}/{g_id}/{ddl}")
    Task createTask(@PathVariable String title, @PathVariable String description, @PathVariable Integer g_id, @PathVariable long ddl){
        Task t = new Task();
        Timestamp timestamp = new Timestamp(ddl);
        t.deadline = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
        t.group_id = g_id;
        t.description = description;
        t.title = title;
        tr.save(t);
        return t;
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
//>>>>>>> d9efa0bd6d4bf17e6b03947b5b4b372f4ae58a99
        Task t = test.get();
        t.changeCompleteStatus(u_id, true);
        tr.save(t);
        return t.getTimeCompleted();
    }

    @PutMapping("/pickup/{u_id}/{t_id}")
    Boolean assignTaskToUser(@PathVariable Integer u_id, @PathVariable Integer t_id){
        Boolean success = tr.existsById(t_id);
        if (success) {
            Optional<Task> test = tr.findById((t_id));
            Task t = test.get();
            success = success && t.getIn_pool();
            if (success) {
                t.assignTaskToUser(u_id);
                tr.save(t);
            }
        }
        return success;
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

    @PutMapping("randomlyAssign/{t_id}")
    //get task's group id, find all users in group, randomly assign
    Boolean randomlyAssignTaskToUser(@PathVariable Integer t_id) {
        Boolean success = tr.existsById(t_id);
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
        return success;
    }

}

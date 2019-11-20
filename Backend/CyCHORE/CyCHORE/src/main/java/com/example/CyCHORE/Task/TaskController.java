package com.example.CyCHORE.Task;

import com.example.CyCHORE.Group.*;
import com.example.CyCHORE.User.*;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.example.CyCHORE.Group.GroupController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TaskController {

    @Autowired
    TaskRepository tr;
    @Autowired
    GroupRepository gr;
    @Autowired
    UserRepository ur;

    /**
    This method returns a list of task assigned to a user (whether or not they are in a group). Information that is sent to this method includes the user_id and group_id.
    It returns a status of ‘0’ if successful and ‘1’ if not (due to some error) along with the list of the task description in the correct JSON format pertaining to that user.

     */

    @RequestMapping(value = "/getTaskList", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getTaskList(HttpServletRequest request) throws JSONException, IOException {
//        @PathVariable Integer uid
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        //String password = (String) jsonObj.get("password");
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

    /**
     This method returns a list of completed tasks by a user (whether or not they are in a group). Information that is sent to this method includes the user_id and group_id.
    It returns a status of ‘0’ if successful and ‘1’ if not (due to some error) along with the list of the completed tasks (history) in the correct JSON format pertaining to that user.

     */
    @RequestMapping(value = "/getTaskListHistory", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getTaskListHistory(HttpServletRequest request) throws JSONException, IOException {
        //@PathVariable String request, @PathVariable Integer uid,@PathVariable Integer gid
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer gid = Integer.valueOf((Integer) jsonObj.get("groupid"));
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        JSONObject toSend = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Task temp : allTaskList) {
            if(temp.completed!=null) {
                if (temp.completed == true) {
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
        }
        toSend.put("status", "0");
        toSend.put("history",jsonArray.toString());
        return toSend.toString();
    }

    /**

    	This method takes input from the frontend to create a task using information such as; task description, deadline, task_id, task title as well as a ‘complete’ field to indicate whether or not it is completed.
    	Returns a status of ‘0’ if successful and ‘1’ if not.

     */

    @RequestMapping(value = "/createTask", method = POST, produces = "application/json;charset=UTF-8")
    public String createTask(HttpServletRequest request) throws JSONException, IOException {
        //@PathVariable String title, @PathVariable String description, @PathVariable Integer g_id, @PathVariable long ddl
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        String title = (String) jsonObj.get("title");
        String description = (String) jsonObj.get("description");
        Integer g_id = Integer.valueOf((Integer) jsonObj.get("groupid"));
        Long ddl = Long.valueOf((Long) jsonObj.get("deadline"));
        List<Task> allTasks = tr.findAll();
        JSONObject toReturn = new JSONObject();
        Task t = new Task();
        int isDuplicate = 0;
        Timestamp timestamp = new Timestamp(ddl);
        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            if (!task.title.equals(title)) {
                if (!task.description.equals(description)) {
                    if(!task.group_id.equals(g_id)){
                        if (!task.deadline.equals(ddl)) {
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

                        }
                    }
                }
            }
            else{
                isDuplicate = 1;
                toReturn.put("status", "1");
            }
        }
//        userID = user.getId();
//        tier = user.getTier();
//        group_id = user.getGroupId();

        return toReturn.toString();
    }

    /**
    	This method deletes a tasks to a user (by taking information such as; task description, deadline, task_id, task title as well as a ‘complete’ field to indicate whether or not it is completed.
    	It returns a status of ‘0’ if successful and ‘1’ if not (due to some error).

     */
    @DeleteMapping("/delete")
    public Boolean deleteTask(HttpServletRequest request) throws IOException, JSONException {
        //@PathVariable Integer t_id
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer t_id = Integer.valueOf((Integer) jsonObj.get("task id"));
        //String description = (String) jsonObj.get("description");
        Boolean success = tr.existsById(t_id);
        if (success) {
            Optional<Task> test = tr.findById((t_id));
            Task t = test.get();
            tr.delete(t);
        }
        return success;
    }
    /**
    	This method changes completion status of a task for a user (by taking information such as; task_id, user_id and group_id).
    	It returns a status of ‘0’ if successful and ‘1’ if not (due to some error).

     */
    @PutMapping("/markAsCompleted")
    public String markAsCompleted(HttpServletRequest request) throws IOException, JSONException {
        //@PathVariable Integer u_id, @PathVariable Integer t_id
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer u_id = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer t_id = Integer.valueOf((Integer) jsonObj.get("task id"));
        Optional<Task> test = tr.findById(t_id);
        Task t = test.get();
        t.changeCompleteStatus(u_id, true);
        tr.save(t);
        return t.getTimeCompleted();
    }

    /**
    This method changes the task status for a user (by taking information such as; task_id, user_id and group_id).
    It returns a status of ‘0’ if successful and ‘1’ if not (due to some error).

     */
    //NEEDS THE UPDATED VERSION FOR HTTP REQUESTS!!!!!!!!!!!!!

    @PostMapping("/ChangeTaskStatus")
    public String ChangeTaskStatus(HttpServletRequest request) throws JSONException, IOException {
        //@PathVariable String request, @PathVariable Integer uid, @PathVariable Integer tid, @PathVariable String changeTo
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer tid = Integer.valueOf((Integer) jsonObj.get("task id"));
        String changeTo = (String) jsonObj.get("status");
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        JSONObject o = new JSONObject();
        Task toUpdate=null;
        for (Task temp : allTaskList) {
            if (temp.id == tid && temp.assigned_to == uid) {
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
        //o.put("error", "Either the task id is incorrect or the user id does not match the task.")
        return o.toString();
    }

    /**
    	This method allows a user to pick up a task that has been pushed to the pool by another user (whether or not they are in a group). Information that is sent to this method includes the user_id and task_id.
    	It returns a status of ‘0’ if successful and ‘1’ if not (due to some error) along with the list of the task description in the correct JSON format pertaining to that user.

     */

    @PostMapping("/pickup")
    public String assignPickUpTaskToUser(HttpServletRequest request) throws JSONException, IOException {
        //"/pickup/{request}/{changeTo}/{u_id}/{t_id}"
        //@PathVariable Integer u_id, @PathVariable Integer t_id
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        //String changeTo = (String) jsonObj.get("status");
        Integer u_id = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer t_id = Integer.valueOf((Integer) jsonObj.get("task id"));
        Boolean success = tr.existsById(t_id);
        Boolean success2 = tr.existsById(u_id);
        JSONObject o = new JSONObject();
        if (success && success2) {
            Optional<Task> test = tr.findById((t_id));
            Task t = test.get();
            success = success && t.getIn_pool();
            if (success) {
                t.assignTaskToUser(u_id);
                tr.save(t);
            }
        }
        if(success && success2){
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

    /**
    	This method randomly assigns users with tasks (whether or not they are in a group)
    	This information is automatically updated in the database.

     */
    @PostMapping("randomlyAssign/{t_id}")
    //get task's group id, find all users in group, randomly assign
    public String randomlyAssignTaskToUser(HttpServletRequest request) throws JSONException, IOException {
        //@PathVariable Integer t_id
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        //String changeTo = (String) jsonObj.get("status");
        //Integer u_id = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer t_id = Integer.valueOf((Integer) jsonObj.get("task id"));
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


    /**
    Gets all the tasks that have been pushed to the pool by the current tenant users. This method takes the user_id and the
    group_id as well as a request
     */
    @RequestMapping(value = "/getTaskPool/{request}/{uid}/{gid}", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getTaskPool(HttpServletRequest request) throws JSONException, IOException {
        //@PathVariable String request, @PathVariable Integer uid,@PathVariable Integer gid
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        //String changeTo = (String) jsonObj.get("status");
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer gid = Integer.valueOf((Integer) jsonObj.get("group id"));
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        JSONObject toSend = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Task temp : allTaskList) {
            JSONObject curTask = new JSONObject();
            if(temp.in_pool != null){
            if(temp.in_pool == true) {
                if (temp.is_assigned_to() == uid || temp.group_id == gid) {
                    curTask.put("title", temp.toString());
                    curTask.put("tid", temp.getId());
                    curTask.put("ddl", temp.getDdl());
                    curTask.put("complete", temp.is_completed());
                    //JObject.Parse(curTask);
                    jsonArray.put(curTask);
                }
            }
            }
        }
        toSend.put("status", "0");
        toSend.put("pool_list",jsonArray.toString());
        return toSend.toString();
    }

}

package com.example.CyCHORE.Group;

import com.example.CyCHORE.Complaint.Complaint;
import com.example.CyCHORE.Listings.Listings;
import com.example.CyCHORE.Task.*;
import com.example.CyCHORE.User.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class GroupController {

    @Autowired
    GroupRepository gr;
    @Autowired
    TaskRepository tr;
    @Autowired
    UserRepository ur;

    @RequestMapping(value = "/getFinishedTasksInGroup", method = POST, produces = "application/json;charset=UTF-8")
    public String getFinishedTasksInGroup(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int gid = (int) jsonObj.get("group_id");
        JSONObject toSend = new JSONObject();
       // TaskController taskc = new TaskController();
        //List<Task> allTasks =  taskc.getAllTasks();
        //allTasks = taskc.getAllTasks();
        List<Task> allTasks = tr.findAll();
        JSONArray jsonArray = new JSONArray();
        List<Task> allFinishedTasksInGroup = new ArrayList<Task>();
        for (Task t : allTasks) {
            if (t.getGroup_id() == gid && t.is_completed()) {
                JSONObject curTask = new JSONObject();
                curTask.put("title", t.getTitle());
                curTask.put("description", t.getDescription());
                curTask.put("ddl", t.getDdl());
                curTask.put("time completed", t.getTimeCompleted());
                curTask.put("assigned to", t.getAssigned_to());
                jsonArray.put(curTask);
            }
        }
        toSend.put("status", "0");
        toSend.put("Finished Tasks",jsonArray);
        return toSend.toString();
    }

    @RequestMapping(value = "/getGroupTaskPool", method = POST, produces = "application/json;charset=UTF-8")
    public String getGroupTaskPool(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int gid =  Integer.valueOf((int) jsonObj.get("group_id"));
        List<Task> allTasks;
        JSONObject toSend = new JSONObject();
        //TaskController taskc = new TaskController();
        allTasks = tr.findAll();
        JSONArray jsonArray = new JSONArray();
        List<Task> allFinishedTasksInGroup = new ArrayList<Task>();
        for (Task t : allTasks) {
            if(t.getIn_pool() != null) {
                if (t.getIn_pool() == true) {
                    if (t.getGroup_id() == gid && t.getIn_pool()) {
                        JSONObject curTask = new JSONObject();
                        curTask.put("title", t.getTitle());
                        curTask.put("description", t.getDescription());
                        curTask.put("ddl", t.getDdl());
                        curTask.put("time completed", t.getTimeCompleted());
                        curTask.put("assigned to", t.getAssigned_to());
                        jsonArray.put(curTask);
                    }
                }
            }
        }
        toSend.put("status", "0");
        toSend.put("Group Task Pool",jsonArray);
        return toSend.toString();
    }

    //Group Management Methods
    //Delete Group
    @RequestMapping(value = "/DeleteGroup", method = DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String DeleteGroup(HttpServletRequest request) throws JSONException, IOException {
        int isValid = -1;
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject o = new JSONObject();
        JSONObject jsonObj = new JSONObject(data);
        int id = (int) jsonObj.get("group_id");
        //String password = (String) jsonObj.get("password");
        Optional<Group> u = gr.findById(id);
        if (u.isPresent()) {
            isValid = 0;
            gr.deleteById(id);
        } else {
            isValid=1;
            o.put("status", isValid);
        }

        o.put("status", isValid);
        o.put("group_id", id);
        return o.toString();
    }

    /*Add tenant to group
    Adds a tenant by taking the user id and group id and verifies if the user exists  and if the group exists by comparing values to the
    User data table. Then it,
    */
    //FOREIGN KEY ERROR, NEEDS CHECKING!!! FOR NEW GROUPS!!!!!
    @RequestMapping(value = "/addTenant", method = POST, produces = "application/json;charset=UTF-8")
    public String addTenant(HttpServletRequest request) throws JSONException, IOException, NullPointerException{
        int isValid = -1;
        JSONObject o = new JSONObject();
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int id = (int) jsonObj.get("uid");
        int gid = (int) jsonObj.get("groupid");
        List<Group> allGroups = gr.findAllById(Collections.singleton(gid));
        Boolean success1 = gr.existsById(gid);
        Optional<User> u = ur.findById(id);
        Optional<Group> g = gr.findById(gid);
        if (u.isPresent()&&g.isPresent()) {
            isValid = 0;
            User u1 =u.get();
            u1.setGroup_id(gid);
            ur.save(u1);
            if (success1) {
                    Group u2 =g.get();
                    Integer num_of_ten  = u2.num_of_tenants +1;
                    u2.num_of_tenants = num_of_ten;
                    gr.save(u2);
                }

        } else {
            isValid = 1;
            o.put("status", isValid);
        }
        o.put("status", isValid);
        o.put("groupid", id);
        return o.toString();
    }
    //Remove tenant from group
    //WORKS!!!!
    @RequestMapping(value = "/RemoveTenant", method = DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String RemoveTenantFromGroup(HttpServletRequest request) throws JSONException, IOException {
        int isValid = -1;
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        JSONObject o = new JSONObject();
        int id = (int) jsonObj.get("uid");
        int gid = (int) jsonObj.get("groupid");
        List<Group> allGroups = gr.findAllById(Collections.singleton(gid));
        Boolean success1 = gr.existsById(gid);
        Integer new_num=0;
        Optional<User> u = ur.findById(id);
        Optional<Group> g = gr.findById(gid);
        if (u.isPresent() &&g.isPresent()) {
            isValid = 0;
            User u1 =u.get();
            u1.setGroup_id(null);
            ur.save(u1);
            if (success1){
                Group u2 =g.get();
               Integer num_of_ten = u2.num_of_tenants -1;
                u2.num_of_tenants = num_of_ten;
                gr.save(u2);
            }
        } else {
            isValid=1;
            o.put("status", isValid);
        }

        o.put("status", isValid);
        o.put("groupid", id);
        return o.toString();
    }

    //
//    View Details of Group, "/getAllGroups" method"
//    This method returns all listings made all users; FOR EVERYBODY
    @RequestMapping(value = "/getAllGroups", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getAllGroups(HttpServletRequest request) throws JSONException, IOException, NullPointerException {
        List<Group> allGroups;
        allGroups = gr.findAll();
        JSONObject toSend = new JSONObject();
        JSONObject Comp = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int GroupCount = 0;
        for (Group temp : allGroups) {
            JSONObject curComp = new JSONObject();
            curComp.put("id",temp.getId().toString());
            curComp.put("address", temp.getAddress().toString());
            curComp.put("num_of_tenants", temp.getNum_of_tenants().toString());
            jsonArray.put(curComp);
            GroupCount++;
        }
        toSend.put("status", "0");
        toSend.put("Number Groups", GroupCount);
        toSend.put("List of Groups:", jsonArray);
        return toSend.toString();
    }

    //Make a new group
    @RequestMapping(value = "/createGroup", method = POST, produces = "application/json;charset=UTF-8")
    public String createGroup(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer group_id = Integer.valueOf((Integer) jsonObj.get("group_id"));
        String address = (String) jsonObj.get("address");
        List<Group> allGroup = gr.findAll();
        JSONObject toReturn = new JSONObject();
        Group g = new Group();
        int isDuplicate = 0;
        for (int i = 0; i < allGroup.size(); i++) {
            Group list = allGroup.get(i);
            if (!list.address.equals(address)) {
                if (!list.id.equals(group_id)) {
                                g.address = address;
                                g.id = group_id;
                                gr.save(g);
                                toReturn.put("status", "0");
                                toReturn.put("address", g.address);
                                toReturn.put("group_id", g.id);
                }
            }
            else {
                isDuplicate = 1;
                toReturn.put("status", "1");
            }
        }
        return toReturn.toString();
    }
}
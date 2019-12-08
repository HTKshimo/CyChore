package com.example.CyCHORE.Group;

import com.example.CyCHORE.Complaint.Complaint;
import com.example.CyCHORE.Listings.Listings;
import com.example.CyCHORE.Task.*;
import com.example.CyCHORE.User.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @RequestMapping("getFinishedTasksInGroup/{g_id}")
    public List<Task> getFinishedTasksInGroup(@PathVariable Integer g_id) {
        List<Task> allTasks;
        allTasks = tr.findAll();
        List<Task> allFinishedTasksInGroup = new ArrayList<Task>();
        for (Task t : allTasks) {
            System.out.println(t.getGroup_id() + " " + t.is_completed());
            if (t.getGroup_id() == g_id && t.is_completed()) {
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
        for (Task t : allTasks) {
            if (t.getGroup_id() == g_id && t.getIn_pool()) {
                allTaskInPool.add(t);
            }
        }
        return allTaskInPool;
    }

    //Group Management Methods
    //Delete Group
    @RequestMapping(value = "/DeleteGroup", method = DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String DeleteGroup(HttpServletRequest request) throws JSONException, IOException {
        int isValid = -1;
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int id = (int) jsonObj.get("group_id");
        //String password = (String) jsonObj.get("password");
        Optional<Group> u = gr.findById(id);
        if (u.isPresent()) {
            isValid = 1;
            gr.deleteById(id);
        } else {
            return null;
        }

        JSONObject o = new JSONObject();
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
//        String data = request.getReader().lines().collect(Collectors.joining());
//        JSONObject jsonObj = new JSONObject(data);
//        Integer num_of_ten=0;
//        Integer group_id = Integer.valueOf((Integer) jsonObj.get("group_id"));
//        Integer user_id = Integer.valueOf((Integer) jsonObj.get("user_id"));
//        //Float price = Float.valueOf((Float) jsonObj.get("price"));
//       // float price = BigDecimal.valueOf((Double) jsonObj.get("price")).floatValue();
//        //String description = (String) jsonObj.get("description");
//        List<User> allUsers = ur.findAll();
//        List<Group> allGroups = gr.findAll();
//        JSONObject toReturn = new JSONObject();
//        Group g = new Group();
//        User u = new User();
//        int isDuplicate = 0;
//        for (int i = 0; i < allUsers.size(); i++) {
//            User tenant = allUsers.get(i);
//            Boolean success = ur.existsById(user_id);
//            Boolean success1 = gr.existsById(group_id);
//                if (success) {
//                    Optional<User> test = ur.findById((user_id));
//                    User u1 = test.get();
//                    u1.setGroup_id(group_id);
//                    //tenant.setGroup_id(group_id);
//                    ur.save(u);
//                    toReturn.put("status", "0");
//                    toReturn.put("group_id", tenant.getGroupId());
//                    toReturn.put("user_id", tenant.getId());
////                    for (int j = 0; j < allGroups.size(); j++) {
////                        Group group = allGroups.get(j);
////                        if (success1) {
////                                num_of_ten ++;
////                                group.setId(num_of_ten);
////                                gr.save(g);
////                            }
////                    }
//            }
//            else {
//                isDuplicate = 1;
//                toReturn.put("status", "1");
//            }
//        }
//        return toReturn.toString();
//
//    }

        //SECOND TRY
//        String data = request.getReader().lines().collect(Collectors.joining());
//        JSONObject jsonObj = new JSONObject(data);
//        Integer u_id = (Integer) jsonObj.get("uid");
//        Integer g_id = (Integer) jsonObj.get("groupid");
//        Boolean success = ur.existsById(u_id);
//        JSONObject toReturn = new JSONObject();
//        if (success) {
//            Optional<User> test = ur.findById((u_id));
//            User u = test.get();
//            u.setGroup_id(g_id);
//            ur.save(u);
//        }
//        if (success) {
//            toReturn.put("status", "0");
//            toReturn.put("uid", u_id);
//            toReturn.put("groupid", g_id);
//        } else {
//            toReturn.put("status", "1");
//        }
//        return toReturn.toString();
//        String data = request.getReader().lines().collect(Collectors.joining());
//        JSONObject jsonObj = new JSONObject(data);
//        JSONObject toSend = new JSONObject();
//        try {
//            Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
//            Integer gid = Integer.valueOf((Integer) jsonObj.get("gid"));
//            Optional<User> test= ur.findById(uid);
//            User u = test.get();
//            u.setGroup_id(gid);
//            //String title = String.valueOf(jsonObj.get("title"));
//            //String description = String.valueOf(jsonObj.get("description"));
//            //Complaint c = new Complaint(tid, title, description, uid);
//            ur.save(u);
//            toSend.put("status", "0");
//        } catch (JSONException e){
//            e.printStackTrace();
//            toSend.put("status", "1");
//        }
//        return toSend.toString();
        int isValid = -1;
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int id = (int) jsonObj.get("uid");
        int gid = (int) jsonObj.get("groupid");
        //String password = (String) jsonObj.get("password");
        Optional<User> u = ur.findById(id);
        if (u.isPresent()) {
            isValid = 1;
            User u1 =u.get();
            u1.setGroup_id(gid);
            ur.save(u1);
        } else {
            return null;
        }

        JSONObject o = new JSONObject();
        o.put("status", isValid);
        o.put("groupid", id);
        return o.toString();
    }
//    Remove tenant from group

    //Delete Group
    //WORKS!!!!
    @RequestMapping(value = "/RemoveTenant", method = DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String RemoveTenantFromGroup(HttpServletRequest request) throws JSONException, IOException {
        int isValid = -1;
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        int id = (int) jsonObj.get("uid");
        int gid = (int) jsonObj.get("groupid");
        //String password = (String) jsonObj.get("password");
        Optional<User> u = ur.findById(id);
        if (u.isPresent()) {
            isValid = 1;
            User u1 =u.get();
            u1.setGroup_id(null);
            ur.save(u1);
        } else {
            return null;
        }

        JSONObject o = new JSONObject();
        o.put("status", isValid);
        o.put("groupid", id);
        return o.toString();
    }

    //
//    View Details of Group, "/getAllGroups" method"
//    This method returns all listings made all users; FOR EVERYBODY
//    Task status indicates whether or not a task has been completed
//    If Task status is '0', the task is not completed
//    If Task status is '1', the task is completed
    @RequestMapping(value = "/getAllGroups", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getAllGroups(HttpServletRequest request) throws JSONException, IOException {
        List<Group> allGroups;
        allGroups = gr.findAll();
        JSONObject toSend = new JSONObject();
        JSONObject Comp = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int GroupCount = 0;
        for (Group temp : allGroups) {
            //if (temp.filer_id == uid){

            JSONObject curComp = new JSONObject();
            //JSONObject curTask = new JSONArray();
            curComp.put("id",temp.getId().toString());
            curComp.put("address", temp.getAddress().toString());
            curComp.put("num_of_tenants", temp.getNum_of_tenants().toString());
            jsonArray.put(curComp);
            GroupCount++;
            //}
        }
        toSend.put("status", "0");
        toSend.put("Number Groups", GroupCount);
        //toSend.put("List of complaints:", Comp.toString());
        toSend.put("List of Groups:", jsonArray);
        return toSend.toString();
    }
}
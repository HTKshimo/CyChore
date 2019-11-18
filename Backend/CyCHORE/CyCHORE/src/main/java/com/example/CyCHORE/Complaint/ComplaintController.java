package com.example.CyCHORE.Complaint;
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

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ComplaintController {

    @Autowired
    ComplaintRepository cr;


    //This method returns complaints made by a particular user; FOR MY COMPLAINTS
    //This method needs uid of the user, that's all.
    //Task status indicates whether or not a task has been completed
    //If Task status is '0', the task is not completed
    //If Task status is '1', the task is completed
    @RequestMapping(value = "/getComplaintListforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getComplaintListforUser(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        List<Complaint> allComplaintList;
        allComplaintList = cr.findAll();
        List<String> complaintList = new ArrayList<String>();
        JSONObject toSend = new JSONObject();
        JSONObject Comp = new JSONObject();
        int ComplaintCount = 0;
        for (Complaint temp : allComplaintList) {
            if (temp.filer_id == uid){

                JSONObject curComp = new JSONObject();
                //JSONObject curTask = new JSONArray();
                curComp.put("tid",temp.getId().toString());
                curComp.put("description", temp.getDescription().toString());
                curComp.put("Task status:", temp.getStatus().toString());
               // Comp.put(temp.toString(), curComp);
                Comp.put(String.valueOf(temp.id), curComp);
                ComplaintCount++;
            }
        }
        toSend.put("status", "0");
        toSend.put("Number of complaints", ComplaintCount);
        toSend.put("List of complaints:", Comp.toString());
        return toSend.toString();
    }


    // Make a method for changing complaint submission by a user





}

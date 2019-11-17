package com.example.CyCHORE.Listings;

import com.example.CyCHORE.Complaint.Complaint;
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
public class ListingsController {

    @Autowired
    ListingsRepository lr;



    //This method returns sublease listings made by a particular user; FOR MY LISTINGS
    //This method needs uid of the user, that's all.
    @RequestMapping(value = "/getListingforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getListingforUser(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        List<Listings> allListing;
        allListing = lr.findAll();
        //List<String> complaintList = new ArrayList<String>();
        JSONObject toSend = new JSONObject();
        JSONObject ListB = new JSONObject();
        int ListCount = 0;
        for (Listings list : allListing) {
            if (list.user_id == uid){

                JSONObject curList = new JSONObject();
                //JSONObject curTask = new JSONArray();
                curList.put("Address",list.getAddress());
                curList.put("Group id", list.getGroup_id());
                curList.put("User id",list.getUser_id());
                curList.put("Price", list.getPrice());
                curList.put("Description", list.getDescription());
                // Comp.put(temp.toString(), curComp);
                ListB.put(list.toString(), curList);
                ListCount++;
            }
        }
        toSend.put("status", "0");
        toSend.put("Number of Sublease Listings", ListCount);
        toSend.put("List of Sublease Listings:", ListB );
        return toSend.toString();
    }
}

package com.example.CyCHORE.Listings;

import com.example.CyCHORE.Task.Task;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
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
        List<Listings> SubleaseList;
        SubleaseList = lr.findAll();
        JSONObject toSend = new JSONObject();
        JSONObject SubleaseReturn = new JSONObject();
        String s = null;
        int ListCount = 0;
        for (Listings temp : SubleaseList) {

            if (temp.user_id == uid){

                JSONObject curList = new JSONObject();
                curList.put("Address",temp.getAddress());
                curList.put("Group id", temp.getGroup_id());
                curList.put("User id",temp.getUser_id());
                curList.put("Price", temp.getPrice());
                curList.put("Description", temp.getDescription());
                SubleaseReturn.put(String.valueOf(temp.list_id), curList);
                ListCount++;
            }
        }
        toSend.put("status", "0");
        toSend.put("Number of Sublease Listings for User", ListCount);
        toSend.put("List of Sublease Listings for User:", SubleaseReturn);
        return toSend.toString();
    }


    //This method returns sublease listings made by a particular user; FOR MY LISTINGS
    //This method needs uid of the user, that's all.
    @RequestMapping(value = "/getListingforGroup", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getListingforGroup(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer gid = Integer.valueOf((Integer) jsonObj.get("gid"));
        List<Listings> SubleaseList;
        SubleaseList = lr.findAll();
        JSONObject toSend = new JSONObject();
        JSONObject SubleaseReturn = new JSONObject();
        String s = null;
        int ListCount = 0;
        for (Listings temp : SubleaseList) {

            if (temp.group_id == gid){

                JSONObject curList = new JSONObject();
                curList.put("Address",temp.getAddress());
                curList.put("Group id", temp.getGroup_id());
                curList.put("Price", temp.getPrice());
                curList.put("Description", temp.getDescription());
                curList.put("List id", temp.getList_id());
                SubleaseReturn.put(String.valueOf(temp.list_id), curList);
                ListCount++;
            }
        }
        toSend.put("status", "0");
        toSend.put("Number of Sublease Listings for Group", ListCount);
        toSend.put("List of Sublease Listings for this Group:", SubleaseReturn);
        return toSend.toString();
    }


    //This method returns all listings made all users; FOR EVERYBODY
    @RequestMapping(value = "/getAllListings", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getAllListings(HttpServletRequest request) throws JSONException, IOException {
        List<Listings> allListings;
        allListings = lr.findAll();
        JSONObject toSend = new JSONObject();
        JSONObject Comp = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int ListingCount = 0;
        for (Listings temp : allListings) {
            JSONObject curComp = new JSONObject();
            curComp.put("List id",temp.getList_id().toString());
            curComp.put("Address", temp.getAddress().toString());
            curComp.put("Group id", temp.getGroup_id().toString());
            curComp.put("User id", temp.getUser_id());
            curComp.put("Price", temp.getPrice());
            curComp.put("Description", temp.getDescription());
            jsonArray.put(curComp);
            ListingCount++;
        }
        toSend.put("status", "0");
        toSend.put("Number of Sublease Listings", ListingCount);
        toSend.put("List of Sublease Listings:", jsonArray);
        return toSend.toString();
    }


    /**

     This method takes input from the frontend to create a sub lease listing using information such as; address, group id, user id, price, description.
     Returns a status of ‘0’ if successful and ‘1’ if not.

     */

    @RequestMapping(value = "/createListing", method = POST, produces = "application/json;charset=UTF-8")
    public String createList(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        String address = (String) jsonObj.get("address");
        Integer group_id = Integer.valueOf((Integer) jsonObj.get("group_id"));
        Integer user_id = Integer.valueOf((Integer) jsonObj.get("user_id"));
        float price = BigDecimal.valueOf((Double) jsonObj.get("price")).floatValue();
        String description = (String) jsonObj.get("description");
        List<Listings> allListings = lr.findAll();
        JSONObject toReturn = new JSONObject();
        Listings l = new Listings();
        int isDuplicate = 0;
        for (int i = 0; i < allListings.size(); i++) {
            Listings list = allListings.get(i);
            if (!list.address.equals(address)) {
                if (!list.group_id.equals(group_id)) {
                    if (!list.user_id.equals(user_id)) {
                        if (!list.price.equals(price)) {
                            if (!list.description.equals(description)) {
                                l.address = address;
                                l.group_id = group_id;
                                l.user_id = user_id;
                                l.price = price;
                                l.description =description;
                                lr.save(l);
                                toReturn.put("status", "0");
                                toReturn.put("address", l.address);
                                toReturn.put("group_id", l.group_id);
                                toReturn.put("user_id", l.user_id);
                                toReturn.put("price", l.price);
                                toReturn.put("description", l.description);
                            }
                        }
                    }
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


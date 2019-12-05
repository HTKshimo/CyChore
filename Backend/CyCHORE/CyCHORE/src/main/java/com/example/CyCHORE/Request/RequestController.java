package com.example.CyCHORE.Request;

import com.example.CyCHORE.Task.Task;
import com.example.CyCHORE.Task.TaskRepository;
import com.example.CyCHORE.User.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RequestController {

    @Autowired
    RequestRepository rr;
    @Autowired
    UserRepository ur;

    @RequestMapping(value = "/fileNewRequest", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String fileNewRequest(HttpServletRequest request) throws JSONException, IOException {
//    @RequestMapping("fileNewRequest/{uid}/{type}")
//    public String fileNewRequest(@PathVariable Integer uid, @PathVariable Integer type) throws JSONException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        JSONObject toSend = new JSONObject();
        try {
            Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
            Integer type = Integer.valueOf((Integer) jsonObj.get("type"));
            Request r = new Request(uid, type);
            rr.save(r);
            toSend.put("status", "0");
        } catch (JSONException e){
            e.printStackTrace();
            toSend.put("status", "1");
        }
        return toSend.toString();
        //TODO: an idea: real time update on admin screen when received new request?
    }

    /**
     * This method takes user id and status requested and return list of requests that have given status and are assigned to admin with given user id.
     *
     * @param request
     * @return Returned Requests based on order of last updated timestamp new->old
     * @throws JSONException
     * @throws IOException
     */
    @RequestMapping(value = "/getRequest", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getRequest(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer type = Integer.valueOf((Integer) jsonObj.get("type"));
        Integer request_status = Integer.valueOf((Integer) jsonObj.get("request_status"));
//    @RequestMapping("getRequest/{uid}/{type}/{request_status}")
//    public String getRequest(@PathVariable Integer uid, @PathVariable Integer type, @PathVariable Integer request_status) throws JSONException {

        JSONObject toSend = new JSONObject();
        HashMap<Long, JSONObject> timeRequestHashMap = new HashMap<>();
        Timestamp t;
        List<Request> allRequestList;
        allRequestList = rr.findAll();

        int requestCount = 0;
        for (Request temp : allRequestList) {
            if ((type == temp.getType()) && (temp.getRequesteeId() == uid || uid == 0) && (request_status == 5 || temp.getStatus() == request_status || ((temp.getStatus() == 2 || temp.getStatus() == 3) && request_status == 4))) {
                JSONObject curComp = new JSONObject();
                t = new Timestamp(temp.getLastUpdated());
                curComp.put("rid", temp.getId());
                curComp.put("type", temp.getType());
                curComp.put("status", temp.getStatus().toString());
                curComp.put("lastUpdated", t);
                timeRequestHashMap.put(temp.getLastUpdated(), curComp);
                requestCount++;
            }
        }
        Map<Long, JSONObject> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(timeRequestHashMap);

        toSend.put("status", "0");
        toSend.put("num_requests", requestCount);
        toSend.put("requests", treeMap.values());

        return toSend.toString();
    }

    /**
     * toStatus is 2: approve request; 3: deny request
     * @param request
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @RequestMapping(value = "/changeRequestStatus", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String changeRequestStatus(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer rid = Integer.valueOf((Integer) jsonObj.get("rid"));
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer toStatus = Integer.valueOf((Integer) jsonObj.get("toStatus"));
        String response = String.valueOf(jsonObj.get("response"));

//    @RequestMapping("changeRequestStatus/{uid}/{rid}/{toStatus}/{response}")
//    public String changeRequestStatus(@PathVariable Integer uid, @PathVariable Integer rid, @PathVariable Integer toStatus, @PathVariable String response) throws JSONException {

        JSONObject toSend = new JSONObject();
        Boolean success = rr.existsById(rid);
        if (success) {
            Request r = rr.findById(rid).get();
            r.setStatus(toStatus);
            r.setRequesteeId(uid);
            r.setResponse(response);
            r.setLastUpdated();
            rr.save(r);
            toSend.put("status", "0");
        }else{
            toSend.put("status", "1");
        }
        return toSend.toString();
    }

}

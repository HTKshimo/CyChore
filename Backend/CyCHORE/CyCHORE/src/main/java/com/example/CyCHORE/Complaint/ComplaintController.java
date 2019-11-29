package com.example.CyCHORE.Complaint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ComplaintController {

    @Autowired
    ComplaintRepository cr;

    /**
     * This method takes user id and status requested and return list of complaints that have given status and are assigned to admin with given user id.
     * @param request
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @RequestMapping(value = "/getComplaint", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String getComplaint(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        Integer complaint_status = Integer.valueOf((Integer) jsonObj.get("complaint_status"));
        JSONObject toSend = new JSONObject();
        List<Complaint> allComplaintList;
        allComplaintList = cr.findAll();
        JSONObject Comp = new JSONObject();
        System.out.println(uid);
        System.out.println(complaint_status);
        int ComplaintCount = 0;
        for (Complaint temp : allComplaintList) {
            if ((temp.getFiler_id() == uid || uid == 0) && (complaint_status == 5 || temp.getStatus() == complaint_status || ((temp.getStatus() == 2 || temp.getStatus() == 3) && complaint_status == 4))) {
                System.out.println("found a complaint\n");
                JSONObject curComp = new JSONObject();
                curComp.put("title", temp.getTitle());
                curComp.put("status", temp.getStatus().toString());
                Comp.put(String.valueOf(temp.getId()), curComp);
                ComplaintCount++;
            }
        }
        toSend.put("status", "0");
        toSend.put("num_complaints", ComplaintCount);
        toSend.put("complaints", Comp);

        return toSend.toString();
    }



}

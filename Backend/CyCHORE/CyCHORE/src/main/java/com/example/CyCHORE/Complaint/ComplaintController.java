package com.example.CyCHORE.Complaint;
import com.example.CyCHORE.Task.*;
import com.example.CyCHORE.User.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ComplaintController {

    @Autowired
    ComplaintRepository cr;
    @Autowired
    TaskRepository tr;
    @Autowired
    UserRepository ur;

    @RequestMapping(value = "/fileNewComplaint", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String fileNewComplaint(HttpServletRequest request) throws JSONException, IOException {
//    @RequestMapping("fileNewComplaint/{uid}/{tid}/{title}/{description}")
//    public String fileNewComplaint(@PathVariable Integer uid, @PathVariable Integer tid, @PathVariable String title, @PathVariable String description) throws JSONException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        JSONObject toSend = new JSONObject();
        try {
            Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
            Integer tid = Integer.valueOf((Integer) jsonObj.get("tid"));
            String title = String.valueOf(jsonObj.get("title"));
            String description = String.valueOf(jsonObj.get("description"));
            Complaint c = new Complaint(tid, title, description, uid);
            cr.save(c);
            toSend.put("status", "0");
        } catch (JSONException e){
            e.printStackTrace();
            toSend.put("status", "1");
        }
        return toSend.toString();
    }

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

    @RequestMapping(value = "/approveComplaint", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String approveComplaint(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer cid = Integer.valueOf((Integer) jsonObj.get("cid"));

        Boolean success = cr.existsById(cid);
        JSONObject toSend = new JSONObject();
        if (success) {
            Optional<Complaint> test = cr.findById(cid);
            Complaint c = test.get();
            c.setStatus(2);
            cr.save(c);
            toSend.put("status", "0");
            int tid = c.getTask_id();
            success = tr.existsById(tid);
            if (success){
                Task t = tr.findById(tid).get();
                int uid = t.is_assigned_to();
                success = ur.existsById(uid);
                if (success){
                    User u = ur.findById(uid).get();
                    u.addToScore(-1); //punish user who completed the task being complained
                    ur.save(u);
                }
            }
        }else{
            toSend.put("status", "1");

        }

        return toSend.toString();
    }

    @RequestMapping(value = "/rejectComplaint", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String rejectComplaint(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer cid = Integer.valueOf((Integer) jsonObj.get("cid"));
        JSONObject toSend = new JSONObject();
        Boolean success = cr.existsById(cid);
        if (success) {
            Complaint c = cr.findById(cid).get();
            c.setStatus(3);
            cr.save(c);
            toSend.put("status", "0");
        }else{
            toSend.put("status", "1");
        }
        return toSend.toString();
    }

    @RequestMapping(value = "/processingComplaint", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String processingComplaint(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer cid = Integer.valueOf((Integer) jsonObj.get("cid"));
        Integer uid = Integer.valueOf((Integer) jsonObj.get("uid"));
        JSONObject toSend = new JSONObject();
        Boolean success = cr.existsById(cid) && ur.existsById(uid);
        if (success) {
            Complaint c = cr.findById(cid).get();
            c.setStatus(1);
            c.setAdmin_id(uid);
            cr.save(c);
            toSend.put("status", "0");
        }else{
            toSend.put("status", "1");
        }
        return toSend.toString();
    }

    @RequestMapping(value = "/retrieveComplaint", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String retrieveComplaint(HttpServletRequest request) throws JSONException, IOException {
        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        Integer cid = Integer.valueOf((Integer) jsonObj.get("cid"));
        Boolean success = cr.existsById(cid);
        JSONObject toSend = new JSONObject();
        if (success) {
            Complaint c = cr.findById(cid).get();
            toSend.put("status", "0");
            toSend.put("cid", cid);
            toSend.put("ctitle", c.getTitle());
            toSend.put("cstatus", c.getStatus());
            toSend.put("cfiler", c.getFiler_id());
            toSend.put("tid", c.getTask_id());
            toSend.put("desc", c.getDescription());
            if (c.getStatus() != 0){
                toSend.put("adminid", c.getAdmin_id());
            }
        }else {
            toSend.put("status", "1");
        }

        return toSend.toString();
    }

}

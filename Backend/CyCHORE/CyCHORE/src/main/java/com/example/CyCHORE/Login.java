package com.example.CyCHORE;
import com.example.CyCHORE.User.*;

import com.example.CyCHORE.Task.Task;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;



@RestController
@RequestMapping("/home")
public class Login{

    @Autowired
    MyDatabase db;

    @GetMapping
    public String getHome() throws IOException {
        System.out.println("user logged in");
        return "Blobfish!!!!";
    }

    @RequestMapping(value = "/login", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String validateLogin(HttpServletRequest request) throws JSONException, IOException {

        String data = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObj = new JSONObject(data);
        String email = (String) jsonObj.get("email");
        String password = (String) jsonObj.get("password");

        int userID = -1;
        List<Person> people = db.findAll();
        int isValid = 0;
        for(int i =0;i<people.size();i++){
            Person p = people.get(i);
            if(p.getEmail().equals(email)){
                if(p.getPassword().equals(password)){
                    isValid=1;
                    userID = p.getId();
                }
            }
        }

        JSONObject o = new JSONObject();
        o.put("status",isValid);
        o.put("uid",userID);
        o.put("tier", 1);
        o.put("groupid", 101);

        return o.toString();
    }

    @RequestMapping(value = "/CompletedDate", method = POST)
    @ResponseBody
    public String CheckCompletedDate(HttpServletRequest request) throws JSONException {
        JSONObject o = new JSONObject();
        Task t = new Task();
        Person p = new Person();

        o.put("completed_date", t.getDdl());
        //t.getDdl();

        return o.toString();


    }




    @RequestMapping(value = "/singletask", method = POST)
    @ResponseBody
    public String SingleTask(HttpServletRequest request) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("title", "mop floor");
        o.put("tid",10001);
        o.put("ddl","1569623441258");
        o.put("complete",1);
        return o.toString();
    }

    @RequestMapping(value = "/UserTaskList", method = POST)
    @ResponseBody
    public String UserTaskList(HttpServletRequest request) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("title", "mop floor");
        o.put("tid",10001);
        o.put("ddl","1569623441258");
        o.put("complete",1);

//        JSONArray a = new JSONArray();
//        a.put(o);
        JSONObject o1 = new JSONObject();
        o1.put("status",0);
        o1.put("todo_count",2);
        o1.put("todo_list",o);
        o1.put("finish_count",1);
        o1.put("finish_list", o);
        return o1.toString();
    }
    @PostMapping
    public String testPost(HttpServletRequest request) throws IOException {
        System.out.println("Helooooooooooooooooo");
        System.out.println("test"+request.getParameter("name") );

        String secretKey = "boooooooooom!!!!";
        String salt = "ssshhhhhhhhhhh!!!!";

        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec ssecretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, ssecretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(request.getParameter("name").getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return "Hello World!";
    }
}



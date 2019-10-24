package com.example.demo01.data;


import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Bundle;
import android.view.View;

import com.example.demo01.MainActivity;
import com.example.demo01.R;
import com.example.demo01.UsrDefaultPage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskCollection  extends MainActivity{
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();

    public static final Map<Integer, TaskItem> ITEM_MAP = new HashMap<Integer, TaskItem>();

    static {
        // Add some sample items.
        // addItem(new TaskItem(10001,"buy apple",1569623441258L,1));
        // addItem(new TaskItem(10002,"kill bug",1569623441258L,1));
    }

    public TaskCollection() {

    }

    public static void addItem(TaskItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.tid, item);
    }

    public static void delItem(TaskItem item) {

        ITEMS.remove(item);
        ITEM_MAP.remove(item.tid, item);
    }

    public static class TaskItem extends ListItem {
        public int tid;
        public int tstatus = 1;
        public Time ddl = new Time(System.currentTimeMillis());
        public String dueTime = "";

        public TaskItem(int givenTid, String name, long givenTime, int status) {
            super("task", name);
            tid = givenTid;
            ddl.setTime(givenTime);
            tstatus = status;

            long time_delta = givenTime - System.currentTimeMillis();
            if (time_delta < 0) {
                dueTime = "Overdue!";
            } else if (time_delta / (1000 * 60 * 60 * 24) > 7) {
                dueTime = "More than a week";
            } else if (time_delta / (1000 * 60 * 60 * 24) > 1) {
                dueTime = time_delta / (1000 * 60 * 60 * 24) + "days";
            } else if (time_delta / (1000 * 60 * 60) > 0) {
                dueTime = time_delta / (1000 * 60 * 60) + "hours";
            }else if(time_delta/ (1000 * 60)>30){
                dueTime = "More than 30 min";
            }else if(time_delta/ (1000 * 60)>0){
                dueTime = time_delta/ (1000 * 60)+"minutes";
            }else{
                dueTime = "less than a minutes";
            }

        }

        @Override
        public String toString() {
            return title + ": " + detail;
        }

        public String toJSON() {
            JSONObject task = new JSONObject();
            try {
                task.put("title", detail);
                task.put("tid", tid);
                task.put("ddl", ddl.getTime());
                task.put("status", tstatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return task.toString();
        }
    }


  /*  public class TaskItem extends ListItem
    {
        public int tid ;
        public int tstatus = 1;
        public Time ddl = new Time(System.currentTimeMillis());
        private static final String task_url = "https://us-central1-task-demo-309.cloudfunctions.net/log_0";
        private Spinner usr_type;



        public TaskItem(int givenTid, String description, long givenTime, int status) {
            super("task",description);
            tid = givenTid;
            ddl.setTime(givenTime);
            tstatus = status;
        }
 /*       public void taskStatus(TaskItem item)
        {
            if(!(task_status == tstatus))
            {
                tstatus = task_status;
            }
        }*/
      /*  public int changeStatus()
        {
            if(tstatus == 0)
            {
                return 0;
            }

            else
            {
                return 1;
            }
        }*/

    /*    @Override
        public String toString()
        {
            return title + ": " + detail;
        }

        public void register(View view)
        {
            //ASK. Do I need to get Title, tid and ddl?
            String title = reg_email.getText().toString();
            String pwd = password.getText().toString();
            String pwd2 = repassword.getText().toString();
            usr_type = (Spinner) findViewById(R.id.usr_type_spinner);
            int usr_tier = 1;
            if (usr_type.getSelectedItem().toString() == "Tenant") {
                usr_tier = 1;
            } else {
                usr_tier = 2;
            }

            toJSON(title, tid, ddl, tstatus);
        }

            public String toJSON()
        {
            JSONObject task = new JSONObject();
            String json;
            try
            {
                task.put("request", "task_update");
               task.put("uid", "uid");
                task.put("tid", tid);
                task.put("ddl", ddl.getTime());
                task.put("changeto", tstatus);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return "";
        }


    }*/
}

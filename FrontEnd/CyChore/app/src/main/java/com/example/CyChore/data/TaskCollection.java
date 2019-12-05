package com.example.CyChore.data;


import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.CyChore.MainActivity;

public class TaskCollection {
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
            } else if (time_delta / (1000 * 60) > 30) {
                dueTime = "More than 30 min";
            } else if (time_delta / (1000 * 60) > 0) {
                dueTime = time_delta / (1000 * 60) + "minutes";
            } else {
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
}


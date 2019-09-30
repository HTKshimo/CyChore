package com.example.demo01.data;

import android.util.Log;

import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskCollection {
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();

    public static final Map<Integer, TaskItem> ITEM_MAP = new HashMap<Integer, TaskItem>();

    static {
        // Add some sample items.
        addItem(new TaskItem(10001,"buy apple",1569623441258L,1));
        addItem(new TaskItem(10002,"kill bug",1569623441258L,1));
        Log.d("static add", ITEMS.toString());
    }

    public static void addItem(TaskItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.tid, item);
    }

    public static void delItem(int i) {

        TaskItem toBeRemove = ITEMS.remove(i);
        ITEM_MAP.remove(toBeRemove.tid, toBeRemove);
    }

    public static class TaskItem extends ListItem {
        public int tid ;
        public int tstatus = 1;
        public Time ddl = new Time(System.currentTimeMillis());

        public TaskItem(int givenTid, String description, long givenTime, int status) {
            super("task",description);
            tid = givenTid;
            ddl.setTime(givenTime);
            tstatus = status;
        }

        @Override
        public String toString() {
            return title + ": " + detail;
        }

        public String toJSON(){
            JSONObject task = new JSONObject();
            String json;
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

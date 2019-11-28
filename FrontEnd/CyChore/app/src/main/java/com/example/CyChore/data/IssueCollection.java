package com.example.CyChore.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueCollection {
    public static final List<IssueItem> ITEMS = new ArrayList<IssueItem>();

    public static final Map<Integer, IssueItem> ITEM_MAP = new HashMap<Integer, IssueItem>();

    static {
        // Add some sample items.
        // addItem(new TaskItem(10001,"buy apple",1569623441258L,1));
        // addItem(new TaskItem(10002,"kill bug",1569623441258L,1));
    }

    public IssueCollection() {

    }

    public static void addItem(IssueItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.tid, item);
    }

    public static void delItem(IssueItem item) {

        ITEMS.remove(item);
        ITEM_MAP.remove(item.tid, item);
    }

    public static void clear(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static class IssueItem extends ListItem {
        public int tid;
        public int fid;
        public String complain = "";

        public IssueItem(int givenTid, String name, String complainContent, int filerid)
        {
            super("issue", name);
            tid = givenTid;
            complain = complainContent;
            fid = filerid;
        }


    }
}


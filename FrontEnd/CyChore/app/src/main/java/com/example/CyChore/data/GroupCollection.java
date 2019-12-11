package com.example.CyChore.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupCollection {
    public static final List<GroupItem> ITEMS = new ArrayList<GroupItem>();

    public static final Map<Integer, GroupItem> ITEM_MAP = new HashMap<Integer, GroupItem>();

    static {
        clear();
        ArrayList<String> gon = new ArrayList<String>();
        gon.add("SamBridges");
        gon.add("JackBovril");
        addItem(new GroupItem(23, "428 Stonehaven Dr, Ames, IA 50010", gon,42.017713,-93.617834));

        ArrayList<String> gtw = new ArrayList<String>();
        gtw.add("AdamSavage");
        gtw.add("JoneSmith");
        addItem(new GroupItem(90, "2329 Storm St, Ames, IA 50014", gtw,42.016278,-93.647852));

        ArrayList<String> gth = new ArrayList<String>();
        gth.add("PennyK");
        gth.add("Zumi");
        addItem(new GroupItem(52, "4717 Twain St, Ames, IA 50014", gth,42.016433, -93.683055));

    }

    public GroupCollection() {

    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(GroupItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.gid, item);
        Log.d("add group item", item.toString());
        Log.d("add group result", ITEMS.toString());
    }


    public String toString() {
        String result = "";
        for (GroupItem i : ITEMS) {
            result = result + i.toString() + " ";
        }
        return result;
    }


    public static class GroupItem extends ListItem {
        public String address;
        public ArrayList<String> groupMembers;
        public int gid;
        public double la;
        public double ln;

        public GroupItem(int givenGid, String addr, ArrayList<String> groupmem, double lat, double lng) {
            super("group", "Group#" + givenGid);
            address = addr;
            groupMembers = new ArrayList<String>(groupmem);
            gid = givenGid;
            la = lat;
            ln = lng;

        }

        public String toString() {
            return this.detail + groupMembers.toString();
        }
    }


}

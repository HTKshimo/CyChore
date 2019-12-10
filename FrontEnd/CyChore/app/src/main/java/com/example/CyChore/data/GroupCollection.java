package com.example.CyChore.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupCollection {
    public static final List<GroupItem> ITEMS = new ArrayList<GroupItem>();

    public static final Map<Integer, GroupItem> ITEM_MAP = new HashMap<Integer, GroupItem>();

    static {
        // Add some sample items.
        ArrayList<String> g1 = new ArrayList<String>();
        g1.add("AdamSavage");
        g1.add("JoneSmith");
        addItem(new GroupItem(90, "2329 Storm St, Ames, IA", g1));

        ArrayList<String> g2 = new ArrayList<String>();
        g1.add("PennyK");
        g1.add("Zumi");
        addItem(new GroupItem(52, "1052 Pine ave, Ames", g2));

    }

    public static void clear(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(GroupItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.gid, item);
    }


    public static class GroupItem extends ListItem {
        public static String address;
        public static ArrayList<String> groupMembers;
        public static int gid;
        public GroupItem(int givenGid, String addr, ArrayList<String> groupmem) {
            super("group", "Group#"+gid);
            address = addr;
            groupMembers = groupmem;
            gid = givenGid;

        }
    }


}

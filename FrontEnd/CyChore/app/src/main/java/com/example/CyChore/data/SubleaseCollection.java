package com.example.CyChore.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubleaseCollection {

    public static final List<subleaseItem> ITEMS = new ArrayList<subleaseItem>();

    public static final Map<Integer, subleaseItem> ITEM_MAP = new HashMap<Integer, subleaseItem>();

    static {
        // Add some sample items.
    }

    public SubleaseCollection()
    {

    }

    public static void addItem(subleaseItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.lid, item);
    }

    public static void delItem(subleaseItem item) {

        ITEMS.remove(item);
        ITEM_MAP.remove(item.lid, item);
    }

    public static void clear()
    {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static class subleaseItem extends ListItem
    {
        int lid;
        public subleaseItem(String name,String addr,String description,int lid){
            super("sublease",name);
            //TODO
        }
    }
}

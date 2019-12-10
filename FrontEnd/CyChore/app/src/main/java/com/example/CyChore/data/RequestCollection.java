package com.example.CyChore.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestCollection {
    public static final List<RequestItem> ITEMS = new ArrayList<RequestCollection.RequestItem>();

    public static final Map<Integer, RequestItem> ITEM_MAP = new HashMap<Integer, RequestCollection.RequestItem>();

    static {
        // Add some sample items.
        // addItem(new TaskItem(10001,"buy apple",1569623441258L,1));
        // addItem(new TaskItem(10002,"kill bug",1569623441258L,1));
    }

    public RequestCollection(){}


    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    private static class RequestItem extends ListItem {
        public RequestItem(String givenTitle, String description) {
            super(givenTitle, description);
        }
    }
}

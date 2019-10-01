package com.example.demo01.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileCollection {

    public static final List<ProfileSelection> ITEMS = new ArrayList<ProfileSelection>();

    public static final Map<String, ProfileSelection> ITEM_MAP = new HashMap<String, ProfileSelection>();

    // add default items
    static {
        // Add some sample items.
        addItem(new ProfileSelection("My Account", "edit personal info"));
        addItem(new ProfileSelection("My Group", "view group info"));
        addItem(new ProfileSelection("Log Out"));

    }

    private static void addItem(ProfileSelection item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    public static class ProfileSelection extends ListItem {

        public ProfileSelection(String givenTitle, String description) {
            super(givenTitle,description);
        }

        public ProfileSelection(String givenTitle) {
            super(givenTitle,"");
        }

        @Override
        public String toString() {
            return title + ": " + detail;
        }
    }
}

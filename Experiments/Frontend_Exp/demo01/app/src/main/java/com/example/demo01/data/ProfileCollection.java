package com.example.demo01.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileCollection {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<ProfileSelection> ITEMS = new ArrayList<ProfileSelection>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ProfileSelection> ITEM_MAP = new HashMap<String, ProfileSelection>();

    //statically add items to the class
    static {
        // Add some sample items.
        addItem(new ProfileSelection("My Account", "edit personal info",""));
        addItem(new ProfileSelection("My Group", "view group info",""));
        addItem(new ProfileSelection("Log Out"));

    }
    //use this
    //implement delete things
    private static void addItem(ProfileSelection item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    private static ProfileSelection createDummyItem(String title) {
        return new ProfileSelection(title);
    }


    public static class ProfileSelection {
        public final String title;
        public final String detail;
        public final String link; //to link to other activities

        public ProfileSelection(String givenTitle, String description, String link) {
            this.title = givenTitle;
            this.detail = description;
            this.link = link;
        }

        public ProfileSelection(String givenTitle, String link) {
            this.title = givenTitle;
            this.detail = "";
            this.link = link;
        }

        public ProfileSelection(String givenTitle) {
            this.title = givenTitle;
            this.detail = "";
            this.link = "blank";
        }

        @Override
        public String toString() {
            return title + ": " + link;
        }
    }
}

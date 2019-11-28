package com.example.CyChore.data;

public abstract class ListItem {

    public final String title;
    public final String detail;

    public ListItem(String givenTitle, String description) {
        this.title = givenTitle;
        this.detail = description;
    }

}

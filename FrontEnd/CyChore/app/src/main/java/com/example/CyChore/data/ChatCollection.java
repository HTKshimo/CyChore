package com.example.CyChore.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatCollection {

    public static final List<ChatSelection> ITEMS = new ArrayList<ChatSelection>();

    public static final Map<String, ChatSelection> ITEM_MAP = new HashMap<String, ChatSelection>();

    static {
        // Add some sample items.
        ArrayList<String> MrHouseChatLog = new ArrayList<>();

        MrHouseChatLog.add("JohnSmith: I'll take care of it.");
        MrHouseChatLog.add("MrHouse: With this accomplished, all preparations will have been made. ");

        MrHouseChatLog.add("JohnSmith: Before I leave, I have a question. What the hell are you?");
        MrHouseChatLog.add("MrHouse: A crude question, crudely asked.");

        MrHouseChatLog.add("JohnSmith: Goodbye.");
        MrHouseChatLog.add("MrHouse: Well enough. Be on your way.");
        addItem(new ChatSelection("MrHouse",MrHouseChatLog));

        ArrayList<String> echoLog = new ArrayList<>();
        echoLog.add("echo test start");
        addItem(new ChatSelection("Echo",echoLog));
    }
    public ChatCollection()
    {

    }

    public static void addItem(ChatSelection item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    public static void clear()
    {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static ChatSelection get(int pos)
    {
        return ITEMS.get(pos);
    }

    public static class ChatSelection extends ListItem
    {

        public String ChatTitle;
        public ArrayList<String> ChatContent;
        public String lastestLine="";

        public ChatSelection(String givenTitle, List<String> givenChatContent)
        {
            super("chat",givenTitle);

            ChatTitle = givenTitle;
            ChatContent = new ArrayList<>(givenChatContent);
            if(givenChatContent.size()!=0) {
                lastestLine = givenChatContent.get(givenChatContent.size() - 1);
            }
        }

        @Override
        public String toString() {
            return title + ": " + detail;
        }
    }
}

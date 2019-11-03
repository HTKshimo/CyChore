package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ChatRoom extends AppCompatActivity {

    public static ArrayList<String> chatlog;
    public static String chatRoomName;


    public RecyclerView chatContents;
    private chatRoomRecyclerViewAdapter chatContents_adaptor;
    public TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        
        chatContents = findViewById(R.id.ChatContents);
        chatContents.setLayoutManager(new LinearLayoutManager(chatContents.getContext()));
        chatContents_adaptor = new chatRoomRecyclerViewAdapter(chatlog);
        chatContents.setAdapter(chatContents_adaptor);

        title = findViewById(R.id.ChatRoomTitle);
        title.setText(chatRoomName);

        getSupportActionBar().hide();
        
        
        
        
        
    }

    
}

package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.CyChore.data.ChatCollection;
import com.example.CyChore.ui.chatTab.ChatTabFragment;

import java.util.ArrayList;

import static com.example.CyChore.UsrDefaultPage.usrName;

public class IssueDetail extends AppCompatActivity {

    public static int tid;
    public static int uid;
    public static String usrName;
    public static String name;
    public static String Complain;
    public static int fid;
    private TextView issue_name;
    private TextView complain_detail;
    private Button contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        issue_name = findViewById(R.id.IssueName);
        issue_name.setText(name);

        complain_detail = findViewById(R.id.IssueComplainDetail);
        complain_detail.setText(Complain);

        contact = findViewById(R.id.IssueContactResponsible);

        getSupportActionBar().hide();
    }

    public void chatResponsible(View view){
        ChatTabFragment.chatItems.addItem(new ChatCollection.ChatSelection("Issue of "+name,new ArrayList<String>()));
        ChatTabFragment.chatlist_adaptor.notifyDataSetChanged();
        Intent intent = new Intent(this, ChatRoom.class);

        ChatRoom.chatRoomName = "Issue of "+name;

        ChatRoom.chatPosition = ChatTabFragment.chatItems.ITEMS.size()-1;
        ChatRoom.chatlog = ChatTabFragment.chatItems.get(ChatRoom.chatPosition).ChatContent;
        ChatRoom.uname = usrName;
        startActivity(intent);
    }
}

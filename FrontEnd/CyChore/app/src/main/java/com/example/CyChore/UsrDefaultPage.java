package com.example.CyChore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.CyChore.data.ChatCollection;
import com.example.CyChore.data.IssueCollection;
import com.example.CyChore.data.ListItem;
import com.example.CyChore.data.TaskCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;
import com.example.CyChore.ui.chatTab.ChatTabFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import okhttp3.MediaType;


public class UsrDefaultPage extends AppCompatActivity implements OnListFragmentInteractionListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static int uid;
    public static int groupid;
    public static String usrName;
    public static final String url_head = "https://us-central1-login-demo-309.cloudfunctions.net/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_default_page);
        BottomNavigationView navView = findViewById(R.id.nav_view_admin);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_group, R.id.navigation_chats, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportActionBar().hide();
        ChatTabFragment.uid = uid;

        Log.d("pass_uid:", uid + "");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    public void logout() {
        getSharedPreferences("accountInfo", Context.MODE_PRIVATE).edit().putBoolean("auto_login", false).commit();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void jumpJoinGroup(View view) {
        Intent intent = new Intent(this, JoinGroup.class);
        startActivity(intent);
    }

    public void jumpAddTask(View view) {
        Intent intent = new Intent(this, AddTaskPage.class);
        startActivity(intent);
    }

    public void jumpProfileEdit() {
        Intent intent = new Intent(this, ProfileEditPage.class);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(ListItem item, int listType) {
        if (item.title.equals("Log Out")) {
            logout();
        } else if (item.title.equals("My Account")) {
            jumpProfileEdit();
        } else if (item.title.equals("My Complains")) {
            jumpMyComplains();
        } else if (item.title.equals("My Group")) {
            Intent intent = new Intent(this, GroupDetail.class);

            GroupDetail.gid = groupid;
            ArrayList<String> mem = new ArrayList<>();
            mem.add("AdamSavage");
            mem.add("JoneSmith");
            GroupDetail.members = mem;
            GroupDetail.address = "2329 Storm St, Ames, IA 50014";

            startActivity(intent);
        } else if (item.title.equals("chat")) {

            ChatCollection.ChatSelection chat = (ChatCollection.ChatSelection) item;
            Intent intent = new Intent(this, ChatRoom.class);

            ChatRoom.chatRoomName = ((ChatCollection.ChatSelection) item).ChatTitle;
            ChatRoom.chatlog = ((ChatCollection.ChatSelection) item).ChatContent;
            ChatRoom.chatPosition = listType;
            ChatRoom.uname = usrName;

            startActivity(intent);
        } else if (item.title.equals("task")) {
            TaskCollection.TaskItem task = (TaskCollection.TaskItem) item;
            Intent intent = new Intent(this, TaskDetail.class);


            TaskDetail.tid = task.tid;
            TaskDetail.uid = uid;
            TaskDetail.tstatus = task.tstatus;
            TaskDetail.ddl = task.ddl.getTime();
            TaskDetail.name = task.detail;
            TaskDetail.detail = "N/A";
            TaskDetail.listType = listType;


            startActivity(intent);
        } else if (item.title.equals("sublease")) {

        } else if (item.title.equals("issue")) {
            IssueCollection.IssueItem issue = (IssueCollection.IssueItem) item;
            Intent intent = new Intent(this, IssueDetail.class);


            IssueDetail.tid = issue.tid;
            IssueDetail.uid = uid;
            IssueDetail.Complain = issue.complain;
            IssueDetail.fid = issue.fid;


            startActivity(intent);
        }

    }

    private void jumpMyComplains() {
        Intent intent = new Intent(this, MyComplainsPage.class);
        startActivity(intent);
    }
}
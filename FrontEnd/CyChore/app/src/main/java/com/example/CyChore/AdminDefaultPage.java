package com.example.CyChore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AdminDefaultPage extends AppCompatActivity implements OnListFragmentInteractionListener {

    public static String usrName;
    public static int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_default_page);
        BottomNavigationView navView = findViewById(R.id.nav_view_admin);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_issues, R.id.navigation_chats, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        ChatTabFragment.uid = uid;
        ChatTabFragment.uname = usrName;
        getSupportActionBar().hide();
    }

    @Override
    public void onListFragmentInteraction(ListItem item, int listType) {
        if (item.title.equals("Log Out")) {
            logout();
        }else if (item.title.equals("chat")) {

            ChatCollection.ChatSelection chat = (ChatCollection.ChatSelection) item;
            Intent intent = new Intent(this, ChatRoom.class);

            ChatRoom.chatRoomName = ((ChatCollection.ChatSelection) item).ChatTitle;
            ChatRoom.chatlog = ((ChatCollection.ChatSelection) item).ChatContent;
            ChatRoom.chatPosition = listType;
            ChatRoom.uname = usrName;

            startActivity(intent);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    public void logout() {
        getSharedPreferences("accountInfo", Context.MODE_PRIVATE).edit().putBoolean("auto_login", false).commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void jumpProfileEdit() {
        Intent intent = new Intent(this, ProfileEditPage.class);
        startActivity(intent);
    }
}

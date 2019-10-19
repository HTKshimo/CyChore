package com.example.demo01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.demo01.data.ListItem;
import com.example.demo01.data.ProfileCollection;
import com.example.demo01.data.TaskCollection;
import com.example.demo01.ui.OnListFragmentInteractionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import okhttp3.MediaType;


public class UsrDefaultPage extends AppCompatActivity implements OnListFragmentInteractionListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static int uid;
    public static int groupid;
    public static final String url_head = "https://us-central1-login-demo-309.cloudfunctions.net/";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_default_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_group, R.id.navigation_chats)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportActionBar().hide();

        Intent intent = getIntent();

        Log.d("pass_uid:",uid+"");
    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }


    public void logout(){
        getSharedPreferences("accountInfo", Context.MODE_PRIVATE).edit().putBoolean("auto_login", false).commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void jumpJoinGroup(View view){
        Intent intent = new Intent(this, JoinGroup.class);
        startActivity(intent);
    }

    public void jumpAddTask(View view){
        Intent intent = new Intent(this, AddTaskPage.class);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(ListItem item) {
        if(item.title.equals("Log Out")){
            logout();
        }else if(item.title.equals("task")){
            TaskCollection.TaskItem task = (TaskCollection.TaskItem) item;
            Intent intent = new Intent(this, TaskDetail.class);


            TaskDetail.tid = task.tid;
            TaskDetail.uid = uid;
            TaskDetail.tstatus = task.tstatus;
            TaskDetail.ddl = task.ddl.getTime();
            TaskDetail.name = task.detail;
            TaskDetail.detail = "N/A";


            startActivity(intent);
        }

    }
}

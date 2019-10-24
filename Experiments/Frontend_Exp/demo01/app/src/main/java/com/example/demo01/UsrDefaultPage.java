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
import com.example.demo01.ui.tasksTab.TasksList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UsrDefaultPage extends AppCompatActivity implements OnListFragmentInteractionListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static int uid;
    public static int groupid;
    public static final String url_head = "https://us-central1-login-demo-309.cloudfunctions.net/";
    //private Button completed;
    //private Button completed3;
    public int task_status;
    TextView textView;
    private Spinner usr_type;
    private Boolean inGroup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inGroup = getSharedPreferences("account info", Context.MODE_PRIVATE).getBoolean("inGroup", false);
        setContentView(R.layout.activity_usr_default_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        if(inGroup)
        {
            //show dashboard
        }
        else
        {
           //show fragment with "Join Group" Button
        }
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

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tasks);*/

   /* @Yamini
   * */
    public void finish_button(View view)
    {
        Button btn1 = (Button) findViewById(R.id.Finish);
        TextView statusText = findViewById(R.id.Finish);
        view.getId();
        Log.d("ID: ", "" + view.getId());
      // statusText = R.id.view.statusText();
     /*   btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {*/

                if(statusText.getText().toString().toLowerCase().equals("incomplete!"))
                {
                    Log.i("enter if" , statusText.getText().toString());
                    statusText.setText("complete!");
                    task_status = 0;
                }
                else if(statusText.getText().toString().toLowerCase().equals("complete!"))
                {
                    Log.i("enter if" , statusText.getText().toString());
                    statusText.setText("incomplete!");
                    task_status = 1;
                }
          //  }
       // });
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
    public void onListFragmentInteraction(ListItem item, int listType) {
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
            TaskDetail.listType = listType;


            startActivity(intent);
        }

    }

    public void test(View view)
    {

    }

  /*  json = task.toString();

            new Thread(new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            //Sending and receiving network calls
            RequestBody body = RequestBody.create(json, JSON); //ASK. Have to make this URL work.
            Request request = new Request.Builder().url(task_url)
                    .post(body)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String reply = response.body().string();
                Log.d("Task respond", reply);
                try {
                    JSONObject respond_json = new JSONObject(reply);
                    if (respond_json.getString("status").equals("0")) {
                        // dialog_handler.sendEmptyMessage(0);
                        tstatus = 0;
                    }
                    else
                    {
                        // dialog_handler.sendEmptyMessage(1);
                        tstatus = 1;
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }).start();*/
}

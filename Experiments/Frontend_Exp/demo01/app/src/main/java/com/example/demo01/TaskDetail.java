package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;


public class TaskDetail extends AppCompatActivity {

    public static int tid;
    public static int tstatus;
    public static String name;
    public static long ddl;
    public static String detail= "not na";
    public static int uid;

    private TextView TaskName;
    private TextView TaskDescription;
    private TextView TaskDdl;

    private Button Pool;
    private Button Pickup;
    private Button Complete;
    private Button Complain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().hide();

        TaskName = findViewById(R.id.TaskName);
        TaskDescription = findViewById(R.id.TaskDescription);
        TaskDdl = findViewById(R.id.TaskDdl);

        Pool = findViewById(R.id.TaskDetailPool);
        Pickup = findViewById(R.id.TaskDetailPickup);
        Complete = findViewById(R.id.TaskDetailComplete);
        Complain = findViewById(R.id.TaskDetailComplain);

        if(tstatus==0){
            Pool.setVisibility(View.GONE);
            Pickup.setVisibility(View.GONE);
            Complete.setVisibility(View.GONE);
        }else if(tstatus==1){
            Complain.setVisibility(View.GONE);
            // TODO: check uid to decide if the usr can pool or pick up
        }

        TaskName.setText(name);
        TaskDescription.setText(detail);
        TaskDdl.setText("Deadline: "+ new Date(ddl).toString());




    }
}

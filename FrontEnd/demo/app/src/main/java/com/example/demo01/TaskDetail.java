package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo01.ui.groupTab.GroupTabFragment;
import com.example.demo01.ui.tasksTab.TasksList;

import java.sql.Time;
import java.util.Date;

import static com.example.demo01.util.TaskStatusUpadateUtil.UpdateTaskStatus;


public class TaskDetail extends AppCompatActivity {

    public static int tid;
    public static int tstatus;
    public static String name;
    public static long ddl;
    public static String detail= "not na";
    public static int uid;
    public static int listType;

    private TextView TaskName;
    private TextView TaskDescription;
    private TextView TaskDdl;

    private Button Pool;
    private Button Pickup;
    private Button Complete;
    private Button Complain;
    private EditText CompainText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().hide();

        TaskName = findViewById(R.id.TaskName);
        TaskDescription = findViewById(R.id.TaskDescription);
        TaskDdl = findViewById(R.id.TaskDdl);

        Pool = findViewById(R.id.TaskDetailPool);
        Pool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int responseCode;
                responseCode = UpdateTaskStatus(tid, uid, 2);
                if (responseCode == 0) {
                    Toast.makeText(v.getContext(), "Task get pooled!", Toast.LENGTH_SHORT).show();
                    TasksList.retriveUsrTasks();
                    GroupTabFragment.retriveHistoryTasks();
                    GroupTabFragment.retrivePoolTasks();
                } else {
                    Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Pickup = findViewById(R.id.TaskDetailPickup);
        Pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int responseCode;
                responseCode = UpdateTaskStatus(tid, uid, 4);
                if (responseCode == 0) {
                    Toast.makeText(v.getContext(), "Pick up successfully!", Toast.LENGTH_SHORT).show();
                    TasksList.retriveUsrTasks();
                    GroupTabFragment.retriveHistoryTasks();
                    GroupTabFragment.retrivePoolTasks();
                } else {
                    Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Complete = findViewById(R.id.TaskDetailComplete);
        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int responseCode;
                responseCode = UpdateTaskStatus(tid, uid, 0);
                if (responseCode == 0) {
                    Toast.makeText(v.getContext(), "Complete status logged!", Toast.LENGTH_SHORT).show();
                    TasksList.retriveUsrTasks();
                    GroupTabFragment.retriveHistoryTasks();
                    GroupTabFragment.retrivePoolTasks();
                } else {
                    Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Complain = findViewById(R.id.TaskDetailComplain);
        
        CompainText = findViewById(R.id.CompainInput);
        Complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int responseCode;
                responseCode = UpdateTaskStatus(tid, uid, 3, CompainText.getText().toString());
                if (responseCode == 0) {
                    Toast.makeText(v.getContext(), "Complete status logged!", Toast.LENGTH_SHORT).show();
                    TasksList.retriveUsrTasks();
                    GroupTabFragment.retriveHistoryTasks();
                    GroupTabFragment.retrivePoolTasks();
                } else {
                    Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(listType==0){
            Pickup.setVisibility(View.GONE);
            Complain.setVisibility(View.GONE);
            CompainText.setVisibility(View.GONE);
        }else if(listType==1){
            Complete.setVisibility(View.GONE);
            Pool.setVisibility(View.GONE);
            Complain.setVisibility(View.GONE);
            CompainText.setVisibility(View.GONE);
        }else if(listType==2){
            Complete.setVisibility(View.GONE);
            Pool.setVisibility(View.GONE);
            Pickup.setVisibility(View.GONE);
        }

        TaskName.setText(name);
        TaskDescription.setText(detail);
        TaskDdl.setText("Deadline: "+ new Date(ddl).toString());




    }
}

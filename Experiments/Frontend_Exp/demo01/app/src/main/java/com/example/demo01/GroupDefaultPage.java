package com.example.demo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class GroupDefaultPage extends AppCompatActivity
{
    Button createTask;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTask=(Button)findViewById(R.id.add_task);
        createTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent i = new Intent(getApplicationContext(),SendPhotos.class);
               // startActivity(i);
                if(v.getId() == R.id.add_task)
                {
                   Intent i = new Intent(GroupDefaultPage.this, AddTaskPage.class);
                   startActivity(i);
                }
            }
        });

    }

}

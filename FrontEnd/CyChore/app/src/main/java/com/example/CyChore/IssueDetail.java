package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IssueDetail extends AppCompatActivity {

    public static int tid;
    public static int uid;
    public static int tstatus;
    public static long ddl;
    public static String name;
    public static String Complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);




        getActionBar().hide();
    }
}

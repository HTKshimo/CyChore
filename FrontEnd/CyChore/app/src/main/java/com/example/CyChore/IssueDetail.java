package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class IssueDetail extends AppCompatActivity {

    public static int tid;
    public static int uid;
    public static String name;
    public static String Complain;
    public static int fid;
    private TextView issue_name;
    private TextView complain_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        issue_name = findViewById(R.id.IssueName);
        issue_name.setText(name);

        complain_detail = findViewById(R.id.IssueComplainDetail);
        complain_detail.setText(Complain);


        getSupportActionBar().hide();
    }
}

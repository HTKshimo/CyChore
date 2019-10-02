package com.example.buttontext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Wire up the button do the stuff
        //get the button
        Button btn = findViewById(R.id.btn_id);
        //set what happens when the user clicks
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView statusText = findViewById(R.id.btn_id);
                if(statusText.getText().toString().equals("INCOMPLETE!"))
                {
                    Log.i("enter if" , statusText.getText().toString());
                    statusText.setText("COMPLETE!");
                }
                else
                {
                    statusText.setText("INCOMPLETE!");
                }
            }
        });
    }

}

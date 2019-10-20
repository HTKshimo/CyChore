package com.example.demo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.support.v4.app.*;

//import com.android.build.OutputFile;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.*;
//annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;


    public class CalendarActivity extends AddTaskPage
    {

        private  static final String TAG = "CalendarActivity";
        private CalendarView mCalendarView;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.calendar_layout);
            mCalendarView = findViewById(R.id.calendarView);
            mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
            {
                @Override
                public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth)
                {
                    String date = year + "/" + month + "/"+ dayOfMonth ;
                    Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
                    Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
        }
    }


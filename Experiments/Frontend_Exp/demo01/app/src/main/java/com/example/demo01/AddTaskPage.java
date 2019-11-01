package com.example.demo01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.widget.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CalendarView;


import com.example.demo01.data.TaskCollection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddTaskPage extends AppCompatActivity
{
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final String task_url = "https://us-central1-login-demo-309.cloudfunctions.net/log_0";

    private TextView Task;
    private TextView task_description;
    private Button submit_task;
    private Handler dialog_handler;


    private Spinner spinner_year;
    private Spinner spinner_month;
    private Spinner spinner_day;
    private Spinner spinner_hour;
    private Spinner spinner_min ;

    private Calendar ddl_calendar;
    private long ddl;
    private String dueIn = "";



    //Use Callable instead of runnable for HTTP
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);

        getSupportActionBar().hide();

        Task  = findViewById(R.id.Task);
        task_description = findViewById(R.id.task_description);
        submit_task = findViewById(R.id.submit_task);

        ddl_calendar = Calendar.getInstance();

        spinner_year = findViewById(R.id.DdlYear);
        spinner_year.setSelection(0);
        spinner_month = findViewById(R.id.DdlMonth);
        spinner_month.setSelection(10);
        spinner_day = findViewById(R.id.DdlDay);
        spinner_day.setSelection(15);
        spinner_hour = findViewById(R.id.DdlHour);
        spinner_hour.setSelection(20);
        spinner_min = findViewById(R.id.DdlMin);
        spinner_min.setSelection(0);




        dialog_handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                Log.d("handle","start");
                switch (msg.what){
                    case 0:
                        showConfirmDialog();
                        submit_task.setText("Submitted!");

                        break;
                    case 1:
                        showAddfailDialog(1);
                    default:break;
                }
            };
        };

    }


    public void setTask_deadline(View view)
    {
        String task_name = Task.getText().toString();
        String task_detail = task_description.getText().toString();

        String task_check = "[A-Za-z]{2,14}";
        Pattern regex = Pattern.compile(task_check);

        if (TextUtils.isEmpty(task_name)) {
            Toast.makeText(view.getContext(), R.string.task_name_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!regex.matcher(task_name).matches()) {
            Toast.makeText(view.getContext(), R.string.invalid_task_name, Toast.LENGTH_SHORT).show();
            return;
        }






        int year = 2019+spinner_year.getSelectedItemPosition();
        int month = 1+spinner_month.getSelectedItemPosition();
        int day = 1+spinner_day.getSelectedItemPosition();
        int hour = 1+spinner_hour.getSelectedItemPosition();
        int min = spinner_min.getSelectedItemPosition()*15;


        ddl_calendar.set(year,month,day,hour,min);
        ddl = ddl_calendar.getTimeInMillis();

        TaskCollection.TaskItem curTask = new TaskCollection.TaskItem(0,task_name,ddl,1);
        dueIn = curTask.dueTime;
















        setSubmitTask(task_name , task_detail);
    }

    private void setSubmitTask(final String task_name,final String task_detail)
    {
        Log.d("Submit func", "start");
        final JSONObject param = new JSONObject();
        try
        {
            param.put("request", "submit task");

            param.put("task", task_name);
            param.put("task_detail", task_detail);
            param.put("ddl", ddl);
        }
        catch (
                JSONException e)
        {
            e.printStackTrace();
        }
        final String reg_json = param.toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(reg_json, JSON);
                Request request = new Request.Builder().url(task_url)
                        .post(body)
                        .build();
                try
                {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();

                    Log.d("Task submission respond", reply);
                    try
                    {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and decide jump or not
                        if (respond_json.getString("status").equals("0"))
                        {
                            dialog_handler.sendEmptyMessage(0);
                        }
                        else
                        {
                            // TODO if fail pop up dialog with fail explained
                            dialog_handler.sendEmptyMessage(1);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void showConfirmDialog()
    {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(AddTaskPage.this);
        normalDialog.setTitle("Task confirmed");
        normalDialog.setMessage("Your task has been confirmed. It will due in "+dueIn);
        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        normalDialog.show();
    }

    private void showAddfailDialog(int fail_code)
    {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(AddTaskPage.this);
        normalDialog.setTitle("Action failed");

        switch (fail_code)
        {
            case 1 :
                normalDialog.setMessage("Something wrong... Try again.");
                break;

        }

        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
        normalDialog.show();
    }


}

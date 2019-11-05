package com.example.demo01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    private Spinner task_deadline;
    private TextView Task;
    private TextView task_description;
    private Button submit_task;
    private Handler dialog_handler;
    private TextView deadline;
    private Button btngocalendar;
    private Date ddl;

    private CalendarView mCalendarView;
    private Button SetDate;

    private boolean calendar_visibility;


    //Use Callable instead of runnable for HTTP
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);

        getSupportActionBar().hide();

        Task  = (TextView) findViewById(R.id.Task);
        task_description = (TextView) findViewById(R.id.task_description);
        submit_task = (Button) findViewById(R.id.submit_task);

        mCalendarView = findViewById(R.id.AddTaskDate);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //TODO
            }
        });

        mCalendarView.setVisibility(View.GONE);

        SetDate = findViewById(R.id.AddTaskSetDate);
        SetDate.setVisibility(View.GONE);

        //deadline = (TextView) findViewById(R.id.date);
        btngocalendar = (Button) findViewById(R.id.btngocalendar);
        btngocalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!calendar_visibility)
                {
                    mCalendarView.setVisibility(View.VISIBLE);
                    calendar_visibility = true;
                    SetDate.setVisibility(View.VISIBLE);

                }
                else
                    {
                    // get date
                    ddl= new Date(mCalendarView.getDate());
                        Log.d("set date", new Date(mCalendarView.getDate()).toString());
                    btngocalendar.setText("Deadline is:" + ddl);

                    mCalendarView.setVisibility(View.GONE);
                    SetDate.setVisibility(View.GONE);
                    calendar_visibility=false;
                }
            }
        });


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
        String task = Task.getText().toString();
        String task_detail = task_description.getText().toString();

        String task_check = "[A-Za-z]{2,14}";
        Pattern regex = Pattern.compile(task_check);


        setSubmitTask(task , task_detail);
    }

    private void setSubmitTask(final String task,final String task_detail)
    {
        Log.d("Submit func", "start");
        final JSONObject param = new JSONObject();
        try
        {
            param.put("request", "submit task");
            //param.put("tier", usr_tier); // user type define
            param.put("task", task);
            param.put("task_detail", task_detail);
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
        normalDialog.setMessage("Your task has been confirmed.");
        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO
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

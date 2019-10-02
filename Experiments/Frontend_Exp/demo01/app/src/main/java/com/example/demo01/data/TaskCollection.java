package com.example.demo01.data;


import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Bundle;
import android.view.View;

import com.example.demo01.MainActivity;
import com.example.demo01.R;
import com.example.demo01.UsrDefaultPage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskCollection  extends MainActivity{
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();

    public static final Map<Integer, TaskItem> ITEM_MAP = new HashMap<Integer, TaskItem>();

    //
    private Button completed;
    private Button completed3;
    public int task_status;
    TextView textView;
    private Spinner usr_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tasks);
        Button btn1 = (Button) findViewById(R.id.complete_button);
        Button btn2 = (Button) findViewById(R.id.complete_button3);
      // Button completed3 = (Button)findViewById(R.id.complete_button3);
      //  textView = (TextView) findViewById(R.id.complete_button);

        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView statusText = findViewById(R.id.complete_button);
                TextView statusText3 = findViewById(R.id.complete_button3);
                if(statusText.getText().toString().equals("INCOMPLETE!"))
                {
                    Log.i("enter if" , statusText.getText().toString());
                    statusText.setText("COMPLETE!");
                    task_status = 0;
                }
                if(statusText.getText().toString().equals("COMPLETE!"))
                {
                    statusText.setText("INCOMPLETE!");
                    task_status = 1;
                }

                //completed button 3
                if(statusText3.getText().toString().equals("INCOMPLETE!"))
                {
                    Log.i("enter if" , statusText3.getText().toString());
                    statusText3.setText("COMPLETE!");
                    task_status = 0;
                }
               /* else
                {
                    Log.i("enter if" , statusText3.getText().toString());
                    statusText3.setText("INCOMPLETE!");
                    task_status = 1;
                }*/
                if(statusText.getText().toString().equals("COMPLETE!"))
                {
                    statusText.setText("INCOMPLETE!");
                    task_status = 1;
                }

            }
        });
    }

    static {
        // Add some sample items.
        // addItem(new TaskItem(10001,"buy apple",1569623441258L,1));
        // addItem(new TaskItem(10002,"kill bug",1569623441258L,1));
    }

    public TaskCollection(){
//        ITEMS.clear();
//        ITEM_MAP.clear();

    }
    public static void addItem(TaskItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.tid, item);
    }

    public static void delItem(TaskItem item) {

        ITEMS.remove(item);
        ITEM_MAP.remove(item.tid, item);
    }



    public class TaskItem extends ListItem
    {
        public int tid ;
        public int tstatus = 1;
        public Time ddl = new Time(System.currentTimeMillis());
        private static final String task_url = "https://us-central1-task-demo-309.cloudfunctions.net/log_0";


        public TaskItem(int givenTid, String description, long givenTime, int status) {
            super("task",description);
            tid = givenTid;
            ddl.setTime(givenTime);
            tstatus = status;
        }
        public void taskStatus(TaskItem item)
        {
            if(!(task_status == tstatus))
            {
                tstatus = task_status;
            }
        }
      /*  public int changeStatus()
        {
            if(tstatus == 0)
            {
                return 0;
            }

            else
            {
                return 1;
            }
        }*/

        @Override
        public String toString()
        {
            return title + ": " + detail;
        }

        public void register(View view) {
            //ASK. Do I need to get Title, tid and ddl?
            String title = reg_email.getText().toString();
            String pwd = password.getText().toString();
            String pwd2 = repassword.getText().toString();

            usr_type = (Spinner) findViewById(R.id.usr_type_spinner);
            int usr_tier = 1;
            if (usr_type.getSelectedItem().toString() == "Tenant") {
                usr_tier = 1;
            } else {
                usr_tier = 2;
            }

            toJSON(title, tid, ddl, tstatus);
        }

            public String toJSON()
        {
            JSONObject task = new JSONObject();
            String json;
            try
            {
                task.put("request", "task_update");
                task.put("uid", uid);
                task.put("tid", tid);
                task.put("ddl", ddl.getTime());
                task.put("changeto", tstatus);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            json = task.toString();

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
            }).start();
            return "";
        }


    }
}

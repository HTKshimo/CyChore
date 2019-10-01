package com.example.demo01.data;


import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

public class TaskCollection extends MainActivity {
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();

    public static final Map<Integer, TaskItem> ITEM_MAP = new HashMap<Integer, TaskItem>();

    //
   private Button completed;
   private Button completed3;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tasks);
        completed = (Button) findViewById(R.id.complete_button);
        completed3 = (Button)findViewById(R.id.complete_button3);
        textView = (TextView) findViewById(R.id.complete_button);

       // completed.setOnClickListener( new View.OnClickListener() {

  /*          @Override
            public void onClick(View v) {
                //textView.setText("INCOMPLETE");
                completed.setText("INCOMPLETE!");
                completed3.setText("INCOMPLETE!");
            }
        });*/

    }
    public void change_status(View v)
    {
        // = (Button) findViewById(R.id.complete_button);
        //set button's new text programmatically
        //setText() method allow us to set a widget's displayed text
        completed.setText("Incomplete!");
        completed3.setText("Incomplete!");

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

    public static class TaskItem extends ListItem {
        public int tid ;
        public int tstatus = 1;
        public Time ddl = new Time(System.currentTimeMillis());
        private Button completed; //changeButtonText

        // Button btn = (Button) findViewById(R.id.button_complete);
       //TextView textView = (TextView) findiewById(R.id.my_textview);

        public TaskItem(int givenTid, String description, long givenTime, int status) {
            super("task",description);
            tid = givenTid;
            ddl.setTime(givenTime);
            tstatus = status;
        }

        //@Override
 /* public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
      View view = inflater.inflate(R.layout.TaskCollection, container, false);
      completed = (Button) view.findViewById(R.id.);
      completed.setOnClickListener(this);
      return view;
      //super.onCreate(savedInstanceState);
  }

  @Override
  public void onClick(View v)
  {
      textView.setText("INCOMPLETE");
  }*/


        public int changeStatus()
        {
            if(tstatus == 0)
            {
                return 0;
            }

            else
            {
                return 1;
            }
        }

        @Override
        public String toString() {
            return title + ": " + detail;
        }

        public String toJSON(){
            JSONObject task = new JSONObject();
            String json;
            try {
                task.put("title", detail);
                task.put("tid", tid);
                task.put("ddl", ddl.getTime());
                task.put("status", tstatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return task.toString();
        }
    }
}

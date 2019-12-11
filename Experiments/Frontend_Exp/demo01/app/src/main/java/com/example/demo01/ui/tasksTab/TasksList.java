package com.example.demo01.ui.tasksTab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo01.R;
import com.example.demo01.data.TaskCollection;
import com.example.demo01.ui.OnListFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.demo01.UsrDefaultPage.JSON;
import static com.example.demo01.UsrDefaultPage.groupid;
import static com.example.demo01.UsrDefaultPage.uid;

public class TasksList extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private RecyclerView todolist;
    private static TaskCollection todoItems;

    private static final String tasklist_url = "https://us-central1-login-demo-309.cloudfunctions.net/uid_0001_tasklist";
//    private static final String tasklist_url = "http://coms-309-ks-2.misc.iastate.edu:8080/getTaskList/5";
    private static tasksRecyclerViewAdapter todolist_adaptor;
    private static JSONArray todoItems_Json = new JSONArray();
    private static Handler listUpdateHandler;
    private Button joinGroupButton;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TasksList newInstance(int columnCount) {
        TasksList fragment = new TasksList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listUpdateHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        todolist_update();
                        break;
                }
            }
        };

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        todolist = view.findViewById(R.id.todolist);
        joinGroupButton = view.findViewById(R.id.joinGroup);

        if(groupid==0){
            todolist.setVisibility(View.GONE);
        }else {
            joinGroupButton.setVisibility(View.GONE);
        }

        todoItems = new TaskCollection();
        

        if (mColumnCount <= 1) {
            todolist.setLayoutManager(new LinearLayoutManager(todolist.getContext()));
        } else {
            todolist.setLayoutManager(new GridLayoutManager(todolist.getContext(), mColumnCount));
        }
        todolist_adaptor = new tasksRecyclerViewAdapter(todoItems.ITEMS, mListener,0);

        todolist_adaptor.setVisibilityType(R.integer.toDoListView);

        todolist.setAdapter(todolist_adaptor);

        retriveUsrTasks();
        Log.d("todoItems_Json in main", todoItems_Json.toString());
        translateTaskCollection(todoItems_Json);

        return view;
    }

    public static void retriveUsrTasks() {
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "usrtasklist");
            param.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String json = param.toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(tasklist_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("list reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and create task list
                        if ((int) respond_json.get("status") == 0) {
                            Log.d("items", respond_json.getJSONArray("todo_list").toString());
                            todoItems_Json = respond_json.getJSONArray("todo_list");
                            listUpdateHandler.sendEmptyMessage(0);
                            Log.d("todoItems_Json", todoItems_Json.toString());
                        } else if (respond_json.getString("status").equals("1")) {
                            // TODO if fail pop up dialog with fail explained

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void todolist_update() {
        translateTaskCollection(todoItems_Json);
    }

    private static void translateTaskCollection(JSONArray list) {
        todolist_adaptor.clear();
        Log.d("before", todoItems.ITEMS.toString());
        if(list== null||list.length()==0){
            Log.d("list", list.toString());
            return;
        }
        for(int i=0;i<list.length();i++) {
            try {
                todolist_adaptor.addDataToTail(new TaskCollection.TaskItem(list.getJSONObject(i).getInt("tid"), list.getJSONObject(i).getString("title"), list.getJSONObject(i).getLong("ddl"), list.getJSONObject(i).getInt("complete")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("after", todoItems.ITEMS.toString());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


}

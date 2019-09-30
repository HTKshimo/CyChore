package com.example.demo01.ui.tasksTab;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import static com.example.demo01.UsrDefaultPage.uid;

public class TasksList extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private RecyclerView todolist;
    private TaskCollection todoItems ;

    private static final String tasklist_url = "https://us-central1-login-demo-309.cloudfunctions.net/uid_0001_tasklist";

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


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        todolist = view.findViewById(R.id.todolist);
        todoItems = new TaskCollection();
        retriveUsrTasks();


        // Set the adapter
        if (todolist instanceof RecyclerView) {
            Context context = todolist.getContext();
            RecyclerView recyclerView = (RecyclerView) todolist;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new tasksRecyclerViewAdapter(todoItems.ITEMS, mListener));
        }
        return view;
    }

    private void retriveUsrTasks() {
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
                Log.d("json", json);
                Request request = new Request.Builder().url(tasklist_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("tasks response json", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and create task list
                        if ((int) respond_json.get("status") == 0) {
                            translateTaskCollection(respond_json.getJSONArray("todo_list"));
                        } else if (respond_json.getString("status").equals("1")){
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

    private void translateTaskCollection(JSONArray list) {


        todoItems.addItem(new TaskCollection.TaskItem(20001,"smaple task",1569623441258L,1));
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

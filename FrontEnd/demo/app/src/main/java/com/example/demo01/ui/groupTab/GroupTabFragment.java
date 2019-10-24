package com.example.demo01.ui.groupTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo01.R;
import com.example.demo01.data.ListItem;
import com.example.demo01.data.TaskCollection;
import com.example.demo01.ui.OnListFragmentInteractionListener;
import com.example.demo01.ui.tasksTab.tasksRecyclerViewAdapter;

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


public class GroupTabFragment extends Fragment
{
    private static final String ARG_COLUMN_COUNT = "column-count";

    //Pool List view declarations
    private static TaskCollection poolItems;
    private static tasksRecyclerViewAdapter poollist_adaptor;
    private static JSONArray poolItems_Json = new JSONArray();
    private RecyclerView poollist;

    //History List view declarations
    private static TaskCollection historyItems;
    private static tasksRecyclerViewAdapter historylist_adaptor;
    private static JSONArray historyItems_Json = new JSONArray();
    private RecyclerView historylist;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private static Handler listUpdateHandler;

    private int mColumnCount = 1;


    private OnListFragmentInteractionListener mListener;

    private static final String poollist_url = "https://us-central1-login-demo-309.cloudfunctions.net/poollist";
    private static final String historylist_url = "https://us-central1-login-demo-309.cloudfunctions.net/historylist";


    public GroupTabFragment()
    {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GroupTabFragment newInstance(String param1, String param2)
    {
        GroupTabFragment fragment = new GroupTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        listUpdateHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        poollist_update();
                        historylist_update();
                        break;
                }
            }
        };

        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_group_tab, container, false);

        if(groupid==0){
            view.findViewById(R.id.GroupTabMain).setVisibility(View.GONE);
            view.findViewById(R.id.AddTaskButton).setVisibility(View.GONE);
            return view;
        }else{
            view.findViewById(R.id.GroupTabJoinGroupButton).setVisibility(View.GONE);
        }


        //Pool List View
        poollist = view.findViewById(R.id.TaskPool);

        poolItems = new TaskCollection();


        if (mColumnCount <= 1)
        {
            poollist.setLayoutManager(new LinearLayoutManager(poollist.getContext()));
        }
        else
            {
            poollist.setLayoutManager(new GridLayoutManager(poollist.getContext(), mColumnCount));
        }

        poollist_adaptor = new tasksRecyclerViewAdapter(poolItems.ITEMS, mListener, 1);
        poollist_adaptor.setVisibilityType(R.integer.poolListView);

        poollist.setAdapter(poollist_adaptor);
        retrivePoolTasks();
        Log.d("poolItems_Json in main", poolItems_Json.toString());
        translatePoolTaskCollection(poolItems_Json);

        //History List View
        historylist = view.findViewById(R.id.HistoryTasks);

        historyItems = new TaskCollection();


        if (mColumnCount <= 1)
        {
            historylist.setLayoutManager(new LinearLayoutManager(historylist.getContext()));
        }
        else
        {
            historylist.setLayoutManager(new GridLayoutManager(historylist.getContext(), mColumnCount));
        }

        historylist_adaptor = new tasksRecyclerViewAdapter(historyItems.ITEMS, mListener,2);
        historylist_adaptor.setVisibilityType(R.integer.historyListView);

        historylist.setAdapter(historylist_adaptor);
        retriveHistoryTasks();
        Log.d("poolItems_Json in main", historyItems_Json.toString());
        translateHistoryTaskCollection(historyItems_Json);


        return view;
    }


    public static void retrivePoolTasks()
    {
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "pooltasklist");
            param.put("uid", uid);
            param.put("groupID", groupid);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        final String json = param.toString();


        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(poollist_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("list reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and create task list
                        if ((int) respond_json.get("status") == 0)
                        {
                            Log.d("items", respond_json.getJSONArray("pool_list").toString());
                            poolItems_Json = respond_json.getJSONArray("pool_list");
                            listUpdateHandler.sendEmptyMessage(0);
                            Log.d("poolItems_Json", poolItems_Json.toString());
                        }
                        else if (respond_json.getString("status").equals("1"))
                        {
                            // TODO if fail pop up dialog with fail explained

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

    public static void retriveHistoryTasks()
    {
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "historytasklist");
            param.put("uid", uid);
            param.put("groupID", groupid);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        final String json = param.toString();


        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(historylist_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("list reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and create task list
                        if ((int) respond_json.get("status") == 0)
                        {
                            Log.d("items", respond_json.getJSONArray("history_list").toString());
                            historyItems_Json = respond_json.getJSONArray("history_list");
                            listUpdateHandler.sendEmptyMessage(0);
                            Log.d("historyItems_Json", historyItems_Json.toString());
                        }
                        else if (respond_json.getString("status").equals("1"))
                        {
                            // TODO if fail pop up dialog with fail explained

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

    public static void poollist_update()
    {
        translatePoolTaskCollection(poolItems_Json);
    }

    public static void historylist_update()
    {
        translateHistoryTaskCollection(historyItems_Json);
    }



    private static void translatePoolTaskCollection(JSONArray list)
    {
        poollist_adaptor.clear();
        Log.d("before", poolItems.ITEMS.toString());
        if(list== null||list.length()==0)
        {
            Log.d("list", list.toString());
            return;
        }
        //should we create a new PoolItem Class?
        for(int i=0;i<list.length();i++)
        {
            try
            {
                poollist_adaptor.addDataToTail(new TaskCollection.TaskItem(list.getJSONObject(i).getInt("tid"), list.getJSONObject(i).getString("title"), list.getJSONObject(i).getLong("ddl"), list.getJSONObject(i).getInt("complete")));
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        Log.d("after", poolItems.ITEMS.toString());
    }

    private static void translateHistoryTaskCollection(JSONArray list)
    {
        historylist_adaptor.clear();
        Log.d("before", historyItems.ITEMS.toString());
        if(list== null||list.length()==0)
        {
            Log.d("list", list.toString());
            return;
        }
        //should we create a new PoolItem Class?
        for(int i=0;i<list.length();i++)
        {
            try
            {
                historylist_adaptor.addDataToTail(new TaskCollection.TaskItem(list.getJSONObject(i).getInt("tid"), list.getJSONObject(i).getString("title"), list.getJSONObject(i).getLong("ddl"), list.getJSONObject(i).getInt("complete")));
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        Log.d("after", historyItems.ITEMS.toString());
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        Log.d("context", ""+context.getClass().toString());
        if (context instanceof OnListFragmentInteractionListener)
        {
            mListener = (OnListFragmentInteractionListener) context;
        } else
            {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

}

package com.example.CyChore.ui.issueTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.CyChore.IssueDetail;
import com.example.CyChore.R;
import com.example.CyChore.data.IssueCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.CyChore.AdminDefaultPage.uid;

public class fragement_issues extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private static Handler IssueUpdateHandler;
    private RecyclerView issuelist;
    public static IssueCollection issueItems;
    public static IssuesRecyclerViewAdaptor issuelist_adaptor;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static String issuelist_url =  "https://us-central1-login-demo-309.cloudfunctions.net/getAllComplains";


    public fragement_issues() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IssueUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        issuelist_adaptor.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_issues, container, false);
        issuelist = view.findViewById(R.id.IssueListRV);
        issueItems = new IssueCollection();
        issuelist.setLayoutManager(new LinearLayoutManager(issuelist.getContext()));
        issuelist_adaptor = new IssuesRecyclerViewAdaptor(issueItems.ITEMS, mListener);
        issuelist.setAdapter(issuelist_adaptor);
        issuelist_update();

        return view;
    }

    public static void issuelist_update() {
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "chats_retrieve");
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
                Request request = new Request.Builder().url(issuelist_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("list reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and create task list
                        if (respond_json.getInt("status") == 0) {
                            JSONArray issues = (JSONArray) respond_json.get("List of complaints");

                            refreshIssues(issues);
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

    private static void refreshIssues(JSONArray issues) throws JSONException {
        issueItems.clear();
        for (int i = 0; i < issues.length(); i++) {
            JSONObject issue = (JSONObject) issues.get(i);

            issueItems.addItem(new IssueCollection.IssueItem(issue.getInt("tid"),issue.getString("task_name"),issue.getString("description"), issue.getInt("Filer id")));
        }

        IssueUpdateHandler.sendEmptyMessage(0);

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

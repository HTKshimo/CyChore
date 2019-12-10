package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.CyChore.data.IssueCollection;
import com.example.CyChore.data.ListItem;
import com.example.CyChore.ui.OnListFragmentInteractionListener;
import com.example.CyChore.ui.issueTab.IssuesRecyclerViewAdaptor;

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
import static com.example.CyChore.UsrDefaultPage.usrName;

public class MyComplainsPage extends AppCompatActivity {

    private static IssueCollection complainsItems;
    private OnListFragmentInteractionListener mListener;
    private static Handler ComplainsUpdateHandler;
    private RecyclerView complainlist;
    public static IssuesRecyclerViewAdaptor complainlist_adaptor;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private int init = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complains_page);
        ComplainsUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        complainlist_adaptor.notifyDataSetChanged();
                        break;
                }
            }
        };
        mListener = new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(ListItem item, int listType) {
                if (item.title.equals("issue")) {
                    IssueCollection.IssueItem issue = (IssueCollection.IssueItem) item;
                    jumpIssue(issue);
                }
            }
        };
        getSupportActionBar().hide();
        complainlist = findViewById(R.id.MyComplainsRV);
        complainsItems = new IssueCollection();
        complainlist.setLayoutManager(new LinearLayoutManager(complainlist.getContext()));
        complainlist_adaptor = new IssuesRecyclerViewAdaptor(complainsItems.ITEMS, mListener);
        complainlist.setAdapter(complainlist_adaptor);
        complainlist_update();

    }

    private void jumpIssue(IssueCollection.IssueItem issue) {

        Intent intent = new Intent(this, IssueDetail.class);


        IssueDetail.tid = issue.tid;
        IssueDetail.uid = uid;
        IssueDetail.Complain = issue.complain;
        IssueDetail.fid = issue.fid;
        IssueDetail.usrName = usrName;
        IssueDetail.name = issue.detail;

        startActivity(intent);
    }

    private void complainlist_update() {
        if (init==1) {
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
                    String issuelist_url = "https://us-central1-login-demo-309.cloudfunctions.net/getAllComplains";
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

                                refreshComplains(issues);
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
        } else {
            complainsItems.clear();
            complainsItems.addItem(new IssueCollection.IssueItem(11, "mop floor", "Job has not been completed", 11, 1));
            complainsItems.addItem(new IssueCollection.IssueItem(11, "clean bath", "not finished", 11, 0));
            complainsItems.addItem(new IssueCollection.IssueItem(11, "Buy cleaning tools", "does not buy all on the list", 11, 0));

        }


    }

    private static void refreshComplains(JSONArray issues) throws JSONException {
        complainsItems.clear();
        for (int i = 0; i < issues.length(); i++) {
            JSONObject issue = (JSONObject) issues.get(i);

            complainsItems.addItem(new IssueCollection.IssueItem(issue.getInt("tid"), issue.getString("task_name"), issue.getString("description"), issue.getInt("Filer id")));
        }

        ComplainsUpdateHandler.sendEmptyMessage(0);

    }

}

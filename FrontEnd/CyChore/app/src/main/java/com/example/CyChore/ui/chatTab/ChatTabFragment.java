package com.example.CyChore.ui.chatTab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CyChore.R;
import com.example.CyChore.data.ChatCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatTabFragment extends Fragment {

    public static int uid;
    public static String uname;
    private RecyclerView chatlist;
    public static ChatCollection chatItems;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static chatsRecyclerViewAdapter chatlist_adaptor;
    private OnListFragmentInteractionListener mListener;
    private static Handler chatsLogUpdateHandler;
    private static String chatlist_url = "http://coms-309-ks-2.misc.iastate.edu:8080/getUserChatHistory";
    public boolean init = false;


    public ChatTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatsLogUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        chatlist_adaptor.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatlist = view.findViewById(R.id.chatlist);
        chatItems = new ChatCollection();

        chatlist.setLayoutManager(new LinearLayoutManager(chatlist.getContext()));
        chatlist_adaptor = new chatsRecyclerViewAdapter(chatItems.ITEMS, mListener);

        chatlist.setAdapter(chatlist_adaptor);
        if (!init) {
            chatlist_init();
            init = !init;
        }

        return view;
    }


    public static void chatlist_init() {
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "chats_retrieve");
            param.put("user_id", 1);
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
                Request request = new Request.Builder().url(chatlist_url)
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
                            Log.d("typr: ", respond_json.get("cr_ids").getClass().toString());
                            JSONArray order = (JSONArray) respond_json.get("cr_ids");

                            refreshChats(order, respond_json);
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

    private static void refreshChats(JSONArray order, JSONObject respond_json) throws JSONException {
        chatItems.clear();
        for (int i = 0; i < order.length(); i++) {
            JSONObject chat = (JSONObject) respond_json.get(order.get(i).toString());
            ArrayList<String> chatlog = new ArrayList<>();
            JSONArray givenChatlog = (JSONArray) chat.get("chatlog");
            for (int j = 0; j < givenChatlog.length(); j++) {
                chatlog.add(givenChatlog.get(j).toString());
            }
            chatItems.addItem(new ChatCollection.ChatSelection("Issue #" + 11+2*i, chatlog));
        }

        chatsLogUpdateHandler.sendEmptyMessage(0);

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
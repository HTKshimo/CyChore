package com.example.demo01.ui.chatTab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo01.R;
import com.example.demo01.data.ChatCollection;
import com.example.demo01.ui.OnListFragmentInteractionListener;

import org.json.JSONArray;

public class ChatTabFragment extends Fragment {

    private RecyclerView chatlist;
    private static ChatCollection chatItems;
    private static JSONArray todoItems_Json = new JSONArray();
    private static Handler chatsLogUpdateHandler;

    private static chatsRecyclerViewAdapter chatlist_adaptor;
    private OnListFragmentInteractionListener mListener;


    public ChatTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatsLogUpdateHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        chatlist_update();
                        break;
                }
            }
        };
    }

    public static void chatlist_update() {
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









        return view;
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
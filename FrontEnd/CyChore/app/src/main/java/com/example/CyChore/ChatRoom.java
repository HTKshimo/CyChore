package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CyChore.ui.chatTab.ChatTabFragment;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.example.CyChore.UsrDefaultPage.usrName;
import static com.example.CyChore.ui.chatTab.ChatTabFragment.chatItems;

public class ChatRoom extends AppCompatActivity {

    public static ArrayList<String> chatlog;
    public static String chatRoomName;
    public static int chatPosition;
    public static String uname;


    public RecyclerView chatContents;
    private chatRoomRecyclerViewAdapter chatContents_adaptor;
    public TextView title;
    private OkHttpClient client;
    private Handler chatUpdateHandler;
    private EditText msgToSend;
    private WebSocket chatSocket;
    private String wsurl = "ws://10.31.5.195:8080/websocket/1/1";


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        
        chatContents = findViewById(R.id.ChatContents);
        chatContents.setLayoutManager(new LinearLayoutManager(chatContents.getContext()));
        chatContents_adaptor = new chatRoomRecyclerViewAdapter(chatlog);
        chatContents.setAdapter(chatContents_adaptor);

        chatUpdateHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        chatContents_adaptor.notifyDataSetChanged();
                        break;
                    case 1:
                        Toast.makeText(ChatRoom.this,"Conneted!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(ChatRoom.this,"Failed!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        title = findViewById(R.id.ChatRoomTitle);
        title.setText(chatRoomName);

        msgToSend = findViewById(R.id.ChatInput);

        getSupportActionBar().hide();

        client = new OkHttpClient();

        Request request = new Request.Builder().url(wsurl).build();
        WebSocketListener listener = new ChatWebSocketListener();
        chatSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();






    }

    public void onClickSend(View view){
        String toSend = uname+": "+msgToSend.getText().toString();
        if(toSend.length()==0 || toSend.replace(" ", "").length()==0){
            Toast.makeText(view.getContext(), R.string.msg_send_null, Toast.LENGTH_SHORT).show();
            return;

        }
        chatSocket.send(toSend);
        msgToSend.setText("");

    }


    private final class ChatWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            chatUpdateHandler.sendEmptyMessage(1);
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.d("onMessage: ", text);
            output( text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
            chatUpdateHandler.sendEmptyMessage(2);
        }
    }

    private void output(final String txt){
        chatItems.ITEMS.get(chatPosition).ChatContent.add(txt);
        chatItems.ITEMS.get(chatPosition).lastestLine = txt;
        chatUpdateHandler.sendEmptyMessage(0);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        ChatTabFragment.chatlist_adaptor.notifyDataSetChanged();
    }
    
}

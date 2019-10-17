package com.example.demo01.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskStatusUpadateUtil {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static String task_update_url = "https://us-central1-login-demo-309.cloudfunctions.net/log_1";
    private static Handler status_handler;

    private static int reply;

    private static Thread post;


    @SuppressLint("HandlerLeak")
    public static int UpdateTaskStatus(int tid, int uid, int newStatus) {
        final JSONObject param = new JSONObject();



        try {
            param.put("request", "task_update");
            param.put("uid", uid);
            param.put("tid", tid);
            param.put("changeto", newStatus);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String json = param.toString();

        Log.d("rescode1", "onClick: "+reply);
        status_handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        reply = 0;
                        break;
                    case 1:
                        reply = 1;
                        break;
                    default:
                        break;
                }
            }
        };

        post = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json", json);
                Request request = new Request.Builder().url(task_update_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("login reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        Log.d("rescode2", "onClick: "+reply);
                        // TODO check login status and decide jump or not
                        if ((int) respond_json.get("status") == 0) {
                            status_handler.sendEmptyMessage(0);
                        } else {
                            status_handler.sendEmptyMessage(1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        post.setPriority(Thread.MAX_PRIORITY);
        post.start();



        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("rescode3", "onClick: "+reply);


        return reply;
    }
}

;

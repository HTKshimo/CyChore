package com.example.CyChore.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.CyChore.UsrDefaultPage.JSON;

public class ChatStartUtil {

    private static String chat_start_url =  "http://coms-309-ks-2.misc.iastate.edu:8080/startChat";

    public static int startChat(int starter, int receiver ){
        final JSONObject param = new JSONObject();
        int[] members = new int[2];
        members[0] = starter;
        members[1] = receiver;

        try {
            param.put("request", "start_chat");
            param.put("members", members);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String json =  param.toString();

        int result = 9999;

        ExecutorService threadPool= Executors.newSingleThreadExecutor();
        Future reply=threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(chat_start_url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                String reply = response.body().string();

                JSONObject respond_json = new JSONObject(reply);

                return (int) respond_json.get("status");
            }
        });


        try {
            result =  (int) reply.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}

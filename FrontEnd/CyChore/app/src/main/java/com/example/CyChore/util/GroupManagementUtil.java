package com.example.CyChore.util;


import android.annotation.SuppressLint;
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

/**
 * A helper util class for static methods of manage group members by communicating with server by using Http post request.
 */
public class GroupManagementUtil {

    //    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static String task_update_url = "https://us-central1-login-demo-309.cloudfunctions.net/log_0";


    /**
     * Communication method for change group info, by creating callable thread
     *
     * @param gid       group id of the group being concerned
     * @param uid       user id of the user who try to change
     * @param newStatus status code for server to notify if the task is completed or not
     * @return status code of the thread's excution
     */
    @SuppressLint("HandlerLeak")
    public static int UpdateGroupInfo(int gid, int uid, int newStatus) {
        int result = 1;

        final String json = createJson(gid, uid, newStatus, "");

        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future reply = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(task_update_url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                String reply = response.body().string();

                JSONObject respond_json = new JSONObject(reply);

                return (int) respond_json.get("status");
            }
        });


        try {
            result = (int) reply.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Communication method specific for complain task, by creating callable thread
     *
     * @param gid       task id of the task being concerned
     * @param uid       user id of the user who try to change the task status
     * @param newStatus status code for server to notify if the task is completed or not
     * @param comment  String of user's complaining detail
     * @return status code of the thread's excution
     */
    public static int UpdateGroupInfo(int gid, int uid, int newStatus, String comment) {
        int result = 1;

        final String json = createJson(gid, uid, newStatus, comment);

        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future reply = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(task_update_url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                String reply = response.body().string();

                JSONObject respond_json = new JSONObject(reply);

                return (int) respond_json.get("status");
            }
        });


        try {
            result = (int) reply.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String createJson(int gid, int uid, int newStatus, String s) {


        final JSONObject param = new JSONObject();


        try {
            param.put("request", "group_update");
            param.put("uid", uid);
            param.put("gid", gid);
            param.put("changeto", newStatus);
            param.put("comment", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return param.toString();
    }
}

;

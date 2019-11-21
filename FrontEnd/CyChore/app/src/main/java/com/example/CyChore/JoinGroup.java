package com.example.CyChore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JoinGroup extends AppCompatActivity
{

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final String join_url = "https://us-central1-login-demo-309.cloudfunctions.net/log_0";

    private Button joinGroupButton;
    private EditText gID;
    private static Handler dialog_handler;
    public static int newgid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        gID = findViewById(R.id.groupID);
        joinGroupButton = findViewById(R.id.joinGroupBtn);

        dialog_handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                Log.d("handle","start");
                switch (msg.what){
                    case 0:
                        UsrDefaultPage.groupid= newgid;

                        showConfirmDialog();
                        joinGroupButton.setText("Joined the Group!");

                        break;
                    case 1:
                        showAddfailDialog(1);
                    default:break;
                }
            };
        };

    }

    public void setGroupID(View view)
    {
        int groupID = Integer.parseInt(gID.getText().toString());

        // String task_check = "[A-Za-z]{2,14}";
        // Pattern regex = Pattern.compile(task_check);


        setJoinGroup(groupID);
    }

    public static void setJoinGroup(final int gID)
    {
//        Log.d("Join group func", "start");
        final JSONObject param = new JSONObject();
        try
        {
            param.put("request", "join group");
            //param.put("tier", usr_tier); // user type define
            param.put("GroupID", gID);
            //param.put("task_detail", task_detail);
        }
        catch (
                JSONException e)
        {
            e.printStackTrace();
        }
        final String join_json = param.toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(join_json, JSON);
                Request request = new Request.Builder().url(join_url)
                        .post(body)
                        .build();
                try
                {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();

                    Log.d("Join Group respond", reply);
                    try
                    {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check join group status and decide jump or not
                        if (respond_json.getString("status").equals("0"))
                        {
                            newgid = respond_json.getInt("groupid");
                            dialog_handler.sendEmptyMessage(0);
                        }
                        else
                        {
                            // TODO if fail pop up dialog with fail explained
                            dialog_handler.sendEmptyMessage(1);
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

    private void showConfirmDialog()
    {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(JoinGroup.this);
        normalDialog.setTitle("Group confirmed");
        normalDialog.setMessage("Your group has been confirmed.");
        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO
                    }
                });
        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumpHome();
                    }
                });
        normalDialog.show();
    }

    private void showAddfailDialog(int fail_code)
    {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(JoinGroup.this);
        normalDialog.setTitle("Action failed");

        switch (fail_code)
        {
            case 1 :
                normalDialog.setMessage("Something wrong... Try again.");
                break;

        }

        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        UsrDefaultPage.groupid = 90;
                    }
                });

        normalDialog.show();
    }

    private void jumpHome()
    {
        Intent intent = new Intent(this, UsrDefaultPage.class);
        startActivity(intent);
    }

}

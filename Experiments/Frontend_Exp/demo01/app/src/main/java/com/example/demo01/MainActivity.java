package com.example.demo01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.demo01.MESSAGE";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final String login_url = "https://postman-echo.com/post";


    private Button login;
    private Button register;
    private EditText email;
    private EditText password;

    private InputMethodManager in;
    private Handler dialog_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        login = (Button) findViewById(R.id.LoginButton);
        register = (Button) findViewById(R.id.RegisterButton);
        email = (EditText) findViewById(R.id.LoginEmail);
        password = (EditText) findViewById(R.id.LoginPassword);

        dialog_handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        showLoginfailDialog(1);
                        break;
                    case 2:
                        showLoginfailDialog(2);
                        break;
                    default:
                        break;
                }
            }

            ;
        };

        getSupportActionBar().hide();


    }

    public void login(View view) {
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String uname = email.getText().toString();
        String pwd = password.getText().toString();

        // check input legal or not
        String email_check = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern regex = Pattern.compile(email_check);

        if (TextUtils.isEmpty(uname)) {
            Toast.makeText(view.getContext(), R.string.login_uname_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!regex.matcher(uname).matches()) {
            Toast.makeText(view.getContext(), R.string.invalid_email_address, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(view.getContext(), R.string.login_pwd_null, Toast.LENGTH_SHORT).show();
            return;
        }


        // login start
        login(uname, pwd);

        // check return value for starting intent


    }

    private void login(final String name, final String pwd) {
        // create json to be send
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "login");
            param.put("email", name);
            param.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String json = param.toString();


        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Request request = new Request.Builder().url(login_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String reply = response.body().string();
                    Log.d("login reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and decide jump or not
                        if (respond_json.getString("url").equals(login_url)) {

                            loginJumpHome(reply);
                        } else {
                            // TODO if fail pop up dialog with fail explained
                            dialog_handler.sendEmptyMessage(1);
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

    private void showLoginfailDialog(int fail_code) {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("Login failed");

        switch (fail_code) {
            case 1:
                normalDialog.setMessage("This email has not registered.");
                break;
            case 2:
                normalDialog.setMessage("Email or password not correct.");
                break;

        }

        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.show();
    }

    private void loginJumpHome(String data) {


        // if login success, jump to home
        Intent intent = new Intent(this, UsrDefaultPage.class);
        intent.putExtra(EXTRA_MESSAGE, data);
        Log.d("intent json", data);
        startActivity(intent);
    }

    public void register(View view) {
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);


        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }


}

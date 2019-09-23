package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.demo01.MESSAGE";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    private Button login;
    private Button register;
    private EditText email;
    private EditText password;

    private InputMethodManager in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        login = (Button) findViewById(R.id.LoginButton);
        register = (Button) findViewById(R.id.RegisterButton);
        email = (EditText) findViewById(R.id.LoginEmail);
        password = (EditText) findViewById(R.id.LoginPassword);

        getSupportActionBar().hide();


    }

    public void login(View view) {
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String uname = email.getText().toString();
        String pwd = password.getText().toString();

        // check input legal or not
        if (TextUtils.isEmpty(uname)) {
            Toast.makeText(view.getContext(), R.string.login_uname_null, Toast.LENGTH_SHORT).show();
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
        int resCode=999;
        // create json to be send
        final JSONObject param = new JSONObject();
        try {
            param.put("request", "login");
            param.put("tier", "0"); // user type define
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
                Request request = new Request.Builder().url("https://postman-echo.com/post")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Log.d("Login attempt", "result: " + result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();






        // if login success, jump to home
        Intent intent = new Intent(this, UsrDefaultPage.class);
        intent.putExtra(EXTRA_MESSAGE, json);
        startActivity(intent);
    }

    // under construction, may be totally undo
    private void decodeResponse(String date) {
        try {
            JSONObject jsonObject = new JSONObject(date);
            String flag = jsonObject.getString("flag");
            if (flag.equals("success")) {

            } else {
                 }
            Message message = new Message();
            message.what = 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void register(View view) {
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);



        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }



}

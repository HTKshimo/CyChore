package com.example.demo01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationPage extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

//    private static final String register_url = "https://us-central1-login-demo-309.cloudfunctions.net/log_0";
    private static final String register_url = "http://coms-309-ks-2.misc.iastate.edu:8080/registerUser/";
    private Button register;
    private EditText reg_email;
    private EditText password;
    private EditText repassword;
    private Spinner usr_type;
    private Handler dialog_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        getSupportActionBar().hide();

        register = (Button) findViewById(R.id.reg_button);
        reg_email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_psw);
        repassword = findViewById(R.id.reg_psw2);

        dialog_handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                Log.d("handle","start");
                switch (msg.what){
                    case 0:
                        showConfirmDialog();
                        break;
                    case 1:
                        showRegfailDialog(1);
                    default:break;
                }
            };
        };

    }

    public void register(View view)
    {
        String email = reg_email.getText().toString();
        String pwd = password.getText().toString();
        String pwd2 = repassword.getText().toString();


        usr_type = (Spinner) findViewById(R.id.usr_type_spinner);
        int usr_tier = 1;
        if (usr_type.getSelectedItem().toString() == "Tenant") {
            usr_tier = 1;
        } else {
            usr_tier = 2;
        }

        String email_check = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern regex = Pattern.compile(email_check);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(view.getContext(), R.string.login_email_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!regex.matcher(email).matches()) {
            Toast.makeText(view.getContext(), R.string.invalid_email_address, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(view.getContext(), R.string.login_pwd_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwd2)) {
            Toast.makeText(view.getContext(), R.string.reg_password_not_match, Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.length() < 6) {
            Toast.makeText(view.getContext(), R.string.reg_password_too_short, Toast.LENGTH_SHORT).show();
            return;
        }
        register(email,pwd,usr_tier);

    }


        private void register(final String email,final String pwd,final int usr_tier)
        {
        Log.d("Register func", "start");
        final JSONObject param = new JSONObject();
        try
        {
            param.put("request", "register");
            param.put("tier", usr_tier); // user type define
            param.put("email", email);
            param.put("password", pwd);
        }
        catch (
                JSONException e) {
            e.printStackTrace();
        }
        final String reg_json = param.toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(reg_json, JSON);
                Request request = new Request.Builder().url(register_url+"a"+"/"+email+"/"+"1")
                        .post(body)
                        .build();
                try
                {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();

                    Log.d("Registration respond", reply);
                    try
                    {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and decide jump or not
                        if (respond_json.getString("status").equals("0")) {
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

    private void showRegfailDialog(int fail_code)
    {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegistrationPage.this);
        normalDialog.setTitle("Login failed");

        switch (fail_code) {
            case 1 :
                normalDialog.setMessage("This email has been registered.");
                break;

        }

        normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
        normalDialog.show();
    }

    private void showConfirmDialog()
    {
        /* @setIcon
         * @setTitle
         * @setMessage
         * setXXX func return Dialog object
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegistrationPage.this);
                normalDialog.setTitle("Registration confirmed");
                normalDialog.setMessage("Your registration has been confirmed.");
                normalDialog.setPositiveButton("done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumpLogin();
                    }
                });
        normalDialog.show();
    }

    private void jumpLogin()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
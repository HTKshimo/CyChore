package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationPage extends AppCompatActivity {


    private Button register;
    private EditText reg_email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text


        register = (Button) findViewById(R.id.reg_button);
        reg_email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_psw);
    }

    public void register(View view) {
        String email = reg_email.getText().toString();
        String pwd = password.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(view.getContext(), R.string.login_email_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(view.getContext(), R.string.login_pwd_null, Toast.LENGTH_SHORT).show();
            return;
        }


        final JSONObject param = new JSONObject();
        try {
            param.put("request", "login");
            param.put("tier", "0"); // user type define
            param.put("email", email);
            param.put("password", pwd);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

    }
}
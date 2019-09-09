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


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.demo01_login.MESSAGE";

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
        Intent intent = new Intent(this, UsrDefaultPage.class);

        String message = email.getText().toString() + " : " + password.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void register(View view) {
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String uname = email.getText().toString();
        String pwd = password.getText().toString();
        if (TextUtils.isEmpty(uname)) {
            Toast.makeText(view.getContext(), R.string.login_uname_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(view.getContext(), R.string.login_pwd_null, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, RegistrationPage.class);

        String message = email.getText().toString() + " : " + password.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}

package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Type Cast
        Name = (EditText)findViewById(R.id.loginName);
        Password = (EditText)findViewById(R.id.loginPassword);
        Login = (Button) findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             validateLogin(Name.getText().toString(), Password.getText().toString());
               // validate();
            }
        });

        Gson gson = new Gson();

        Address address = new Address("India", "Gurgaon");

        User user = new User("Yamini",23, "yamini@iastate.edu", address );
        String json = gson.toJson(user);
      /* String json = "{\"firstname\":\"John\", \"age\":23, \"mail\":\"yamini@iastate.edu\"}";
       User user = gson.fromJson(json, User.class);
       */

    }

  /*  private void validate()
    {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }*/

    private void validateLogin(String userName, String userPassword)
    {
        if( (userName == "Yamini") && (userPassword == "coms309") )
        {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            //to move to the next activity
            startActivity(intent);
        }
       /* else
        {
            System.out.println("Try Again :(");
        }*/
    }
}

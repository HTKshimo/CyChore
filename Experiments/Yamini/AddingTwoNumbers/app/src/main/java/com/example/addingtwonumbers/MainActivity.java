package com.example.addingtwonumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private EditText number1;
    private EditText number2;
    private Button add;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = (EditText) findViewById(R.id.etNum1);
        number2 = (EditText) findViewById(R.id.etNum2);
        add = (Button) findViewById(R.id.btnAdd);
        result = (TextView) findViewById(R.id.tvAnswer);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOne = Integer.parseInt(number1.getText().toString());
                int numberTwo = Integer.parseInt(number2.getText().toString());
                int sum = numberOne + numberTwo;
                result.setText("Answer is " + String.valueOf(sum));
            }
        });
    }
}

package com.anychart.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button plusButton, equalsButton, subtractButton, multiplyButton, divisionButton, clearButton;
    private EditText firstValue, secondValue, resultValue;
    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusButton = (Button) findViewById(R.id.plusButton);
        subtractButton = findViewById(R.id.subtractButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        divisionButton = findViewById(R.id.divisionButton);

        equalsButton = (Button) findViewById(R.id.equalsButton);
        clearButton = findViewById(R.id.clearButton);

        firstValue = (EditText) findViewById(R.id.firstValue);
        secondValue = (EditText) findViewById(R.id.secondValue);
        resultValue = (EditText) findViewById(R.id.resultValue);

        plusButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divisionButton.setOnClickListener(this);

        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double firstVal;
                double secondVal;
                firstVal = Double.valueOf(firstValue.getText().toString());
                secondVal = Double.valueOf(secondValue.getText().toString());
                if(operator.equals("+")) {
                    resultValue.setText(String.valueOf(firstVal + secondVal));
                }
                if(operator.equals("-")) {
                    resultValue.setText(String.valueOf(firstVal - secondVal));
                }
                if(operator.equals("*")) {
                    resultValue.setText(String.valueOf(firstVal * secondVal));
                }
                if(operator.equals("/")) {
                    resultValue.setText(String.valueOf(firstVal / secondVal));
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusButton.setVisibility(View.VISIBLE);
                subtractButton.setVisibility(View.VISIBLE);
                multiplyButton.setVisibility(View.VISIBLE);
                divisionButton.setVisibility(View.VISIBLE);
                firstValue.setText("");
                secondValue.setText("");
                resultValue.setText("");
            }
        });
    }



    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        if(b.getText().toString().equals("+") || b.getText().toString().equals("-") || b.getText().toString().equals("*") || b.getText().toString().equals("/")) {
            hideAll();
            b.setVisibility(View.VISIBLE);
            operator = b.getText().toString();
        }

    }

    private void hideAll() {
        plusButton.setVisibility(View.INVISIBLE);
        subtractButton.setVisibility(View.INVISIBLE);
        multiplyButton.setVisibility(View.INVISIBLE);
        divisionButton.setVisibility(View.INVISIBLE);
    }
}
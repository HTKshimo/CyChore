package com.anychart.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SpringActivity extends AppCompatActivity {

    private Button hashButton;
    private EditText hashText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);

        hashButton = (Button) findViewById(R.id.hashButton);
        hashText = (EditText) findViewById(R.id.hashText);

        hashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plaintext = hashText.getText().toString();
                try {
                    sendPost(plaintext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendPost(final String value) throws Exception {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.31.49.68:8080/home";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SpringActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", value);
                return params;
            }
        };
        queue.add(postRequest);

    }
}



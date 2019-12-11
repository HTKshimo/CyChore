package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.CyChore.UsrDefaultPage.uid;

public class ProfileEditPage extends AppCompatActivity {


    private EditText newUname;
    private EditText oldPsw;
    private EditText newPsw;
    private EditText newPswConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_page);
        getSupportActionBar().hide();
        newUname = findViewById(R.id.ChangeUname);
        oldPsw = findViewById(R.id.CheckOldPsw);
        newPsw = findViewById(R.id.NewPsw);
        newPswConfirm = findViewById(R.id.CheckNewPsw);





    }


    public void submitChange(View view){
        if(!oldPsw.getText().toString().equals(getSharedPreferences("accountInfo", Context.MODE_PRIVATE).getString("pwd",""))){
            Toast.makeText(view.getContext(), R.string.old_password_not_match, Toast.LENGTH_SHORT).show();
            return;
        }
        if(newPsw.getText().toString().length()==0&&newPswConfirm.getText().toString().length()==0){
            createChangeJson(uid,newUname.getText().toString(),oldPsw.getText().toString());
        }else{

        }

    }

    private void createChangeJson(int uid, String newUname, String newPsw) {
    }
}

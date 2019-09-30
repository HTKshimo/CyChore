package com.example.demo01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.demo01.data.ProfileCollection;
import com.example.demo01.ui.profile.OnListFragmentInteractionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class UsrDefaultPage extends AppCompatActivity implements OnListFragmentInteractionListener {

    public static String uid = "";
    public static final String url_head = "https://us-central1-login-demo-309.cloudfunctions.net/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_default_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_group, R.id.navigation_chats)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        uid = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Log.d("intent_uid:",uid);



    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();

    }
//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        if (fragment instanceof ProfileFragment) {
//            ProfileFragment profileFragment = (ProfileFragment) fragment;
//            profileFragment.setOnListFragmentInteractionListener(this);
//        }
//    }

//    @Override
//    public void onListFragmentInteraction(String primaryKey) {
//        if(primaryKey.equals("Log Out")){
//            logout();
//        }
//    }

    public void OnListFragmentInteractionListener(){}


    public void logout(){
        getSharedPreferences("accountInfo", Context.MODE_PRIVATE).edit().putBoolean("auto_login", false).commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(ProfileCollection.ProfileSelection item) {
        if(item.title.equals("Log Out")){
            logout();
        }
    }
}

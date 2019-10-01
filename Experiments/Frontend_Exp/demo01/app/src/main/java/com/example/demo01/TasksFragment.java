package com.example.demo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.demo01.ui.home.HomeViewModel;

public class TasksFragment extends Fragment {
//Yamini TODO
    //Fragment Home - Fragment Tasks
    //implement recycler view -
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        com.example.demo01.ui.home.HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
            View root = inflater.inflate(R.layout.fragment_home, container, false);


            return root;


    }




}

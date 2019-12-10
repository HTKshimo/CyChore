package com.example.CyChore.ui.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.CyChore.R;
import com.example.CyChore.data.ProfileCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;

public class ProfileFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    public static boolean isAdmin = false;


    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if(!isAdmin) {
                recyclerView.setAdapter(new MyProfileRecyclerViewAdapter(ProfileCollection.ITEMS, mListener));
            }else{
                ProfileCollection options = new ProfileCollection();
                options.clear();
                options.addItem(new ProfileCollection.ProfileSelection("My Account", "edit personal info"));
                options.addItem(new ProfileCollection.ProfileSelection("Group Management", "manage group info"));
                options.addItem(new ProfileCollection.ProfileSelection("Log Out"));
                recyclerView.setAdapter(new MyProfileRecyclerViewAdapter(options.ITEMS, mListener));
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


}

package com.example.CyChore;

import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CyChore.data.GroupCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;

import java.util.List;

public class groupManagementRecyclerViewAdaptor extends RecyclerView.Adapter<groupManagementRecyclerViewAdaptor.ViewHolder> {

    private final List<GroupCollection.GroupItem> mValues;
    private OnListFragmentInteractionListener mlistener;

    public groupManagementRecyclerViewAdaptor(List<GroupCollection.GroupItem> items, OnListFragmentInteractionListener listener){
        mValues = items;
        mlistener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_management_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.GroupTitle);
        }
    }
}

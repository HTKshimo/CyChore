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
    private OnListFragmentInteractionListener mListener;

    public groupManagementRecyclerViewAdaptor(List<GroupCollection.GroupItem> items, OnListFragmentInteractionListener listener){
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_management_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).detail);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, 0);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public TextView name;
        public GroupCollection.GroupItem mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.GroupTitle);
        }
    }
}

package com.example.CyChore.ui.issueTab;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CyChore.R;
import com.example.CyChore.data.IssueCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;

import java.util.List;

public class IssuesRecyclerViewAdaptor extends RecyclerView.Adapter<IssuesRecyclerViewAdaptor.ViewHolder> {

    private final List<IssueCollection.IssueItem> mValues;

    private final OnListFragmentInteractionListener mListener;


    public IssuesRecyclerViewAdaptor(List<IssueCollection.IssueItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issue_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.IssueName.setText(mValues.get(position).detail);
        holder.IssueComplain.setText(mValues.get(position).complain);
        if(mValues.get(position).status == 1) {
            holder.IssueName.setBackgroundColor(Color.parseColor("#72AA2855"));
            holder.IssueComplain.setBackgroundColor(Color.parseColor("#72AA2855"));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,0);
                    // TODO: set onclick listener for buttons in holder
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addData(int position, IssueCollection.IssueItem item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void addDataToTail(IssueCollection.IssueItem item) {
        int position = mValues.size();
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void removeData(IssueCollection.IssueItem item) {
        int position = mValues.indexOf(item);
        mValues.remove(item);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = mValues.size();
        mValues.clear();
        while (size > 0) {
            notifyItemRemoved(size - 1);
            size--;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public IssueCollection.IssueItem mItem;

        public final TextView IssueName;
        public final TextView IssueComplain;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            IssueName = itemView.findViewById(R.id.IssueItemName);
            IssueComplain = itemView.findViewById(R.id.IssueItemComplain);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + IssueName.getText() + IssueComplain.getText();
        }

    }
}

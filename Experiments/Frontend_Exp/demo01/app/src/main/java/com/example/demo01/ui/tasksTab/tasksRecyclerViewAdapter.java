package com.example.demo01.ui.tasksTab;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo01.R;
import com.example.demo01.data.TaskCollection;
import com.example.demo01.ui.OnListFragmentInteractionListener;


import java.util.List;

public class tasksRecyclerViewAdapter extends RecyclerView.Adapter<tasksRecyclerViewAdapter.ViewHolder> {


    private final List<TaskCollection.TaskItem> mValues;

    private final OnListFragmentInteractionListener mListener;



    public tasksRecyclerViewAdapter(List<TaskCollection.TaskItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        Log.d("todolist", items.toString());
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usr_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).detail);
        holder.mContentView.setText(mValues.get(position).dueTime);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    // TODO: set onclick listener for buttons in holder
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public void addData(int position, TaskCollection.TaskItem item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void addDataToTail(TaskCollection.TaskItem item) {
        int position = mValues.size();
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void removeData(TaskCollection.TaskItem item) {
        int position = mValues.indexOf(item);
        mValues.remove(item);
        notifyItemRemoved(position);
    }

    public void clear(){
        int size = mValues.size();
        mValues.clear();
        while(size>0){
            notifyItemRemoved(size-1);
            size--;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button PoolTask;
        public final Button Finish;
        public TaskCollection.TaskItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.taskName);
            mContentView = (TextView) view.findViewById(R.id.ddl);
            PoolTask = view.findViewById(R.id.PoolTask);
            Finish = view.findViewById(R.id.Finish);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}


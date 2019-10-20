package com.example.demo01.ui.tasksTab;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo01.JoinGroup;
import com.example.demo01.R;
import com.example.demo01.TaskDetail;
import com.example.demo01.data.TaskCollection;
import com.example.demo01.ui.OnListFragmentInteractionListener;
import com.example.demo01.util.TaskStatusUpadateUtil;


import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.demo01.UsrDefaultPage.uid;

public class tasksRecyclerViewAdapter extends RecyclerView.Adapter<tasksRecyclerViewAdapter.ViewHolder> {


    private final List<TaskCollection.TaskItem> mValues;

    private final OnListFragmentInteractionListener mListener;
    private int appearanceType=0;


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

        if(appearanceType==R.integer.toDoListView){
            holder.Pickup.setVisibility(View.GONE);
            holder.Complain.setVisibility(View.GONE);
        }else if(appearanceType==R.integer.poolListView){
            holder.PoolTask.setVisibility(View.GONE);
            holder.Complain.setVisibility(View.GONE);
            holder.Finish.setVisibility(View.GONE);
        }else if(appearanceType==R.integer.historyListView){
            holder.Pickup.setVisibility(View.GONE);
            holder.PoolTask.setVisibility(View.GONE);
            holder.Finish.setVisibility(View.GONE);
            holder.mContentView.setVisibility(View.GONE);
            holder.duein.setVisibility(View.GONE);

        }

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

    public void clear() {
        int size = mValues.size();
        mValues.clear();
        while (size > 0) {
            notifyItemRemoved(size - 1);
            size--;
        }

    }

    public void setVisibilityType(int i) {
        appearanceType = i;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView duein;
        public final Button PoolTask;
        public final Button Finish;
        public final Button Pickup;
        public final Button Complain;
        public TaskCollection.TaskItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            duein = view.findViewById(R.id.duein);
            mIdView = (TextView) view.findViewById(R.id.taskName);
            mContentView = (TextView) view.findViewById(R.id.ddl);
            PoolTask = view.findViewById(R.id.PoolTask);

            PoolTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int responseCode;
                    responseCode = TaskStatusUpadateUtil.UpdateTaskStatus(mItem.tid, uid, 3);
                    if (responseCode == 0) {
                        Toast.makeText(v.getContext(), "Task get pooled!", Toast.LENGTH_SHORT).show();
                        TasksList.retriveUsrTasks();
                    } else {
                        Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Finish = view.findViewById(R.id.TaskFinish);
            Finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int responseCode;
                    responseCode = TaskStatusUpadateUtil.UpdateTaskStatus(mItem.tid, uid, 0);
                    if (responseCode == 0) {
                        Toast.makeText(v.getContext(), "Complete status logged!", Toast.LENGTH_SHORT).show();
                        TasksList.retriveUsrTasks();
                    } else {
                        Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Pickup = view.findViewById(R.id.Pickup);
            Pickup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int responseCode;
                    responseCode = TaskStatusUpadateUtil.UpdateTaskStatus(mItem.tid, uid, 5);
                    if (responseCode == 0) {
                        Toast.makeText(v.getContext(), "Complete status logged!", Toast.LENGTH_SHORT).show();
                        TasksList.retriveUsrTasks();
                    } else {
                        Toast.makeText(v.getContext(), "Something wrong... Try later", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Complain = view.findViewById(R.id.TaskComplain);
            Complain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), TaskDetail.class);


                    TaskDetail.tid = mItem.tid;
                    TaskDetail.uid = uid;
                    TaskDetail.tstatus = mItem.tstatus;
                    TaskDetail.ddl = mItem.ddl.getTime();
                    TaskDetail.name = mItem.detail;
                    TaskDetail.detail = "N/A";

                    v.getContext().startActivity(intent);

                }
            });


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}


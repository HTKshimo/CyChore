package com.example.CyChore.ui.chatTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CyChore.R;
import com.example.CyChore.data.ChatCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;

import java.util.List;

public class chatsRecyclerViewAdapter extends RecyclerView.Adapter<chatsRecyclerViewAdapter.ViewHolder> {

    private final List<ChatCollection.ChatSelection> mValues;

    private final OnListFragmentInteractionListener mListener;


    public chatsRecyclerViewAdapter(List<ChatCollection.ChatSelection> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.ChatTitle.setText(mValues.get(position).ChatTitle);
        holder.ChatLatestLine.setText(mValues.get(position).lastestLine);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,position);
                    // TODO: set onclick listener for buttons in holder
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addData(int position, ChatCollection.ChatSelection item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void addDataToTail(ChatCollection.ChatSelection item) {
        int position = mValues.size();
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void removeData(ChatCollection.ChatSelection item) {
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
        public ChatCollection.ChatSelection mItem;

        public TextView ChatTitle;
        public TextView ChatLatestLine;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ChatTitle = itemView.findViewById(R.id.ChatWith);
            ChatLatestLine = itemView.findViewById(R.id.LatestChat);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + ChatTitle.getText() + ChatLatestLine.getText();
        }

    }
}

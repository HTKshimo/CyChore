package com.example.CyChore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

class chatRoomRecyclerViewAdapter  extends Adapter<chatRoomRecyclerViewAdapter.ViewHolder>{

    private final List<String> mValues;


    public chatRoomRecyclerViewAdapter(List<String> chatContents){
        mValues = chatContents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_line_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.line.setText(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public TextView line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            line = mView.findViewById(R.id.ChatLine);
        }
    }
}

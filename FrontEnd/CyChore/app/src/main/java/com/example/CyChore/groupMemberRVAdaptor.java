package com.example.CyChore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class groupMemberRVAdaptor extends RecyclerView.Adapter<groupMemberRVAdaptor.ViewHolder> {
    private final List<String> mValues;
    private boolean isAdmin;

    public groupMemberRVAdaptor(List<String> memList, boolean Admin) {
        mValues = memList;
        isAdmin = Admin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_member_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.memName.setText(mValues.get(position));
        if(!isAdmin){
            holder.removeMember.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public TextView memName;
        public Button removeMember;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            memName = mView.findViewById(R.id.MemberName);
            removeMember = mView.findViewById(R.id.memberRemove);
        }
    }
}

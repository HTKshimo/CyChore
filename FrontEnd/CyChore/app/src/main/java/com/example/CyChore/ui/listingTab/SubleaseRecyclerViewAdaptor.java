package com.example.CyChore.ui.listingTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CyChore.R;
import com.example.CyChore.data.SubleaseCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;
import com.example.CyChore.ui.listingTab.SubleaseRecyclerViewAdaptor;

import java.util.List;

public class SubleaseRecyclerViewAdaptor //extends RecyclerView.Adapter<SubleaseRecyclerViewAdaptor.ViewHolder>
{
   // private final List<SubleaseCollection.subleaseItem> mValues;

  //  private final OnListFragmentInteractionListener mListener;


  /*  public IssuesRecyclerViewAdaptor(List<SubleaseCollection.subleaseItem> items, OnListFragmentInteractionListener listener)
    {
  //      mValues = items;
  //      mListener = listener;
    }

    @NonNull
    @Override
    public SubleaseRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
      //  View view = LayoutInflater.from(parent.getContext())
        //        .inflate(R.layout.sublease_item, parent, false);
        return new SubleaseRecyclerViewAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubleaseRecyclerViewAdaptor.ViewHolder holder, final int position) {
     //   holder.mItem = mValues.get(position);
    //    holder.SubleaseName.setText(mValues.get(position).detail);
     //   holder.SubleaseComplain.setText(mValues.get(position).sublease);

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

    public void addData(int position, SubleaseCollection.subleaseItem item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void addDataToTail(SubleaseCollection.subleaseItem item)
    {
        int position = mValues.size();
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void removeData(SubleaseCollection.subleaseItem item) {
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
        public SubleaseCollection.subleaseItem mItem;

    //    public final TextView SubleaseName;
      //  public final TextView SubleaseDescription;
//

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
           // SubleaseName = itemView.findViewById(R.id.SubleaseItemName);
        //    SubleaseDescription = itemView.findViewById(R.id.SubleaseItemDescription);


        }

        @Override
        public String toString() {
         //   return super.toString() + " '" + SubleaseName.getText() + SubleaseDescription.getText();
            return "";
        }

    }*/
}

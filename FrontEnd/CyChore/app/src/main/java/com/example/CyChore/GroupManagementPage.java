package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.CyChore.data.GroupCollection;
import com.example.CyChore.data.ListItem;
import com.example.CyChore.ui.OnListFragmentInteractionListener;

public class GroupManagementPage extends AppCompatActivity {

    private RecyclerView groupsRV;
    private groupManagementRecyclerViewAdaptor groupsRV_adpator;
    public static GroupCollection groupItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupItems = new GroupCollection();

        setContentView(R.layout.activity_group_management_page);
        groupsRV = findViewById(R.id.GroupMngRV);
        OnListFragmentInteractionListener mlistener = new OnListFragmentInteractionListener(){

            @Override
            public void onListFragmentInteraction(ListItem item, int listType) {
                jumpGroupDetail((GroupCollection.GroupItem) item);
            }
        };
        groupsRV_adpator = new groupManagementRecyclerViewAdaptor(groupItems.ITEMS, mlistener);
        groupsRV.setLayoutManager(new LinearLayoutManager(groupsRV.getContext()));
        groupsRV.setAdapter(groupsRV_adpator);
        getSupportActionBar().hide();
        Log.d("groupitems", groupItems.toString());
    }

    private void jumpGroupDetail(GroupCollection.GroupItem gitem) {
        Intent intent = new Intent(this, GroupDetail.class);
        GroupDetail.gid = gitem.gid;
        GroupDetail.address = gitem.address;
        GroupDetail.members = gitem.groupMembers;
        GroupDetail.isAdmin = true;
        GroupDetail.lat = gitem.la;
        GroupDetail.lng = gitem.ln;
        startActivity(intent);
    }

}

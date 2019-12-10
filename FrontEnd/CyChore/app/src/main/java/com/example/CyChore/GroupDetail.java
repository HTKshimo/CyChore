package com.example.CyChore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GroupDetail extends AppCompatActivity implements OnMapReadyCallback {

    public static int gid;
    public static ArrayList<String> members;
    public static String address;
    public TextView addr;
    public RecyclerView groupMemberList;
    public groupMemberRVAdaptor groupMemberList_adaptor;
    public MapView mMapView;

    public static double lat = 42.016278;
    public static double lng = -93.647852;

    public static boolean isAdmin = false;


    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView = findViewById(R.id.mapView);

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
        groupMemberList = findViewById(R.id.GroupMemRV);

        groupMemberList.setLayoutManager(new LinearLayoutManager(groupMemberList.getContext()));
        groupMemberList_adaptor = new groupMemberRVAdaptor(members, isAdmin);
        groupMemberList.setAdapter(groupMemberList_adaptor);



        addr = findViewById(R.id.GroupAddress);
        addr.setText(address);
        getSupportActionBar().hide();
    }




    @Override
    public void onMapReady(GoogleMap map) {

        LatLng appointLoc = new LatLng(lat, lng);

        map.addMarker(new MarkerOptions().position(appointLoc).title("Marker"));
        map.setMinZoomPreference(15.0f);
        map.moveCamera(CameraUpdateFactory.newLatLng(appointLoc));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }



    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}

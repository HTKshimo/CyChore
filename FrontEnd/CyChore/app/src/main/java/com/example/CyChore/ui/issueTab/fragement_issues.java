package com.example.CyChore.ui.issueTab;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.CyChore.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragement_issues.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragement_issues#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragement_issues extends Fragment {

    private OnFragmentInteractionListener mListener;

    public fragement_issues() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragement_issues.
     */
    // TODO: Rename and change types and number of parameters
    public static fragement_issues newInstance(String param1, String param2) {
        fragement_issues fragment = new fragement_issues();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issues, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

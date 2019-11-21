package com.example.CyChore.ui.listingTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.CyChore.R;
import com.example.CyChore.data.SubleaseCollection;
import com.example.CyChore.ui.OnListFragmentInteractionListener;
import com.example.CyChore.ui.listingTab.SubleaseRecyclerViewAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.CyChore.AdminDefaultPage.uid;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListingFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListingFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingFragement extends Fragment
{


    private OnListFragmentInteractionListener mListener;

    private static String sublist_url =  "https://us-central1-login-demo-309.cloudfunctions.net/getAllComplains";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static SubleaseCollection subleaseItems;
    public static SubleaseRecyclerViewAdaptor sublist_adaptor;
    private static Handler SubleaseUpdateHandler;


    public ListingFragement()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListingFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static ListingFragement newInstance(String param1, String param2) {
        ListingFragement fragment = new ListingFragement();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SubleaseUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        sublist_adaptor.notifyDataSetChanged();
                        break;
                }
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing, container, false);
    }

    public static void sublist_update()
    {
        final JSONObject param = new JSONObject();

        try {
            param.put("request", "sublists_retrieve");
            param.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String json = param.toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(json, JSON);
                Log.d("json req", json);
                Request request = new Request.Builder().url(sublist_url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    String reply = response.body().string();
                    Log.d("list reply", reply);
                    try {
                        JSONObject respond_json = new JSONObject(reply);
                        // TODO check login status and create task list
                        if (respond_json.getInt("status") == 0) {
                            JSONArray sublist = (JSONArray) respond_json.get("List of Sublease");

                            refreshIssues(sublist);
                        } else if (respond_json.getString("status").equals("1")) {
                            // TODO if fail pop up dialog with fail explained

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static void refreshIssues(JSONArray issues) throws JSONException {
        subleaseItems.clear();
        for (int i = 0; i < issues.length(); i++) {
            JSONObject issue = (JSONObject) issues.get(i);

            subleaseItems.addItem(new SubleaseCollection.subleaseItem("","", "", 1));
            //Item(issue.getInt("tid"),issue.getString("task_name"),issue.getString("description"), issue.getInt("Filer id")));
        }

        SubleaseUpdateHandler.sendEmptyMessage(0);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

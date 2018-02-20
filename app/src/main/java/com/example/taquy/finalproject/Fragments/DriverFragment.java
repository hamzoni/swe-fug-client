package com.example.taquy.finalproject.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.taquy.finalproject.API.TripDAL;
import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Entities.User;
import com.example.taquy.finalproject.Misc.Authentication;
import com.example.taquy.finalproject.Misc.Debugger;
import com.example.taquy.finalproject.R;
import com.google.gson.Gson;

import org.json.JSONObject;

public class DriverFragment extends Fragment {

    private Context ctx;
    private View root;

    private Button btn_trip;

    public DriverFragment() {
    }

    public static DriverFragment getInstance() {
        return new DriverFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_driver, container, false);
        this.ctx = root.getContext();

        this.btn_trip = root.findViewById(R.id.btn_trip);
        this.btn_trip.setOnClickListener(new BtnCreateTrip());

        loadData();
        attachEvent();

        return root;
    }

    private void attachEvent() {
        GridView gridView = root.findViewById(R.id.ctn_trip_list);

        // show detail dialog when passenger click on a trip record
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip trip = (Trip) parent.getItemAtPosition(position);
                DriverTripDialog dialog = new DriverTripDialog();

                Bundle bundle = new Bundle();
                bundle.putSerializable("trip", trip);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "Hello world");
            }
        });
    }

    private void loadData() {
        try {
            Authentication auth = new Authentication(ctx);

            if (!auth.isAuth()) return;
            User user = auth.retrieve();

            JSONObject params = new JSONObject();
            params.put("login", user.getEmail());
            params.put("pwd", user.getPassword());
            new TripDAL(root, TripDAL.CMD_DRIVER_TRIPS).makeRequest(params.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class BtnCreateTrip implements View.OnClickListener {

        @Override
        public void onClick(View view) {

        }

    }

}

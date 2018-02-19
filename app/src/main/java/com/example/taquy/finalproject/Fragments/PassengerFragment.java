package com.example.taquy.finalproject.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.taquy.finalproject.API.TripDAL;
import com.example.taquy.finalproject.Entities.User;
import com.example.taquy.finalproject.Misc.Authentication;
import com.example.taquy.finalproject.R;

public class PassengerFragment extends Fragment {

    private Context ctx;
    private View root;

    private Button btn_trip;

    public PassengerFragment() {
    }

    public static PassengerFragment getInstance() {
        return new PassengerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_passenger, container, false);
        this.ctx = root.getContext();

        loadData();
        attachEvent();

        return root;
    }

    private void attachEvent() {
        GridView gridView = root.findViewById(R.id.ctn_trip_list);
    }

    private void loadData() {
        try {
            Authentication auth = new Authentication(ctx);
            User user = auth.retrieve();

            new TripDAL(root, TripDAL.CMD_PASSENGER_TRIPS).makeRequest(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

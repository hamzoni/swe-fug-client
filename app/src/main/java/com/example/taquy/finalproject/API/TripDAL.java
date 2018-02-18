package com.example.taquy.finalproject.API;

import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Misc.Tool;
import com.example.taquy.finalproject.R;
import com.example.taquy.finalproject.Views.DriverRecordAdapter;
import com.example.taquy.finalproject.Views.PassengerRecordAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TripDAL extends DAL<Trip> {

    // Command

    private View root;

    public TripDAL(View root) {
        this.root = root;
    }

    // Requests
    @Override
    public void makeRequest(Object object) {

    }

    public void listAll() throws Exception {
        new DataCaller(this).execute(SERVER_NAME);
    }

    // Responses

    @Override
    public void makeResponse(Object object) {
        ArrayList<Trip> trips = parseJsonList((ArrayList<JSONObject>) object);
        GridView gridView = root.findViewById(R.id.ctn_trip_list);
        gridView.setAdapter(new PassengerRecordAdapter(root, trips));
    }

    // Others

    public Trip parseJson (JSONObject item) {
        Trip trip = null;

        UserDAL userDAL = new UserDAL();
        try {
            trip = new Trip();

            trip.setId(item.getInt("id"));

            trip.setFrom(item.getString("from"));
            trip.setTo(item.getString("to"));
            trip.setDescription(item.getString("description"));

            JSONObject driver = item.getJSONObject("driver");
            JSONObject passenger = item.getJSONObject("passenger");
            trip.setDriver(userDAL.parseJson(driver));
            trip.setPassenger(userDAL.parseJson(passenger));

            try {
                double price = Double.parseDouble(item.getString("price"));
                trip.setPrice(price);
            } catch (Exception e) {
                Log.e(Log.ERROR + "", e.getMessage());
            }

            trip.setStatus(item.getInt("status"));
            trip.setTime(Tool.stringToDate(item.getString("time")));
        } catch (Exception e) {
            Log.e(Log.ERROR + "", e.getMessage());
        }
        return trip;
    }

    @Override
    protected ArrayList<Trip> parseJsonList(ArrayList<JSONObject> objects) {
        ArrayList<Trip> trips = new ArrayList<>();
        for (JSONObject object : objects) {
            Trip trip = parseJson(object);
            if (trip == null) continue;
            trips.add(trip);
        }
        return trips;
    }

}

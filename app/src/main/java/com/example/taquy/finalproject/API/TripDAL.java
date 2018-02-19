package com.example.taquy.finalproject.API;

import android.util.Log;
import android.widget.GridView;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Misc.Debugger;
import com.example.taquy.finalproject.Misc.Tool;
import com.example.taquy.finalproject.R;
import com.example.taquy.finalproject.Adapters.PassengerRecordAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class TripDAL extends DAL<Trip> {
    private String uri = SERVER_NAME + "trip/";

    public static final int CMD_DRIVER_TRIPS = 0;       // Filter User Owned trips
    public static final int CMD_PASSENGER_TRIPS = 1;    // Filter OPENDED trips
    public static final int CMD_ALL_TRIPS = 2;

    // Command

    public TripDAL(Object... args) {
        super(args);
    }

    private Object requestVal;
    private Object responseVal;
    // Requests
    @Override
    public void makeRequest(Object object) {
        this.requestVal = object;
        String requestUrl = "";
        String requestData = "";
        switch (cmd) {
            case CMD_ALL_TRIPS:
                requestUrl = uri;
                new Axios(this, Axios.MULTIPLE_DATA).execute(requestUrl);
                break;
            case CMD_DRIVER_TRIPS:
                requestUrl = uri + "driver";
                new Axios(this, Axios.MULTIPLE_DATA).execute(requestUrl);
                break;
            case CMD_PASSENGER_TRIPS:
                requestUrl = uri + "passenger";
                new Axios(this, Axios.MULTIPLE_DATA).execute(requestUrl);
                break;
        }
        Debugger.log(requestUrl);
    }

    // Responses

    @Override
    public void makeResponse(Object object) {
        Debugger.log(cmd);
        this.responseVal = object;
        if (object == null) return;
        switch (cmd) {
            case CMD_ALL_TRIPS:
            case CMD_DRIVER_TRIPS:
            case CMD_PASSENGER_TRIPS:
                ArrayList<JSONObject> objects = (ArrayList<JSONObject>) object;
                ArrayList<Trip> trips = parseJsonList((ArrayList<JSONObject>) object);
                GridView gridView = root.findViewById(R.id.ctn_trip_list);
                gridView.setAdapter(new PassengerRecordAdapter(root, trips));
                break;
        }
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
                e.printStackTrace();
            }

            trip.setStatus(item.getInt("status"));
            trip.setTime(Tool.stringToDate(item.getString("time")));
        } catch (Exception e) {
            e.printStackTrace();
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

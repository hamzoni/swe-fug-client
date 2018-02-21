package com.example.taquy.finalproject.API;

import android.os.Debug;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.taquy.finalproject.Adapters.DriverRecordAdapter;
import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Misc.Debugger;
import com.example.taquy.finalproject.Misc.Tool;
import com.example.taquy.finalproject.R;
import com.example.taquy.finalproject.Adapters.PassengerRecordAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TripDAL extends DAL<Trip> {
    private String uri = SERVER_NAME + "trip/";

    public static final int CMD_DRIVER_TRIPS = 0;       // Filter User Owned trips
    public static final int CMD_PASSENGER_TRIPS = 1;    // Filter OPENDED trips
    public static final int CMD_ALL_TRIPS = 2;
    public static final int CMD_UPDATE_OWNED_TRIP = 3;  // Update authenticated user trip
    public static final int CMD_CREATE_OWNED_TRIP = 4;  // Create authenticated user trip

    private static int[] authorizedAct = new int[] {
            CMD_DRIVER_TRIPS,
            CMD_UPDATE_OWNED_TRIP,
            CMD_CREATE_OWNED_TRIP
    };
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
        if (object == null) this.requestVal = "";

        String requestUrl = "";
        try {
            JSONObject json;

            if (Tool.indexOf(authorizedAct, cmd) != -1) {
                requestVal = attachAuthInfo((String) requestVal);
            }

            switch (cmd) {
                case CMD_ALL_TRIPS:
                    requestUrl = uri;
                    new Axios(this, Axios.MULTIPLE_DATA).execute(requestUrl);
                    break;

                 case CMD_DRIVER_TRIPS:
                            requestUrl = uri + "driver";
                            new Axios(this, Axios.MULTIPLE_DATA, Axios.POST).execute(requestUrl, (String) requestVal);
                            break;

                    case CMD_UPDATE_OWNED_TRIP:
                    case CMD_CREATE_OWNED_TRIP:
                            json = new JSONObject((String) requestVal);

                            Axios axios =  null;

                            switch (cmd) {
                                case CMD_UPDATE_OWNED_TRIP:
                                    requestUrl = uri + json.get("id");
                                    axios = new Axios(this, Axios.SINGLE_DATA, Axios.PUT);
                                    break;
                                case CMD_CREATE_OWNED_TRIP:
                                    requestUrl = uri ;
                                    axios = new Axios(this, Axios.SINGLE_DATA, Axios.POST);
                                    break;
                            }
                            if (axios != null) axios .execute(requestUrl, (String) requestVal);

                    break;

                case CMD_PASSENGER_TRIPS:
                    requestUrl = uri + "passenger";
                    new Axios(this, Axios.MULTIPLE_DATA).execute(requestUrl);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Responses

    @Override
    public void makeResponse(Object object) throws JSONException {

        this.responseVal = object;
        if (object == null) {
            Debugger.log("Response from server is empty");
            return;
        }

        switch (cmd) {
            case CMD_ALL_TRIPS:
            case CMD_DRIVER_TRIPS:
            case CMD_PASSENGER_TRIPS:
                ArrayList<JSONObject> objects = (ArrayList<JSONObject>) object;
                ArrayList<Trip> trips = parseJsonList((ArrayList<JSONObject>) object);
                GridView gridView = root.findViewById(R.id.ctn_trip_list);

                BaseAdapter adapter = new PassengerRecordAdapter(root, trips);
                if (cmd == CMD_DRIVER_TRIPS) adapter = new DriverRecordAdapter(root, trips);
                if (gridView == null) {
                    Debugger.log("Unable to find GridView for filling trip list");
                    return;
                }

                gridView.setAdapter(adapter);
                break;
            case CMD_UPDATE_OWNED_TRIP:
            case CMD_CREATE_OWNED_TRIP:
                JSONObject data = (JSONObject) object;

                boolean status = data.getBoolean("status");
                String message = data.getString("message");

                Toast.makeText(root.getContext(), message, Toast.LENGTH_LONG).show();

                // update gridview (trip list) after make change
                if (rootCtn != null) {
                    // view must contains gridview to run this request (gridview is needed for record listing)
                    new TripDAL(rootCtn, TripDAL.CMD_DRIVER_TRIPS).makeRequest("");
                }
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

            double price = Tool.parseDouble(item.getString("price"));
            if (price > 0) trip.setPrice(price);

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

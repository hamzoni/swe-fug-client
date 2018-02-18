package com.example.taquy.finalproject.API;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by taquy on 2/18/2018.
 */

public class DataCaller extends AsyncTask<String, Void, String> {
    private Exception exception;

    public static final int SINGLE_DATA = 0;
    public static final int MULTIPLE_DATA = 1;

    private int requestType;
    private DAL dal;

    public DataCaller(DAL dal) {
        this.dal = dal;
        this.requestType = MULTIPLE_DATA;
    }

    public DataCaller(DAL dal, int requestType) {
        this.dal = dal;
        this.requestType = requestType;
    }


    protected String doInBackground(String... urls)  {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urls[0]);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            return sb.toString();
        } catch (Exception e) {
            this.exception = e;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }

    protected void onPostExecute(String json) {
        switch (requestType) {
            case MULTIPLE_DATA:
                dal.makeResponse(parseMultiple(json));
                break;
            case SINGLE_DATA:
                dal.makeResponse(parseSingle(json));
                break;
        }

    }

    private Object parseSingle(String json) {
        try {
            return new JSONObject(json);
        } catch (Exception e) {
            Log.e(Log.ERROR + "", e.getMessage());
        }
        return null;
    }

    private Object parseMultiple(String json) {
        try {
            JSONArray list = new JSONArray(json);
            ArrayList<JSONObject> objects = new ArrayList<>();

            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                objects.add(item);
            }

            return objects;
        } catch (Exception e) {
            Log.e(Log.ERROR + "", e.getMessage());
        }
        return null;
    }
}

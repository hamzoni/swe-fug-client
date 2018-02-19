package com.example.taquy.finalproject.API;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.taquy.finalproject.Misc.Debugger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by taquy on 2/18/2018.
 */

public class Axios extends AsyncTask<String, Void, String> {
    private Exception exception;

    public static final int SINGLE_DATA = 0;
    public static final int MULTIPLE_DATA = 1;

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int PATCH = 3;
    public static final int DELETE = 4;
    public static final int OPTIONS = 5;
    private static final String[] methods = new String[] {
        "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
    };

    private int requestType = MULTIPLE_DATA;
    private int requestMethod = GET;
    private DAL dal;

    public Axios(DAL dal) {
        this.dal = dal;
    }

    public Axios(DAL dal, int requestType) {
        this.dal = dal;
        this.requestType = requestType;
    }

    public Axios(DAL dal, int requestType, int requestMethod) {
        this.dal = dal;
        this.requestType = requestType;

        if (requestMethod < 0 || requestMethod >= methods.length)
            requestMethod = 0;
        this.requestMethod = requestMethod;
    }

    protected String doInBackground(String... params)  {
        URL url = null; // here is your URL path
        HttpURLConnection conn = null;
        try {
            url = new URL(params[0]);
            Debugger.log(params[0]);
            Debugger.log(methods[requestMethod] + " " + requestMethod);

            JSONObject json = null;
            if (params.length > 1) json = new JSONObject(params[1]);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(methods[requestMethod]);
            conn.setDoInput(true);

            if (requestMethod != Axios.GET) {
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(queryBuilder(json));

                writer.flush();
                writer.close();
                os.close();
            } else {
                conn.setDoOutput(false);
            }

            conn.connect();

            int responseCode = conn.getResponseCode();
            Debugger.log("Response Code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStreamReader is = new InputStreamReader(conn.getInputStream());
                BufferedReader in = new BufferedReader(is);

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                is.close();

                Debugger.log("got here");
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
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

    private String queryBuilder(JSONObject params) throws Exception {
        if (params == null) return "";

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first) first = false;
            else result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private Object parseSingle(String json) {
        if (json == null) return null;
        try {
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object parseMultiple(String json) {
        Debugger.log(json);
        if (json == null) return null;
        try {
            ArrayList<JSONObject> items = new ArrayList<>();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

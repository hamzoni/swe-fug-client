package com.example.taquy.finalproject.API;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by taquy on 2/18/2018.
 */

public abstract class DAL<T> {
    public static String[] addresses = new String[] {
        "192.168.8.96",         // Highland Caffe Dolphin
        "192.168.100.12",       // Highland Caffe Ham Nghi
    };
    public static final String SERVER_NAME = "http://" + addresses[0] + "/api/trip/";

    protected abstract void setView(Object object);
    protected abstract T parseJson(JSONObject object);
    protected abstract ArrayList<T> parseJsonList(ArrayList<JSONObject> objects);
}

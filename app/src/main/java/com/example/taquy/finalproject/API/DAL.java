package com.example.taquy.finalproject.API;

import android.view.View;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by taquy on 2/18/2018.
 */

public abstract class DAL<T> implements DALi {
    protected View root;
    protected int cmd = -1;

    public static String[] addresses = new String[] {
            "192.168.9.121",        // Highland Caffe N4
            "192.168.100.12",       // Home
            "10.10.24.134",         // Highland Caffe Vin
            "192.168.8.96",         // Highland Caffe Dolphin
    };

    protected DAL(Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (i == 0) root = (View) args[0];
            if (i == 1) cmd = (Integer) args[1];
        }
    }


    public static final String SERVER_NAME = "http://" + addresses[0] + "/api/";
    protected abstract T parseJson(JSONObject object);
    protected abstract ArrayList<T> parseJsonList(ArrayList<JSONObject> objects);
}

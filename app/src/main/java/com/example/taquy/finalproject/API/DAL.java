package com.example.taquy.finalproject.API;

import android.view.View;

import com.example.taquy.finalproject.Entities.User;
import com.example.taquy.finalproject.Misc.Authentication;
import com.example.taquy.finalproject.Misc.Debugger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by taquy on 2/18/2018.
 */

public abstract class DAL<T> implements DALi {
    protected View root;
    // View container (where dialog is called)
    protected View rootCtn;

    protected int cmd = -1;

    public static String[] addresses = new String[] {
            "192.168.8.98",         // Highland Caffe Dolphin2
            "192.168.8.96",         // Highland Caffe Dolphin
            "192.168.100.12",       // Home
            "192.168.9.121",        // Highland Caffe N4
            "10.10.24.134",         // Highland Caffe Vin
    };

    protected DAL(Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (i == 0) root = (View) args[0];
            if (i == 1) cmd = (Integer) args[1];
            if (i == 2) rootCtn = (View) args[2];
        }
    }

    // any request that go through AuthSessionExtend must attached authenticated user data
    protected String attachAuthInfo(String requestData) throws JSONException {
        if (root == null) return null;

        JSONObject json = new JSONObject();
        if (requestData != null) {
            if (requestData.trim().length() != 0) {
                json = new JSONObject(requestData);
            }
        }

        Authentication auth = new Authentication(root.getContext());
        User user = auth.retrieve();
        json.put("login", user.getEmail());
        json.put("pwd", user.getPassword());
        return json.toString();
    }


    public static final String SERVER_NAME = "http://" + addresses[0] + "/api/";
    protected abstract T parseJson(JSONObject object);
    protected abstract ArrayList<T> parseJsonList(ArrayList<JSONObject> objects);
}

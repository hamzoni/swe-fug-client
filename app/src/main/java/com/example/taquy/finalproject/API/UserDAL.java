package com.example.taquy.finalproject.API;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.taquy.finalproject.Entities.User;
import com.example.taquy.finalproject.LoginActivity;
import com.example.taquy.finalproject.MainActivity;
import com.example.taquy.finalproject.Misc.Authentication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by taquy on 2/17/2018.
 */

public class UserDAL extends DAL<User> {

    private String uri = SERVER_NAME + "user/";

    // Commands

    public static final int CMD_LOGIN = 0;
    public static final int CMD_REGISTRATION = 1;

    public UserDAL(Object... args) {
        super(args);
    }
    // Requests

    private Object requestVal;
    private Object responseVal;
    @Override
    public void makeRequest(Object object) {
        this.requestVal = object;
        String requestUrl = "";
        String requestData = "";
        switch (cmd) {
            case CMD_LOGIN:
                requestUrl = uri + "login";
                requestData = (String) object; // stringify JSON parameters for authentication
                new Axios(this, Axios.SINGLE_DATA, Axios.POST).execute(requestUrl, requestData);
                break;
            case CMD_REGISTRATION:
                requestUrl = uri + "registry";
                requestData = (String) object; // stringify JSON parameters for registration
                new Axios(this, Axios.SINGLE_DATA, Axios.POST).execute(requestUrl, requestData);
                break;
        }
    }

    // Responses

    @Override
    public void makeResponse(Object object) {
        this.responseVal = object;
        switch (cmd) {
            case CMD_LOGIN: f_cmd_login(object); break;
            case CMD_REGISTRATION: f_cmd_registry(object); break;
        }
    }

    // Others

    private void f_cmd_registry(Object object) {
        JSONObject result = (JSONObject) object;
        Context ctx = root.getContext();
        if (result == null) {
            Toast.makeText(ctx, "Register fail", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            if ((Boolean) result.get("status")) {
                Toast.makeText(ctx, "Register success", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ctx, LoginActivity.class);
                intent.putExtra("auto-fill-data", (String) this.requestVal);
                ctx.startActivity(intent);
            } else {
                Toast.makeText(ctx, "Register fail", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void f_cmd_login(Object object) {
        User user = parseJson((JSONObject) object);
        Context ctx = root.getContext();
        if (user == null) {
            Toast.makeText(ctx, "Wrong username or password", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            JSONObject json = new JSONObject((String) this.requestVal);
            user.setPassword(json.getString("pwd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Authentication auth = new Authentication(ctx);
        auth.store(user);

        Intent intent = new Intent(ctx, MainActivity.class);
        ctx.startActivity(intent);
    }


    @Override
    protected User parseJson(JSONObject object) {
        if (object == null) return null;
        User user = null;
        try {
            user = new User();
            user.setId(object.getInt("id"));
            user.setName(object.getString("name"));
            user.setEmail(object.getString("email"));
            user.setPhone(object.getString("phone"));
            user.setBrand(object.getString("brand"));
            user.setPlate(object.getString("plate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected ArrayList<User> parseJsonList(ArrayList<JSONObject> objects) {
        return null;
    }
}

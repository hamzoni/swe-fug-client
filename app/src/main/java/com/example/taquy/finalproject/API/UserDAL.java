package com.example.taquy.finalproject.API;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.taquy.finalproject.Entities.User;
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

    public UserDAL(Object... args) {
        super(args);
    }
    // Requests

    @Override
    public void makeRequest(Object object) {
        switch (cmd) {
            case CMD_LOGIN:
                String requestUrl = uri + "login";
                String loginCredentials = (String) object; // stringify JSON parameters
                new Axios(this, Axios.SINGLE_DATA, Axios.POST).execute(requestUrl, loginCredentials);
                break;
        }
    }

    // Responses

    @Override
    public void makeResponse(Object object) {
        switch (cmd) {
            case CMD_LOGIN: f_cmd_login(object); break;
        }
    }

    // Others

    private void f_cmd_login(Object object) {
        User user = parseJson((JSONObject) object);
        Context ctx = root.getContext();
        if (user == null) {
            Toast.makeText(ctx, "Wrong username or password", Toast.LENGTH_LONG).show();
            return;
        }
        Authentication auth = new Authentication(root.getContext());
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

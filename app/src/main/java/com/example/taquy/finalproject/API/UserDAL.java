package com.example.taquy.finalproject.API;

import com.example.taquy.finalproject.Entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by taquy on 2/17/2018.
 */

public class UserDAL extends DAL<User> {

    // Commands
    private int cmd = -1;
    public static final int CMD_LOGIN = 0;

    public UserDAL(int cmd) {
        this.cmd = cmd;
    }

    // Requests

    @Override
    public void makeRequest(Object object) {
        switch (cmd) {
            case CMD_LOGIN:

                break;
        }
    }

    // Responses

    @Override
    public void makeResponse(Object object) {

    }

    // Others

    @Override
    protected User parseJson(JSONObject object) {
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
